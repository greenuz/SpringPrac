package com.greenux.blog.controller;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenux.blog.dto.ResponseDto;
import com.greenux.blog.model.KakaoProfile;
import com.greenux.blog.model.OAuthToken;
import com.greenux.blog.model.User;
import com.greenux.blog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Controller
public class UserController {
    
    // 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
    // 그냥 주소가 / 이면 index.jsp 허용
    // static 폴더 아래의 /js/, /css/, /image/ 허용


    @GetMapping("/auth/joinForm") //user폴더에 joinForm.jsp
    public String joinForm(){
        
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){

        return "user/loginForm";
    }
    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }

    //카카오 로그인 서비스 떄문에 만듦
    @Autowired
    private UserService userService;

    //카카오 로그인 처리
    @Autowired
    private AuthenticationManager authenticationManager;


    @Value("${greenux.key}")
    private String greenuxKey;

    @GetMapping("/auth/kakao/callback")
    public /*@ResponseBody*/ String kakaoCallback(String code) {//ResponseBody를 붙여주면 Data를 리턴해주는 컨트롤러 함수가 되는 것이다. 'redirect:/'' 를 리턴할 때에는 ResponseBody를 지워줘야 viewresolver가 파일을 찾아감.

        //POST 방식으로 key=value 데이터를 카카오쪽으로 요청. 이때 필요한게  RestTemplate
        //Retrofit2 요것도 가능 (안드로이드)
        //OkHttp 요것도 가능
        //HttpsURLConnection 요것도 가능
        //  등등이 있지만 RestTemplate로 사용한다.
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8"); //내가 전송 할 Http Body 데이터가 key-value 형태라고 header에 알려주는 것.
        
        //Httpbody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id", "0117d4eadcf336937bbd6a0974a54a9e");
        params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
        params.add("code",code);

        //header + body => entity(하나의 오브젝트에 담기)
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
            new HttpEntity<>(params,headers);

        //Http 요청하기 - Post 방식으로 
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);//토큰 발급 주소, Method 방식, Entity, 응답받을 형식.

        //response로 받은 json 데이터를 object로 담는다. 이때 사용하는게 Gson, Json Simple, ObjectMapper 중 ObjectMapper사용
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        System.out.println("카카오 엑세스 토큰" +oauthToken.getAccess_token());




        ///////////////////////////////////////////////////

        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8"); //내가 전송 할 Http Body 데이터가 key-value 형태라고 header에 알려주는 것.
        
        //body는 필요 없다.
        

        //header + body => entity(하나의 오브젝트에 담기)
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
            new HttpEntity<>(headers2);

        //Http 요청하기 - Post 방식으로 
        ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest2, String.class);//토큰 발급 주소, Method 방식, Entity, 응답받을 형식.

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        //user오브젝트는 id password email을 가지고 구성을 해보자. 사실 password까지는 애바이지.
        System.out.println("카카오 아이디(번호) : " +kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());

        System.out.println("블로그 유저네임 : " +kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());//중복안되게.
        System.out.println("블로그 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        // UUID garbagePassword = UUID.randomUUID();//쓰레기 패스워드 (어짜피 안씀)
        System.out.println("블로그 패스워드 : " + greenuxKey);
        //권한은 그냥 USER 


        User kakaoUser = User.builder()
            .username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
            .password(greenuxKey)
            .email(kakaoProfile.getKakao_account().getEmail())
            .oauth("kakao")
            .build();

        
        //가입자 혹은 비가입자 체크 해서 처리
        User originUser = userService.회원찾기(kakaoUser.getUsername());
        if(originUser.getUsername()== null){
            System.out.println("기존 회원이 아닙니다. 강비을 진행합니다.");
            userService.회원가입(kakaoUser);
        }
        //로그인 처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), greenuxKey));
        SecurityContextHolder.getContext().setAuthentication(authentication); //세션에 이친구가 자동으로 접근하여 등록을 해준다.


        return /*"카카오 인증 완료 코드값(스트링) :"+ code + "\n카카오 토큰 요청 완료: 토큰 요청에 대한 응답(스트링)"+ */"redirect:/";

        //scope는 프로필과 이메일이다.(개인정보 동의 목록)
    }
    
}

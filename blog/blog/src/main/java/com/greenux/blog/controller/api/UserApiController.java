package com.greenux.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import com.greenux.blog.config.auth.PrincipalDetail;
import com.greenux.blog.dto.ResponseDto;
import com.greenux.blog.model.Roletype;
import com.greenux.blog.model.User;
import com.greenux.blog.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class UserApiController {
    @Autowired //DI
    private UserService userService;

    @Autowired //DI
    private BCryptPasswordEncoder encode;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){
        System.out.println("userApiController: 호출");
        //실제 db에 인서트를 해보자. DI를 통해 받아온거 그냥 바로 넣어서 사용한다.
        // user.setRole(Roletype.USER); //user 테이블 중에 role 만 넣어준다. defalt로 안들어 가기 때문에~
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK,1);
    }


    // // @Autowired
    // // private HttpSession session; 스프링 컨테이너가 세션객체에 대해서는 Bean으로 자동으로 가지고 있다. 따라서 필요하면 이렇게 받아서 DI로 사용할 수 있다.
    // //아래의 방식은 전통방식의 로그인 방법이다. 다음엔 Spring Security 를 이용해서 로그인을 해볼 것이다.
    // @PostMapping("/api/user/login")
    // public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){ //컨트롤러에 세션 매개변수를 받을 수 있다.
    //     System.out.println("UserApiController : login호출됨");
    //     User principal = userService.로그인(user); //principal (접근 주체라고 한다.)
        
    //     if(principal != null){
    //         session.setAttribute("principal", principal);
    //     }
    //     return new ResponseDto<Integer>(HttpStatus.OK,1);
    // }

    @PutMapping("/user/infoModify")
    public ResponseDto update(@RequestBody User user/*@AuthenticationPrincipal PrincipalDetail principal, HttpSession session*/) {//key=value, x-www-form-urlencoded //json 데이터 받고 싶으면 RequestBody
        userService.회원수정(user);

        /* 아래의 코드는 더이상 사용이 불가능하네. */
        // !!회원수정 서비스가 종료된 바로 이 시점에는
        // 트랜잭션이 종료되기 때문에  DB의 값은 변경이 됐지만, 세션값은 변경되지 않은 상태이다.
        // 로그아웃을 하고 다시 들어가봐야 변경된 것을 확인 할 수 있다. 직접 세션 값을 변경해 보도록 하자.
        // // Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()); //권한까지도 필요하네.
        // // SecurityContext securityContext = SecurityContextHolder.getContext();
        // // securityContext.setAuthentication(authentication);//강제로세션값 바꾸는것
        // // session.setAttribute("SPRING_SECURITY_CONTEXT",securityContext ); //내가 세션값을 변경하는 것. 

        //세션등록

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication); //세션에 이친구가 자동으로 접근하여 등록을 해준다.
        return new ResponseDto<Integer>(HttpStatus.OK,1);
    }
}

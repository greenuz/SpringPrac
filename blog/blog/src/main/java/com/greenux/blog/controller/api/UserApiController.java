package com.greenux.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import com.greenux.blog.dto.ResponseDto;
import com.greenux.blog.model.Roletype;
import com.greenux.blog.model.User;
import com.greenux.blog.service.UserService;


@RestController
public class UserApiController {
    @Autowired //DI
    private UserService userService;

    @Autowired //DI
    private BCryptPasswordEncoder encode;

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
}

package com.greenux.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    
    @GetMapping({"/",""}) //아무것도 안붙였을 때랑, / 붙였을 때
    public String index(/**@AuthenticationPrincipal PrincipalDetail principal**/){ //시큐리티를 사옹하였을 때 컨트롤러에서 해당 시큐리티 세션을 어떻게 찾는가? 
        // /WEB-INF/views/index.jsp
        // System.out.println("로그인 사용자 아이디: " + principal.getUsername());
        return "index"; //파일 뿌려주기.
    }
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}

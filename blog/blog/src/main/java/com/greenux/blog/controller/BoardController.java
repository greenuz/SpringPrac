package com.greenux.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    
    @GetMapping({"/",""}) //아무것도 안붙였을 때랑, / 붙였을 때
    public String index(){
        // /WEB-INF/views/index.jsp
        return "index"; //파일 뿌려주기.
    }
}

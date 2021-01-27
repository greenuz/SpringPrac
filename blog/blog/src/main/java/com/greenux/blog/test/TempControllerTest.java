package com.greenux.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //데이터를 리턴할 것이 아닌 파일을 리턴할 것이다.
public class TempControllerTest {
    
    //http://localhost:8000/blog/temp/home [context path + Mapping]
    @GetMapping("/temp/home")
    public String tempHome(){
        System.out.println("tempHome()");
        // 스프링에서 파일리턴 기본 경로는 : src/main/resources/static 이 경로가 기본 경로이다.
        // 따라서 이경로 밑에 /home.html 을 리턴해라는 말이다.
        return "/home.html";
    }
    
    @GetMapping("/temp/image")
    public String tempImage(){
        System.out.println("tempImage()!!");
        return "/apple.jpg";
    }

    @GetMapping("/temp/jsp")
    public String tempJsp(){
        System.out.println("tempJsp()!!");

        //prefix :/WEB-INF/views/
        //suffix : .jsp
        return "test";
        // 최종 FULL NAME : /WEB-INF/views/test.jsp
    }
}

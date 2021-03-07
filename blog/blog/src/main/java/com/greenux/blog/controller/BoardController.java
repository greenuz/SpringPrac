package com.greenux.blog.controller;


import com.greenux.blog.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Controller
public class BoardController {
    

    @GetMapping({"/",""}) //아무것도 안붙였을 때랑, / 붙였을 때
    public String index(Model model, @PageableDefault(size=3,sort="id", direction=Sort.Direction.DESC) Pageable pageable/**@AuthenticationPrincipal PrincipalDetail principal**/){ //시큐리티를 사옹하였을 때 컨트롤러에서 해당 시큐리티 세션을 어떻게 찾는가? 
        // /WEB-INF/views/index.jsp
        // System.out.println("로그인 사용자 아이디: " + principal.getUsername());

        model.addAttribute("boards", boardService.글목록(pageable));
        return "index"; //파일 뿌려주기. 
        //@Controller이기 때문에 viewResolver 작동 -> return 값 인덱스에 prefix와 suffix(application.yml)
        //모델의 정보를 들고 index로 이동.
    }
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }

    //글목록을 작업할 때, BoardService를 연동해서 해보자. 인덱스를 작업해 준다.
    @Autowired
    private BoardService boardService;

}

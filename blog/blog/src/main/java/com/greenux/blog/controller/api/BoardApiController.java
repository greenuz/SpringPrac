package com.greenux.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.greenux.blog.config.auth.PrincipalDetail;
import com.greenux.blog.dto.ResponseDto;
import com.greenux.blog.model.Board;
import com.greenux.blog.service.BoardService;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.글쓰기(board,principal.getUser());
        //실제 db에 인서트를 해보자. DI를 통해 받아온거 그냥 바로 넣어서 사용한다.
        // user.setRole(Roletype.USER); //user 테이블 중에 role 만 넣어준다. defalt로 안들어 가기 때문에~
        return new ResponseDto<Integer>(HttpStatus.OK,1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){
        boardService.글삭제하기(id);
        return new ResponseDto<Integer>(HttpStatus.OK,1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
        boardService.글수정하기(id,board);
        
        return new ResponseDto<Integer>(HttpStatus.OK,1);
    }
}

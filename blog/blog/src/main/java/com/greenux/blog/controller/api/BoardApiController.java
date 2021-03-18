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
import com.greenux.blog.model.Reply;
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
    //원래는 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    // dto를 사용하지 않은 이유는 자그마한 프로그래램이기 때문이다. [data transfer object]
    // 내가 필요한 데이터를 한번에 받아서 어딘가에 날릴 수 있다.
    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal){

        
        boardService.댓글쓰기(principal.getUser(), boardId,reply);
        return new ResponseDto<Integer>(HttpStatus.OK,1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
        boardService.댓글삭제(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK,1);
    }
}

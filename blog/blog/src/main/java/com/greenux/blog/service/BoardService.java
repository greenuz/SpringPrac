package com.greenux.blog.service;

import com.greenux.blog.model.Board;
import com.greenux.blog.model.User;
import com.greenux.blog.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Service라는 어노테이션을 붙여야만 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다. 메모리에 대신 띄어준다.
@Service 
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    

    @Transactional //회원가입 전체의 서비스가 하나의 트랜잭션으로 묶이게 된다.전체가 성공하면 커밋, 전체에서 하나라도 실패가 뜨면 롤백.
    public void 글쓰기(Board board, User user){
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }
}

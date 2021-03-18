package com.greenux.blog.service;

import java.util.List;

import com.greenux.blog.model.Board;
import com.greenux.blog.model.Reply;
import com.greenux.blog.model.User;
import com.greenux.blog.repository.BoardRepository;
import com.greenux.blog.repository.ReplyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

//Service라는 어노테이션을 붙여야만 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다. 메모리에 대신 띄어준다.
@Service 
@RequiredArgsConstructor
public class BoardService {

    // @Autowired
    // private BoardRepository boardRepository;
    
    // @Autowired
    // private ReplyRepository replyRepository;
    // AutoWired를 아래와 같이 풀어서 쓸 수 있다.
    // // private BoardRepository boardRepository;
    // // private ReplyRepository replyRepository;
    // // public BoardService(BoardRepository bRepo, ReplyRepository rRepo){
    // //     this.boardRepository = bRepo;
    // //     this.replyRepository = rRepo;
    // // }
    // 위의 과정을 가장 심플하게 표현할 때에는 @RequiredArgsConstructor
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional //회원가입 전체의 서비스가 하나의 트랜잭션으로 묶이게 된다.전체가 성공하면 커밋, 전체에서 하나라도 실패가 뜨면 롤백.
    public void 글쓰기(Board board, User user){
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    //dummyConroller에서 페이징 해본 것을 기억해서 해보자.
    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable){
        return boardRepository.findAll(pageable); //boardRepository 가 jpaRepository를 상속한 것이기 때문에, findAll()을 가지고 있다.
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id){
        return boardRepository.findById(id)
            .orElseThrow(()->{
                return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
            });
    }

    @Transactional
    public void 글삭제하기(int id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard){
        Board board = boardRepository.findById(id)
            .orElseThrow(()->{
                return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
            }); //영속화 시키기  -> 영속성 컨텍스트에 이제 Board가 들어온 것이다. 데이터 베이스의 Board 테이블과 똑같이 동기화가 된다.
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        //해당 함수로 종료시에(service가 종료될 때 ) 트랜잭션이 종료된다. 이때 더티체킹이 일어난다. - 자동 업데이트가 db에 flush 됨.
    }

    @Transactional
    public void 댓글쓰기(User user, int boardId,Reply requestReply){
        Board board = boardRepository.findById(boardId).orElseThrow(()->{
            return new IllegalArgumentException("댓글 쓰기 실패: 게시글 아이디를 찾을 수 없습니다.");
        });//영속화하기
        requestReply.setUser(user);
        requestReply.setBoard(board);
        System.out.println(requestReply.getUser() + "     " + requestReply.getContent());

        replyRepository.save(requestReply);
    }

    @Transactional
    public void 댓글삭제(int replyId){
        replyRepository.deleteById(replyId);
    }
}
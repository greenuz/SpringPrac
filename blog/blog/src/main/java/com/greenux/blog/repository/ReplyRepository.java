package com.greenux.blog.repository;

import com.greenux.blog.model.Reply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Integer>{

    //직접 한번 만들어 보자.
    /*
    @Modifying
    @Query(value="INSERT INTO reply( userid, boardid, content, createDate) VALUES(?1,?2,?3,now())",nativeQuery=true)
    public int mSave(ReplySaveRequestDto replySaveRequestDto); //업데이트 된 행의 개수를 리턴해줌.

    */
    
}

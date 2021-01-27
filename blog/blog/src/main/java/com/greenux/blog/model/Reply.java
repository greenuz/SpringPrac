package com.greenux.blog.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GenerationType;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Reply {

    @Id //primarykey
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id; //auto_increment
    
    @Column(nullable = false, length = 200)
    private String content;

    //이 답변은 어느게시글의 답변인가 - 연관관계 필요, 하나의 게시글에는 여러개의 답변이 있을 수 있다.
    @ManyToOne //여러개의 답변은 하나의 게시글에 존재할 수 있다.
    @JoinColumn(name="boardId") // Foreign key boardId 컬럼이름 , Board의 Id값을 참조한다.
    private Board board;

    //하나의 Foreign Key 가 더 필요하다. 답변을 누가적었는지 알아야 한다. 하나의 유저는 여러개의 답변이 있을 수 있다.
    //여러개의 유저는 하나의 답변이달 수 있다(?)(X)
    
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;
}

package com.greenux.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data //gettersetter
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체 생성자.
@Builder //빌더 패턴
//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술.
@Entity //테이블화 시키기 위해서 필요한 어노테이션. 스프링부트 프로젝트가 실행 될 때, 아래애들을 읽어서 자동으로 mysql에 테이블을 생성.
//@DynamicInsert //insert 시에 null인 필드를 제외시켜준다.
public class User {
    
    @Id //id가 프라이머리키라는 것을 알려주기 위해 어노테이션 추가.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //넘버링 전략 : 프로젝트에서 연결된 DB의 넘버링 전략을 따라가겠다. 해당 IDENTITY는 오라클 DB를 사용했을 때에는 시퀀스일것이고, MYSQL 을 사용했을 때에는 auto_increment 일 것이다.
    private int id; //오라클에선 시퀀스, mysql에선 auto_increment로 가져갈 전략

    @Column(nullable = false, length = 20) // username 은 null 이 될 수 없다. length 는 20자로 세팅.
    private String username; //id 값.

    @Column(nullable = false, length = 100) //12345-> 해쉬 떠서 비밀번호 암호화 함.
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //@ColumnDefault("'user'")//문자라는 것을 알려주기 위해 default 로 'user' 로 하기 위해.
    //private String role; //현재는 String 이지만 Enum 을 쓰는게 좋다. 도메인(범위)를 줄 수 있기 때문에. [admin - user - manager]
    //DB는 해당RoleType이란 것이 없기 때문에, 아래 어노테이션 활용.
    @Enumerated(EnumType.STRING)
    private Roletype role; //ADMIN,USER

    @CreationTimestamp //시간이 자동으로 입력이 된다.
    private Timestamp createDate; //Java SQL 이 들고 있는 것. 
}

package com.greenux.blog.model;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Board {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // mariadb = auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content; //내용은 엄청 길 수 있다. 그래서 섬머노트라는 라이브러리 툴을 사용할 것이다.
    /*
        우리가 적는 일반적인 글이 디자인이 되어서 들어가게 되는데 <html> 태그가 섞여서 디자인된다.
        데이터의 용량이 커진다. 따라서 Lob 이라는 어노테이션을 주어서 -> 대용량을 처리할 수 있도록 한다.
    */

    // @ColumnDefault("0") //int 이므로  '0' 으로 표현하는 것이 아닌 
    private int count; //조회수용
  
    //private int userId; //어떤 작성자가 적었는지 확인하는 정보. ORM 에서는 해당 방법 처럼 Forign key로 하는 것이 아니라, 바로 객체 생성.
    @ManyToOne(fetch = FetchType.EAGER)//Many to one 에서 fetch 타입이 EAGER 전략이다. Board테이블을 셀렉트 하면,User 정보는 가지고 올게. 왜? 1건밖에 없으니까 열정적으로 가지고 옴.
    @JoinColumn(name="userId")
    private User user; //DB 는 오브젝트를 저장할 수 없다. FK를 원래 사용하는데, 객체 지향 프로그램에서는 오브젝트를 저장할 수 있다.
    /*
        그럼 자바랑 DB는 충돌이 날것 이다. 자바는 오브젝트를 저장했는데, DB는 오브젝트를 저장할 수 없으므로.
        따라서, 자바 프로그램에서 해당 객체를 데이터베이스 테이블에 맞추어서 자료형을 만들게 된다. @JoinColumn을 활용하여 자바 프로그램에서 진행.
        근데 연관관계를 표현을 해주어야 한다.-> 요걸 ManyToOne으로 표현 [Board= Many , User=one]
        즉, 한명의 유저는 여러개의 개시글을 쓸 수 있다.
        그럼 총체적으로, 필드값은 userId로 만들어지고, 연관관계는 ManyToOne으로 만들어 진다.
        요게 User 객체이므로 User 클래스 파일을 참조할 것이다. 그럼 자동으로 해당 필드는 Board에서 Foreign Key로 만들어진다.
        User user는 자바에서는 오브젝트이지만, 데이터베이스에 만들질 때에는 id를 참조하는 foreign key 이기 때문에 int 값으로 만들어져야 한다.

    */

    //@JoinColumn(name="replyId") 이 조인 컬럼이 필요가 없다. 
    //즉, 이 replyId ForeignKey가 필요 없다. 왜? 테이블 컬럼에 여러값이 들어가면 안됨. 1정규화(원자성 위배 될가능성 높아짐)
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)//하나의 게시글은 여러개의 댓글을 대응. 이 mappedBy는 연관관계의 주인이 아니다.(난 FK가 아니다.) DB에 컬럼을 만들지 말아라. 
    //mappedby 뒤에 오는 board는 필드이름이다. 무슨필드이름이냐면, Reply 클래스에 있는 private Board board에서 board 이다.
    //OneToMany 일 때에는, Fetchtype.EAGER 를 사용할 수 없음. 왜냐하면, Fetch가 천건 만건이 될 수 있기 떄문에.
    //따라서 기본전략이 LAZY이다.
    //그런데 생각을 해보자, Board 페이지를 열 때에, 댓글을 바로 보여줘야 하는 경우도 있다. 이럴땐 Eager 전략
    //하지만, 펼치기를 누르기 전까지 댓글을 보여줄 필요가 없다고 한다면, Lazy 전략을 택해도 되겠지.
    //즉, UI에 따라 달라지기도 한다.
    private List<Reply> reply; //하나의 게시판에 한개가 아니고 여러개의 댓글을 불러와야 하기 때문, 

    @CreationTimestamp //데이터가 인서트 될 때, 혹은 업데이트 될 때 자동으로 값이 들어가도록
    private Timestamp createDate; //

}

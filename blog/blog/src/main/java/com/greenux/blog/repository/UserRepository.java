package com.greenux.blog.repository;

import java.util.Optional;

import com.greenux.blog.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*
    해당 JpaRepository는 User 테이블이 관리하는 repository이다. 그리고, 이 User table의 primary key는 Integer(숫자)이다.
    JpaRepository는 findAll()이란 함수를 들고 있다. User테이블이 들고 있는 모든 행을 다 return 해라.
    또한 findAll()에서 PagingAndSortingRepository 가 있어서 페이징을 해서 받을 수 있고, sort해서 받을 수 있다.
    또 PagingAndSortingRepository는 CrudRepository에서 extends해서 insert 및 update를 할수 있다.(save)
    뿐만 아니라, id로 행찾기, id로 삭제하기 등등 
*/

//얘는 한마디로 JSP로 치면, DAO(Data Access Object) 라고 볼 수 있다.
//Spring legacy, Spring Boot에서는 Bean으로 등록이 되는지? -> 스프링 IOC에서 객체를 가지고 있는지. 요거는 자동으로 bean등록이 된다.
// 따라서 @Repository 라는 어노테이션을 이제는 생략할 수 있다. 예전에는 썼다고 함.
// 스프링에 얘를 알아서 띄어줄 거니까 이제 이친구를 활용만 잘 해보자.
public interface UserRepository extends JpaRepository<User,Integer>{
    //로그인을 위한 어떤 함수를 한다. JPA Naming 전략
    //JPA에서는 Naming을 아래와 같이 만들면, 실제 함수는 없더라도 자동으로 쿼리가 아래와 같이 실행
    //SELECT * FROM user WHERE username=? AND password=?
    //첫번째 물음표에는 첫번째 인수, 두번째 물음표에도 파라미터
    // User findByUsernameAndPassword(String username, String password);

    /*
        위의 함수를 아래와 같이 어노테이션을 통해 만들 수 있다.
        @Query(value="SELECT * FROM user where username = ?1 AND password = ?2", nativeQuery = true)
        User login(String username, String password);
    */
    // Select * FROM user where username = 1?; 아래와 같은 이름으로 findByUsername무조건.
    Optional<User> findByUsername(String username);
}

package com.greenux.blog.test;

import com.greenux.blog.model.Roletype;
import com.greenux.blog.model.User;
import com.greenux.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//회원가입이 잘 되었다 안되었다를 응답만 해줄 수 있기 해줘보자.
@RestController
public class DummyControllerTest {

    @Autowired //의존성 주입(DI)
    private UserRepository userRepository; 
    //스프링이 RestController롤 읽어서 DummyController를 읽어줄 때에는 NULL 일 것이다. 
    //이떄 Autowired라는 어노테이션을붙여 준다면, DummyControllerTest가 메모리에 뜰 때에, UserRepository 도 메모리에 같이 뜬다.
    //요 Autowired는 스프링이 관리하고 있는 객체중에 UserRepository 라는 타입이 있다면, 요것을 해당 userRepository 변수에 넣어서 메모리에 띄어준다.
    //스프링이 컴포넌트 스캔할 때, repository 패키지에  UserRepository 가 이미 인터페이스로 메모리에 띄어주므로 그냥 Autowired를 이용해서 사용하기만 하면된다.(의존성 주입)

    
    // http://localhost:8000/blog/dummy/join 에 요청을 하게되면,
    // http 의 body 에 username, password, email 데이터를 가지고 요청을 함.
    // @PostMapping("/dummy/join") //회원가입을 할 것이므로 insert를 해야하므로 postmapping
    // public String join(String username, String password, String email){ //key = value(약속된 규칙)
    //     //User 클래스를 보았을 때, username, password, email 만 받고 나머지는 default로 들어감.
    //     System.out.println("username :" + username);
    //     System.out.println("password :" + password);
    //     System.out.println("email:" + email);
    //     return "회원가입이 완료되었다.";
    // }
    @PostMapping("/dummy/join") //회원가입을 할 것이므로 insert를 해야하므로 postmapping
    public String join(User user){//object
        //User 클래스를 보았을 때, username, password, email 만 받고 나머지는 default로 들어감.
        System.out.println("id: :"  +user.getId());
        System.out.println("username :" + user.getUsername());
        System.out.println("password :" + user.getPassword());
        System.out.println("email:" + user.getEmail());
        System.out.println("role : " + user.getRole());
        System.out.println("createDate: " +user.getCreateDate());
        user.setRole(Roletype.USER); //user2 이런것도 만들 수 있다. 요런거 실수하지 않기 위해, enum이란것을 사용.
        userRepository.save(user); //객체를 집어넣어서 사용만 하면된다. 이미 메모리에 위에서 띄어져 있는것을 확인.
        /*
              Hibernate: 
                insert 
                into
                    User
                    (createDate, email, password, role, username) 
                values
                    (?, ?, ?, ?, ?)

        요런식으로 명령어가 들어가므로 role 값만 본다면, null이 들어가는데, 아얘 전달이 안되어야 default 값이 된다.
        이럴때에는 @DynamicInsert 어노테이션을 붙여서 null인 필드를 제외한다.
        User 클래스에 적용한다.
        하지만 이런 어노테이션을 하나하나 아는것도 팁이긴 한데, 실제적으로 할 수도 있어야지...
        따라서 주석처리 한다.그리고, user.setRole로 처리한다.
         */
        return "회원가입이 완료되었다.";
    }
}

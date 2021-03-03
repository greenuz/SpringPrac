package com.greenux.blog.service;



import com.greenux.blog.model.Roletype;
import com.greenux.blog.model.User;
import com.greenux.blog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Service라는 어노테이션을 붙여야만 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다. 메모리에 대신 띄어준다.
@Service 
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional //회원가입 전체의 서비스가 하나의 트랜잭션으로 묶이게 된다.전체가 성공하면 커밋, 전체에서 하나라도 실패가 뜨면 롤백.
    public void 회원가입(User user){
        String rawPassword = user.getPassword(); //원문
        String encPassword = encoder.encode(rawPassword);//해쉬
        user.setPassword(encPassword);
        user.setRole(Roletype.USER);
        userRepository.save(user);
        //아래의 주석은 어짜피 GlobalExceptionHandler에 의해 처리됨.
        // try {
        //     userRepository.save(user);
        //     return 1;
        // } catch(Exception e){
        //     e.printStackTrace();
        //     System.out.println("UserService: 회원가입():" + e.getMessage());
        // }
        // return -1;
    }
    // @Transactional(readOnly = true) //select만 할것이기 때문에 Trasactional 이 필요 없을 수 있지만, select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성)유지.
    // public User 로그인(User user){
    //     return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    // }
}
/*
    서비스 설명
    1. 서비스는 트랜잭션 관리를 한다. - 
    2. 서비스 의미 때문에 중요하다. -> 하나의 기능이 되어야 한다.
        예를 들어, 송금 서비스는 하나의 업데이트가 인서트가 아니라, 여러개의 묶음 작업으로 볼 수 있다. 필요시에 다 활용하는 것 기능을 만들어라.
        하나의 업데이터를 치는것을 하나의 트랜잭션이라고 본다면, 송금이 두번의 트랜잭션을 하나의 서비스로 묶어 놓았다고 한다면,
        즉, 두개의 트랜잭션은이 하나의 서비스인 경우라고 보며, rollback의 경우에도 마찬가지이다.
        여러개의 트랜잭션이 모여 하나의 서비스를 구현할 수 있다.
*/
package com.greenux.blog.config.auth;

import com.greenux.blog.repository.UserRepository;
import com.greenux.blog.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // 빈 등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    //스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데, password 부분 처리는 알아서 함.
    //password는 스프링이 따로 가져간다. 인코딩을 해야하기 떄문에.
    // username이 DB에 있는지만 확인해서 리턴해주면 됨.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
            .orElseThrow(()->{
                return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.: "+username);
            });
        return new PrincipalDetail(principal); //이 때 시큐리티의 세션에 유저 정보가 저장된다.
    }
    
}

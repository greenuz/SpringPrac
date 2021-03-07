package com.greenux.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import com.greenux.blog.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class PrincipalDetail implements UserDetails {
    private User user; // 컴포지션 (객체를 품고 있는 것)

    public PrincipalDetail(User user){
        this.user = user;
    }
    //스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를 스프링 시큐리티 고유한 세션 저장소에 저장을 한다.

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getUsername();
    }
    //계정이 만료되지 않았는지 리턴한다.(true: 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    //꼐정이 잠겨있지 않았는지 리턴(true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    //비밀번호가 만료되지 않았는지 리턴한다.(true: 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    //계정 활성화가 되어있는지(true: 활성화)
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    //계정 권한을 리턴. 리턴타입이 까다로움.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(new GrantedAuthority(){

			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return "ROLE_"+user.getRole();//스프링 시큐리티 에서는 ROLE_ 이라는 prefix가 필수적으로 있어야함.
			}
            
        });
       //람다식 표현 collectors.add(()->{return "ROLE_"+user.getRole();})
        return collectors;
    }
    
}

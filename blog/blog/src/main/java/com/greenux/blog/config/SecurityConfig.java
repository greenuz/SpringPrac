package com.greenux.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
//이 시큐리티가 모든 요청을 가로챔
// 컨트롤러로 가서 실행이 되기전에, 얘가 동작을 해서 /auth/**로 들어오는 것은 허용하고, 그게 아닌 것들은 인증이 필요하다고 필터링함. -> EnableWebSecurtiy
@Configuration //빈등록(IoC 관리)
@EnableWebSecurity //시큐리티 필터 추가 = 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 여기 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Bean //IoC가 된다. BCryptPasswordEncoder()를 스프링이 관리를 한다.
    public BCryptPasswordEncoder encodePWD(){
        String encPassword = new BCryptPasswordEncoder().encode("1234");
        return new BCryptPasswordEncoder();    
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() //csrf 토큰 비활성화(테스트 시 걸어두는 게 좋음)
            .authorizeRequests()//어떤 요청이 들어오면, 어떤작업을 하겠다.
                .antMatchers("/auth/**","/js/**", "/css/**","/image/**","/")//auth/login, /auth/joinForm 요런 것들은 누구나 들어올 수 있다. /js 밑의 파일들 접근할 때. 마지막에 / 메인페이지도.
                .permitAll()
                .anyRequest()//이게 아닌 다른 모든 요청은 
                .authenticated()//인증이 되어야 함
            .and()
                .formLogin()
                .loginPage("/auth/loginForm");//인증이 필요한 곳에 요청이 필요하면 다 이페이지로 매핑
    }
}

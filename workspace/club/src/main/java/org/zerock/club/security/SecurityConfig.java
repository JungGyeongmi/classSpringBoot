package org.zerock.club.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.club.security.handler.CustomAccessDeniedHandler;
import org.zerock.club.security.handler.CustomLogoutSuccessHandler;
import org.zerock.club.security.handler.CustomLoginSuccessHandler;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CustomAccessDeniedHandler cDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    CustomLoginSuccessHandler loginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    CustomLogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }


    /* 
        security를 적용하기 위한 url에 대한 설정과 로그인과 
        access denied되는 경우에
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 어느 페이지에 어떤 권한을 줄 것인가에 대한 것
        http.authorizeHttpRequests()
        .antMatchers("/sample/all").permitAll()
        .antMatchers("/sample/member")
        .hasRole("USER"); // 여기서 ADMIN으로 바꾸면 로그인 할 수 있는 대상이 다르게 제한됨
        
        http.exceptionHandling().accessDeniedHandler(cDeniedHandler());
        
        //1. Security login form 사용
        // http.formLogin();

        /*2. 사용자 정의에 의한 loginprocessingurl 사용
         http.formLogin().loginPage("파일경로").loginProcessingUrl("매핑경로");
         member에 있는 login */
        // http.formLogin().loginPage("/member/login"); 
        // http.csrf().disable();
        /* csrf 없이 동작 x */

        /* 3. UserDetailsService사용 : security가 들고있는 '/login' */
        http.formLogin().loginPage("/member/login")
            .loginProcessingUrl("/login")
            .successHandler(new CustomLoginSuccessHandler());

        http.logout() // 로그아웃 시 이루어질 동작 분기
        .logoutSuccessHandler(logoutSuccessHandler());
        //.logoutUrl("/member/logout").logoutSuccessURL("/member/login");
        
        http.oauth2Login();
    }

    /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         // inMemory 방식, 사용자의컴퓨터에 저장??? <-> DB에서 데이터들고오기
        auth.inMemoryAuthentication()
         .withUser("user1")
         .password("$2a$10$JVJC1geWkmjTSHOy5Ch8ZeBovWB3kJHnW.v7.1jDU/v52NXXzN4uu")
         .roles("USER");
     }*/
    
}

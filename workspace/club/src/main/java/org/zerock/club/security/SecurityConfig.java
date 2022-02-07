package org.zerock.club.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    /* 
        security를 적용하기 위한 url에 대한 설정과 로그인과 
        access denied 때
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().antMatchers("/sample/all").permitAll()
        .antMatchers("/sample/member").hasRole("USER");
        http.exceptionHandling().accessDeniedHandler(cDeniedHandler());
        // http.formLogin();
        // http.formLogin().loginPage("파일경로").loginProcessingUrl("매핑경로");
        http.formLogin().loginPage("/member/login").loginProcessingUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("user1")
        .password("$2a$10$JVJC1geWkmjTSHOy5Ch8ZeBovWB3kJHnW.v7.1jDU/v52NXXzN4uu")
        .roles("USER");
    }
    
}

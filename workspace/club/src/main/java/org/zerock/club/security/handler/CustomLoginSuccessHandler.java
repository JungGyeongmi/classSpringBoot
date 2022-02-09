package org.zerock.club.security.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication auth) 
            throws IOException, ServletException {
       
                log.warn("Login Success");
                List<String> roleNames = new ArrayList<>();

                auth.getAuthorities().forEach(authority -> {
                    roleNames.add(authority.getAuthority());
                });
                
                // 로그인 하는 경우 권한 있는 페이지로 넘어가게 설정함
                log.warn("ROLE NAMES : "+roleNames);
                if(roleNames.contains("ROLE_ADMIN")){
                    // 응답아닌가?
                    response.sendRedirect(request.getContextPath()+"/sample/admin");
                    return;
                }
                
                if(roleNames.contains("ROLE_MEMBER")){
                    // 응답아닌가?
                    response.sendRedirect(request.getContextPath()+"/sample/member");
                    return;
                }

                if(roleNames.contains("ROLE_USER")){
                    // 응답아닌가?
                    response.sendRedirect(request.getContextPath()+"/sample/all");
                    return;
                }

                response.sendRedirect("/");
    }
    
}

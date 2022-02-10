package org.zerock.club.security.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.club.security.dto.ClubAuthMemberDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{
   
    private RedirectStrategy redirect = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;
    public CustomLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /*
        onAuthenticationSuccess 
        주체는 소셜
        response는 응답
        request는 요청 
    */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication auth) 
            throws IOException, ServletException {
       
                log.warn("Login Success");
                List<String> roleNames = new ArrayList<>();
                

                ClubAuthMemberDTO clubAuthMemberDTO = (ClubAuthMemberDTO) auth.getPrincipal();
                boolean fromSocial = clubAuthMemberDTO.isFromSocial();
                log.info("Need Modify Member? "+fromSocial);
                boolean passwordResult = passwordEncoder.matches("1", clubAuthMemberDTO.getPassword());

                log.info("dd"+passwordResult);
                log.info(fromSocial);
                if(fromSocial && passwordResult){
                    /* 
                        redirectStrategy를 이용하면 request와 response를 계속 활용 할 수 있다.
                        fromSocial이 true고 password가 아직 1이면 신규가입자다.
                        그럼 이 사람에게 자기 정보를 바꾸라고 redirect url로 보내려고 하는것
                        response를 하려면 그냥 주소만 이동되고 넘어가는게 없음
                        이렇게 하면 일일이 response 다시 설정하고 일일이 해줘야함
                        이 신규 가입자가 request url을 통해 그 request객체를 처리하고
                        다시 requestResponse를 이용해서 해당 페이지로 넘어가야함
                        훨씬 더 이전부터 쓰이는 객체인 redirectStrategy라는 객체는
                        파라미터의 해당 url로 그대로 넘겨줌 
                    */
                    redirect.sendRedirect(request, response, "/member/modify?from=social");
                    return;
                    // 그냥 from의 social이라는 문자를 넘김
                }

                auth.getAuthorities().forEach(authority -> {
                    roleNames.add(authority.getAuthority());
                });
                
                /* 로그인 하는 경우 권한 있는 페이지로 넘어가게 설정함 */
                log.warn("ROLE NAMES : "+roleNames);
                if(roleNames.contains("ROLE_ADMIN")){
                    response.sendRedirect(request.getContextPath()+"/sample/admin");
                    return;
                }
                
                if(roleNames.contains("ROLE_MEMBER")){
                    response.sendRedirect(request.getContextPath()+"/sample/member");
                    return;
                }

                if(roleNames.contains("ROLE_USER")){
                    response.sendRedirect(request.getContextPath()+"/sample/all");
                    return;
                }

                response.sendRedirect("/");
    }
    
}

package org.zerock.club.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{
    
    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean loginCheck(String username, String password) {
        
        log.info("loginCheck.....");
        boolean passResult = false;
        passResult = passwordEncoder.matches(password, "1");
        
        boolean usernameResult = username.equals("user1")?true:false;
        return passResult && usernameResult;
    }

}

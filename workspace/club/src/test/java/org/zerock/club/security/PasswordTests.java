package org.zerock.club.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest

public class PasswordTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void testPasswordEncoder() {
        String pass = "1";
        String enPw = passwordEncoder.encode(pass);
        
        System.out.println("enPw : "+enPw);

        boolean matchResult = passwordEncoder.matches(pass, enPw);
        
        System.out.println("matchResult : "+matchResult);

    }
}

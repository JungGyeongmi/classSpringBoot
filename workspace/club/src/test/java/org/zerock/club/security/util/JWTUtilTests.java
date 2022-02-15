package org.zerock.club.security.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class JWTUtilTests {
    
    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefor(){
        System.out.println("testBefore_____________");
        jwtUtil = new JWTUtil();
    }

    @Test
    public void testEncode() throws Exception {
        String email = "user1@zerock.org";
        String str = jwtUtil.generateToken(email);
        System.out.println(str);
    }


    @Test
    public void testValidate() throws Exception {
        String email = "user1@zerock.org";
        String str = jwtUtil.generateToken(email);
        Thread.sleep(5000);
        String resultEmail = jwtUtil.validateAndExtract(str);
        System.out.println(resultEmail);
    }

}


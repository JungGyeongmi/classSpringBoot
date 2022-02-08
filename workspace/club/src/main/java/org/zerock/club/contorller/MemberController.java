package org.zerock.club.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.club.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    MemberService memberService;

    @GetMapping("/login")
    public void login(){
        
        log.info("login get....");
    }

    @PostMapping("/login")
    public String loginForm(String username, String password){
        String resultUrl = "";
        log.info("login get....");
        boolean logincheck = memberService.loginCheck(username, password);
        log.info("logincheck: " + logincheck);
        resultUrl = logincheck?"redirect:/":"redirect:/member/login?error=fail";
        log.info("result URL  >> "+resultUrl);
        return resultUrl;
    }
}

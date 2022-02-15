package org.zerock.club.contorller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.club.security.dto.ClubAuthMemberDTO;
import org.zerock.club.security.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/sample")
public class SampleController {
    
    private final JWTUtil jwtUtil;
    
    /*
    @PreAuthorize("isAuthenticated")
    @PreAuthorize("permitAll")
    */
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('USER')")
    @GetMapping("/all")
    public void all() {
        log.info("all.........");
    }

    @GetMapping("/member")
    public void member(@AuthenticationPrincipal ClubAuthMemberDTO authDTO) {
        log.info("member.........");
        log.info("ClubAuthMemberDTO: "+authDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void admin() {
        log.info("admin......");
    }

    @PreAuthorize("#clubAuthMemberDTO != null && #clubAuthMemberDTO.username eq \"gm950715@gmail.com\" ")
    @GetMapping("/exOnly")
    public void exOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("exOnly....clubAuth MemberDTO: " + clubAuthMemberDTO);
    }

    @GetMapping("/notes")
    public void notes(String email, Model model) throws Exception {
        model.addAttribute("jwtValue", jwtUtil.generateToken(email));
    }
}

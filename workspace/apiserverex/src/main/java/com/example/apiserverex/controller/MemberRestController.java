// package com.example.apiserverex.controller;

// import com.example.apiserverex.dto.CommonMemberDTO;
// import com.example.apiserverex.service.MemberService;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.log4j.Log4j2;

// @RestController
// @Log4j2
// @RequestMapping("/test")
// @RequiredArgsConstructor
// public class MemberRestController {
//     public final MemberService memberService;
//     /* 
//     json형태의 값 + http의 status를 같이 전달하기 때문에
//     반환값은 ResponseEntity로 받는다 

//     response와 request의 주체는 서버가 된다
//     */
//     @PostMapping("/memberRegister")
//     public ResponseEntity<Long> regiser(@RequestBody CommonMemberDTO dto) {
        
//         log.info("rest/memberRegister...Common"+dto);
        
//         Long num = memberService.restRegister();
        
//         return ;
//     }
// }

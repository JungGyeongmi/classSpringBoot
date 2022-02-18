package org.zerock.mybatis.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.mybatis.service.MemberService;
import org.zerock.mybatis.vo.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class MemberController {
    
    private final MemberService memberService;

    @GetMapping("list")
    public void getList(Model model) {
        List<MemberVO> list = memberService.getList();
        model.addAttribute("list", list);
        log.info("list: " + list);
        model.addAttribute("mem", memberService.getMember(10L));
    }
}

package zerock.org.bimovie2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.bimovie2.dto.CommonMemberDTO;
import org.zerock.bimovie2.security.dto.CommonAuthMemberDTO;
import org.zerock.bimovie2.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("login")
  public void login() {
    log.info("login get...");
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/detail")
  public void all(Model model,@AuthenticationPrincipal CommonAuthMemberDTO cAuthMemberDTO) {
    log.info("detail........");
    model.addAttribute("auth", cAuthMemberDTO);
  }

  @GetMapping("modify")
  public void modify(@AuthenticationPrincipal CommonAuthMemberDTO cAuthMemberDTO, Model model) {
    model.addAttribute("auth", cAuthMemberDTO);
    List<String> roleNames = new ArrayList<>();
    cAuthMemberDTO.getAuthorities().forEach(authority -> {
      roleNames.add(authority.getAuthority());
    });
    model.addAttribute("roleNames", roleNames);
  }

  @PostMapping("modify")
  public String modifyForm(CommonMemberDTO cMemberDTO, Model model) {
    String result = "redirect:/member/detail";
    log.info("CommonMemberDTO:" + cMemberDTO);
    memberService.updateCommonMemberDTO(cMemberDTO);
    return result;
  }
}

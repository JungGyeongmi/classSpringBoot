package org.zerock.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("guestbook")
@RequiredArgsConstructor
public class GuestbookController {
    
    private final GuestbookService service; // GuestbookService는 인터페이스 인터페이스를 상속받은 것을 모두 받을 수 있음
    // class와 class사이의 관계를 느슨하게 연결 가능(독립성 보장)

    @GetMapping({"","/","list"})
    public String list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list.............");
        model.addAttribute("result",service.getList(pageRequestDTO));
        return "/guestbook/list";
    }

    @GetMapping("/register") // register 등록
    public void register(){ log.info("register.........get");}

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes ra){ // redirect가 없어도 쓸 수 는 있다
        log.info("dto..."+dto);
        Long gno = service.register(dto);
        ra.addFlashAttribute("msg",gno+" 등록");

        return "redirect:/guestbook/list"; // redirect는 controller를 다시 타는 것이다 // 그럼 해당 페이지에있는 controller만 가능?
    }
                                
    @GetMapping({"/read", "/modify"})        // read.html에서 requestDTO로(이름을) 쓸 수 있음
    public void read(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Long gno, Model model) {
        /*커맨드 객체 정리필요
        커맨드 객체는 클라이언트로부터 매개변수를 객체이름으로
        받을 수 있고 다음 페이지에 커맨드 객체를 넘겨서 사용가능하다.
        여기서 커맨드 객체는 RequestDTO가 된다.*/
        log.info("read...................gno"+gno);
        GuestbookDTO dto = service.read(gno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes ra) {
        log.info("remove..........gno:"+gno);
        service.remove(gno);
        ra.addFlashAttribute("msg", gno+" 삭제");
        return "redirect:/guestbook/";
    }

    @PostMapping("/modify")                           // command 객체로 그냥 넘어감 쓰지 않아도 자동으로
    public String modify(GuestbookDTO dto, RedirectAttributes ra, PageRequestDTO requestDTO) {
        log.info("remove..........gno:"+dto.getGno()+" 수정");
        service.modify(dto);
        ra.addAttribute("page", requestDTO.getPage());
        ra.addAttribute("gno", dto.getGno());
        ra.addFlashAttribute("msg", dto.getGno()+" 수정");
        return "redirect:/guestbook/list";
    }

}
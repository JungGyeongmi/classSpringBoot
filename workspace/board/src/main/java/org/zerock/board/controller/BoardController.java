package org.zerock.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService; // 순환참조를 막기위해서 상수로 정의

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
      
        log.info("list............"+pageRequestDTO);
        model.addAttribute("result", boardService.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register(){
        log.info("register.....");
    }

    @PostMapping("/register")
    public String register (BoardDTO boardDTO, RedirectAttributes ra) {
        log.info("register...post...dto :"+boardDTO);
        Long bno = boardService.register(boardDTO);
        log.info("BNO"+bno);
        ra.addFlashAttribute("msg", bno+" 등록");

        return "redirect:/board/list";
    }
    
    @GetMapping({ "/read", "/modify" })
    public void read(PageRequestDTO pageRequestDTO, Long bno, Model model) {
        log.info("read......bno >>" + bno);

        BoardDTO boardDTO = boardService.get(bno);
        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes ra, PageRequestDTO pageRequestDTO) {
        log.info("remove..........bno:"+bno);
        boardService.removeWithReplies(bno);

        // 해당 페이지에 내용물이 없으면 이전 페이지로 당김
        if(boardService.getList(pageRequestDTO).getDtoList().size()==0 &&
        pageRequestDTO.getPage()!=1) {
            pageRequestDTO.setPage(pageRequestDTO.getPage()-1);
        };

        ra.addAttribute("page", pageRequestDTO.getPage());
        ra.addFlashAttribute("msg", bno+" 삭제");
        ra.addAttribute("keyword", pageRequestDTO.getKeyword());
        ra.addAttribute("type", pageRequestDTO.getType());
        return "redirect:/board/list";
    }

    @PostMapping("/modify")                           
    // command 객체로 그냥 넘어감 쓰지 않아도 자동으로
    public String modify(BoardDTO dto, RedirectAttributes ra, PageRequestDTO pageRequestDTO) {
        log.info("modify..........gno:"+dto.getBno()+" 수정");
        boardService.modify(dto);

        ra.addAttribute("page", pageRequestDTO.getPage());
        ra.addAttribute("keyword", pageRequestDTO.getKeyword());
        ra.addAttribute("type", pageRequestDTO.getType());
        ra.addAttribute("bno", dto.getBno());
        
        ra.addFlashAttribute("msg", dto.getBno()+" 수정");
        return "redirect:/board/read";
    }
}
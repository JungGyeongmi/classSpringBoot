package org.zerock.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

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
}
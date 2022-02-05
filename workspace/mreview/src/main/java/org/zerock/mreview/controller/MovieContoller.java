package org.zerock.mreview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.MovieImageDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieContoller {

    private final MovieService movieService; // final

    @GetMapping("/register")
    public void register() {
    }

    @PostMapping("register")
    public String register(MovieDTO movieDTO, RedirectAttributes ra) {
        log.info("movieDTO : " + movieDTO);
        Long mno = movieService.register(movieDTO);
        ra.addFlashAttribute("msg", mno+" 등록");
        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    @GetMapping({"/read","/modify"})
    public void read(Long mno, PageRequestDTO pageRequestDTO, Model model) {

        log.info("pageRequestDTO: " + pageRequestDTO);     
        MovieDTO movieDTO = movieService.getMovie(mno);
        model.addAttribute("dto", movieDTO);
    }

    @PostMapping("/modify")
    public String modify(MovieDTO movieDTO, MovieImageDTO imageDTO, PageRequestDTO pageRequestDTO, RedirectAttributes ra) {
        // 수정
        movieService.modify(movieDTO, imageDTO);
        
        ra.addAttribute("mno", movieDTO.getMno());
        ra.addAttribute("page", pageRequestDTO.getPage());
        ra.addAttribute("keyword", pageRequestDTO.getKeyword());
        ra.addAttribute("type", pageRequestDTO.getType());
        
        // log.info("movieDTO : " + movieDTO);
        Long mno = movieDTO.getMno();
        ra.addFlashAttribute("msg", mno+" 수정");
        
        // log.info("테스트 => "+movieDTO.getTitle());

        return "redirect:/movie/read";
    }

    @PostMapping("/remove")
    public String remove(long mno, RedirectAttributes ra, PageRequestDTO pageRequestDTO) {
        log.info("remove..........mno:"+mno);
        movieService.removeWithReviews(mno);

        // 해당 페이지에 내용물이 없으면 이전 페이지로 당김
        if(movieService.getList(pageRequestDTO).getDtoList().size()==0 &&
        pageRequestDTO.getPage()!=1) {
            pageRequestDTO.setPage(pageRequestDTO.getPage()-1);
            };

        ra.addAttribute("page", pageRequestDTO.getPage());
        ra.addFlashAttribute("msg", mno+" 삭제");
        ra.addAttribute("keyword", pageRequestDTO.getKeyword());
        ra.addAttribute("type", pageRequestDTO.getType());
        return "redirect:/movie/list";
    }

}
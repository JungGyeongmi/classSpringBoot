package org.zerock.ex3.contoller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex3.dto.SampleDTO;

import lombok.extern.log4j.Log4j2;

// 요청된 메서드의 이름은 unique 해야한다
// sample에서 가져오겠다는 의미
@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {
    @GetMapping("ex1")
    public void ex1() {
        log.info("ex1...........................");
    }

    @GetMapping({ "ex2", "exLink" })
    public void ex2(Model model) {
        log.info("ex2...........................");
        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i -> {
            SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("First..." + i)
                    .last("Last..." + i)
                    .regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());
        model.addAttribute("list", list);
    }

    @GetMapping("ex3")
    public void ex3() {
        log.info("ex3...........................");
    }

    @GetMapping("exInline")
    public String exInline(RedirectAttributes ra) {
        log.info("exInline...........................");
        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First..100")
                .last("Last..100")
                .regTime(LocalDateTime.now())
                .build();
        ra.addFlashAttribute("result", "success");
        ra.addFlashAttribute("dto", dto);
        return "redirect:/sample/ex3";
    }

    @GetMapping({ "exLayout1", "exLayout2", "exTemplate,", "onetruelayout", "exSidebar" })
    public void exLayout() {
        log.info("exLayout...........................");
    }
}

package org.zerock.moviefileupload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class UploadControllerTest {
    
    @GetMapping("uploadEx")
    public void uplaodEx() { // 요청된 resource그대로 들여옴

    }
}

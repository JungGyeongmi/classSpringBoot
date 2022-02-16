package org.zerock.bimovie.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class CommonController {
    @RequestMapping({"/", ""})
    public String home() {
        return "index";
    }


    @GetMapping("/accessError")
    public void accessError(Authentication auth, Model model){
        log.info("access denied : " +auth);
        model.addAttribute("msg","Access Denied");
    }

}
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
  
  @GetMapping(value = {"", "/"})
  public String home(Model model) {
    model.addAttribute("myName", "정경미");
    return "index";
  }
}
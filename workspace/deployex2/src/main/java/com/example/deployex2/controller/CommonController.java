package com.example.deployex2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class CommonController {
  
  @RequestMapping(value = {"","/"})
  public String home(){
    return "index";
  }

  @GetMapping("/hello")
  public @ResponseBody String getIndex(){
    log.info("hello..............");
    return "hello";
  }
}
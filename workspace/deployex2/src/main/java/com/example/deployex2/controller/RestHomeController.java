package com.example.deployex2.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestHomeController {

   @GetMapping(value="/api/test", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public Map<String, Object> test(){
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("status", 200);
      return map;
   }
}
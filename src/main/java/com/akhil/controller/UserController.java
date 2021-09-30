package com.akhil.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @GetMapping(path = "/{userId}")
    public void getUser(@PathVariable String userId){
      log.info("GEt user was called " + userId);
    }
}

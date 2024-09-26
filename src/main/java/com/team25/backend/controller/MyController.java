package com.team25.backend.controller;

import com.team25.backend.annotation.LoginUser;
import com.team25.backend.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MyController {
    @GetMapping("/my")
    @ResponseBody
    public String myAPI(@LoginUser User user) {
        log.info("user = {}", user.getUsername());
        return "my route";
    }
}
package com.team25.backend.controller;

import com.team25.backend.annotation.LoginUser;
import com.team25.backend.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Controller
public class MyController {
    @GetMapping("/my")
    @ResponseBody
    public String myAPI(@LoginUser User user){
        log.info("uuid: "+user.getUuid());
        return "my route";
    }
}

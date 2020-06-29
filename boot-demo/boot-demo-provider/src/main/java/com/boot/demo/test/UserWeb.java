package com.boot.demo.test;

import com.boot.demo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWeb {
    @Autowired UserService S;

    @RequestMapping(value = "/test")
    public void test(){ S.say();}
}

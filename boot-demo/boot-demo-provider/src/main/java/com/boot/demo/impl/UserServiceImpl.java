package com.boot.demo.impl;

import com.boot.demo.UserService;
import org.apache.dubbo.config.annotation.Service;

import java.util.Date;

@Service(version = "1.0.0", interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public void say() {
        System.out.println("say hello : " + new Date());
    }
}

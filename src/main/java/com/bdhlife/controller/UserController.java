package com.bdhlife.controller;

import com.bdhlife.entity.AdminUser;
import com.bdhlife.service.UserService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/adminUser")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(String username,String password){
        AdminUser adminUser=userService.login(username,password);
        if (adminUser == null){
            return Result.build(500,"账号或密码错误");
        }
        return Result.build(200,"登陆成功");
    }

}

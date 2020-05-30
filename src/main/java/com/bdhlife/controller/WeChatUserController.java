package com.bdhlife.controller;

import com.bdhlife.service.WeChatUserService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WeChatUserController {

    @Autowired
    private WeChatUserService weChatUserService;

    public Result weChatUserLogin(String code){
        String sendGet=weChatUserService.weChatUserLogin(code); //根据code去调用接口获取用户openid和session_key
        return null;
    }

}

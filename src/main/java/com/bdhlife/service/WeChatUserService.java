package com.bdhlife.service;

import com.bdhlife.entity.WeChatUser;

import java.util.List;

public interface WeChatUserService {

    int weChatUserLogin(WeChatUser user);

    WeChatUser findUserByOpenId(String openId);

    List<WeChatUser> findUserList();

}

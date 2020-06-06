package com.bdhlife.service.impl;

import com.bdhlife.entity.WeChatUser;
import com.bdhlife.mapper.WeChatUserMapper;
import com.bdhlife.service.WeChatUserService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WeChatUserServiceImpl implements WeChatUserService {

    @Autowired
    private WeChatUserMapper weChatUserMapper;


    @Override
    public int weChatUserLogin(WeChatUser user) {
        return weChatUserMapper.weChatUserLogin(user);
    }

    @Override
    public WeChatUser findUserByOpenId(String openId) {
        return weChatUserMapper.findUserByOpenId(openId);
    }

    @Override
    public List<WeChatUser> findUserList() {
        return weChatUserMapper.findUserList();
    }
}

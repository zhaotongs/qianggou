package com.bdhlife.service.impl;

import com.bdhlife.mapper.WeChatUserMapper;
import com.bdhlife.service.WeChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WeChatUserServiceImpl implements WeChatUserService {

    @Autowired
    private WeChatUserMapper weChatUserMapper;

    @Override
    public String weChatUserLogin(String code) {
        return null;
    }
}

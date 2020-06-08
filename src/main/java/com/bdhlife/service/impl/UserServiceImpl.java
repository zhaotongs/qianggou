package com.bdhlife.service.impl;

import com.bdhlife.entity.AdminUser;
import com.bdhlife.mapper.UserMapper;
import com.bdhlife.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public AdminUser login(String username, String password) {
        return userMapper.login(username,password);
    }
}

package com.bdhlife.service;

import com.bdhlife.entity.AdminUser;

public interface UserService {
    AdminUser login(String username, String password);
}

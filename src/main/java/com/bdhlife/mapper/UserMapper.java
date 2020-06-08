package com.bdhlife.mapper;

import com.bdhlife.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    AdminUser login(@Param("username") String username,@Param("password") String password);
}

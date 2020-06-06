package com.bdhlife.mapper;

import com.bdhlife.entity.WeChatUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeChatUserMapper {

    int weChatUserLogin(WeChatUser user);
}

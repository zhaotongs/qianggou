package com.bdhlife.mapper;

import com.bdhlife.entity.WeChatUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeChatUserMapper {

    int weChatUserLogin(WeChatUser user);

    WeChatUser findUserByOpenId(String openId);

    List<WeChatUser> findUserList();

}

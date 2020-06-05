package com.bdhlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class WeChatUser {

    // openId,标识该公众号下面的该用户的唯一Id
    private String openId;
    // 用户昵称
    private String nickName;
    // 性别
    private int sex;
    // 省份
    private String province;
    // 城市
    private String city;
    // 区
    private String country;
    // 头像图片地址
    private String headimgurl;
    // 语言
    private String language;
    // 用户权限，这里没什么作用
    private String[] privilege;


}

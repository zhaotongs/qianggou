package com.bdhlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class UserAccessToken {

    // 获取到的凭证
    private String accessToken;
    // 凭证有效时间，单位：秒
    private String expiresIn;
    // 表示更新令牌，用来获取下一次的访问令牌，这里没太大用处
    private String refreshToken;
    // 该用户在此公众号下的身份标识，对于此微信号具有唯一性
    private String openId;
    // 表示权限范围，这里可省略
    private String scope;


}

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
    private String sex;
    // 省份
    private String province;
    // 城市
    private String city;
    // 区
    private String country;
    // 头像图片地址
    private String headimgurl;
    //用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）,这里没什么作用
    private String[] privilege;
    //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String unionId;


}

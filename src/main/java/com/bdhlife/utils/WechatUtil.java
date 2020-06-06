package com.bdhlife.utils;

import com.bdhlife.entity.UserAccessToken;
import com.bdhlife.entity.WeChatUser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * 微信工具类
 *
 * @author xiangze
 *
 */
public class WechatUtil {

    private static Logger log = LoggerFactory.getLogger(WechatUtil.class);

    /**
     * 获取UserAccessToken实体类
     *
     * @param code
     * @return
     * @throws IOException
     */
    public static UserAccessToken getUserAccessToken(String code,String appId,String appsecret) {
        log.debug("appId:" + appId);
        log.debug("secret:" + appsecret);
        // 根据传入的code,拼接出访问微信定义好的接口的URL
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret
                + "&code=" + code + "&grant_type=authorization_code";
        // 向相应URL发送请求获取token json字符串
        JSONObject jsonToken =new JSONObject( HttpClientUtil.doGet(url));
        log.debug("userAccessToken:" + jsonToken);
        UserAccessToken token = new UserAccessToken();
        token.setAccessToken(jsonToken.get("access_token").toString());
        token.setExpiresIn(jsonToken.get("expires_in").toString());
        token.setRefreshToken(jsonToken.get("refresh_token").toString());
        token.setOpenId(jsonToken.get("openid").toString());
        token.setScope(jsonToken.get("scope").toString());
        log.debug("token:" + token);
        return token;
    }

    /**
     * 获取WechatUser实体类
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public static WeChatUser getUserInfo(String accessToken, String openId) {
        // 根据传入的accessToken以及openId拼接出访问微信定义的端口并获取用户信息的URL
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
                + "&lang=zh_CN";
        // 访问该URL获取用户信息json 字符串
        JSONObject json=new JSONObject(HttpClientUtil.doGet(url));
        log.debug("user info :" + json);
        WeChatUser user = new WeChatUser();
        user.setOpenId(json.get("openid").toString());
        user.setNickName(json.get("nickname").toString());
        user.setSex(json.get("sex").toString());
        user.setProvince(json.get("province").toString());
        user.setCity(json.get("city").toString());
        user.setCountry(json.get("country").toString());
        user.setHeadimgurl(json.get("headimgurl").toString());
        return user;
    }
}
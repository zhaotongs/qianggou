package com.bdhlife.utils;

import com.bdhlife.entity.UserAccessToken;
import com.bdhlife.entity.WeChatUser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public static UserAccessToken getUserAccessToken(String code,String appId,String appsecret) throws IOException {
        log.debug("appId:" + appId);
        log.debug("secret:" + appsecret);
        // 根据传入的code,拼接出访问微信定义好的接口的URL
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret
                + "&code=" + code + "&grant_type=authorization_code";
        // 向相应URL发送请求获取token json字符串
        String jsonToken = HttpClientUtil.doGet(url);
        log.debug("userAccessToken:" + jsonToken);
        UserAccessToken token = new UserAccessToken();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将json字符串转换成相应对象
            token = objectMapper.readValue(jsonToken, UserAccessToken.class);
        } catch (JsonParseException e) {
            log.error("获取用户accessToken失败: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            log.error("获取用户accessToken失败: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("获取用户accessToken失败: " + e.getMessage());
            e.printStackTrace();
        }
        if (token == null) {
            log.error("获取用户accessToken失败。");
            return null;
        }
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
        String json=HttpClientUtil.doGet(url);
        log.debug("user info :" + json);
        WeChatUser user = new WeChatUser();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将json字符串转换成相应对象
            user = objectMapper.readValue(json, WeChatUser.class);
        } catch (JsonParseException e) {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        }
        if (user == null) {
            log.error("获取用户信息失败。");
            return null;
        }
        return user;
    }
}
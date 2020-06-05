package com.bdhlife.controller;

import com.bdhlife.entity.UserAccessToken;
import com.bdhlife.entity.WeChatUser;
import com.bdhlife.service.WeChatUserService;
import com.bdhlife.utils.HttpClientUtil;
import com.bdhlife.utils.WechatUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=您的appId&redirect_uri=http://o2o.yitiaojieinfo.com/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 *
 * @author xiangze
 *
 */
@RestController
@RequestMapping("/weChatUser")
@PropertySource("classpath:application.properties")
public class WeChatUserController {

    private static Logger log = LoggerFactory.getLogger(WeChatUserController.class);

    @Autowired
    private WeChatUserService weChatUserService;

    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;

    @RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
    public String logincheck(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin login get...");
        // 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
        String code = request.getParameter("code");
        // 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        // String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        if (null != code) {
            try {
                // 通过code获取access_token
                UserAccessToken token = WechatUtil.getUserAccessToken(code,appId,appSecret);
                log.debug("weixin login token:" + token.toString());
                // 通过token获取accessToken
                String accessToken = token.getAccessToken();
                // 通过token获取openId
                String openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
                WeChatUser user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId); }
                catch (IOException e) {
                    log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                    e.printStackTrace();
                }
            }
            // ======todo begin======
            // 前面咱们获取到openId后，可以通过它去数据库判断该微信帐号是否在我们网站里有对应的帐号了，
            // 没有的话这里可以自动创建上，直接实现微信与咱们网站的无缝对接。
            // ======todo end======
            return null;
    }

}

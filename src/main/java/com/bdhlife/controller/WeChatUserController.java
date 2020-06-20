package com.bdhlife.controller;

import com.bdhlife.entity.UserAccessToken;
import com.bdhlife.entity.WeChatUser;
import com.bdhlife.service.WeChatUserService;
import com.bdhlife.utils.MapUtil;
import com.bdhlife.utils.Result;
import com.bdhlife.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping("/wechatlogin")
@PropertySource("classpath:application.properties")
public class WeChatUserController {

    private static Logger log = LoggerFactory.getLogger(WeChatUserController.class);

    @Autowired
    private WeChatUserService weChatUserService;

    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;

    @PostMapping(value = "/logincheck")
    public Result logincheck(String code, String state, HttpServletRequest request,HttpServletResponse response) {
        try{
            System.out.println("weixin login get...");
            // 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
            System.out.println("weixin login code:" + code);
            // 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
            //log.debug("weixin login state:" + state);
            if (null != code) {
                // 通过code获取access_token
                UserAccessToken token = WechatUtil.getUserAccessToken(code,appId,appSecret);
                System.out.println("weixin login token:" + token.toString());
                // 通过token获取accessToken
                String accessToken = token.getAccessToken();
                // 通过token获取openId
                String openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
                WeChatUser user = WechatUtil.getUserInfo(accessToken, openId);
                System.out.println("weixin login user:" + user.toString());
                //根据openId获取本地用户数据
                WeChatUser weChatUser=weChatUserService.findUserByOpenId(openId);
                if (weChatUser==null){
                    //如果为空则说明表中没有该用户，则添加新的用户
                    int flag = weChatUserService.weChatUserLogin(user);
                    if (flag==1){
                        return Result.build(200,"授权成功",openId);
                    }
                    return Result.build(500,"授权失败",openId);
                }
                //如果已经存在该用户，则直接返回
                return Result.build(500,"您已经授权过了",openId);
            }

            return Result.build(500,"未输入code值");
        }catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }

    @PostMapping("/findUserByOpenId")
    public Result findUserByOpenId(String openId){
        try{
            WeChatUser user = weChatUserService.findUserByOpenId(openId);
            Map<String, String> map = MapUtil.convertToMap(user);
            String sex = user.getSex();
            if ("1".equals(sex)){
                map.put("sex","男");
            }
            if ("2".equals(sex)){
                map.put("sex","女");
            }
            if ("0".equals(sex)){
                map.put("sex","未知");
            }
            return Result.ok(map);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }

    @PostMapping("findUserList")
    public Result findUserList(){
        try{
            List<WeChatUser>list=weChatUserService.findUserList();
            List<Map> l= new ArrayList<Map>();
            for (WeChatUser weChatUser : list) {
                Map<String, String> map = MapUtil.convertToMap(weChatUser);
                String sex = weChatUser.getSex();
                if ("1".equals(sex)){
                    map.put("sex","男");
                }
                if ("2".equals(sex)){
                    map.put("sex","女");
                }
                if ("0".equals(sex)){
                    map.put("sex","未知");
                }
                l.add(map);
            }
            return Result.ok(l);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }

    }
}

package com.bdhlife.controller;

import com.bdhlife.service.WeChatUserService;
import com.bdhlife.utils.HttpClientUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weChatUser")
public class WeChatUserController {

    @Autowired
    private WeChatUserService weChatUserService;

    private static String appId = "wx7f770864a1a08e90";
    private static String appSecret = "52da9d2386081eb8a191163af124f2f9";
    //域名
    private static String safetyDomainNameURL="localhost:8080/weChatUser/";

    @RequestMapping("/login")
    public String weChatUserLogin(HttpServletRequest request){
        String appid=  appId;
        String safety_domain_name= safetyDomainNameURL;
        //scpoe为snsapi_base则不会出现需用户点击授权的弹框，但如果在用户没关注微信公众号的前提下是无法获取微信名和头像
        String queryURL=
                "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                        "appid=" +appId+
                        "&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php" +
                        "&response_type=code" +
                        "&scope=snsapi_userinfo" +
                        "&state=666" +
                        "#wechat_redirect";
        return "redirect"+queryURL;
    }
    @RequestMapping("/toMain")
    public void toMain(HttpServletRequest request){
        String code=request.getParameter("code");
        System.out.println("code========================"+code);
        //通过code换取网页授权access_token,这个access_token主要用于下面获取用户基本信息
        Map<String,String>reader=getReader(code,"POST");
        String oppen_id = reader.get("oppen_id").toString();
        String access_token = reader.get("access_token");
        //查询用户基本信息
        Map map_baseMsg=isBaseMsg(request,oppen_id,access_token);
        String wechat_name = map_baseMsg.get("nickname").toString();
        String headimgurl = map_baseMsg.get("headimgurl").toString();
        //获取用户是否关注公众号
        Map map_isattention=isattention(request,oppen_id);
        String subscribe = map_isattention.get("subscribe").toString();
        System.out.println("用户名"+wechat_name+"头像"+headimgurl+"是否关注公众号"+subscribe);
    }

    //通过code换取网页授权access_token,这个access_token主要用于下面获取用户基本信息
    public Map<String, String> getReader(String code, String method) {
        String queryURL="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ appId
                +"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
        Map<String, String> map = new HashMap<String,String>();
        String oppen_id=null;
        String access_token=null;
        try{
            JSONObject jsonObject = new JSONObject(HttpClientUtil.doGet(queryURL));
            if (jsonObject!=null){
                access_token=jsonObject.get("access_token").toString();
                oppen_id=jsonObject.get("openid").toString();
                map.put("access_token",access_token);
                map.put("open_id",oppen_id);
            }
        }
        catch (Exception e){

        }
        return map;
    }
    //查询用户基本信息
    public Map isBaseMsg(HttpServletRequest request, String oppen_id, String access_token) {
        Map<String, String> map = new HashMap<String,String>();
        String attentionUrl="https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="
                +oppen_id+"&lang=zh_CN";
        try{
            String wechat_name = null;
            String headimgurl = null;
            JSONObject jsonObject = new JSONObject(HttpClientUtil.doGet(attentionUrl));
            if (jsonObject != null ){
                wechat_name = jsonObject.get("nickname").toString();
                headimgurl=jsonObject.get("headimgurl").toString();
                map.put("wechat_name",wechat_name);
                map.put("headimgurl",headimgurl);
            }
        }
        catch (Exception e){

        }
        return map;
    }

    //查询用户是否关注公众号，关注的前提下可以获取微信名和头像
    @SuppressWarnings({"unused","rawtypes"})
    private Map isattention(HttpServletRequest request, String oppen_id) {
        Map<String, String> map = new HashMap<String,String>();
        String attentionUrl="https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                + /*WeChat_var.getProp("access_token")+*/"&openid="+oppen_id+"&lang=zh_CN";
        try{
            String subscribe = null;
            String wechat_name = null;
            String headimgurl = null;
            JSONObject jsonObject = new JSONObject(HttpClientUtil.doGet(attentionUrl));
            if (jsonObject != null){
                subscribe=jsonObject.get("subscribe").toString();
                if ("1".equals(subscribe)){
                    wechat_name=jsonObject.get("nickname").toString();
                    headimgurl=jsonObject.get("headimgurl").toString();
                }else {
                    wechat_name="未关注";
                    headimgurl="未关注";
                }
            }
            map.put("wechat_name",wechat_name);
            map.put("subscribe",subscribe);
            map.put("headimgurl",headimgurl);
        }
        catch (Exception e){

        }
        return map;
    }

}

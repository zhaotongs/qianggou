package com.bdhlife.controller;

import com.bdhlife.utils.SignUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/wechat")
public class WeChatController {
    private static Logger log = LoggerFactory.getLogger(WeChatController.class);

    @RequestMapping(method = { RequestMethod.GET })
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin get...");
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        System.out.println("微信加密签名"+signature);
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        System.out.println("时间戳"+timestamp);
        // 随机数
        String nonce = request.getParameter("nonce");
        System.out.println("随机数"+nonce);
        // 随机字符串
        String echostr = request.getParameter("echostr");
        System.out.println("随机字符串"+echostr);
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        PrintWriter out = null;
        //out.print(echostr);
        try {
            out = response.getWriter();
            log.debug("weixin get success....");
            out.print(echostr);
            /*if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                log.debug("weixin get success....");
                out.print(echostr);
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null){
                out.close();
            }
        }
    }
}

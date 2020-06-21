package com.bdhlife.controller;

import com.bdhlife.config.IWxPayConfig;
import com.bdhlife.entity.Order;
import com.bdhlife.service.OrderService;
import com.bdhlife.utils.IpUtils;

import com.bdhlife.utils.wxpay.WXPay;
import com.bdhlife.utils.wxpay.WXPayConstants;
import com.bdhlife.utils.wxpay.WXPayUtil;
import com.sun.jmx.snmp.Timestamp;
import org.apache.commons.net.ntp.TimeStamp;
import org.bouncycastle.jce.provider.JCEMac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IWxPayConfig iWxPayConfig;
    @Autowired
    private OrderService orderService;

    //微信统一下单
    @PostMapping("/unifiedOrder")
    public Map<String, String> unifiedOrder(
            String body,
            String openId,
            String orderNumber,// 咱们自己所提供的订单号,需要唯一
            HttpServletRequest request
    ){
        Map<String, String> result = new HashMap<>();
        // 发起微信支付
        try {
            // ******************************************
            //  统一下单
            // ******************************************
            WXPay wxpay = new WXPay(iWxPayConfig); // *** 注入自己实现的微信配置类, 创建WXPay核心类, WXPay 包括统一下单接口
            Map<String, String> data = new HashMap<String, String>();
            Order order = orderService.findOrderByNumber(orderNumber);
            data.put("body", body);
            data.put("out_trade_no", orderNumber); // 订单唯一编号, 不允许重复
            data.put("total_fee", order.getPrice().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString()); // 订单金额, 单位分
            //获取发起ip
            String ipAddress = IpUtils.getIpAddress(request);
            data.put("spbill_create_ip", ipAddress); // 下单ip
            data.put("openid", openId); // 微信公众号统一标示openid
            data.put("notify_url", "yq.bdhlife.com"); // 订单结果通知, 微信主动回调此接口
            data.put("trade_type", "JSAPI"); // 固定填写
            System.out.println("发起微信支付下单接口,数据："+ data);
            Map<String, String> response = wxpay.unifiedOrder(data); // 微信sdk集成方法, 统一下单接口unifiedOrder, 此处请求   MD5加密   加密方式
            System.out.println("微信支付下单成功, 返回值 response:"+response);
            String returnCode = response.get("return_code");
            if (!"SUCCESS".equals(returnCode)) {
                return null;
            }
            String resultCode = response.get("result_code");
            if (!"SUCCESS".equals(resultCode)) {
                return null;
            }
            String prepay_id = response.get("prepay_id");
            if (prepay_id == null) {
                return null;
            }
            // ******************************************
            //  前端调起微信支付必要参数
            // ******************************************
            String packages = "prepay_id=" + prepay_id;
            Map<String, String> wxPayMap = new HashMap<String, String>();
            wxPayMap.put("appId", iWxPayConfig.getAppID());
            Date date = new Date();
            long time = date.getTime() / 1000;
            wxPayMap.put("timeStamp",String.valueOf(time));
            String nonceStr = WXPayUtil.generateNonceStr();
            wxPayMap.put("nonceStr",nonceStr);
            wxPayMap.put("package", packages);
            wxPayMap.put("signType", "MD5");
            // 加密串中包括 appId timeStamp nonceStr package signType 5个参数, 通过sdk WXPayUtil类加密, 注意, 此处使用  MD5加密  方式
            String sign = WXPayUtil.generateSignature(wxPayMap, iWxPayConfig.getKey(), WXPayConstants.SignType.MD5);
            // ******************************************
            //  返回给前端调起微信支付的必要参数
            // ******************************************
            wxPayMap.put("paySign", sign);
            boolean flag = WXPayUtil.isSignatureValid(wxPayMap, iWxPayConfig.getKey(),WXPayConstants.SignType.MD5);
            System.out.println(flag);
            result.putAll(wxPayMap);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    //微信支付异步通知接口
    @PostMapping("/payCallback")
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入微信支付异步通知");
        try{
            //
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String resXml=sb.toString();
            System.out.println("微信支付异步通知请求包:"+resXml);
            return payBack(resXml);
        }catch (Exception e){
            System.out.println("微信支付回调通知失败:"+e);
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }


    public String payBack(String notifyData) {
        System.out.println("payBack() start, notifyData:"+ notifyData);
        String xmlBack="";
        Map<String, String> notifyMap = null;
        try {
            WXPay wxpay = new WXPay(iWxPayConfig);
            notifyMap = WXPayUtil.xmlToMap(notifyData);         // 转换成map
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                // 签名正确，进行处理。
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                String return_code = notifyMap.get("return_code");//状态
                String out_trade_no = notifyMap.get("out_trade_no");//订单号
                if (out_trade_no == null) {
                    System.out.println("微信支付回调失败订单号:"+ notifyMap);
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }

                // 业务逻辑处理 ****************************
                Integer oid = Integer.valueOf(notifyMap.get("out_trade_no"));
                orderService.updateOrder(oid,2);
                System.out.println("微信支付回调成功订单号:"+ notifyMap);
                xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";
                return xmlBack;
            } else {
                System.out.println("微信支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            System.out.println("微信支付回调通知失败"+e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }

}

package com.bdhlife.entity;

import java.math.BigDecimal;

public class PayEntity {

    String appid;//APPID （已有）
    String mch_id;//商户ID（已有）
    String nonce_str;//随机字符串
    String sign;//签名
    String body;// 商品描述所支付的名称
    String out_trade_no;// 咱们自己所提供的订单号,需要唯一
    int total_fee;// 支付金额
    String spbill_create_ip;//IP地址
    String notify_url;//回调地址
    String trade_type;// 支付类型:JSAPI -JSAPI支付,NATIVE -Native支付,APP -APP支付
    String openid;// 支付人的微信公众号对应的唯一标识

}

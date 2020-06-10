package com.bdhlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Order {

    private Integer oid;//订单表id
    private Integer skuId;//对应sku表的id
    private Integer spuId;//对应spu表的id
    private Integer count;//数量
    private BigDecimal price;//金额
    private String phone;//联系人手机号
    private String address;//收件地址
    private String realName;//联系姓名
    private Integer state;//状态
    private String openId;//用户的openid
    private String orderNumber;//订单编号
}

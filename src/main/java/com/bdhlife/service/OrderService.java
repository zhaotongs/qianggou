package com.bdhlife.service;

import com.bdhlife.entity.Order;
import com.bdhlife.entity.WeChatUser;
import com.bdhlife.utils.Result;

import java.util.List;

public interface OrderService {

    WeChatUser findUserByOpenId(String openId);

    int addOrder(Integer skuId, Integer spuId, Integer count, String phone, String address, String realName,String openId);

    List<Order> findOrderList(Integer spuId, String openId);

    Order findOrderByNumber(String orderNumber);

    int updateOrder(Integer oid,Integer state);

}

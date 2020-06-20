package com.bdhlife.service.impl;

import com.bdhlife.entity.KuCun;
import com.bdhlife.entity.Order;
import com.bdhlife.entity.WeChatUser;
import com.bdhlife.mapper.GoodsMapper;
import com.bdhlife.mapper.OrderMapper;
import com.bdhlife.mapper.WeChatUserMapper;
import com.bdhlife.service.OrderService;
import com.bdhlife.utils.ODDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private WeChatUserMapper weChatUserMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public WeChatUser findUserByOpenId(String openId) {
       return weChatUserMapper.findUserOrder(openId);
    }

    @Override
    public String addOrder(Integer skuId, Integer spuId, Integer count, String phone, String address, String realName,String openId) {
        WeChatUser user = new WeChatUser();
        user.setRealName(realName);
        user.setPhone(phone);
        user.setAddress(address);
        user.setOpenId(openId);
        weChatUserMapper.updateUser(user);
        Order order = new Order();
        order.setSpuId(spuId);
        order.setSkuId(skuId);
        order.setCount(count);
        KuCun kunCun = goodsMapper.findKuCunById(skuId);
        BigDecimal unitPrice = kunCun.getPrice();
        order.setPrice(unitPrice.multiply(new BigDecimal(count.toString())));
        order.setPhone(phone);
        order.setAddress(address);
        order.setRealName(realName);
        order.setState(1);
        order.setOpenId(openId);
        String orderNumber = ODDGenerator.getNumber();
        order.setOrderNumber(orderNumber);
        int flag = orderMapper.addOrder(order);
        if (flag == 1){
            Integer stock = kunCun.getStock();
            kunCun.setStock(stock-count);
            goodsMapper.updateKunCun(kunCun);
            return order.getOrderNumber();
        }
        return null;
    }

    @Override
    public List<Order> findOrderList(Integer spuId, String openId) {
        return orderMapper.findOrderList(spuId,openId);
    }

    @Override
    public Order findOrderByNumber(String orderNumber) {
        return orderMapper.findOrderByNumber(orderNumber);
    }

    @Override
    public int updateOrder(Integer oid, Integer state) {
        Order order = new Order();
        order.setOid(oid);
        order.setState(state);
        return orderMapper.updateOrder(order);
    }


}

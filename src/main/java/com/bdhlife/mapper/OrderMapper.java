package com.bdhlife.mapper;

import com.bdhlife.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    int addOrder(Order order);

    List<Order> findOrderList(@Param("spuId") Integer spuId,@Param("openId") String openId);

    Order findOrderByNumber(String orderNumber);

}

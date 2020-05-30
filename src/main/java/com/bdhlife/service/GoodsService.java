package com.bdhlife.service;

import com.bdhlife.entity.Goods;

import java.util.List;

public interface GoodsService {

    List<Goods> findGoodsList(Integer goodsId);

    int addKuCun( String name, String images, String shangpId, String size,
                 String color, String stock);
}

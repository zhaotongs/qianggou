package com.bdhlife.service;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;

import java.util.List;

public interface GoodsService {

    List<Goods> findGoodsList(Integer goodsId);

    int addKuCun( String name, String images, String shangpId, String size,
                 String color, String stock, String price);

    List<KuCun> queryKuCunList(String color, String size, String skuId);
}

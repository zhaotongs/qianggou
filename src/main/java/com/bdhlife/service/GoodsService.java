package com.bdhlife.service;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsService {

    List<Goods> findGoodsList(Integer goodsId);

    int addGood(String name, String title, String description);

    int delGood(int spuId);

    int addKuCun( String name, String file, String shangpId, String size,
                 String color, String stock, BigDecimal price);

    List<KuCun> queryKuCunList(String color, String size, Integer skuId,Integer shangpId);

    int delKuCun(int skuId);

}

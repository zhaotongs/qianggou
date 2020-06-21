package com.bdhlife.service;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsService {

    List<Goods> findGoodsList(Integer spuId);

    int addGood(String name, String title, String description,BigDecimal minPrice, BigDecimal maxPrice);

    int delGood(int spuId);

    int addKuCun( String name, String file, String shangpId, String size,
                 String color, Integer stock, BigDecimal price);

    List<KuCun> queryKuCunList(String color, String size, Integer skuId,Integer shangpId,boolean groupColor,boolean groupSize);

    int delKuCun(int skuId);

    List<String> findSize(Integer shangpId);

    List<String> findColor(Integer shangpId);




}

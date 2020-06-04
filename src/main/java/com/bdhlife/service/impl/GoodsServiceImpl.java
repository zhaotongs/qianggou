package com.bdhlife.service.impl;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;
import com.bdhlife.mapper.GoodsMapper;
import com.bdhlife.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> findGoodsList(Integer goodsId) {
        return goodsMapper.findGoodsList(goodsId);
    }

    @Override
    public int addGood(String name, String title, String description) {
        Goods goods = new Goods();
        goods.setName(name);
        goods.setTitle(title);
        goods.setDescription(description);
        goods.setState(1);
        return goodsMapper.addGood(goods);
    }

    @Override
    public int delGood(int spuId) {
        return goodsMapper.delGood(spuId);
    }

    @Override
    public int addKuCun( String name, String images, String shangpId, String size, String color,
                        String stock, BigDecimal price) {
        return goodsMapper.addKuCun(name,images,shangpId,size,color,stock,price);
    }

    @Override
    public List<KuCun> queryKuCunList(String color, String size, Integer skuId,Integer shangpId) {
        return goodsMapper.queryKuCunList(color, size, skuId,shangpId);
    }

    @Override
    public int delKuCun(int skuId) {
        return goodsMapper.delKuCun(skuId);
    }
}

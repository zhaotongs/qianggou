package com.bdhlife.service.impl;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;
import com.bdhlife.mapper.GoodsMapper;
import com.bdhlife.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper pictureMapper;

    @Override
    public List<Goods> findGoodsList(Integer goodsId) {
        return pictureMapper.findGoodsList(goodsId);
    }

    @Override
    public int addKuCun( String name, String images, String shangpId, String size, String color,
                        String stock, String price) {
        return pictureMapper.addKuCun(name,images,shangpId,size,color,stock,price);
    }

    @Override
    public List<KuCun> queryKuCunList(String color, String size, String skuId) {
        return pictureMapper.queryKuCunList(color, size, skuId);
    }
}

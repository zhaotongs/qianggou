package com.bdhlife.service.impl;

import com.bdhlife.entity.Goods;
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
                        String stock) {
        return pictureMapper.addKuCun(name,images,shangpId,size,color,stock);
    }
}

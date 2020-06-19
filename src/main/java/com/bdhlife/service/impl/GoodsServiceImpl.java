package com.bdhlife.service.impl;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;
import com.bdhlife.mapper.GoodsMapper;
import com.bdhlife.service.GoodsService;
import com.bdhlife.utils.ImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@PropertySource("classpath:application.properties")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Value("${img.path}")
    private String path;

    @Value("${img.url}")
    private String imgUrl;

    @Override
    public List<Goods> findGoodsList(Integer spuId) {
        return goodsMapper.findGoodsList(spuId);
    }

    @Override
    public int addGood(String name, String title, String description,BigDecimal minPrice, BigDecimal maxPrice) {
        Goods goods = new Goods();
        goods.setName(name);
        goods.setTitle(title);
        goods.setDescription(description);
        goods.setState(1);
        goods.setMinPrice(minPrice);
        goods.setMaxPrice(maxPrice);
        return goodsMapper.addGood(goods);
    }

    @Override
    public int delGood(int spuId) {
        return goodsMapper.delGood(spuId);
    }

    @Override
    public int addKuCun( String name, String file, String shangpId, String size, String color,
                        Integer stock, BigDecimal price) {
        KuCun kuCun = new KuCun();
        kuCun.setName(name);
        kuCun.setShangpId(shangpId);
        kuCun.setSize(size);
        kuCun.setColor(color);
        kuCun.setStock(stock);
        kuCun.setPrice(price);
        if (file != null && file.length()>0){
            String[] imagesList = ImgUtil.savePic(file, path);
            String images = imagesList[0];
            kuCun.setImages(images);
            return goodsMapper.addKuCun(kuCun);
        }
        return goodsMapper.addKuCun(kuCun);
    }

    @Override
    public List<KuCun> queryKuCunList(String color, String size, Integer skuId,Integer shangpId) {
        List<KuCun> list = goodsMapper.queryKuCunList(color, size, skuId, shangpId);
        for (KuCun kuCun : list) {
            String images = kuCun.getImages();
            String str = images.substring(path.length() , images.length() );
            String newUrl=imgUrl+str;
            kuCun.setImages(newUrl);
        }
        return list;
    }

    @Override
    public int delKuCun(int skuId) {
        KuCun kuCun = findKuCunById(skuId);
        String images = kuCun.getImages();
        if (images != null ){
            ImgUtil.delFolder(images);
        }
        return goodsMapper.delKuCun(skuId);
    }

    public KuCun findKuCunById(int id){
        return goodsMapper.findKuCunById(id);
    }
}

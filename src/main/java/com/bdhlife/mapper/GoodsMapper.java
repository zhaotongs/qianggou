package com.bdhlife.mapper;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {


    List<Goods> findGoodsList(Integer goodsId);

    int addKuCun(@Param("skuId") Integer skuId, @Param("name")String name,
                 @Param("images")String images, @Param("shangpId")String shangpId, @Param("size")String size,
                 @Param("color")String color, @Param("stock")String stock);
}

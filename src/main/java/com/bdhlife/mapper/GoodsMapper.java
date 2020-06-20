package com.bdhlife.mapper;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;
import com.bdhlife.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface GoodsMapper {


    List<Goods> findGoodsList(Integer spuId);

    int addGood(Goods goods);

    int delGood(int spuId);

    List<KuCun> queryKuCunList(@Param("color")String color, @Param("size")String size,@Param("skuId")Integer skuId, @Param("shangpId")Integer shangpId);

    int addKuCun(KuCun kuCun);


    int delKuCun(int skuId);

    int updateKunCun(KuCun kuCun);

    KuCun findKuCunById(int skuId);

    List<String> findSize(Integer shangpId);

    List<String> findColor(Integer shangpId);

}

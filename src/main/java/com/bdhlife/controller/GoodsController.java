package com.bdhlife.controller;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;
import com.bdhlife.entity.Picture;
import com.bdhlife.service.GoodsService;
import com.bdhlife.service.PictureService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/findGoodList")
    //查询库存列表，
    public Result findPictureList(Integer goodsId){
        List<Goods> list=goodsService.findGoodsList(goodsId);
        return Result.ok(list);
    }
    @RequestMapping("/addGood")
    public Result addGood(
            String name,//商品名称
            String title,//顶部文本内容
            String description//详细描述
    ){
        int flag=goodsService.addGood(name,title,description);
        if (flag==1){
            return Result.ok();
        }
        return Result.build(500,"添加失败");
    }

    @RequestMapping("/addKuCun")
    public Result addKuCun(String name,String images, String shangpId,String size,
                           String color,String stock,BigDecimal price ) {
        goodsService.addKuCun(name,images,shangpId,size,color,stock,price);
        return Result.ok();
    }

    @RequestMapping("/queryKuCun")
    public Result queryKuCunList(String color, String size, Integer skuId,Integer shangpId) {
        List<KuCun> kuCuns = goodsService.queryKuCunList(color, size, skuId,shangpId);
        return Result.build(200, "success", kuCuns);

    }

}

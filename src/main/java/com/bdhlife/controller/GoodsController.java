package com.bdhlife.controller;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.Picture;
import com.bdhlife.service.GoodsService;
import com.bdhlife.service.PictureService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    @RequestMapping("/addKuCun")
    public Result addKuCun(String name,String images, String shangpId,String size,
                           String color,String stock) {
        goodsService.addKuCun(name,images,shangpId,size,color,stock);
        return Result.ok();
    }

}

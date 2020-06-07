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

    //查询商品列表
    @RequestMapping("/findGoodList")
    public Result findGoodsList(Integer spuId){
        List<Goods> list=goodsService.findGoodsList(spuId);
        return Result.ok(list);
    }
    //添加商品
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
    //删除商品
    @RequestMapping("delGood")
    public Result delGood(int spuId){
        int flag=goodsService.delGood(spuId);
        if (flag==1){
            return Result.ok();
        }
        return Result.build(500,"删除失败");
    }

    //添加一个库存商品
    @RequestMapping("/addKuCun")
    public Result addKuCun(String name,String file, String shangpId,String size,
                           String color,String stock,BigDecimal price ) {
        goodsService.addKuCun(name,file,shangpId,size,color,stock,price);
        return Result.ok();
    }

    //条件查询库存商品
    @RequestMapping("/queryKuCun")
    public Result queryKuCunList(String color, String size, Integer skuId,Integer shangpId) {
        List<KuCun> kuCuns = goodsService.queryKuCunList(color, size, skuId,shangpId);
        return Result.build(200, "success", kuCuns);

    }

    //删除库存商品
    @RequestMapping("/delKuCun")
    public Result delKuCun(int skuId){
        int flag=goodsService.delKuCun(skuId);
        if (flag==1){
            return Result.ok();
        }
        return Result.build(500,"删除失败");
    }

}

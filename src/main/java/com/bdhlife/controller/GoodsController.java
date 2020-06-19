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
        try{
            List<Goods> list=goodsService.findGoodsList(spuId);
            return Result.build(200, "查询成功", list);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }
    //添加商品
    @RequestMapping("/addGood")
    public Result addGood(
            String name,//商品名称
            String title,//顶部文本内容
            String description,//详细描述
            BigDecimal minPrice,
            BigDecimal maxPrice
    ){
        try{
            int flag=goodsService.addGood(name,title,description,minPrice,maxPrice);
            if (flag==1){
                return Result.build(200,"添加成功");
            }
            return Result.build(500,"添加失败");
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }

    }
    //删除商品
    @RequestMapping("delGood")
    public Result delGood(int spuId){
        try{
            int flag=goodsService.delGood(spuId);
            if (flag==1){
                return Result.build(200,"删除成功");
            }
            return Result.build(500,"删除失败");
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }

    }

    //条件查询库存商品
    @RequestMapping("/queryKuCun")
    public Result queryKuCunList(String color, String size, Integer skuId,Integer shangpId) {
        try{
            List<KuCun> kuCuns = goodsService.queryKuCunList(color, size, skuId,shangpId);
            return Result.build(200, "查询成功", kuCuns);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }

    //添加一个库存商品
    @RequestMapping("/addKuCun")
    public Result addKuCun(String name,String file, String shangpId,String size,
                           String color,Integer stock,BigDecimal price ) {
        try{
            goodsService.addKuCun(name,file,shangpId,size,color,stock,price);
            return Result.build(200,"添加成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }

    }



    //删除库存商品
    @RequestMapping("/delKuCun")
    public Result delKuCun(int skuId){
        try{
            int flag=goodsService.delKuCun(skuId);
            if (flag==1){
                return Result.build(200,"删除成功");
            }
            return Result.build(500,"删除失败");
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }

    }

}

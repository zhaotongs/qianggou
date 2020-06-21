package com.bdhlife.controller;

import com.bdhlife.entity.Goods;
import com.bdhlife.entity.KuCun;
import com.bdhlife.entity.Picture;
import com.bdhlife.service.GoodsService;
import com.bdhlife.service.PictureService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Result queryKuCunList(String color, String size, Integer skuId,Integer shangpId,
                                 @RequestParam(defaultValue="false") boolean groupColor,@RequestParam(defaultValue="false")boolean groupSize) {
        try{
            List<KuCun> kuCuns = goodsService.queryKuCunList(color, size, skuId,shangpId,groupColor,groupSize);
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

    //根据shangpId查询该商品下的所有规格
    @PostMapping("/findSpec")
    public Result findSpec(Integer shangpId){
        try{
            List<String> size=goodsService.findSize(shangpId);
            List<String> color=goodsService.findColor(shangpId);
            Map<String,List>map=new HashMap<>();
            map.put("size",size);
            map.put("color",color);
            return Result.build(200,"查询成功",map);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }

}

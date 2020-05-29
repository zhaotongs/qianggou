package com.bdhlife.controller;

import com.bdhlife.entity.Carousel;
import com.bdhlife.service.CarouselService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    //查询图片列表
    @RequestMapping("findCarouselList")
    public Result findCarouselList(){
        List<Carousel>list=carouselService.findCarouselList();
        return Result.build(200,"查询成功",list);
    }

    //删除一张图片
    @RequestMapping("delCarousel")
    public Result delCarousel(Integer id){
        int flag=carouselService.delCarousel(id);
        if (flag == 1){
            return Result.ok();
        }
        return Result.build(500,"请求失败",null);
    }

    //添加一张图片
    @RequestMapping("addCarousel")
    public Result addCarousel(){

        return Result.ok();
    }



}

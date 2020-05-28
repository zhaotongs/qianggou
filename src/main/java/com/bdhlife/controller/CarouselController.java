package com.bdhlife.controller;

import com.bdhlife.service.CarouselService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    //添加一张图片
    @RequestMapping("addCarousel")
    public Result addCarousel(){

        return Result.ok();
    }
    //删除一张图片
    @RequestMapping("delCarousel")
    public Result delCarousel(){
        return null;
    }
    //查询图片列表
    @RequestMapping("findCarouselList")
    public Result findCarouselList(){
        return null;
    }


}

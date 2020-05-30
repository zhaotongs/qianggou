package com.bdhlife.controller;

import com.bdhlife.entity.Picture;
import com.bdhlife.service.PictureService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    //添加一张图片
    @RequestMapping("/addPicture")
    public Result addPicture(Integer goodsId,Integer position,Integer sort,String url){
        int flag=pictureService.addPicture( goodsId, position, sort, url);
        if (flag==1){
            return Result.ok();
        }
        return Result.build(500,"删除失败");
    }
    //删除一张图片
    @RequestMapping("/delPicture")
    public Result delPicture(Integer pid){
        if ( null == pid ){
            return Result.build(500,"未输入id");
        }
        int flag=pictureService.delPicture(pid);
        if (flag==1){
            return Result.ok();
        }
        return Result.build(500,"删除失败");
    }
    @RequestMapping("/findPictureList")
    //查询图片列表
    public Result findPictureList(Integer goodsId){
        List<Picture>list=pictureService.findPictureList(goodsId);
        return Result.ok(list);
    }

}

package com.bdhlife.controller;

import com.bdhlife.entity.Picture;
import com.bdhlife.service.PictureService;
import com.bdhlife.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    //添加一张图片
    @PostMapping("/addPicture")
    public Result addPicture(Integer goodsId,Integer position,Integer sort,String file){
        try {
            int flag=pictureService.addPicture( goodsId, position, sort, file);
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
    //删除一张图片
    @PostMapping("/delPicture")
    public Result delPicture(int pid){
        try {
            int flag=pictureService.delPicture(pid);
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
    @PostMapping("/findPictureList")
    //查询图片列表
    public Result findPictureList(Integer goodsId,Integer position){
        try {
            List<Picture>list=pictureService.findPictureList(goodsId,position);
            return Result.build(200,"查询成功",list);
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.build(500, "未知异常");
        }
    }

}

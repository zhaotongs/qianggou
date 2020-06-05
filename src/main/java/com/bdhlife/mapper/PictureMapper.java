package com.bdhlife.mapper;

import com.bdhlife.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PictureMapper {

    int addPicture(Picture picture);

    int delPicture(Integer pid);

    List<Picture> findPictureList(@Param("goodsId") Integer goodsId,@Param("position") Integer position);

    Picture findByPid(int pid);
}

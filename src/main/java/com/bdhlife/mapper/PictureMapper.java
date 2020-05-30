package com.bdhlife.mapper;

import com.bdhlife.entity.Picture;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PictureMapper {

    int addPicture(Picture picture);

    int delPicture(Integer pid);

    List<Picture> findPictureList(Integer goodsId);

}

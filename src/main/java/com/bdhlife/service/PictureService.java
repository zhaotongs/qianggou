package com.bdhlife.service;

import com.bdhlife.entity.Picture;

import java.util.List;

public interface PictureService {

    int addPicture(Integer goodsId,Integer position,Integer sort,String file);

    int delPicture(Integer pid);

    List<Picture> findPictureList(Integer goodsId,Integer position);



}

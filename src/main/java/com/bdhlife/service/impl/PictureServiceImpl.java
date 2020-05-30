package com.bdhlife.service.impl;

import com.bdhlife.entity.Picture;
import com.bdhlife.mapper.PictureMapper;
import com.bdhlife.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public int addPicture(Integer goodsId,Integer position,Integer sort,String url) {
        Picture picture = new Picture();
        picture.setGoodsId(goodsId);
        picture.setPosition(position);
        picture.setSort(sort);
        picture.setUrl(url);
        return pictureMapper.addPicture(picture);
    }

    @Override
    public int delPicture(Integer pid) {
        return pictureMapper.delPicture(pid);
    }

    @Override
    public List<Picture> findPictureList(Integer goodsId) {
        return pictureMapper.findPictureList(goodsId);
    }
}

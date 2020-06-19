package com.bdhlife.service.impl;

import com.bdhlife.entity.Picture;
import com.bdhlife.mapper.PictureMapper;
import com.bdhlife.service.PictureService;
import com.bdhlife.utils.ImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@PropertySource("classpath:application.properties")
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Value("${img.path}")
    private String path;

    @Value("${img.url}")
    private String imgUrl;

    @Override
    public int addPicture(Integer goodsId,Integer position,Integer sort,String file) {
        Picture picture = new Picture();
        picture.setGoodsId(goodsId);
        picture.setPosition(position);
        picture.setSort(sort);
        String[] urls = ImgUtil.savePic(file,path);
        picture.setUrl(urls[0]);
        return pictureMapper.addPicture(picture);

    }

    @Override
    public int delPicture(Integer pid) {
        Picture picture = findByPid(pid);
        if (picture != null ){
            ImgUtil.delFolder(picture.getUrl());
            return pictureMapper.delPicture(pid);
        }
        return 0;
    }

    @Override
    public List<Picture> findPictureList(Integer goodsId,Integer position) {
        List<Picture> pictureList = pictureMapper.findPictureList(goodsId, position);
        for (Picture picture : pictureList) {
            String url = picture.getUrl();
            String str = url.substring(path.length() , url.length() );
            String newUrl=imgUrl+str;
            picture.setUrl(newUrl);
        }
        return pictureList;
    }

    public Picture findByPid(int pid){
        return pictureMapper.findByPid(pid);
    }
}

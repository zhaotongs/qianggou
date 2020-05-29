package com.bdhlife.service.impl;

import com.bdhlife.entity.Carousel;
import com.bdhlife.mapper.CarouselMapper;
import com.bdhlife.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> findCarouselList() {
        return carouselMapper.findCarouselList();
    }

    @Override
    public int delCarousel(Integer id) {
        return 0;
    }
}

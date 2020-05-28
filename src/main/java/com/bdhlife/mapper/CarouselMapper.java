package com.bdhlife.mapper;

import com.bdhlife.entity.Carousel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarouselMapper {

    List<Carousel> findCarouselList();

}

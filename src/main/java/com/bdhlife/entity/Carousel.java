package com.bdhlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Carousel {
    private Integer id;//轮播图表id
    private String url;//地址
    private String name;//名称
    private Date createTime;//创建时间
    private Integer goodsId;//商品id

}

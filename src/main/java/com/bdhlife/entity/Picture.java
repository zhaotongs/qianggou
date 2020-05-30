package com.bdhlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Picture {
    private Integer pid;//轮播图表id
    private String url;//地址
    private Integer goodsId;//商品id
    private Integer position;//位置类型(1顶部,2底部)
    private Integer sort;//排序(大数在上)

}

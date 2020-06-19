package com.bdhlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.soap.Text;
import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Goods {

    private Integer spuId;
    private String name;
    private String title;
    private String description;
    private int state;
    private BigDecimal minPrice;//最小价格
    private BigDecimal maxPrice;//最大价格

}

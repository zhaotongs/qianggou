package com.bdhlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class KuCun {
    private Integer skuId;
    private String name;
    private String images;
    private String shangpId;
    private String size;
    private String color;
    private Integer stock;
    private BigDecimal price;


}

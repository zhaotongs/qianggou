package com.bdhlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.soap.Text;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Goods {
    private Integer spuId;
    private String name;
    private String title;
    private String description;
    private String state;

}

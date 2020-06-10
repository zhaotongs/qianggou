package com.bdhlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class AdminUser {

    private int id;
    private String username;
    private String password;

}

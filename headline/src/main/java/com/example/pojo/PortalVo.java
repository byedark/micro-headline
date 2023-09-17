package com.example.pojo;

import lombok.Data;

@Data
public class PortalVo {
    
    private String keyWords;
    private Integer type;
    private Integer pageNum;
    private Integer pageSize;
}
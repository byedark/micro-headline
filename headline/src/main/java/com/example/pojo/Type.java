package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName news_type
 */
@TableName(value ="news_type")
@Data
public class Type implements Serializable {
    private Integer tid;

    private String tname;

    private Integer version;

    private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}
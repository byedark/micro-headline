package com.example.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

/**
 * @TableName news_user
 */
@TableName(value ="news_user")
@Data
public class User implements Serializable {
    //主键配置
    @TableId
    private Integer uid;

    @Length(min = 2,max = 6)
    private String username;

    @Length(min = 6)
    private String userPwd;

    @Length(min = 4)
    private String nickName;
    //乐观锁，防止并发出现数据错误
    @Version
    private Integer version;
    //逻辑删除
    @TableLogic
    private Integer isDeleted;
    //序列化和反序列化
    @Serial
    private static final long serialVersionUID = 1L;
}
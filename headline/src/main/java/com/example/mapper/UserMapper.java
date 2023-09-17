package com.example.mapper;

import com.example.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author XT189
* @description 针对表【news_user】的数据库操作Mapper
* @createDate 2023-09-14 19:04:20
* @Entity com.example.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}





package com.example.service;

import com.example.pojo.PortalVo;
import com.example.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.utils.Result;

import java.util.List;

/**
* @author XT189
* @description 针对表【news_type】的数据库操作Service
* @createDate 2023-09-14 19:04:20
*/
public interface TypeService extends IService<Type> {

   public List<Type> list();



}

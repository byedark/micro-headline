package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pojo.PortalVo;
import com.example.pojo.Type;
import com.example.service.TypeService;
import com.example.mapper.TypeMapper;
import com.example.utils.JwtHelper;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author XT189
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2023-09-14 19:04:20
*/

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> list() {
        List<Type> types = typeMapper.selectList(null);

        return types;
    }

}





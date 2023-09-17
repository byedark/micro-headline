package com.example.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.PortalVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author XT189
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2023-09-14 19:04:20
* @Entity com.example.pojo.Headline
*/
@Mapper
public interface HeadlineMapper extends BaseMapper<Headline> {
    Map selectDetailMap(Integer hid);
    IPage<Map> selectByInfo(IPage<Map> page,
                     @Param("portalVo") PortalVo portalVo);
}





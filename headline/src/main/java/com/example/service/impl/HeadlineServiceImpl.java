package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pojo.Headline;
import com.example.pojo.PortalVo;
import com.example.service.HeadlineService;
import com.example.mapper.HeadlineMapper;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author XT189
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2023-09-14 19:04:20
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{
    @Autowired
    private HeadlineMapper headlineMapper;
    @Override
    public Result findNewPage(PortalVo portalVo){
        LambdaQueryWrapper<Headline> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(!StringUtils.isEmpty(portalVo.getKeyWords()),
                        Headline::getTitle,portalVo.getKeyWords())
                .eq(portalVo.getType()!=0,
                        Headline::getType,portalVo.getType()
        );
        //但是这样子没有办法拿到已经过去的时间数字，因为自带的查询的语句里不包括这个部分
        //增加分页参数
        IPage<Map> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        //分号务必谨慎
        headlineMapper.selectByInfo(page,portalVo);
        Map<String,Object> pageInfo =new HashMap<>();
        pageInfo.put("pageData",page.getRecords());
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());
        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("pageInfo",pageInfo);
        // 响应JSON
        return Result.ok(pageInfoMap);
    }
    @Override
    public Result showHeadlineDetail(int hid) {
        //1.实现根据id的查询(多表
        Map headLineDetail = headlineMapper.selectDetailMap(hid);
        //2.拼接头条对象(阅读量和version)进行数据更新
        //取回来还要再+1
        Headline headline = new Headline();
        headline.setHid(hid);
        headline.setPageViews(  (Integer) headLineDetail.get("pageViews")+1    ); //阅读量+1
        headline.setVersion(     (Integer) headLineDetail.get("version")       ); //设置版本
        //你Debug居然不用AOP，emmm
        //System.out.println("我感觉就是你这里有BUG");
        headlineMapper.updateById(headline);
        //System.out.println("我到这里了");
        headLineDetail.put("pageViews",(Integer)headLineDetail.get("pageViews")+1);
        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("headline",headLineDetail);
        return Result.ok(pageInfoMap);
    }

    @Override
    public Result publicArticle(Headline headline){
        System.out.println("我进入到了service");
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        headline.setPageViews(0);
        //初始化浏览次数为0
        headlineMapper.insert(headline);
        return Result.ok(null);
    }

    @Override
    public Result removeById(int hid){
        headlineMapper.deleteById(hid);
        return Result.ok(null);
    }
    @Override
    public Result updateHeadLine(Headline headline){
        //一开始不会有初始version?
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);
        headline.setUpdateTime(new Date());
        headlineMapper.updateById(headline);
        return Result.ok(null);
    }

    @Override
    public Result findHeadlineById(int hid){
        Headline h = headlineMapper.selectById(hid);
        Map map = new HashMap<>();
        map.put("headline",h);
        Result ans = Result.ok(map);
        return ans;
    }
}





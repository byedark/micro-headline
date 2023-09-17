package com.example.service;

import com.example.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.PortalVo;
import com.example.utils.Result;

/**
* @author XT189
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2023-09-14 19:04:20
*/
public interface HeadlineService extends IService<Headline> {
    public Result findNewPage(PortalVo portalVo);
    public Result showHeadlineDetail(int hid);
    public Result publicArticle(Headline headline);
    public Result removeById(int hid);
    public Result updateHeadLine(Headline headline);
    public Result findHeadlineById(int hid);
}

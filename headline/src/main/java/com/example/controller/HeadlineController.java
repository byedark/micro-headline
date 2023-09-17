package com.example.controller;


import com.example.pojo.Headline;
import com.example.service.HeadlineService;
import com.example.utils.JwtHelper;
import com.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController //一定要加rest不然返回的不是json
@RequestMapping("headline")
@CrossOrigin  //开启跨域访问
public class HeadlineController {

    @Autowired
    HeadlineService headlineService;

    @Autowired
    JwtHelper jwtHelper;

    //需要得到作者信息，所以还需要一个token值，这个token不是用来给拦截器的，拦截器有自己自带的
    @PostMapping("publish")
    public Result publicArticle(@RequestBody Headline headline, @RequestHeader String token){
        //先要设置id，再传过去
        int id = jwtHelper.getUserId(token).intValue();//获得的是一个long类型
        headline.setPublisher(id);
        Result result = headlineService.publicArticle(headline);
        return result;
    }

    @PostMapping("removeByHid")
    public Result removeById(int hid){
        headlineService.removeById(hid);
        return Result.ok(null);
    }

    @PostMapping("update")
    public Result update(@RequestBody Headline headline){
        Result result = headlineService.updateHeadLine(headline);
        return result;
    }

    @PostMapping("findHeadlineByHid")
    public Result findHeadline(int hid){
        Result result = headlineService.findHeadlineById(hid);
        return result;
    }

}

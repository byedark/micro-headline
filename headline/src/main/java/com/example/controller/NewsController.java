package com.example.controller;


import com.example.pojo.PortalVo;
import com.example.pojo.Type;
import com.example.service.HeadlineService;
import com.example.service.TypeService;
import com.example.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  //返回JSON
//@RequestMapping("/app-dev")
@CrossOrigin  //开启跨域访问
public class NewsController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private HeadlineService headlineService;

    @GetMapping("/portal/findAllTypes")
    public Result findAllTypes(){
        //直接调用业务层,查询全部数据
        List<Type> list = typeService.list();
        return  Result.ok(list);
    }
    @PostMapping("/portal/findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewPage(portalVo);
        return  result;
    }

    @PostMapping("/portal/showHeadlineDetail")
    public Result showHeadlineDetail(@Param("hid") Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }
}

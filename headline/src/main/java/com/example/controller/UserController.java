package com.example.controller;

import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.Result;
import com.example.utils.JwtHelper;
import com.example.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController  //返回JSON
@RequestMapping("user")
@CrossOrigin  //开启跨域访问
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("login")//传进来的是json参数，所以param不管用
    public Result login(@RequestBody User user){
        Result ans = userService.login(user);
        return ans;
    }

    @PostMapping("regist")
    public Result register(@RequestBody User user){
        Result result = userService.register(user);
        return result;
    }
    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkName(username);
        return result;
    }
    @GetMapping("getUserInfo")
    public Result userInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        return result;
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)){
            //没有传或者过期 未登录
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }

}
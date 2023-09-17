package com.example.service;

import com.example.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.utils.Result;
import org.springframework.web.bind.annotation.RequestHeader;

/**
* @author XT189
* @description 针对表【news_user】的数据库操作Service
* @createDate 2023-09-14 19:04:20
*/

public interface UserService extends IService<User> {
    public Result login(User user);
    public  Result register(User user);
    public  Result checkName(String name);
    public Result getUserInfo(String token);
}

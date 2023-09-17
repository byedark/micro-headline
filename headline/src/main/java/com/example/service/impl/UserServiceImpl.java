package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.mapper.UserMapper;
import com.example.utils.JwtHelper;
import com.example.utils.MD5Util;
import com.example.utils.Result;
import com.example.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private  UserMapper userMapper;
    @Override
    public Result<Map<String,String>> login(User user){
        String pwd = user.getUserPwd();
        String username = user.getUsername();
        //查找数据库，并生成jwt
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);
        if(loginUser==null) return Result.build(null, ResultCodeEnum.USERNAME_USED);
        else if (StringUtils.isEmpty(user.getUserPwd())||
                !loginUser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd()))) {
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        //全部验证通过配置一个token
        String jwtKey = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
        Map<String,String> map = new HashMap<String, String>();
        map.put("token",jwtKey);
        return Result.ok(map);
    }


    @Override
    public Result register(User user){
        //这种地方打AOP会更好一些
        System.out.println("进入函数register");
        String username = user.getUsername();
        String pwd = user.getUserPwd();
        String nick = user.getNickName();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //前一个是数据库字段，后一个是匹配字段
        lambdaQueryWrapper.eq(User::getUsername,username);
        User registerUser = userMapper.selectOne(lambdaQueryWrapper);
        if(registerUser!=null) {
            //用户民已经使用过了
            System.out.println("用户名已经存在");
            //这个地方解耦合打AOP会更加好一些
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        userMapper.insert(user);
        return Result.ok(null);
    }

    @Override
    public  Result checkName(String name){
        System.out.println("进入函数check");
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //前一个是数据库字段，后一个是匹配字段
        lambdaQueryWrapper.eq(User::getUsername,name);
        User registerUser = userMapper.selectOne(lambdaQueryWrapper);
        if(registerUser!=null) {
            //用户民已经使用过了
            System.out.println("用户名已经存在");
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }

    @Override
    public Result getUserInfo(String token) {

        //1.判定是否有效期
        if (jwtHelper.isExpiration(token)) {
            //true过期,直接返回未登录
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }

        //2.获取token对应的用户
        int userId = jwtHelper.getUserId(token).intValue();

        //3.查询数据
        User user = userMapper.selectById(userId);

        if (user != null) {
            user.setUserPwd(null);
            Map data = new HashMap();
            data.put("loginUser",user);
            return Result.ok(data);
        }
        return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }

}





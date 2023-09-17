package com.example.Interceptor;

import com.example.utils.JwtHelper;
import com.example.utils.Result;
import com.example.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginProtectInterceptor implements HandlerInterceptor{

    @Autowired
    JwtHelper jwtHelper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String s = request.getHeader("token");
        if(jwtHelper.isExpiration(s)||StringUtils.isEmpty(s)){
            Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
            //专门用来转成json字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(result);
            response.getWriter().println(json);
            return false;
        }
        return true;
    }

}

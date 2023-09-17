package com.example.config;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LogAdvice {
    @Before("execution(* com.example.service.impl.HeadlineServiceImpl.findNewPage(..))")
    public void before(JoinPoint joinPoint){
        System.out.println(Arrays.toString(joinPoint.getArgs()));
    }

    @Before("execution(* com.example.service.impl.HeadlineServiceImpl.showHeadlineDetail(..))")
    public void beforef(JoinPoint joinPoint){
        System.out.println("显示全文");
    }
}

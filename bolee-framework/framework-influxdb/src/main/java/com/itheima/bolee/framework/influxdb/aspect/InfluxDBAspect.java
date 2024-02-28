package com.itheima.bolee.framework.influxdb.aspect;

import com.itheima.bolee.framework.influxdb.anno.Insert;
import com.itheima.bolee.framework.influxdb.anno.Select;
import com.itheima.bolee.framework.influxdb.core.Executor;
import com.itheima.bolee.framework.influxdb.core.ParameterHandler;
import com.itheima.bolee.framework.influxdb.core.ResultSetHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * @ClassName InfluxDBAspect.java
 * @Description TODO
 */
@Aspect
@Component
public class InfluxDBAspect {

    private final Executor executor;

    private final ParameterHandler parameterHandler;

    private final ResultSetHandler resultSetHandler;

    @Autowired
    public InfluxDBAspect(Executor executor,ParameterHandler parameterHandler,ResultSetHandler resultSetHandler) {
        this.executor = executor;
        this.parameterHandler = parameterHandler;
        this.resultSetHandler = resultSetHandler;
    }

    @Around("@annotation(select)")
    public Object select(ProceedingJoinPoint joinPoint, Select select) {
        //从joinPoint中获得MethodSignature，并获得Method和Select注解

        //获得执行参数getParameters

        //获得执行参数值getArgs

        //获得执行sql

        //parameterHandler替换参数

        //注解selectAnnotation声明返回类型

        //executor查询结果

        //resultSetHandler根据返回类型返回结果
        return null;
    }

    @Around("@annotation(insert)")
    public void insert(ProceedingJoinPoint joinPoint, Insert insert) {
        //获得执行参数值

        //executor执行insert

    }
}

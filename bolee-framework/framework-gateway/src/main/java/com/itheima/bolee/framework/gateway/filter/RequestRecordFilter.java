package com.itheima.bolee.framework.gateway.filter;

import com.itheima.bolee.framework.gateway.decorator.CacheServerHttpRequestDecorator;
import com.itheima.bolee.framework.gateway.properties.LogProperties;
import com.itheima.bolee.framework.gateway.util.RequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @ClassName RequestRecordFilter.java
 * @Description 请求日志拦截
 */
@Component
@EnableConfigurationProperties(LogProperties.class)
public class RequestRecordFilter implements GlobalFilter,Ordered {

    @Autowired
    LogProperties logProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //处理文件上传:如果是文件上传则不记录日志

        //忽略路径处理:获得请求路径然后与logProperties的路进行匹配，匹配上则不记录日志

        //无需记录日志：直接放过请求

        //需记录日志:对ServerHttpRequest进行二次封装，解决requestBody只能读取一次的问题

        //把当前的请求体进行改变，用于传递新放入的body
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}

package com.itheima.bolee.trade.handler.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.kernel.Config;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.itheima.bolee.framework.commons.constant.trade.TradeConstant;
import com.itheima.bolee.framework.commons.dto.trade.TradeVO;
import com.itheima.bolee.framework.commons.enums.trade.TradeEnum;
import com.itheima.bolee.framework.commons.exception.ProjectException;
import com.itheima.bolee.framework.commons.utils.BeanConv;
import com.itheima.bolee.framework.commons.utils.EmptyUtil;
import com.itheima.bolee.framework.rabbitmq.pojo.MqMessage;
import com.itheima.bolee.framework.rabbitmq.source.TradeSource;
import com.itheima.bolee.trade.service.ITradeService;
import com.itheima.bolee.trade.client.alipay.Factory;
import com.itheima.bolee.trade.config.AliPayConfig;
import com.itheima.bolee.trade.handler.PayNotifyHandler;
import com.itheima.bolee.trade.pojo.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AliNotifyHandler.java
 * @Description 支付通知接口
 */
@Component
public class AliPayNotifyHandler implements PayNotifyHandler {

    @Autowired
    AliPayConfig aliPayConfig;

    @Autowired
    ITradeService tradeService;

    @Autowired
    TradeSource tradeSource;

    @Autowired
    IdentifierGenerator identifierGenerator;

    @Override
    public String notify(HttpServletRequest request, HttpEntity<String> httpEntity,String companyNo) {
        //获取支付结果参数

        try {
            //获得支付宝配置文件

            //配置如果为空，抛出异常

            //使用配置

            //验证签名

            //校验支付状态：成功

                //同步状态

                //发送同步业务信息的MQ信息



            //校验支付状态：关闭

                //同步状态

                //发送同步业务信息的MQ信息

                return "success";
            //接收失败
           
        } catch (Exception e) {
            //6、异常返回 fail 给支付宝
            return "fail";
        }
    }
}

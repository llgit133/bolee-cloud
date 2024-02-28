package com.itheima.bolee.trade.handler.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.itheima.bolee.framework.commons.constant.basic.SuperConstant;
import com.itheima.bolee.framework.commons.constant.trade.TradeConstant;
import com.itheima.bolee.framework.commons.dto.trade.TradeVO;
import com.itheima.bolee.framework.commons.enums.trade.TradeEnum;
import com.itheima.bolee.framework.commons.exception.ProjectException;
import com.itheima.bolee.framework.commons.utils.BeanConv;
import com.itheima.bolee.framework.commons.utils.EmptyUtil;
import com.itheima.bolee.framework.commons.utils.ExceptionsUtil;
import com.itheima.bolee.trade.handler.WapPayHandler;
import com.itheima.bolee.trade.client.alipay.Factory;
import com.itheima.bolee.trade.pojo.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @ClassName AliWapPayHandler.java
 * @Description 手机网页支付
 */
@Slf4j
@Component
public class AliWapPayHandler extends AliCommonPayHandler implements WapPayHandler {

    @Override
    public TradeVO wapTrade(TradeVO tradeVO) {
        //1、交易前置处理：检测交易单参数

        //2、交易前置处理：幂等性处理

        //3、获得支付宝配置文件

        //4、配置如果为空，抛出异常

        //5、使用配置

        try {
            //6、调用接口

            //7、检查网关响应结果
           return null;
        } catch (Exception e) {
            log.error("支付宝手机网页支付统一下单创建失败：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new RuntimeException("支付宝手机网页支付统一下单创建失败");
        }
    }
}

package com.itheima.bolee.trade.face.impl;

import com.itheima.bolee.trade.adapter.WapPayAdapter;
import com.itheima.bolee.framework.commons.constant.trade.TradeCacheConstant;
import com.itheima.bolee.framework.commons.enums.trade.TradeEnum;
import com.itheima.bolee.framework.commons.exception.ProjectException;
import com.itheima.bolee.trade.face.WapPayFace;
import com.itheima.bolee.framework.commons.utils.ExceptionsUtil;
import com.itheima.bolee.framework.commons.dto.trade.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName WapPayFaceImpl.java
 * @Description 手机网页支付Face接口实现
 */
@Slf4j
@Component
public class WapPayFaceImpl implements WapPayFace {

    @Autowired
    WapPayAdapter wapPayAdapter;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public TradeVO wapTrade(TradeVO tradeVO) {
        //构建锁对象

        try {
            //加锁处理

            return null;
        } catch (Exception e) {
            log.error("统一收单线下交易预创建异常:{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradeEnum.TRAD_PAY_FAIL);
        }finally {

        }

    }
}

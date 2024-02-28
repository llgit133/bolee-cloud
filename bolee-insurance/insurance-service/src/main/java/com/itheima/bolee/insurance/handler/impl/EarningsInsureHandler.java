package com.itheima.bolee.insurance.handler.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.google.common.collect.Lists;
import com.itheima.bolee.framework.commons.constant.warranty.WarrantyConstant;
import com.itheima.bolee.framework.commons.constant.warranty.WarrantyOrderConstant;
import com.itheima.bolee.framework.commons.utils.EmptyUtil;
import com.itheima.bolee.insurance.constant.InsuranceConstant;
import com.itheima.bolee.insurance.dto.*;
import com.itheima.bolee.insurance.handler.InsureHandler;
import com.itheima.bolee.insurance.handler.InsureProcessHandler;
import com.itheima.bolee.insurance.pojo.SelfSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @ClassName EarningsInsureHandler.java
 * @Description 理财型：保险投保、创建订单、理财收益
 */
@Service("earningsInsureHandler")
public class EarningsInsureHandler implements InsureHandler {

    @Autowired
    InsureProcessHandler insureProcessHandler;

    @Autowired
    IdentifierGenerator identifierGenerator;

    @Override
    public WarrantyVO doInsure(DoInsureVo doInsureVo) {
        //不支持团险

        //不支持指定生效期

        //投保对象信息

        //保险产品

        //保险方案

        //保险系数

        //被投保人关系ID

        //被投保人信息

        //投保人信息

        //系数唯一性检查

        //理财保险必填参数检测

        //理财投保金额是否符合检测

        //检查投保年龄是否符合检测

        //生产合同编号

        //收益计算

        //犹豫期截止时间、等待期期截止时间、保障截止时间

        //构建保障合同对象
        return null;
    }

    @Override
    public List<WarrantyOrderVO> createWarrantyOrderVO(WarrantyVO warrantyVO) {
        //构建订单合同

        //总周期数
            //首期：利润分成
                //有代理人

                //无代理人
            //非首期：无利润分成

            //多期

                //计划执行时间

                        //按周

                        //按月

                        //按年

            //单期

            //宽限期截止时间

                    //按天

                    //按月

                    //按年


            //复效期截止时间

                    //按天

                    //按月

                    //按年


            //补全投保人信息

        return null;
    }

    @Override
    public EarningVO doEarnings(DoInsureVo doInsureVo) {
        //不支持团险
        if (doInsureVo.getCustomerRelationIds().size()>1){
            throw new RuntimeException("不支持团险");
        }
        //不支持指定生效期
        if (!EmptyUtil.isNullOrEmpty(doInsureVo.getSafeguardStartTime())){
            throw new RuntimeException("不支持指定生效期");
        }
        //投保对象信息
        InsureProcessVO insureProcessVO = insureProcessHandler.buildInsureProcessVO(doInsureVo.getInsuranceId(),
            doInsureVo.getInsurancePlanId(),
            doInsureVo.getCompanyNo(),
            doInsureVo.getInsuranceCoefficentIds());
        //保险产品
        InsuranceVO insuranceVO = insureProcessVO.getInsuranceVO();
        //保险方案
        InsurancePlanVO insurancePlanVO = insureProcessVO.getInsurancePlanVO();
        //保险系数
        List<InsuranceCoefficentVO> coefficentVOs = insureProcessVO.getCoefficents();
        //投保人信息
        CustomerRelationVO applicant = insureProcessHandler.buildApplicant();
        //被投保人信息
        CustomerRelationVO insured = insureProcessHandler.buildInsured(doInsureVo.getCustomerRelationIds().get(0));
        //系数唯一性检查
        Boolean flag = insureProcessHandler.checkBaseOnly(coefficentVOs);
        if (!flag){
            throw new RuntimeException("相同系数多于2个!");
        }
        //理财保险必填参数检测
        flag = insureProcessHandler.checkEarnings(coefficentVOs);
        if (!flag){
            throw new RuntimeException("理财型保险缺少必填参数!");
        }
        //检查投保年龄是否符合检测
        flag = insureProcessHandler.checkAge(insuranceVO,insured);
        if (!flag){
            throw new RuntimeException("投保年龄不符合!");
        }
        //理财投保金额是否符合检测
        flag = insureProcessHandler.checkPrice(insurancePlanVO,
                coefficentVOs,doInsureVo.getPrice());
        if (!flag){
            throw new RuntimeException("投保金额不符合!");
        }
        //试算收益
        return insureProcessHandler.earningsCompute(null,applicant,doInsureVo,insurancePlanVO, coefficentVOs, insured,true);
    }

    @Override
    public String doPremium(DoInsureVo doTrialVo) {
        return null;
    }

}

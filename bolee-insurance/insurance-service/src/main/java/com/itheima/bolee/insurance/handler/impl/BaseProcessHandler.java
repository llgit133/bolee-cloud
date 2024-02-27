package com.itheima.bolee.insurance.handler.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdcardUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ibm.icu.text.DecimalFormat;
import com.itheima.bolee.framework.commons.constant.basic.SuperConstant;
import com.itheima.bolee.framework.commons.constant.insure.InsureConstant;
import com.itheima.bolee.framework.commons.dto.security.CompanyVO;
import com.itheima.bolee.framework.commons.utils.EmptyUtil;
import com.itheima.bolee.framework.commons.utils.SubjectContent;
import com.itheima.bolee.insurance.constant.CustomerRelationConstant;
import com.itheima.bolee.insurance.constant.InsuranceConstant;
import com.itheima.bolee.insurance.constant.PlanEarningsConstant;
import com.itheima.bolee.insurance.constant.WarrantyEarningsOrderConstant;
import com.itheima.bolee.insurance.dto.*;
import com.itheima.bolee.insurance.handler.InsureProcessHandler;
import com.itheima.bolee.insurance.pojo.WarrantyEarningsOrder;
import com.itheima.bolee.insurance.service.*;
import com.itheima.bolee.security.feign.CompanyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName InsureProcessHandler.java
 * @Description 投保业务操作流程
 */
@Service("baseProcessHandler")
public class BaseProcessHandler implements InsureProcessHandler {

    @Autowired
    IInsuranceService insuranceService;

    @Autowired
    IInsurancePlanService insurancePlanService;

    @Autowired
    IPlanSafeguardService planSafeguardService;

    @Autowired
    CompanyFeign companyFeign;

    @Autowired
    IdentifierGenerator identifierGenerator;

    @Autowired
    IInsuranceCoefficentService insuranceCoefficentService;

    @Autowired
    ICustomerRelationService customerRelationService;

    @Override
    public InsureProcessVO buildInsureProcessVO(Long insuranceId, Long insurancePlanId, String companyNo, List<Long> InsuranceCoefficentIds) {
        //保险产品

        //保险方案

        //方案保障项

        //保险公司

        //保险系数项

        //返回结果
        return null;
    }

    @Override
    public CustomerRelationVO buildInsured(String customerRelationId) {
        return customerRelationService.findById(customerRelationId);
    }

    @Override
    public List<CustomerRelationVO> buildInsureds(List<String> customerRelationIds) {
        return customerRelationService.findInId(customerRelationIds);
    }

    @Override
    public CustomerRelationVO buildApplicant() {
        //当前登录人
        Long customerId = SubjectContent.getUserVO().getId();
        //当前登录人关系
        List<CustomerRelationVO> list = customerRelationService
            .findList(CustomerRelationVO.builder().customerId(customerId).build());
        //投保人
        return list.stream()
            .filter(n -> { return n.getRelation().equals(CustomerRelationConstant.SELF);})
            .findFirst().get();
    }

    @Override
    public Boolean checkBaseOnly(List<InsuranceCoefficentVO> insuranceCoefficentVOs) {
        //保险系数相同系数只可拥有一个

        return true;
    }

    @Override
    public Boolean checkAge(InsuranceVO insuranceVO, CustomerRelationVO insured) {
        //保险年龄限制信息，包括4部分：起始、起始单位、结束、结束单位

        //限制补全则无限制

        //根据身份证获得当年龄,年纪为0则表示不满一周

        //限制起始单位：年，年纪小于1岁，直接拒绝

        //限制起始单位：年，年纪大于1岁，判断年纪是否在起始时间和结束时间的闭空间内

        //限制起始单位：天，年纪小于1岁，获得被投保人出生天数，判断出生天数是否大于开始时间

        //限制起始单位：天，年纪大于1岁，判断结束时间是否大于年纪

        return true;
    }

    @Override
    public Boolean checkPrice(InsurancePlanVO insurancePlanVO, List<InsuranceCoefficentVO> insuranceCoefficentVOs, BigDecimal price) {
        //投入方式

        //趸交

        //定投

        //单位判定
        return null;
    }

    @Override
    public Boolean checkSafeguard(List<InsuranceCoefficentVO> coefficentVOs) {
        //系数：必须包含连续投保系数
        List<InsuranceCoefficentVO> autoWarrantyExtensionList = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.AUTO_WARRANTY_EXTENSION);})
            .collect(Collectors.toList());
        if (EmptyUtil.isNullOrEmpty(autoWarrantyExtensionList)){
            return false;
        }
        //系数：必须包含保障期限
        List<InsuranceCoefficentVO> protectionPeriodList = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.PROTECTION_PERIOD);})
            .collect(Collectors.toList());
        if (EmptyUtil.isNullOrEmpty(protectionPeriodList)){
            return false;
        }
        //系数：必须包含投入时长
        List<InsuranceCoefficentVO> buyDurationList = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.PERIODIC); })
            .collect(Collectors.toList());
        if (EmptyUtil.isNullOrEmpty(buyDurationList)){
            return false;
        }
        //系数：付款方式
        List<InsuranceCoefficentVO> payMentList = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.PAY_MENT); })
            .collect(Collectors.toList());
        return !EmptyUtil.isNullOrEmpty(payMentList);
    }

    @Override
    public Boolean checkTravel(List<InsuranceCoefficentVO> coefficentVOs) {
        //系数：必须包含保障期限
        List<InsuranceCoefficentVO> protectionPeriodList = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.PROTECTION_PERIOD);})
            .collect(Collectors.toList());
        return !EmptyUtil.isNullOrEmpty(protectionPeriodList);
    }

    @Override
    public Boolean checkEarnings(List<InsuranceCoefficentVO> coefficentVOs) {
        //系数：必须含领取开始

        //系数：必须含领取周期

        //系数：必须投入方式

        //判定：是否为追投方式

            //系数：必须含投入周期单位

            //系数：必须包含投入周期时长

        return true;
    }

    @Override
    public List<InsuranceCoefficentVO> ageHandler(List<InsuranceCoefficentVO> coefficentVOs, Long insuranceId, CustomerRelationVO insured) {
        //获得保险年龄系数
        InsuranceCoefficentVO insuranceCoefficentVO = InsuranceCoefficentVO.builder()
            .coefficentKey(InsuranceConstant.RANGE_AGE)
            .insuranceId(insuranceId)
            .build();
        List<InsuranceCoefficentVO> rangeAgeList = insuranceCoefficentService.findList(insuranceCoefficentVO);
        if (!EmptyUtil.isNullOrEmpty(rangeAgeList)) {
            //根据身份证获得当年龄
            int age = IdcardUtil.getAgeByIdCard(insured.getIdentityCard(), new Date());
            List<InsuranceCoefficentVO> rangeAgeListHandler = rangeAgeList.stream().filter(n -> {
                //转换为RangAgeVo对象
                RangJsonAttribute rangJsonAttribute = JSONObject.parseObject(n.getCoefficentValue(), RangJsonAttribute.class);
                //保险起始单位：天,且获得年龄等于0，则要进行天数判断
                if (InsuranceConstant.DAY.equals(rangJsonAttribute.getStartUnit())&&age==0) {
                    //获得投保人生日
                    String birthByIdCard = IdcardUtil.getBirthByIdCard(insured.getIdentityCard());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    LocalDate startDate = LocalDate.parse(birthByIdCard, formatter);
                    long until = startDate.until(LocalDate.now(),ChronoUnit.DAYS);
                    return Long.parseLong(rangJsonAttribute.getStart()) < until;
                //保险起始单位：年,且获得年龄大于0，则要进行年龄判断
                } else if (InsuranceConstant.YEAR.equals(rangJsonAttribute.getStartUnit())&&age>0) {
                    return Long.parseLong(rangJsonAttribute.getStart()) <= age
                        &&Long.parseLong(rangJsonAttribute.getEnd()) > age;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            //确定投入年龄系数后需要累加
            if (!EmptyUtil.isNullOrEmpty(rangeAgeListHandler)) {
                coefficentVOs.addAll(rangeAgeListHandler);
            }
        }
        return coefficentVOs;
    }

    @Override
    public List<InsuranceCoefficentVO> numberOfPeopleHandler(List<InsuranceCoefficentVO> coefficentVOs, Long insuranceId, List<CustomerRelationVO> insureds) {
        //保险：人数系数
        InsuranceCoefficentVO insuranceCoefficentVO = InsuranceCoefficentVO.builder()
            .coefficentKey(InsuranceConstant.NUMBER_OF_PEOPLE)
            .insuranceId(insuranceId)
            .build();
        List<InsuranceCoefficentVO> numberOfPeopleList = insuranceCoefficentService.findList(insuranceCoefficentVO);
        if (!EmptyUtil.isNullOrEmpty(numberOfPeopleList)) {
            //获得投保人数
            Long numberOfPeople = Long.valueOf(insureds.size());
            List<InsuranceCoefficentVO> numberOfPeopleListHandler = numberOfPeopleList.stream().filter(n -> {
                //转换为RangJsonAttribute处理对象
                RangJsonAttribute rangJsonAttribute = JSONObject.parseObject(n.getCoefficentValue(), RangJsonAttribute.class);
                //投保人所在范围
                return numberOfPeople >= Long.valueOf(rangJsonAttribute.getStart())&&
                        numberOfPeople < Long.valueOf(rangJsonAttribute.getEnd());
            }).collect(Collectors.toList());
            //确定投入人数系数后需要累加
            if (!EmptyUtil.isNullOrEmpty(numberOfPeopleListHandler)) {
                coefficentVOs.addAll(numberOfPeopleListHandler);
            }
        }
        return coefficentVOs;
    }

    @Override
    public String premiumComputeTravel(InsurancePlanVO insurancePlanVO, List<InsuranceCoefficentVO> coefficentVOs) {
        //最终系数合=累加所有系数维度值[不包含投保人数系数]+1
        BigDecimal sumScore = coefficentVOs.stream()
            .filter(n->{ return !n.getCoefficentKey().equals(InsuranceConstant.NUMBER_OF_PEOPLE);})
            .map(n -> {return !EmptyUtil.isNullOrEmpty(n.getScore())?n.getScore():BigDecimal.ZERO;})
            .reduce(BigDecimal.ZERO, BigDecimal::add).add(BigDecimal.ONE);
        //系数:投保人数
        InsuranceCoefficentVO numberOfpeople = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.NUMBER_OF_PEOPLE);})
            .findFirst().get();
        BigDecimal numberOfpeopleScore = numberOfpeople.getScore();
        //最终投保金额=方案起步价X最终系数合X投保人数系数
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(insurancePlanVO.getPrice().multiply(sumScore).multiply(numberOfpeopleScore));
    }

    @Override
    public String premiumComputeSafeguard(InsurancePlanVO insurancePlanVO, List<InsuranceCoefficentVO> coefficentVOs) {
        //最终系数合=1+系数A+系数B+系数C+......[不含缴费方式]
        BigDecimal sumScore = coefficentVOs.stream()
            .filter(n->{ return !n.getCoefficentKey().equals(InsuranceConstant.PAY_MENT);})
            .map(n -> {return !EmptyUtil.isNullOrEmpty(n.getScore())?n.getScore():BigDecimal.ZERO;})
            .reduce(BigDecimal.ZERO, BigDecimal::add).add(BigDecimal.ONE);
        //付款方式
        InsuranceCoefficentVO payment = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.PAY_MENT);})
            .findFirst().get();
        BigDecimal PaymentScore = payment.getScore();
        //最终投保金额=方案起步价X最终系数X缴费方式系数
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(insurancePlanVO.getPrice().multiply(sumScore).multiply(PaymentScore));
    }

    @Autowired
    IWarrantyEarningsOrderService warrantyEarningsOrderService;

    @Override
    public EarningVO earningsCompute(String warrantyNo,CustomerRelationVO applicant,DoInsureVo doInsureVo,
             InsurancePlanVO insurancePlanVO,
             List<InsuranceCoefficentVO> coefficentVOs,
             CustomerRelationVO insured,
             Boolean isTrial) {
        //系数：投入方式

        //转换JsonAttribute

        //定义：投入周期时长

        //定义：投入周期单位

        //定义：投入总周期数

        //定义：投入总金额

        //定投

        //一次性

        //投入结束时间

        //领取周期单位

        //领取起始时间

        //理财影响系数:影响最终收益的系数

        //领取计划

        //领取总金额

        return null;
    }

    @Override
    public BigDecimal premiumsHandler(List<InsuranceCoefficentVO> coefficentVOs, BigDecimal premiums) {
        //最终系数合=累加所有系数维度值+1
        BigDecimal sumScore = coefficentVOs.stream()
            .map(n -> {return !EmptyUtil.isNullOrEmpty(n.getScore())?n.getScore():BigDecimal.ZERO;})
            .reduce(BigDecimal.ZERO, BigDecimal::add).add(BigDecimal.ONE);
        //最终投保金额=原始保费X最终系数
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return premiums.multiply(sumScore);
    }

    @Override
    public List<PeriodicVo> periodicVos( String warrantyNo,CustomerRelationVO applicant,CustomerRelationVO insured,
             InsurancePlanVO insurancePlanVO, BigDecimal premiums,
             List<InsuranceCoefficentVO> coefficentVOs, String actualGetPeriodicUnit,
             LocalDateTime actualGetStartTime,Boolean isTrial,DoInsureVo doInsureVo) {
        //领取计划存储

        //保险给付计划

        //此处定义的jsonAttributes的val为每年领取比例

        //终身领取型

            //养老试算时，用户指定测算时间

            //非养老试算时，合同规定截止时间，领至终身假定150岁

            //计算领取总年数=领取结束时间-领取开始时间

                //领取截止时间

                //被投保人当前年龄

                //领取截止时间

            //按月领取

            //按年领取

        //固定期限领取

        //非试算，需要保存给付计划订单

        return null;
    }

    @Override
    public Integer earningsPeriods(List<InsuranceCoefficentVO> coefficentVOs) {
        //投入周期时长【投3年、投5年、投10年】

        //投入周期单位

        //周期计算

        return null;
    }

    @Override
    public Integer SafeguardPeriods(List<InsuranceCoefficentVO> coefficentVOs) {
        //投入周期时长【投3年、投5年、投10年】
        BigDecimal periodic = periodic(coefficentVOs);
        //投入周期单位
        String periodicUnit = payMent(coefficentVOs);
        //周期计算
        switch (periodicUnit){
            //按周
            case InsuranceConstant.WEEK:
                periodic = periodic.multiply(new BigDecimal(52));
                break;
            //按月
            case InsuranceConstant.MONTH:
                periodic = periodic.multiply(new BigDecimal(12));
                break;
            //按年
            case InsuranceConstant.YEAR:
                periodic = periodic.multiply(new BigDecimal(1));
                break;
            default:
                throw new RuntimeException("周期单位不符合");
        }
        return periodic.intValue();
    }

    @Override
    public WarrantyTimeVO timeSetup(DoInsureVo doInsureVo,List<InsuranceCoefficentVO> coefficentVOs, InsuranceVO insuranceVO) {
        //保障起始时间
        LocalDateTime safeguardStartTime = EmptyUtil.isNullOrEmpty(doInsureVo.getSafeguardStartTime())?
            LocalDateTime.now():doInsureVo.getSafeguardStartTime();
        //犹豫期截止时间
        LocalDateTime hesitationTime = null;
        //等待期截止时间
        LocalDateTime waitTime = null;
        //保障截止时间
        LocalDateTime safeguardEndTime = null;
        //医疗和重疾类保险才有犹豫期和等待期
        if (doInsureVo.getCheckRule().equals(InsureConstant.CHECK_RULE_0)||
            doInsureVo.getCheckRule().equals(InsureConstant.CHECK_RULE_1)){
            //犹豫期截止时间
            hesitationTime = LocalDateTimeUtil.offset(LocalDateTime.now(), insuranceVO.getHesitation(), ChronoUnit.DAYS);
            //等待期截止时间
            waitTime = LocalDateTimeUtil.offset(LocalDateTime.now(), insuranceVO.getWaits(), ChronoUnit.DAYS);
        }
        //养老和储蓄类保险无保障结束时间
        if (!doInsureVo.getCheckRule().equals(InsureConstant.CHECK_RULE_3)&&
            !doInsureVo.getCheckRule().equals(InsureConstant.CHECK_RULE_4)){
            //保障期限
            InsuranceCoefficentVO protectionPeriod = coefficentVOs.stream()
                .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.PROTECTION_PERIOD);})
                .findFirst().get();
            String valProtectionPeriod = JSONObject.parseObject(protectionPeriod.getCoefficentValue(), JsonAttribute.class).getUnit();
            String calculatedVal = JSONObject.parseObject(protectionPeriod.getCoefficentValue(), JsonAttribute.class).getCalculatedVal();
            Integer rearwardShift = 0;
            //推迟：年计算
            if (valProtectionPeriod.equals(InsuranceConstant.YEAR)) {
                rearwardShift = Integer.valueOf(calculatedVal);
                safeguardEndTime = LocalDateTimeUtil.offset(safeguardStartTime, rearwardShift, ChronoUnit.YEARS);
            }
            //推迟：月计算
            if (valProtectionPeriod.equals(InsuranceConstant.MONTH)){
                rearwardShift = Integer.valueOf(calculatedVal);
                safeguardEndTime = LocalDateTimeUtil.offset(safeguardStartTime, rearwardShift, ChronoUnit.MONTHS);
            }
            //推迟：天计算
            if (valProtectionPeriod.equals(InsuranceConstant.DAY)){
                rearwardShift = Integer.valueOf(calculatedVal);
                safeguardEndTime = LocalDateTimeUtil.offset(safeguardStartTime, rearwardShift, ChronoUnit.DAYS);
            }
        }
        return WarrantyTimeVO.builder()
            .hesitationTime(hesitationTime)
            .waitTime(waitTime)
            .safeguardStartTime(safeguardStartTime)
            .safeguardEndTime(safeguardEndTime).build();
    }

    @Override
    public String autoWarrantyExtension(List<InsuranceCoefficentVO> coefficentVOs) {
        //是否自动续保
        InsuranceCoefficentVO autoWarrantyExtension = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.AUTO_WARRANTY_EXTENSION);})
            .findFirst().get();
        return JSONObject.parseObject(autoWarrantyExtension.getCoefficentValue(),JsonAttribute.class).getVal();
    }

    @Override
    public BigDecimal periodic(List<InsuranceCoefficentVO> coefficentVOs) {
        //投入时长
        InsuranceCoefficentVO periodic = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.PERIODIC);})
            .findFirst().get();
        return new BigDecimal(JSONObject.parseObject(periodic.getCoefficentValue(),JsonAttribute.class).getCalculatedVal());
    }

    @Override
    public String periodicUnit(List<InsuranceCoefficentVO> coefficentVOs) {
        //投入周期单位
        InsuranceCoefficentVO periodicUnit = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.PERIODIC_UNIT);})
            .findFirst().get();
        return JSONObject.parseObject(periodicUnit.getCoefficentValue(),JsonAttribute.class).getVal();
    }

    @Override
    public String payMent(List<InsuranceCoefficentVO> coefficentVOs) {
        //投入周期单位
        InsuranceCoefficentVO payMent = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.PAY_MENT);})
            .findFirst().get();
        return JSONObject.parseObject(payMent.getCoefficentValue(),JsonAttribute.class).getVal();
    }

    @Override
    public LocalDateTime actualGetStartTime(List<InsuranceCoefficentVO> coefficentVOs, CustomerRelationVO insured) {
        //领取开始系数
        InsuranceCoefficentVO actualGet = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.ACTUAL_GET_START);})
            .findFirst().get();
        JsonAttribute jsonActualGet = JSONObject.parseObject(actualGet.getCoefficentValue(), JsonAttribute.class);
        BigDecimal compoundAnnualInterest = BigDecimal.ZERO;
        //多少年后开始领取
        if (InsuranceConstant.YEAR.equals(jsonActualGet.getUnit())){
            compoundAnnualInterest = new BigDecimal(jsonActualGet.getCalculatedVal());
        }
        //多少岁后开始领取
        if (InsuranceConstant.AGE.equals(jsonActualGet.getUnit())){
            //被投保人当前年龄
            int age = IdcardUtil.getAgeByIdCard(insured.getIdentityCard(), new Date());
            compoundAnnualInterest = new BigDecimal(jsonActualGet.getCalculatedVal()).subtract(new BigDecimal(age));
        }
        return LocalDateTimeUtil.offset(LocalDateTime.now(), compoundAnnualInterest.longValue(), ChronoUnit.YEARS);
    }

    @Override
    public String actualGetPeriodicUnit(List<InsuranceCoefficentVO> coefficentVOs) {
        //领取周期
        InsuranceCoefficentVO actualGetPeriodicUnit = coefficentVOs.stream()
            .filter(n -> { return n.getCoefficentKey().equals(InsuranceConstant.ACTUAL_GET_UNIT);})
            .findFirst().get();
        JsonAttribute jsonActualGet = JSONObject.parseObject(actualGetPeriodicUnit.getCoefficentValue(), JsonAttribute.class);
        return jsonActualGet.getVal();
    }
}

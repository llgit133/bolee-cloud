package com.itheima.bolee.sms.handler.aliyun;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.itheima.bolee.framework.commons.constant.sms.SmsConstant;
import com.itheima.bolee.framework.commons.dto.sms.SmsSignVO;
import com.itheima.bolee.framework.commons.enums.sms.SmsSignEnum;
import com.itheima.bolee.framework.commons.exception.ProjectException;
import com.itheima.bolee.framework.commons.utils.BeanConv;
import com.itheima.bolee.framework.commons.utils.EmptyUtil;
import com.itheima.bolee.framework.commons.utils.ExceptionsUtil;
import com.itheima.bolee.sms.handler.SmsSignHandler;
import com.itheima.bolee.sms.handler.aliyun.config.AliyunSmsConfig;
import com.itheima.bolee.sms.pojo.SmsSign;
import com.itheima.bolee.sms.service.ISmsSignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SmsSignAdapter.java
 * @Description 阿里云签名处理器接口
 */
@Slf4j
@Service("aliyunSmsSignHandler")
public class AliyunSmsSignHandlerImpl implements SmsSignHandler {

    @Autowired
    ISmsSignService smsSignService;

    @Autowired
    AliyunSmsConfig aliyunSmsConfig;

    @Override
    public SmsSign addSmsSign(SmsSignVO smsSignVO){
        //查询当前签名是否保存过

        //保存过则同步远程数据

            //查询当前签名在远程的是否存在


                //受理成功

                //审核通过

                //审核失败

                //保存结果

        //构建请求对象

        //签名名称

        //签名来源

        //申请说明

        //证明材料SignFileList

        //获得客户端

        //发起三方请求

        //同步结果并保存

            //受理成功

            //审核中


            //受理失败

        //保持信息
        return null;
    }

    @Override
    public Boolean deleteSmsSign(SmsSignVO smsSignVO){
        DeleteSmsSignRequest deleteSmsSignRequest = new DeleteSmsSignRequest();;
        deleteSmsSignRequest.setSignName(smsSignVO.getSignName());
        Client client =aliyunSmsConfig.queryClient();
        DeleteSmsSignResponse response = null;
        try {
            response = client.deleteSmsSign(deleteSmsSignRequest);
        } catch (Exception e) {
            log.error("请求删除阿里云签名出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.DELETE_FAIL);
        }
        return smsSignService.removeById(smsSignVO.getId());
    }

    @Override
    public SmsSign modifySmsSign(SmsSignVO smsSignVO){
        //构建请求对象

        //签名名称

        //签名来源。取值：

        //申请说明

        //证明材料SignFileList

        //同步结果并保存

        //处理结果

            //受理成功

            //审核中


            //受理失败

            //重置审核状态


        return null;
    }

    private QuerySmsSignResponse query(SmsSignVO smsSignVO) {
        QuerySmsSignRequest querySmsSignRequest = new QuerySmsSignRequest();
        querySmsSignRequest.setSignName(smsSignVO.getSignName());
        // 复制代码运行请自行打印 API 的返回值
        Client client =aliyunSmsConfig.queryClient();
        QuerySmsSignResponse response = null;
        try {
            response = client.querySmsSign(querySmsSignRequest);
        } catch (Exception e) {
            log.error("请求查询阿里云签名出错：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(SmsSignEnum.SELECT_FAIL);
        }
        return response;
    }

    @Override
    public SmsSign querySmsSign(SmsSignVO smsSignVO){
        QuerySmsSignResponse response =query(smsSignVO);
        //受理状态
        String code = response.getBody().getCode();
        String message = response.getBody().getMessage();
        SmsSign smsSign = BeanConv.toBean(smsSignVO, SmsSign.class);
        if ("OK".equals(code)){
            Integer SignStatus =response.getBody().getSignStatus();
            //审核通过
            if (SignStatus==1){
                smsSignVO.setAuditStatus(SmsConstant.STATUS_AUDIT_0);
                smsSignVO.setAuditMsg("审核通过");
                smsSignService.updateById(smsSign);

            //审核失败
            }else if (SignStatus==2){
                smsSignVO.setAuditStatus(SmsConstant.STATUS_AUDIT_1);
                smsSignVO.setAuditMsg(response.getBody().getReason());
                smsSignService.updateById(smsSign);
            }else {
                log.info("阿里云签名：{},审核中", response.getBody().getSignName());
            }
        }else {
            log.warn("受理查询阿里云签名出错：{}", message);
            throw new ProjectException(SmsSignEnum.SELECT_FAIL);
        }
        return smsSign;
    }
}

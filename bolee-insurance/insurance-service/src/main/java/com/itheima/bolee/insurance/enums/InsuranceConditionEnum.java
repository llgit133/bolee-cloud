package com.itheima.bolee.insurance.enums;

import com.itheima.bolee.framework.commons.enums.basic.IBaseEnum;

/**
* @ClassName InsuranceConditionEnum.java
* @Description 保险筛选项枚举
*/

public enum InsuranceConditionEnum implements IBaseEnum {

    PAGE_FAIL(53001, "查询保险筛选项分页失败"),
    LIST_FAIL(53002, "查询保险筛选项列表失败"),
    FIND_ONE_FAIL(53003, "查询保险筛选项对象失败"),
    SAVE_FAIL(53004, "保存保险筛选项失败"),
    UPDATE_FAIL(53005, "修改保险筛选项失败"),
    DEL_FAIL(53006, "删除保险筛选项失败")
    ;

    private Integer code;

    private String msg;

    InsuranceConditionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

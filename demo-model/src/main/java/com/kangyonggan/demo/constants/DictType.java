package com.kangyonggan.demo.constants;

import com.kangyonggan.demo.annotation.Enum;
import lombok.Getter;

/**
 * 字典类型枚举
 *
 * @author kangyonggan
 * @since 8/9/18
 */
@Enum
public enum DictType {

    /**
     * 证件类型
     */
    ID_TYPE("ID_TYPE", "证件类型"),

    /**
     * 币种
     */
    CUR_CO("CUR_CO", "币种");


    /**
     * 类型代码
     */
    @Getter
    private final String code;

    /**
     * 类型名称
     */
    @Getter
    private final String name;

    DictType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
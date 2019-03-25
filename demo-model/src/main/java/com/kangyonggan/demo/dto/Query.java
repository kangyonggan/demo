package com.kangyonggan.demo.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 请求参数
 *
 * @author kangyonggan
 * @since 5/4/18
 */
public class Query extends HashMap<String, Object> implements Serializable {

    /**
     * 获取String类型的值
     *
     * @param name 参数名
     * @return 返回参数值
     */
    public String getString(String name) {
        Object obj = get(name);
        if (obj == null) {
            return null;
        }
        return String.valueOf(obj);
    }

    /**
     * 获取int类型的值
     *
     * @param name 参数名
     * @return 返回参数值
     */
    public Integer getInteger(String name) {
        return Integer.parseInt(getString(name));
    }

    /**
     * 获取int类型的值, 带有默认值
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @return 返回参数值
     */
    public Integer getInteger(String name, Integer defaultValue) {
        try {
            return Integer.parseInt(getString(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 获取Date类型的值
     *
     * @param name 参数名
     * @return 返回参数值
     */
    public Date getDate(String name) {
        String date = getString(name);
        if (date != null && date.trim().length() > 0) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                throw new RuntimeException("日期解析异常：" + date);
            }
        }

        return null;
    }

}

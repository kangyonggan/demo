package com.kangyonggan.demo.constants;


import lombok.Getter;

/**
 * @author kangyonggan
 * @since 5/4/18
 */
public enum Resp {

    /**
     * 操作成功
     */
    SUCCESS("0000", "操作成功"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR("1002", "密码错误，如果忘记密码请清除Cookie！"),

    /**
     * 用户名或密码错误
     */
    USERNAME_PASSWORD_ERROR("1001", "用户名或密码错误！"),

    /**
     * 权限不足
     */
    PERMISSION_DENIED("9997", "您无权访问所请求的资源！"),

    /**
     * 登录已失效
     */
    INVALID_LOGIN("9998", "您尚未登录或登录已失效！"),

    /**
     * 未知错误
     */
    FAILURE("9999", "未知错误，请联系管理员！");

    /**
     * 响应码
     */
    @Getter
    String respCo;

    /**
     * 响应消息
     */
    @Getter
    String respMsg;

    Resp(String respCo, String respMsg) {
        this.respCo = respCo;
        this.respMsg = respMsg;
    }

}

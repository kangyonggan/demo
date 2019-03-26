package com.kangyonggan.demo.util;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具类
 *
 * @author kangyonggan
 * @since 5/4/18
 */
public final class IpUtil {

    /**
     * unknown
     */
    private static final String UNKNOWN = "unknown";

    /**
     * 私有构造, 任何时候都不能实例化
     */
    private IpUtil() {

    }

    /**
     * 获取请求IP
     *
     * @param request Http请求
     * @return 返回请求IP
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

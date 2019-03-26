package com.kangyonggan.demo.dto;

import com.kangyonggan.demo.constants.Resp;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 通用响应
 *
 * @author kangyonggan
 * @since 5/4/18
 */
public final class Response extends HashMap<String, Object> implements Serializable {

    /**
     * 响应码的key
     */
    public static final String RESP_CO = "respCo";

    /**
     * 响应消息的key
     */
    public static final String RESP_MSG = "respMsg";

    /**
     * 私有化构造
     */
    private Response() {

    }

    /**
     * 获取一个空的响应
     *
     * @return 返回一个空的响应
     */
    public static Response getResponse() {
        return new Response();
    }

    /**
     * 获取一个成功的响应
     *
     * @return 返回一个成功响应
     */
    public static Response getSuccessResponse() {
        Response response = new Response();
        response.put(RESP_CO, Resp.SUCCESS.getRespCo());
        response.put(RESP_MSG, Resp.SUCCESS.getRespMsg());
        return response;
    }

    /**
     * 获取一个失败的响应
     *
     * @return 返回一个失败响应
     */
    public static Response getFailureResponse() {
        return getFailureResponse(Resp.FAILURE.getRespMsg());
    }

    /**
     * 获取一个失败的响应
     *
     * @param msg 失败消息
     * @return 返回一个失败响应
     */
    public static Response getFailureResponse(String msg) {
        return getFailureResponse(Resp.FAILURE.getRespCo(), msg);
    }

    /**
     * 获取一个自定义失败的响应
     *
     * @param code 失败代码
     * @param msg  失败消息
     * @return 返回一个失败响应
     */
    public static Response getFailureResponse(String code, String msg) {
        Response response = new Response();
        response.put(RESP_CO, code);
        response.put(RESP_MSG, msg);
        return response;
    }

    /**
     * 响应置为失败
     *
     * @return 返回置为失败的响应
     */
    public Response failure() {
        failure(Resp.FAILURE.getRespMsg());
        return this;
    }

    /**
     * 响应置为失败
     *
     * @param msg 失败消息
     * @return 返回置为失败的响应
     */
    public Response failure(String msg) {
        return failure(Resp.FAILURE.getRespCo(), msg);
    }

    /**
     * 响应置为自定义的失败
     *
     * @param code 失败代码
     * @param msg  失败消息
     * @return 返回置为失败的响应
     */
    public Response failure(String code, String msg) {
        put(RESP_CO, code);
        put(RESP_MSG, msg);
        return this;
    }

    /**
     * 设置成功消息
     *
     * @return 返回设置为成功的消息
     */
    public Response success() {
        return success(Resp.SUCCESS.getRespMsg());
    }

    /**
     * 设置成功消息
     *
     * @param msg 成功消息
     * @return 返回设置为成功的消息
     */
    public Response success(String msg) {
        put(RESP_CO, Resp.SUCCESS.getRespCo());
        put(RESP_MSG, msg);
        return this;
    }

    /**
     * 判断响应是否成功
     *
     * @return 成功返回true，失败返回false
     */
    public boolean isSuccess() {
        return Resp.SUCCESS.getRespCo().equals(get(RESP_CO));
    }
}

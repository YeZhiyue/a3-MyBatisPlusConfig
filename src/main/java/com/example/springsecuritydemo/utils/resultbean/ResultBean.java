package com.example.springsecuritydemo.utils.resultbean;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: wangzy
 * @Date: 2019/3/31 11:31
 * @Description:
 */
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Builder.Default
    private int code = HttpStatus.HTTP_OK;

    @Getter
    @Setter
    @Builder.Default
    private String msg = "success";

    @Getter
    @Setter
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = HttpStatus.HTTP_INTERNAL_ERROR;
    }

    public static <T> ResultBean<T> resultBean(T data, int code, String msg) {
        ResultBean<T> resultBean = new ResultBean<>();
        resultBean.setCode(code);
        resultBean.setData(data);
        resultBean.setMsg(msg);
        return resultBean;
    }

    public static <T> ResultBean<T> restResult(T data, ErrorCodeInfo errorCode) {
        return resultBean(data, errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> ResultBean<T> ok() {
        return resultBean(null, CommonConstants.SUCCESS, null);
    }

    public static <T> ResultBean<T> ok(T data) {
        return resultBean(data, CommonConstants.SUCCESS, null);
    }

    public static <T> ResultBean<T> ok(T data, String msg) {
        return resultBean(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> ResultBean<T> failed() {
        return resultBean(null, CommonConstants.FAIL, null);
    }

    public static <T> ResultBean<T> failed(String msg) {
        return resultBean(null, CommonConstants.FAIL, msg);
    }

    public static <T> ResultBean<T> failed(T data) {
        return resultBean(data, CommonConstants.FAIL, null);
    }

    public static <T> ResultBean<T> failed(T data, String msg) {
        return resultBean(data, CommonConstants.FAIL, msg);
    }
}

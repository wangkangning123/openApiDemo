package com.openapi.demo.customConfig.error;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

public class ErrorMessage<T> {

    @ApiModelProperty(value = "状态码")
    private final int code;

    @ApiModelProperty(value = "提示语")
    private final String msg;

    @ApiModelProperty(value = "状态(true=成功)")
    private final boolean status;

    @ApiModelProperty(value = "请求方式")
    private final HttpMethod httpMethod;

    @ApiModelProperty(value = "跳转页面")
    private String path;

    public ErrorMessage(String msg, Exception e, NativeWebRequest request) {
        this.code = 400;
        this.msg = msg;
        this.status = false;
        this.httpMethod = ((ServletWebRequest) request).getHttpMethod();
        this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    public ErrorMessage(int code, String msg, Exception e, NativeWebRequest request) {
        this.code = code;
        this.msg = msg;
        this.status = code < 300;
        this.httpMethod = ((ServletWebRequest) request).getHttpMethod();
        this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Message [code=" +
            code +
            ", msg=" +
            msg +
            ", status=" +
            status +
            ", httpMethod=" +
            httpMethod +
            "]";
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}

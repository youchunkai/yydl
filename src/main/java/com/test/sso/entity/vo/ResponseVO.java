package com.test.sso.entity.vo;

import com.test.sso.entity.dto.abstracts.AbstractResponse;
import lombok.Data;

import java.io.Serializable;

/**
 * @类名： ResponseVO
 * @描述: TODO
 * @作者： zxlei
 * @创建日期： 2019/11/27
 * @版本号： V1.0
 **/
@Data
public class ResponseVO<T> extends AbstractResponse {



    private boolean result;

    private T data;

    private PageInfo pageinfo;

    private ResponseVO(){

    }


    private ResponseVO(int code, String msg, boolean result, T data, PageInfo pageinfo) {
        this.code = code;
        this.msg = msg;
        this.result = result;
        this.data = data;
        this.pageinfo = pageinfo;
    }

    public int getCode() {
        return code;
    }

    public ResponseVO setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseVO setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isResult() {
        return result;
    }

    public ResponseVO setResult(boolean result) {
        this.result = result;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageInfo getPageinfo() {
        return pageinfo;
    }

    public void setPageinfo(PageInfo pageinfo) {
        this.pageinfo = pageinfo;
    }

    /**
     * Desc 创建一个带返回值与分页信息的返回体
     * Param: data 返回的数据  page分页信息
     * @return ResponseVO 返回体
     */
    public static <T> ResponseVO<T> createOKWithDataWithPageinfo(String msg,T data,PageInfo pageInfo){
        return new ResponseVO(ResponseCode.OK, msg, true, data, pageInfo);
    }


    /**
     * Desc 创建一个带返回值 不带分页信息的返回体
     * Param: data 返回的数据  page分页信息
     * @return ResponseVO 返回体
     */
    public static <T> ResponseVO<T> createOKWithDataWithoutPageinfo(String msg,T data){
        return createOKWithDataWithPageinfo(msg,data, null);
    }


    /**
     * Desc 自定义返回体
     * Param: code 状态码  msg 执行信息  result 执行结果  data 返回的数据  page分页信息
     * @return ResponseVO 返回体
     */
    public static <T> ResponseVO<T> create(int code, String msg, boolean result, T data, PageInfo pageinfo) {
        return new ResponseVO<T>(code, msg, result, data, pageinfo);
    }

    /**
     * Desc 自定义异常返回体
     * Param: code 状态码  msg 执行信息  result 执行结果  data 返回的数据  page分页信息
     * @return ResponseVO 返回体
     */
    public static <T> ResponseVO<T> createWithException(int code, String msg) {
        return new ResponseVO<T>(code, msg, false, null, null);
    }

    /**
     * Desc 创建一个空返回体
     * Param: code 状态码  msg 执行信息  result 执行结果  data 返回的数据  page分页信息
     * @return ResponseVO 返回体
     */
    public static <T> ResponseVO<T> createEmptyResponse() {
        return new ResponseVO<T>();
    }
}

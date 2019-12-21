package com.test.sso.utils;

import com.test.sso.entity.dto.abstracts.AbstractResponse;
import com.test.sso.entity.vo.ResponseCode;
import com.test.sso.exception.ValidateException;

import java.io.IOException;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/16
 **/
public class ExceptionUtil {

    /**
     * 将下层抛出的异常转换为resp返回码
     *
     * @param e Exception
     * @return
     */
    public static AbstractResponse handlerException4biz(AbstractResponse response, Exception e){
        Exception ex = null;
        if (!(e instanceof Exception)) {
            return null;
        }
        if (e instanceof ValidateException) {
            response.setCode(((ValidateException) e).getErrorCode());
            response.setMsg(e.getMessage());
        }else if(e instanceof Exception){
            response.setCode(ResponseCode.BAD_REQUEST);
            response.setMsg(e.getMessage());
        }
        return response;
    }
}

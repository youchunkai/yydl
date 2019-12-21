package com.test.sso.utils;

import com.test.sso.entity.dto.abstracts.AbstractResponse;
import com.test.sso.entity.vo.ResponseCode;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/16
 **/
public class ExceptionProcessorUtils {

    public static void wrapperHandlerException(AbstractResponse response, Exception e){
        try {
            ExceptionUtil.handlerException4biz(response,e);
        } catch (Exception ex) {
            response.setCode(ResponseCode.BAD_REQUEST);
            response.setMsg(response.getMsg());
        }
    }
}

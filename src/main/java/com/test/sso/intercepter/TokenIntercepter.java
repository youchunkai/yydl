package com.test.sso.intercepter;

import com.alibaba.fastjson.JSON;
import com.test.sso.annotation.Anoymous;
import com.test.sso.entity.vo.ResponseCode;
import com.test.sso.entity.vo.ResponseVO;
import com.test.sso.utils.ExceptionProcessorUtils;
import com.test.sso.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/16
 **/
public class TokenIntercepter extends HandlerInterceptorAdapter {

    Logger log = LoggerFactory.getLogger(TokenIntercepter.class);


    public static String ACCESS_TOKEN="token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        if(isAnoymous(handlerMethod)){
            return true;
        }
        String token = request.getHeader(ACCESS_TOKEN);
        if(StringUtils.isEmpty(token)){
            ResponseVO responseVO = ResponseVO.createWithException(ResponseCode.ACCESS_DENIED, "token不存在");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSON(responseVO).toString());
            return false;
        }

        ResponseVO responseVO = validToken(token);
        if(responseVO.isResult()){
            request.setAttribute("user",responseVO.getData());
            return super.preHandle(request, response, handler);
        }

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSON.toJSON(responseVO).toString());
        return false;
    }

    private boolean isAnoymous(HandlerMethod handlerMethod){
        Object bean=handlerMethod.getBean();
        Class clazz=bean.getClass();
        if(clazz.getAnnotation(Anoymous.class)!=null){
            return true;
        }
        Method method=handlerMethod.getMethod();
        return method.getAnnotation(Anoymous.class)!=null;
    }

    public ResponseVO validToken(String token) {
        log.info("Begin validToken,token is :"+token);
        ResponseVO responseVO = ResponseVO.createWithException(400,"");
        try{
            String decodeMsg=JwtTokenUtils.builder().token(token).build().freeJwt();
            if(StringUtils.isNotBlank(decodeMsg)){
                log.info("validate success");
                responseVO.setData(JSON.parse(decodeMsg));
                responseVO.setResult(true);
                responseVO.setCode(200);
                return responseVO;
            }
        }catch (Exception e){
            log.error("UserLoginServiceImpl.validToken Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(responseVO,e);
        }
        return responseVO;
    }
}

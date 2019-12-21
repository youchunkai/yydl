package com.test.sso.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.sso.annotation.Anoymous;
import com.test.sso.entity.dto.UserLoginRequest;
import com.test.sso.entity.vo.ResponseVO;
import com.test.sso.service.CaptchaService;
import com.test.sso.service.UserLoginService;
import com.test.sso.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.test.sso.entity.po.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/12
 **/
@RequestMapping("/user")
@RestController
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    CaptchaService captchaService;


    @Anoymous
    @PostMapping("/login")
    public ResponseVO login(@RequestBody Map<String,String> map, HttpServletRequest request,HttpServletResponse response){

        //验证码 如果错误次数>=3  需要验证码验证
        HttpSession session = request.getSession();
        int errorcount = (session.getAttribute("errorcount") == null ? 0:(Integer)session.getAttribute("errorcount"));
        if(errorcount >= 3){
            ResponseVO validateKaptchaResponse = captchaService.validateKaptchaCode(session,map);

            if(!validateKaptchaResponse.isResult()){
                return validateKaptchaResponse;
            }
        }

        //登录验证
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName(map.get("usname"));
        userLoginRequest.setPassword(map.get("password"));
        ResponseVO responseVO = userLoginService.login(userLoginRequest);

        //登录成功 设置token 刷新token
        if (responseVO.isResult()){
            session.removeAttribute("errorcount");
            session.removeAttribute("verifyCode");

            Map<String, Object> payload = new HashMap<>();
            User user = (User)responseVO.getData();
            payload.put("usCode",user.getUsCode());
            payload.put("usLnnm",user.getUsLnnm());
            String token = JwtTokenUtils.builder().msg(JSON.toJSONString(payload)).build().creatJwtToken(7);
            response.setHeader("token",token);
        }else{//登录失败 设置失败次数，并刷新验证码
            session.setAttribute("errorcount",++errorcount);
            if (errorcount >= 3) {
                ResponseVO newCaptchaResponse = captchaService.getKaptchaCode(session);
                responseVO.setData(newCaptchaResponse.getData());
            }

        }
        return responseVO;
    }


    @GetMapping("/test")
    public ResponseVO test(HttpServletRequest request, HttpServletResponse response){
        JSONObject json = (JSONObject)request.getAttribute("user");
        System.out.println(json.get("usCode"));
        return ResponseVO.createOKWithDataWithoutPageinfo("执行成功", json);
    }

    @Anoymous
    @GetMapping("/test2")
    public String test2(HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            System.out.println(cookies[i].getValue());
        }
        return "893efba06e4c494aaff346dbe559e8c5";

    }


}

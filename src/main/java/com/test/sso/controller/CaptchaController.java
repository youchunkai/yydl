package com.test.sso.controller;

import com.test.sso.annotation.Anoymous;
import com.test.sso.entity.vo.ResponseVO;
import com.test.sso.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/17
 **/
@RequestMapping("/user")
@RestController
public class CaptchaController {

    @Autowired
    CaptchaService captchaService;

    @Anoymous
    @GetMapping("/captcha")
    public ResponseVO getKaptchaCode(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        return captchaService.getKaptchaCode(session);
    }
}

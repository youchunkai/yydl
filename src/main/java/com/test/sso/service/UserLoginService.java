package com.test.sso.service;

import com.test.sso.constants.SysRetCodeConstants;
import com.test.sso.entity.dto.UserLoginRequest;
import com.test.sso.entity.po.User;
import com.test.sso.entity.vo.ResponseCode;
import com.test.sso.entity.vo.ResponseVO;

import com.test.sso.mapper.UserMapper;
import com.test.sso.utils.AesEncryptUtil;
import com.test.sso.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/16
 **/
@Service
@Slf4j
public class UserLoginService {

    @Autowired
    UserMapper userMapper;

    public ResponseVO login(UserLoginRequest request) {
        log.info("开始登录,request:" + request);
        ResponseVO response = ResponseVO.createEmptyResponse();
        try{
            //查询用户信息
            List<User> users = userMapper.selectUserByLoginName(request);
            if(null==users||users.size()==0){
                log.error("用户不存在");
                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode())
                        .setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage())
                        .setResult(false);
                return response;
            }

            //验证密码是否匹配
            if (!users.get(0).getPassword().equals(AesEncryptUtil.desEncrypt(request.getPassword()))) {
                log.error("账号密码不匹配");
                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode())
                        .setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage())
                        .setResult(false);
                return response;
            }

            users.get(0).setPassword(null);
            response.setCode(ResponseCode.OK)
                    .setMsg("登录成功")
                    .setResult(true)
                    .setData(users.get(0));
            return response;

        }catch(Exception e){
            log.error("UserLoginService.login Occur Exception :"+e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}

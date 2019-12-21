package com.test.sso.mapper;

import com.test.sso.entity.dto.UserLoginRequest;
import com.test.sso.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/16
 **/
@Component
@Mapper
public interface UserMapper {

    /**
     *
     * @param request 登录体封装了账号密码
     * @return List<User> 返回账号匹配的用户
     */
    List<User> selectUserByLoginName(UserLoginRequest request);
}

package com.test.sso.entity.dto;

import com.test.sso.entity.dto.abstracts.AbstractRequest;
import com.test.sso.entity.vo.ResponseCode;
import com.test.sso.exception.ValidateException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/16
 **/
@Data
public class UserLoginRequest extends AbstractRequest {
    private String userName;
    private String password;

    @Override
    public void requestCheck() {
        if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)){
            throw new ValidateException(ResponseCode.BAD_REQUEST,"参数为空");
        }
    }
}

package com.test.sso.entity.dto.abstracts;

import java.io.Serializable;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/16
 **/
public class AbstractResponse implements Serializable {

    private static final long serialVersionUID = 7505997295595095971L;
    public int code;
    public String msg;

    public int getCode() {
        return code;
    }

    public AbstractResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public AbstractResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}

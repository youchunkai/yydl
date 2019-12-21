package com.test.sso.entity.dto.abstracts;

import java.io.Serializable;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/16
 **/
public abstract class AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1717442845820713651L;

    public abstract void requestCheck();

    @Override
    public String toString() {
        return "AbstractRequest{}";
    }
}

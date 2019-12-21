package com.test.sso.exception;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/12/16
 **/
public class ValidateException extends BaseBusinessException{

    public ValidateException() {
        super();
    }

    public ValidateException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ValidateException(Throwable arg0) {
        super(arg0);
    }

    public ValidateException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ValidateException(int errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public ValidateException(int errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }
}

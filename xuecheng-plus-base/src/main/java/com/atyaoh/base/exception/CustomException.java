package com.atyaoh.base.exception;

/**
 * 自定义异常
 *
 * @author YaraHong
 */
public class CustomException extends RuntimeException {

    private String errMessage;

    public CustomException() {
        super();
    }

    public CustomException(String errMessage) {
        super();
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(CommonError commonError) {
        throw new CustomException(commonError.getErrMessage());
    }

    public static void cast(String errMessage) {
        throw new CustomException(errMessage);
    }
}

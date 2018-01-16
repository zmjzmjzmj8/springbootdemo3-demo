package org.zmj.springbootdemo.demo.common.exception;

import org.zmj.springbootdemo.demo.utils.sysenum.ErrorCode;

public class CommonException extends RuntimeException {

    private Integer code;

    /**
     * 带附加信息的异常构造方法
     * @param errorCode
     * @param addMessage
     */
    public CommonException(ErrorCode errorCode,String addMessage) {
        super(errorCode.getDescription()+":"+addMessage);
        this.code = errorCode.getCode();
    }

    /**
     * 手动异常构造方法
     * @param code
     * @param message
     */
    public CommonException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    /**
     * ErrorCode的异常构造方法
     * @param errorCode
     */
    public CommonException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.code = errorCode.getCode();
    }

    /**
     * 默认异常构造方法
     * @param message
     */
    public CommonException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

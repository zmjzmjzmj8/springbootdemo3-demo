package org.zmj.springbootdemo.demo.commmon.exception;

public class CommonException extends RuntimeException {
    private Integer code;

    public CommonException(Integer code,String message) {
        super(message);
        this.code = code;
    }

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

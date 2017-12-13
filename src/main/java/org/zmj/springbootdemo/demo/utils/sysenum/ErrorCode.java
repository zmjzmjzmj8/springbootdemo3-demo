package org.zmj.springbootdemo.demo.utils.sysenum;

public enum ErrorCode {

    NULL_ERROR(300,"空异常");
    /**
     * 说明
     */
    String description;

    /**
     * 代码
     */
    int code;

    ErrorCode( int code ,String description) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

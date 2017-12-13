package org.zmj.springbootdemo.demo.utils.sysenum;

public enum ErrorCode {

    NULL_ERROR(301,"空异常"),
    VERIFY_ERROR(302,"验证异常"),
    FILE_UPLOAD_ERROR(401,"文件上传异常"),
    NOT_FIND_ERROR(601,"数据未找到"),
    KNOWS_ERROR(500, "已知异常"),
    UNKNOWNS_ERROR(500, "未知异常");
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

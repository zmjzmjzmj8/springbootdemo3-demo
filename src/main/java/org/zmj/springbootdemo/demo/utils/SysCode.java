package org.zmj.springbootdemo.demo.utils;

public enum SysCode implements CodeInterface{
    SYS_CODE_STATUS_SUCCESS (200,"成功"),SYS_CODE_STATUS_KNOWS_ERROR(500,"已知失败"), SAYS_CODE_STATUS_UNKNOWNS_ERROR(500,"未知失败");

    String description;
    int code;

    SysCode() {
    }

    SysCode( int code,String description) {
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

    @Override
    public String getInfo() {
        return this.description;
    }

    @Override
    public void print() {
        System.out.println(this.code+":"+this.description);
    }
}

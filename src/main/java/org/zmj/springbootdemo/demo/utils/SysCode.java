package org.zmj.springbootdemo.demo.utils;

public enum SysCode implements CodeInterface{
    SYS_CODE_STATUS_SUCCESS (200,"成功"),SYS_CODE_STATUS_KNOWS_ERROR(500,"已知失败"), SAYS_CODE_STATUS_UNKNOWNS_ERROR(500,"未知失败");

    String description;
    int value;

    SysCode() {
    }

    SysCode( int value,String description) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getInfo() {
        return this.description;
    }

    @Override
    public void print() {
        System.out.println(this.value+":"+this.description);
    }
}

package org.zmj.springbootdemo.demo.commmon.register;

public interface RegisterManager {
    /**
     * 注册功能
     * @param usermane 用户名
     * @param password 密码
     * @return 用户ID
     * @throws Exception
     */
    Long Register(String usermane, String password) throws Exception;

}

package org.zmj.springbootdemo.demo.service.userinfo;

import net.sf.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUserInfo;

import java.io.IOException;
import java.io.InputStream;

public interface UserInfoManager {
    /**
     * 文件上传方法
     * @param file
     * @throws CommonException
     * @throws IOException
     */
    void uploadfile(MultipartFile file) throws CommonException, IOException;

    /**
     * 文件上传方法
     * @param file
     * @param fileName
     * @throws CommonException
     * @throws IOException
     */
    void uploadfile(MultipartFile file,String fileName) throws CommonException, IOException;

    /**
     * 显示拥有的object
     * @return
     * @throws CommonException
     */
    JSONArray listObjects() throws CommonException;

    /**
     * 下载文件方法
     * @param filename
     * @return
     * @throws CommonException
     * @throws IOException
     */
    InputStream downloadfile(String filename) throws CommonException, IOException;

    /**
     * 保存用户信息方法
     * @param sysUserInfo
     * @return
     * @throws CommonException
     */
    SysUserInfo saveSysUserInfo(SysUserInfo sysUserInfo) throws  CommonException;

    /**
     * 头像上传方法
     * @param file
     * @param userId
     * @throws CommonException
     */
    void uploadAvatar(MultipartFile file,String userId) throws CommonException, IOException;
}

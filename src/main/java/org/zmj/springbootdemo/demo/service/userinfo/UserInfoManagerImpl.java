package org.zmj.springbootdemo.demo.service.userinfo;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zmj.springbootdemo.demo.commmon.CommonManager;
import org.zmj.springbootdemo.demo.commmon.aliOSS.HelloOSS;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysUserInfoDao;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUserInfo;
import org.zmj.springbootdemo.demo.utils.MD5Util;

import java.io.*;
import java.util.List;

@Service
public class UserInfoManagerImpl extends CommonManager implements UserInfoManager {

    static Logger logger = Logger.getLogger(UserInfoManagerImpl.class);

    @Autowired
    SysUserInfoDao sysUserInfoDao;
    /**
     * 文件上传方法
     * @param file
     * @throws CommonException
     * @throws IOException
     */
    @Override
    public void uploadfile(MultipartFile file) throws CommonException, IOException {
        PropertyConfigurator.configure("conf/log4j-AliOSS.properties");
        OSSClient ossClient = new OSSClient(HelloOSS.ENDPOINT, HelloOSS.ACCESS_KEY_ID, HelloOSS.ACCESS_KEY_SECRET);
        ossClient.putObject(HelloOSS.BUCKET_NAME, file.getOriginalFilename(), file.getInputStream());
        System.out.println("Object：" + file.getOriginalFilename() + "存入OSS成功。");
        ossClient.shutdown();
    }
    /**
     * 文件上传方法
     * @param file
     * @param fileName
     * @throws CommonException
     * @throws IOException
     */
    @Override
    public void uploadfile(MultipartFile file,String fileName) throws CommonException, IOException {
        PropertyConfigurator.configure("conf/log4j-AliOSS.properties");
        OSSClient ossClient = new OSSClient(HelloOSS.ENDPOINT, HelloOSS.ACCESS_KEY_ID, HelloOSS.ACCESS_KEY_SECRET);
        ossClient.putObject(HelloOSS.BUCKET_NAME, fileName, file.getInputStream());
        System.out.println("Object：" + fileName + "存入OSS成功。");
        ossClient.shutdown();
    }


    /**
     * 显示拥有的object
     * @return
     * @throws CommonException
     */
    @Override
    public JSONArray listObjects() throws CommonException {
        PropertyConfigurator.configure("conf/log4j-AliOSS.properties");
        OSSClient ossClient = new OSSClient(HelloOSS.ENDPOINT, HelloOSS.ACCESS_KEY_ID, HelloOSS.ACCESS_KEY_SECRET);
        ObjectListing objectListing = ossClient.listObjects(HelloOSS.BUCKET_NAME);
        List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
        JSONArray jsonArray = new JSONArray();
        System.out.println("您有以下Object：");
        for (OSSObjectSummary object : objectSummary) {
            System.out.println("\t" + object.getKey());
            jsonArray.add(object.getKey());
        }
        ossClient.shutdown();
        return jsonArray;
    }
    /**
     * 下载文件方法
     * @param filename
     * @return
     * @throws CommonException
     * @throws IOException
     */
    @Override
    public InputStream downloadfile(String filename) throws CommonException, IOException {
        PropertyConfigurator.configure("conf/log4j-AliOSS.properties");
        OSSClient ossClient = new OSSClient(HelloOSS.ENDPOINT, HelloOSS.ACCESS_KEY_ID, HelloOSS.ACCESS_KEY_SECRET);
        OSSObject ossObject = ossClient.getObject(HelloOSS.BUCKET_NAME, filename);
        InputStream in = ossObject.getObjectContent();
        return in;
    }


    /**
     * 保存用户信息方法
     * @param sysUserInfo
     * @return
     * @throws CommonException
     */
    @Override
    public SysUserInfo saveSysUserInfo(SysUserInfo sysUserInfo) throws CommonException {
        return  sysUserInfoDao.saveAndFlush(sysUserInfo);
    }

    /**
     * 头像上传方法
     * @param file
     * @param userId
     * @throws CommonException
     */
    @Override
    public void uploadAvatar(MultipartFile file,String userId) throws CommonException, IOException {
        SysUserInfo  sysUserInfo= sysUserInfoDao.findByUsrId(Long.valueOf(userId));
        if(sysUserInfo==null) {
            throw new CommonException("找不到对应id的用户");
        }
        String filename = file.getOriginalFilename();
        String prefix=filename.substring(filename.lastIndexOf(".")+1);//获取文件后缀名
        filename= MD5Util.encode(userId)+"."+prefix;//md5加密文件名
        sysUserInfo.setAvatar_Key(filename);
        sysUserInfoDao.saveAndFlush(sysUserInfo);//更新用户信息
        this.uploadfile(file,filename);//上传头像文件
    }
}

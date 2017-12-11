package org.zmj.springbootdemo.demo.userinfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zmj.springbootdemo.demo.commmon.CommonController;
import org.zmj.springbootdemo.demo.commmon.JsonAnnotation;
import org.zmj.springbootdemo.demo.commmon.exception.CommonException;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysUserDao;
import org.zmj.springbootdemo.demo.mapper.func.dao.SysUserInfoDao;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUser;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUserInfo;

import java.io.*;

@Controller
@RequestMapping(value = "/apiCall")
public class UserInfoController extends CommonController{

    @Autowired
    UserInfoManager userInfoManager;
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    SysUserInfoDao sysUserInfoDao;

    // 访问路径为：http://ip:port/upload
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "upload_test";
    }

    @RequestMapping(value = "/upload3", method = RequestMethod.GET)
    public String upload3() {
        return "upload3";
    }

    @RequestMapping(value = "/upload2", method = RequestMethod.GET)
    public String upload2() {
        return "upload2";
    }

    /**
     * 文件上传具体实现方法（单文件上传）
     *
     * @param file
     * @return
     *
     * @author 单红宇(CSDN CATOOP)
     * @create 2017年3月11日
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST,produces="application/javascript;charset=UTF-8")
    @ResponseBody
    @JsonAnnotation
    public String upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 这里只是简单例子，文件直接输出到项目路径下。
                // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
                // 还有关于文件格式限制、文件大小限制，详见：中配置。
                /*BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("./uploadfiles/"+file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();*/
                userInfoManager.uploadfile(file);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return "上传失败,文件过大";
            }catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }catch (CommonException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            return "上传成功";
        } else {
            return "上传失败，因为文件是空的.";
        }
    }

    /**
     * @return 获取文件列表
     * @throws CommonException
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download() throws CommonException {
        JSONArray jsonArray = userInfoManager.listObjects();
        request.setAttribute("list",jsonArray.toString());
        return "download_test";
    }

    /**
     * 根据filename下载文件
     * @param filename
     * @return
     * @throws CommonException
     * @throws IOException
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> download(String filename) throws CommonException, IOException {
        JSONArray jsonArray = userInfoManager.listObjects();
        request.setAttribute("list",jsonArray.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        InputStream in = userInfoManager.downloadfile(filename);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(in));
    }

    /**
     * 获取用户信息
     * @param name
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/getUserInfoByName", method = RequestMethod.GET,produces="application/javascript;charset=UTF-8")
    @ResponseBody
    @JsonAnnotation
    public String getUserInfoByName(String name)throws CommonException{
        if (name==null|| "".equals(name)) {
            throw new CommonException("name入参为空");
        }
        SysUser sysUser= sysUserDao.findByUsername(name);
        SysUserInfo sysUserInfo = sysUserInfoDao.findByUsrId(sysUser.getId());
        return JSONObject.fromObject(sysUserInfo).toString();
    }

    /**
     * 设置用户信息
     * @param sysUserInfo
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/setUserInfo", method = RequestMethod.POST,produces="application/javascript;charset=UTF-8")
    @ResponseBody
    @JsonAnnotation
    public String setUserInfo(SysUserInfo sysUserInfo) throws CommonException{
        try {
            if (sysUserInfo==null) {
                throw new CommonException("UserInfo入参为空");
            }else if(sysUserInfo.getUsrId()==0){
                SysUser sysUser= getThisUser();
                sysUserInfo.setUsrId(sysUser.getId());
            }
            return mapper.writeValueAsString(userInfoManager.saveSysUserInfo(sysUserInfo));
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     * @return
     * @throws CommonException
     */
    @RequestMapping(value = "/getThisUserInfo", method = RequestMethod.GET,produces="application/javascript;charset=UTF-8")
    @ResponseBody
    @JsonAnnotation
    public String getThisUserInfo() throws CommonException {
        try {
            SysUser sysUser= getThisUser();
            SysUserInfo sysUserInfo = sysUserInfoDao.findByUsrId(sysUser.getId());
            return mapper.writeValueAsString(sysUserInfo);
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
    }

    /**
     * 头像上传具体实现方法（单文件上传）
     * @param file
     * @return
     *
     */
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST,produces="application/javascript;charset=UTF-8")
    @ResponseBody
    @JsonAnnotation
    public String uploadAvatar(@RequestParam("file") MultipartFile file,String userId) {
        if (!file.isEmpty()) {
            try {
                userInfoManager.uploadAvatar(file,userId);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return "上传失败,文件过大";
            }catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }catch (CommonException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            return "上传成功";
        } else {
            return "上传失败，因为文件是空的.";
        }
    }
}

package org.zmj.springbootdemo.demo.common.permissions;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zmj.springbootdemo.demo.common.CommonController;
import org.zmj.springbootdemo.demo.common.aspect.RestfulAnnotation;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysPermission;

import java.util.List;

@RestController
public class PermissionsController extends CommonController{
    @Autowired
    PermissionsManager permissionsManager;

    /**
     * 显示所有权限列表
     * @return
     */
    @RequestMapping(value = "/showAllPermissions",method = RequestMethod.GET,produces="application/javascript;charset=UTF-8")
    @RestfulAnnotation
    public String showFunc() throws Exception{
        JSONObject jsonObject = new JSONObject();
        List<SysPermission> sysPermissions = permissionsManager.showAllPermissions();
        jsonObject.put("list",sysPermissions);
        return jsonObject.toString();
    }
}

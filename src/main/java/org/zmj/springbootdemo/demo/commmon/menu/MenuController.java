package org.zmj.springbootdemo.demo.commmon.menu;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zmj.springbootdemo.demo.commmon.CommonController;
import org.zmj.springbootdemo.demo.commmon.aspect.RestfulAnnotation;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysPermission;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/apiCall")
public class MenuController extends CommonController{

    @Autowired
    MenuManager menuManager;

    /**
     * 显示用户权限列表
     * @return
     */
    @RequestMapping(value = "/showFunc",method = RequestMethod.GET,produces="application/javascript;charset=UTF-8")
    @RestfulAnnotation
    public String showFunc() throws Exception{
        JSONObject jsonObject = new JSONObject();
        String username = getThisUser().getUsername();
        List<SysPermission> sysPermissions = menuManager.findAllByUserName(username);
        jsonObject.put("list",sysPermissions);
        return jsonObject.toString();
    }

    /**
     * 显示用户拥有的菜单
     * @return
     */
    @RequestMapping(value = "/showMenu",method = RequestMethod.GET,produces="application/javascript;charset=UTF-8")
    @RestfulAnnotation
    public String showMenu() throws Exception{
        String username = getThisUser().getUsername();
        List<SysPermission> sysPermissions = menuManager.findAllByUserName(username);
        List<SysPermission> menuType1 = new ArrayList<>();
        List<SysPermission> menuType2 = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissions){
            if ("1".equals(sysPermission.getType())){
                menuType1.add(sysPermission);
            }else if ("2".equals(sysPermission.getType())){
                menuType2.add(sysPermission);
            }
        }
        for (SysPermission sysPermission1 : menuType1) {
            for (SysPermission sysPermission2 : menuType2) {
                if (sysPermission1.getId().equals(sysPermission2.getPid())){
                    sysPermission1.getChildren().add(sysPermission2);
                }
            }
        }
        return JSONArray.fromObject(menuType1).toString();
    }
}

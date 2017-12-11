package org.zmj.springbootdemo.demo.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ZmjUtil {

    /**
     * 判断对象是否为空
     * @param o
     * @return
     */
    public static boolean isNullOrEmpty(Object o){
        if (o == null) {
            return true;
        }

        if (o instanceof CharSequence) {
            return ((CharSequence) o).length() == 0;
        }

        if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        }

        if (o instanceof Map) {
            return ((Map) o).isEmpty();
        }

        if (o instanceof Object[]) {
            Object[] object = (Object[]) o;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    /**
     * 复制对象的非空属性
     * @param newObj    源对象
     * @param oldObj    结果对象
     */
    public static void copyPropertiesIgnoreNull(Object newObj, Object oldObj) {
        BeanUtils.copyProperties(newObj, oldObj, getNullPropertyNames(newObj));
    }

    /**
     * 获取空属性名
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void main(String[] args) {
        String s = "";
        System.out.println(ZmjUtil.isNullOrEmpty(s));
        SysUser sysUser1 = new SysUser();
        sysUser1.setUsername("aaa");
        sysUser1.setPassword("aaa");
        SysUser sysUser2 = new SysUser();
        sysUser2.setUsername("bbb");
        copyPropertiesIgnoreNull(sysUser2,sysUser1);
        System.out.printf(sysUser1.getUsername()+","+sysUser1.getPassword());
    }
}

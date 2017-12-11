package org.zmj.springbootdemo.demo.utils;

import org.zmj.springbootdemo.demo.mapper.func.pojo.SysUser;

import java.util.Collection;
import java.util.Map;

public class ZmjUtil {
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

    public static void main(String[] args) {
        String s = "";
        System.out.println(ZmjUtil.isNullOrEmpty(s));
    }
}

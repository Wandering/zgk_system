package cn.thinkjoy.jx.k12system.common;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yhwang on 15/6/29.
 */
public class HttpUtil {

    public static void setSession(HttpServletRequest request,String key,Object o ){
        request.getSession().setAttribute(key,o);
    }

    public static void removeSession(HttpServletRequest request,String key){
        request.getSession().removeAttribute(key);
    }

    public static Object getSession(HttpServletRequest request,String key){
        Object o = request.getSession().getAttribute(key);
        return o;
    }
    public static String getParameter(HttpServletRequest request,String param,String initVal){
        String paramVal = request.getParameter(param);
        if(StringUtils.isNotBlank(paramVal)){
            return paramVal;
        }else {
            return initVal;
        }
    }
}

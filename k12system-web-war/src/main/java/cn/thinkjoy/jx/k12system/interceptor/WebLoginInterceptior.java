package cn.thinkjoy.jx.k12system.interceptor;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.jx.k12system.common.ERRORCODE;
import cn.thinkjoy.jx.k12system.common.HttpUtil;
import cn.thinkjoy.jx.k12system.pojo.UserPojo;
import cn.thinkjoy.jx.k12system.util.CacheService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yhwang on 15-6-23.
 */
public class WebLoginInterceptior implements HandlerInterceptor {
    @Autowired
    CacheService cacheService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        // 从session 里面获取用户名的信息
        UserPojo user = (UserPojo) HttpUtil.getSession(request,"user");
        // 判断如果没有取到用户信息，就跳转到登陆页面，提示用户进行登陆
        if (user == null || "".equals(user.toString())) {
            throw new BizException(ERRORCODE.NO_SESSION.getCode(),ERRORCODE.NO_SESSION.getMessage());
        }
        String token = request.getParameter("token");
        if(StringUtils.isBlank(token)){
            throw new BizException(ERRORCODE.NO_PERMISSION.getCode(),ERRORCODE.NO_PERMISSION.getMessage());
        }
        String localToken = cacheService.getValue(user.getAccountCode().toString());
        if(!token.equals(localToken)){
            throw new BizException(ERRORCODE.NO_PERMISSION.getCode(),ERRORCODE.NO_PERMISSION.getMessage());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}



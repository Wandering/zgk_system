package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IEXLoginService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yhwang on 15/9/2.
 */
@Controller
@RequestMapping("/system/login")
public class LoginController {

    @Autowired
    IEXLoginService iexLoginService;

    private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkLogin",method = RequestMethod.POST)
    public UserPojo checkLogin(HttpServletRequest request){
        String loginNumber = request.getParameter("loginNumber");
        String pwd = request.getParameter("pwd");
        if(StringUtils.isBlank(loginNumber) || StringUtils.isBlank(pwd)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        UserPojo userPojo = null;
        try {
             userPojo = iexLoginService.checkLogin(loginNumber,pwd);

             HttpUtil.setSession(request, "user", userPojo);

        }catch (Exception e){
            LOGGER.error(loginNumber + "   login error");
        }
        if(userPojo == null){
            throw new BizException(ERRORCODE.PASSWD_OR_ACCOUNT_ERROR.getCode(),ERRORCODE.PASSWD_OR_ACCOUNT_ERROR.getMessage());
        }
        return userPojo;
    }
}

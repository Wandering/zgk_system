package cn.thinkjoy.zgk.zgksystem.service.account;

import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;

/**
 * Created by yhwang on 15/9/6.
 */
public interface IEXLoginService {

    /**
     * 检查登陆并生成access_token
     * @param loginNumber
     * @param pwd
     * @return
     */
    UserPojo checkLogin(String loginNumber,String pwd);
}

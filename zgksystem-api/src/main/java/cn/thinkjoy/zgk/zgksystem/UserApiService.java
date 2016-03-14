package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;

/**
 * Created by yhwang on 15/8/2.
 */
public interface UserApiService {
    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
     UserPojo getUserPojo(String token);

}

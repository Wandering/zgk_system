package cn.thinkjoy.zgk.zgksystem.service.apiImpl;

import cn.thinkjoy.zgk.zgksystem.UserInfoApiService;
import cn.thinkjoy.zgk.zgksystem.domain.UserInfo;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by chx991 on 2015/11/23.
 */
@Service("UserInfoApiServiceImpl")
public class UserInfoApiServiceImpl implements UserInfoApiService {
    @Autowired
    private IUserInfoService userInfoService;


    public void updateOrSave(UserInfo var1, Long var2)
    {
        userInfoService.updateOrSave(var1, var2);
    }

    public List<UserInfo> findList(String var1, Object var2)
    {
        return userInfoService.findList(var1, var2);
    }

    public UserInfo findOne(String var1, Object var2)
    {
        return (UserInfo)userInfoService.findOne(var1,var2);
    }

    public int update(UserInfo var1)
    {
        return userInfoService.update(var1);
    }


    public UserInfo queryOne(Map<String, Object> var1)
    {
        return (UserInfo)userInfoService.queryOne(var1);

    }
}
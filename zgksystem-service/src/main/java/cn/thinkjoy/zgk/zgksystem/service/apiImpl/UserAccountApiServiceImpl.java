package cn.thinkjoy.zgk.zgksystem.service.apiImpl;

import cn.thinkjoy.zgk.zgksystem.UserAccountApiService;
import cn.thinkjoy.zgk.zgksystem.domain.UserAccount;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by chx991 on 2015/11/23.
 */
@Service("UserAccountApiServiceImpl")
public class UserAccountApiServiceImpl implements UserAccountApiService {
    @Autowired
    private IUserAccountService userAccountService;

    public UserAccount findOne(String var1, Object var2)
    {
        return (UserAccount)userAccountService.findOne(var1,var2);
    }


    public void updateOrSave(UserAccount var1, Long var2)
    {
        userAccountService.updateOrSave(var1, var2);
    }

    public UserAccount queryOne(Map<String, Object> var1)
    {
        return (UserAccount)userAccountService.queryOne(var1);

    }

    public int update(UserAccount var1){
        return userAccountService.update(var1);
    }
}

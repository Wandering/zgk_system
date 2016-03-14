package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.domain.UserAccount;

import java.util.Map;

/**
 * Created by chx991 on 2015/11/23.
 */
public interface UserAccountApiService {

    UserAccount findOne(String var1, Object var2);

    void updateOrSave(UserAccount var1, Long var2);

    UserAccount queryOne(Map<String, Object> var1);

    int update(UserAccount var1);
}

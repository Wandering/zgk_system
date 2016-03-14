package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.domain.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by chx991 on 2015/11/23.
 */
public interface UserInfoApiService {
    void updateOrSave(UserInfo var1, Long var2);

    List<UserInfo> findList(String var1, Object var2);

    UserInfo findOne(String var1, Object var2);

    int update(UserInfo var1);

    UserInfo queryOne(Map<String, Object> var1);
}

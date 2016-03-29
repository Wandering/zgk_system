package cn.thinkjoy.zgk.zgksystem.service.account;

import cn.thinkjoy.zgk.zgksystem.domain.UserAccount;

import java.util.Map;

/**
 * Created by yhwang on 15/9/11.
 */
public interface IEXUserAccountService {

    /**
     * 根据ID查询账户区域信息
     * @param paramMap
     * @return
     */
    Map<String, Object> findUserInfo(Map<String,String> paramMap);
}

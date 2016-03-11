package cn.thinkjoy.jx.k12system.service.account;

import cn.thinkjoy.jx.k12system.domain.UserAccount;

/**
 * Created by yhwang on 15/9/11.
 */
public interface IEXUserAccountService {
    /**
     * 根据编码查询账户
     * @param code
     * @return
     */
    UserAccount findUserAccount(Integer code);
}

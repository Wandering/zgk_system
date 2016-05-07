package cn.thinkjoy.zgk.zgksystem.service.account.impl;

import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXUserAccountDAO;
import cn.thinkjoy.zgk.zgksystem.service.account.IEXUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by yhwang on 15/9/11.
 */
@Service("EXUserAccountService")
public class EXUserAccountService implements IEXUserAccountService{
    @Autowired
    IEXUserAccountDAO exUserAccountDAO;

    /**
     * 根据ID查询账户区域信息
     * @param paramMap
     * @return
     */
    public Map<String, Object> findUserInfo(Map<String, String> paramMap)
    {
        return exUserAccountDAO.queryUserInfo(paramMap);
    }

    @Override
    public boolean delUserInfo(long userId) {
        int result = exUserAccountDAO.delUserInfo(userId);
        return result == 1;
    }
}

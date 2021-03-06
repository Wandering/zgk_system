package cn.thinkjoy.zgk.zgksystem.service.account.impl;

import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXLoginInfoDAO;
import cn.thinkjoy.zgk.zgksystem.service.account.IEXLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yhwang on 15/9/21.
 */
@Service("EXLoginInfoServiceImpl")
public class EXLoginInfoServiceImpl implements IEXLoginInfoService{
    @Autowired
    private IEXLoginInfoDAO iexLoginInfoDAO;
    /**
     * 根据token和当前时间获取用户code
     *
     * @param token
     * @param nowTime
     * @return
     */
    @Override
    public Long getAccountCodeByToken(String token, long nowTime) {
        return iexLoginInfoDAO.getAccountCodeByToken(token,nowTime);
    }
}

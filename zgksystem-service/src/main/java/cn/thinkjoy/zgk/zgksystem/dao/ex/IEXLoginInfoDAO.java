package cn.thinkjoy.zgk.zgksystem.dao.ex;

import org.apache.ibatis.annotations.Param;

/**
 * Created by yhwang on 15/9/21.
 */
public interface IEXLoginInfoDAO {
    /**
     * 根据token和当前时间获取用户code
     * @param token
     * @param nowTime
     * @return
     */
    Long getAccountCodeByToken(@Param("token")String token,@Param("nowTime") long nowTime);
}

package cn.thinkjoy.zgk.zgksystem.dao.ex;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/9/8.
 */
public interface IEXIdentityDAO {
    /**
     * 批量删除身份
     *
     * @param identityCodes
     */
     void delIdentityByCodes(@Param("identityCodes") List<Long> identityCodes);
}

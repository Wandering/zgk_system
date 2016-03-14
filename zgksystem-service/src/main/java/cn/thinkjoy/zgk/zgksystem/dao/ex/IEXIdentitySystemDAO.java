package cn.thinkjoy.zgk.zgksystem.dao.ex;

import cn.thinkjoy.zgk.zgksystem.pojo.K12SystemPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/9/6.
 */
public interface IEXIdentitySystemDAO {
    /**
     * 根据身份查询系统列表
     * @param identityCode
     * @return
     */
    List<K12SystemPojo> getSystemList(@Param("identityCode")Long identityCode);

    /**
     * 根据身份解除身份与系统的关系
     * @param codeList
     */
    void delIdentitySystemByIdentity(@Param("codeList")List<Long> codeList);
}

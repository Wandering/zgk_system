package cn.thinkjoy.zgk.zgksystem.dao.ex;

import cn.thinkjoy.zgk.zgksystem.pojo.K12SystemPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/9/15.
 */
public interface IEXk12systemDAO {
    /**
     * 根据岗位查询系统列表
     * @param postCode
     * @return
     */
    List<K12SystemPojo> getSystemListByPost(@Param("postCode")Long postCode);
}

package cn.thinkjoy.jx.k12system.dao.ex;

import org.apache.ibatis.annotations.Param;

/**
 * Created by yhwang on 15/10/20.
 */
public interface IEXPostDataauthorityDAO {
    /**
     * 根据postCode删除岗位数据权限表
     * @param postCode
     */
    void deleteByPost(@Param("postCode") Long postCode);
}

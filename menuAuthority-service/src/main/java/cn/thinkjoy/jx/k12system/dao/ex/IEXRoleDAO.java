package cn.thinkjoy.jx.k12system.dao.ex;

import org.apache.ibatis.annotations.Param;

/**
 * Created by yhwang on 15/9/16.
 */
public interface IEXRoleDAO {
    /**
     * 修改角色菜单表
     * @param roleCode
     */
    void updateRoleMenu(@Param("roleCode")Long roleCode);

    /**
     * 修改角色资源表
     * @param roleCode
     */
    void updateRoleResource(@Param("roleCode")Long roleCode);
}

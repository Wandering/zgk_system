package cn.thinkjoy.zgk.zgksystem.service.role;

/**
 * Created by yhwang on 15/9/16.
 */
public interface IEXRoleService {
    /**
     * 修改角色菜单表
     * @param roleCode
     */
    void updateRoleMenu(Long roleCode);

    /**
     * 修改角色资源表
     * @param releCode
     */
    void updateRoleResource(Long releCode);
}

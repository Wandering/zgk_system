package cn.thinkjoy.jx.k12system.service.menu;

import cn.thinkjoy.jx.k12system.common.TreeBean;

import java.util.List;

/**
 * Created by yhwang on 15/9/15.
 */
public interface IEXMenuService {
    /**
     * 根据岗位code获取菜单树
     * @param postCode
     * @return
     */
    List<TreeBean> menuList(Long postCode,boolean hasResource);

    /**
     * 按照菜单code修改菜单角色表
     * @param menuCode
     */
    void updateMenuRole(Long menuCode);

    /**
     * 根据角色code获取菜单code列表
     * @param roleCode
     * @return
     */
    List<Long> getMenuCodeByRole(Long roleCode);
}

package cn.thinkjoy.zgk.zgksystem.service.menu.impl;

import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.dao.IMenuDAO;
import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXMenuDAO;
import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXResourceDAO;
import cn.thinkjoy.zgk.zgksystem.pojo.MenuPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.ResourcesPojo;
import cn.thinkjoy.zgk.zgksystem.service.menu.IEXMenuService;
import cn.thinkjoy.zgk.zgksystem.util.IdentityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhwang on 15/9/15.
 */
@Service("EXMenuServiceImpl")
public class EXMenuServiceImpl implements IEXMenuService{
    @Autowired
    IEXMenuDAO iexMenuDAO;
    @Autowired
    IMenuDAO imenuDAO;
    @Autowired
    private IEXResourceDAO iexResourceDAO;
    /**
     * 根据岗位code获取菜单树
     *
     * @param postCode
     * @return
     */
    @Override
    public List<TreeBean> menuList(Long postCode,boolean hasResource) {
        List<TreeBean> treeBeanList = new ArrayList<>();
        List<MenuPojo> menuList = new ArrayList<>();
        long parentCode = -1;
        if(postCode == IdentityUtil.ADMIN_MANAGER_POST || postCode == IdentityUtil.SYSTEM_MANAGER_POST)
        {
            menuList = iexMenuDAO.getMenuAllPojoList(parentCode);// 拉取全部菜单
        }else {
            menuList = iexMenuDAO.getMenuPojoListByPost(parentCode,postCode);
        }
        for(MenuPojo menuPojo:menuList){
            TreeBean treeBean = new TreeBean();
            treeBean.setId(menuPojo.getMenuCode());
            treeBean.setName(menuPojo.getMenuName());
            treeBean.setLeafIcon(menuPojo.getMenuIcon());
            treeBean.setChildren(recursionMenu(menuPojo.getMenuCode(), postCode, hasResource));
            treeBeanList.add(treeBean);
        }
        return treeBeanList;
    }

    /**
     * 菜单树的递归方法
     * @param parentCode
     * @return
     */
    public List<TreeBean> recursionMenu(Long parentCode,Long postCode,boolean hasResource){
        List<TreeBean> treeBeanList = new ArrayList<>();
        List<MenuPojo> childMenuList = new ArrayList<>();
        if(postCode == IdentityUtil.ADMIN_MANAGER_POST || postCode == IdentityUtil.SYSTEM_MANAGER_POST)
        {
            childMenuList = iexMenuDAO.getMenuAllPojoList(parentCode);// 拉取全部菜单
        }else {
            childMenuList = iexMenuDAO.getMenuPojoListByPost(parentCode,postCode);
        }
        if(childMenuList != null && childMenuList.size() > 0){
            for(MenuPojo menuPojo: childMenuList){
                TreeBean treeBean = new TreeBean();
                treeBean.setId(menuPojo.getMenuCode());
                treeBean.setName(menuPojo.getMenuName());
                treeBean.setLeafIcon(menuPojo.getMenuIcon());
                treeBean.setChildren(recursionMenu(menuPojo.getMenuCode(), postCode, hasResource));
                treeBeanList.add(treeBean);
                //treeBeanList.add(new TreeBean(menu.getMenuCode(),menu.getMenuName(),menu.getMenuIcon(),recursionMenu(menu.getMenuCode(), postCode, hasResource)));
            }
        }else {
            if(hasResource){
                List<ResourcesPojo> resourcesPojos = null;
                if(postCode == IdentityUtil.ADMIN_MANAGER_POST || postCode == IdentityUtil.SYSTEM_MANAGER_POST)
                {
                    resourcesPojos = iexResourceDAO.getResourcesAll(parentCode);
                }else {
                    resourcesPojos = iexResourceDAO.getResourcesByMenuPost(parentCode,postCode);
                }
                if(resourcesPojos != null && resourcesPojos.size() > 0){
                    for(ResourcesPojo resourcesPojo:resourcesPojos){
                        treeBeanList.add(new TreeBean(resourcesPojo.getResourceCode(),resourcesPojo.getResourceName(),resourcesPojo.getResourceIcon(),Boolean.TRUE));
                    }
                }

            }
        }
        return treeBeanList;
    }

    /**
     * 按照菜单code修改菜单角色表
     *
     * @param menuCode
     */
    @Override
    public void updateMenuRole(Long menuCode) {
        iexMenuDAO.updateMenuRole(menuCode);
    }

    /**
     * 根据角色code获取菜单code列表
     *
     * @param roleCode
     * @return
     */
    @Override
    public List<Long> getMenuCodeByRole(Long roleCode) {
        return iexMenuDAO.getMenuCodeByRole(roleCode);
    }
}

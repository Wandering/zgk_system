package cn.thinkjoy.zgk.zgksystem.service.role.impl;

import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXRoleDAO;
import cn.thinkjoy.zgk.zgksystem.service.role.IEXRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yhwang on 15/9/16.
 */
@Service("EXRoleServiceImpl")
public class EXRoleServiceImpl implements IEXRoleService {
    @Autowired
    private IEXRoleDAO iexRoleDAO;
    /**
     * 修改角色菜单表
     *
     * @param roleCode
     */
    @Override
    public void updateRoleMenu(Long roleCode) {
        iexRoleDAO.updateRoleMenu(roleCode);
    }

    /**
     * 修改角色资源表
     *
     * @param roleCode
     */
    @Override
    public void updateRoleResource(Long roleCode) {
        iexRoleDAO.updateRoleResource(roleCode);
    }
}

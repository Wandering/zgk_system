package cn.thinkjoy.jx.k12system.service.resource.impl;

import cn.thinkjoy.jx.k12system.dao.ex.IEXResourceDAO;
import cn.thinkjoy.jx.k12system.service.resource.IEXResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yhwang on 15/9/16.
 */
@Service("EXResourceServiceImpl")
public class EXResourceServiceImpl implements IEXResourceService{
    @Autowired
    private IEXResourceDAO iexResourceDAO;

    /**
     * 根据角色code查询资源code列表
     *
     * @param roleCode
     * @return
     */
    @Override
    public List<Long> resourceCodeListByRole(Long roleCode) {
        return iexResourceDAO.resourceCodeListByRole(roleCode);
    }
}

package cn.thinkjoy.zgk.zgksystem.service.system.impl;

import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXk12systemDAO;
import cn.thinkjoy.zgk.zgksystem.pojo.K12SystemPojo;
import cn.thinkjoy.zgk.zgksystem.service.system.IEXK12SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yhwang on 15/9/1.
 */
@Service("EXK12SystemServiceImpl")
public class EXK12SystemServiceImpl implements IEXK12SystemService {
    @Autowired
    private IEXk12systemDAO ieXk12systemDAO;

    /**
     * 根据岗位查询系统列表
     *
     * @param postCode
     * @return
     */
    @Override
    public List<K12SystemPojo> getSystemListByPost(Long postCode) {
        return ieXk12systemDAO.getSystemListByPost(postCode);
    }
}

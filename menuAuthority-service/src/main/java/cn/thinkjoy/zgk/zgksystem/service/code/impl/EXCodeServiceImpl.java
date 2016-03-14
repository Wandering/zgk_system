package cn.thinkjoy.zgk.zgksystem.service.code.impl;

import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXCodeDAO;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyongqiang on 15/9/1.
 */
@Service("IEXCodeServiceImpl")
public class EXCodeServiceImpl implements IEXCodeService {

    @Autowired
    private IEXCodeDAO excodeDAO;

    public Long selectMaxCode(String columnName,String tableName){
        return excodeDAO.selectMaxCode(columnName,tableName);
    }

    public Long selectMaxCodeByParent(String columnName,String tableName, String parentName,Long parentCode){
        return excodeDAO.selectMaxCodeByParent(columnName, tableName, parentName, parentCode);
    }

    public Long selectMaxCodeByScope(String columnName,String tableName,long minCode,long maxCode){
        return excodeDAO.selectMaxCodeByScope(columnName,tableName,minCode,maxCode);
    }
}

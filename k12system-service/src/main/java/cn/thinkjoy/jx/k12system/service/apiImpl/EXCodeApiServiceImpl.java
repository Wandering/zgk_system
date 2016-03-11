package cn.thinkjoy.jx.k12system.service.apiImpl;

import cn.thinkjoy.jx.k12system.service.code.IEXCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import cn.thinkjoy.jx.k12system.EXCodeApiService;
import org.springframework.stereotype.Service;

/**
 * Created by chx991 on 2015/11/23.
 */
@Service("EXCodeApiServiceImpl")
public class EXCodeApiServiceImpl implements EXCodeApiService{
    @Autowired
    private IEXCodeService eXCodeService;

    /**
     *
     * @param columnName
     * @param tableName
     * @param minCode
     * @param maxCode
     * @return
     */
    public Long selectMaxCodeByScope(String columnName,String tableName, long minCode,long maxCode)
    {
        return eXCodeService.selectMaxCodeByScope(columnName,tableName,minCode,maxCode);
    }
}
package cn.thinkjoy.zgk.zgksystem;

/**
 * Created by chx991 on 2015/11/23.
 */
public interface EXCodeApiService {
    Long selectMaxCodeByScope(String columnName,String tableName, long minCode,long maxCode);
}

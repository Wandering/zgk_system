package cn.thinkjoy.jx.k12system.service.code;



/**
 * Created by wangyongqiang on 15/9/1.
 */
public interface IEXCodeService {

    public Long selectMaxCode( String columnName,String tableName) ;
    public Long selectMaxCodeByParent(String columnName,String tableName, String parentName,Long parentCode) ;
    public Long selectMaxCodeByScope(String columnName,String tableName, long minCode,long maxCode) ;

}

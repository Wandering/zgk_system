package cn.thinkjoy.zgk.zgksystem.dao.ex;

import org.apache.ibatis.annotations.Param;

/**
 * Created by wangyongqiang on 15/9/1.
 */
public interface IEXCodeDAO {

     Long selectMaxCode( @Param("columnName") String columnName,@Param("tableName") String tableName) ;
     Long selectMaxCodeByParent(@Param("columnName") String columnName,@Param("tableName") String tableName,@Param("parentName") String parentName,@Param("parentCode") Long parentCode) ;
     Long selectMaxCodeByScope(@Param("columnName") String columnName,@Param("tableName") String tableName,@Param("minCode") Long minCode,@Param("maxCode") Long maxCode) ;

}

package cn.thinkjoy.jx.k12system.dao.ex;

import cn.thinkjoy.jx.k12system.domain.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/8.
 */
public interface IEXCompanyDAO {

    Integer totalCount(@Param("condition") Map<String,Object> var1 );

    List<Company> queryByPageAndCondition(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);

}

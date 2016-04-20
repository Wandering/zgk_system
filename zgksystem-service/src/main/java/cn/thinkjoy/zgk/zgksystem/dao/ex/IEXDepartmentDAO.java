package cn.thinkjoy.zgk.zgksystem.dao.ex;

import cn.thinkjoy.zgk.zgksystem.domain.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangguorong on 16/4/19.
 */
public interface IEXDepartmentDAO {

    /**
     * 根据地区编码查询部门集合
     *
     * @param areaCode
     * @param index
     * @param pageSize
     * @return
     */
    List<Department> queryDepartmentsByAreaCode(@Param("areaCode") String areaCode,
                                                @Param("index") int index,
                                                @Param("pageSize") int pageSize);

    /**
     * 根据地区编码查询部门个数
     *
     * @param areaCode
     * @return
     */
    Integer getDepartmentCountByAreaCode(@Param("areaCode") String areaCode);
}

package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.Department;

import java.util.List;

/**
 * Created by yhwang on 15/10/8.
 */
public interface DeparmentApiService {
    /**
     * 查询部门树  parentCode
     * @return Page<T>
     */
    List<TreeBean> recursionTree(Long parentCode);

    /**
     * 根据地区编码查询部门集合
     *
     * @param areaCode
     * @param areaType 类型  1:省 2：市  3：区县
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<Department> queryDepartmentsByAreaCode(String areaCode,int areaType,int currentPage,int pageSize);

    /**
     * 根据地区编码查询部门个数
     *
     * @param areaCode
     * @param areaType
     * @return
     */
    int getDepartmentCountByAreaCode(String areaCode,int areaType);

    /**
     * 根据部门编号查询部门信息
     *
     * @param departmentCode
     * @return
     */
    Department quertDepartmentInfoByCode(long departmentCode);

}

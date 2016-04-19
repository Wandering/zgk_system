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
     * @param roleType
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<Department> queryDepartmentsByAreaCode(String areaCode,int roleType,int currentPage,int pageSize);

}

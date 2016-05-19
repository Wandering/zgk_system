package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.domain.DepartmentProductRelation;

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

    /**
     * 根据部门编号查询部门代理产品的种类及价格
     *
     * @param departmentCode
     * @return
     */
    List<DepartmentProductRelation> queryProductPriceByCode(long departmentCode);

    /**
     * 根据区域ID查询不同产品的价格信息
     *
     * @param areaId
     * @return
     */
    List<DepartmentProductRelation> queryProductPriceByAreaId(String areaId);
}

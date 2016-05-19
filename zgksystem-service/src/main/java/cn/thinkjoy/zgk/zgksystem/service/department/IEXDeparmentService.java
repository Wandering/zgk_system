package cn.thinkjoy.zgk.zgksystem.service.department;

import cn.thinkjoy.zgk.zgksystem.domain.Department;

import java.util.List;

/**
 * Created by yangguorong on 16/4/19.
 */
public interface IEXDeparmentService {

    /**
     * 根据地区编码查询部门集合
     *
     * @param areaCode
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<Department> queryDepartmentsByAreaCode(String areaCode,int currentPage,int pageSize);

    /**
     * 根据地区编码查询部门个数
     *
     * @param areaCode
     * @return
     */
    int getDepartmentCountByAreaCode(String areaCode);

    /**
     * 根据区域编码修改部门信息
     *
     * @param areaCode
     */
    void updateDepartmentInfoByAreaCode(String areaCode,double wechatPrice,double webPrice);

    /**
     * 根据部门ID修改下级代理商的商品售价
     *
     * @param departmentCode
     * @param productId
     * @param salePrice
     */
    void updateDepartmentPrice(long departmentCode,long productId,double salePrice);
}

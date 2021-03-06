/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  DepartmentService.java 2015-08-31 11:11:28 $
 */

package cn.thinkjoy.zgk.zgksystem.service.department;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.common.TreePojo;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.pojo.DepartmentPojo;

import java.util.List;

public interface IDepartmentService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{
    /**
     * 查询部门
     * @param currentPageNo
     * @param pageSize
     * @param parentCode
     * @return
     */
    Page<DepartmentPojo> queryDepartment(String currentPageNo, String pageSize, String parentCode);


    /**
     * 获取单个部门
     * @return
     */
    Department getDepartment(String departmentId);

    /**
     * 根据部门ID查询代理商信息
     *
     * @param departmentId
     * @return
     */
    DepartmentPojo getDepartmentById(String departmentId);

    /**
     * 获取部门Code和部门名称树形结构
     * @param parentCode
     * @return
     */
    TreePojo queryTreeDepartment(Long parentCode);

    /**
     * 递归查询部门树形结构
     * @param parentCode
     * @return
     */
    List<TreeBean> recursionTree(Long parentCode);

    List<TreeBean> recursionTreeAll(Long parentCode);

    List<TreeBean> recursionSubTree(Long parentCode);
}

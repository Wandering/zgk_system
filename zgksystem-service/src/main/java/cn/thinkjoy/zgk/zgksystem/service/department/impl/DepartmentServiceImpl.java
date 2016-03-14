/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  DepartmentServiceImpl.java 2015-08-31 11:11:28 $
 */
package cn.thinkjoy.zgk.zgksystem.service.department.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.common.TreePojo;
import cn.thinkjoy.zgk.zgksystem.dao.IDepartmentDAO;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("DepartmentServiceImpl")
public class DepartmentServiceImpl extends AbstractPageService<IBaseDAO<Department>, Department> implements IDepartmentService<IBaseDAO<Department>,Department> {
    @Autowired
    private IDepartmentDAO departmentDAO;

    @Override
    public IBaseDAO<Department> getDao() {
        return departmentDAO;
    }



    public Page<Department> queryDepartment(String currentPageNo,String pageSize,String parentCode){
        if(StringUtils.isBlank(parentCode)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        Map<String,Object> statusMap=new HashMap<String,Object>();
        Map<String,Object> parentMap=new HashMap<String,Object>();
        parentMap.put("op"," = ");
        parentMap.put("data", parentCode);
        statusMap.put("op", " = ");
        statusMap.put("data", Constants.NORMAL_STATUS);
        dataMap.put("groupOp", " AND ");
        dataMap.put("status", statusMap);
        dataMap.put("parentCode", parentMap);
        int count=this.count(dataMap);
        if(count>0){
            List<Department> departmentList = this.queryPage(dataMap, ((Integer.valueOf(currentPageNo) - 1) * Integer.valueOf(pageSize)), Integer.valueOf(pageSize), CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC);
            Page<Department> page=new Page<>();
            page.setCount(count);
            page.setList(departmentList);
            return page;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    public Department getDepartment(String departmentId){
        if(StringUtils.isBlank(departmentId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",Long.parseLong(departmentId));
        dataMap.put("status", Constants.NORMAL_STATUS);//获取正常
        Department department =(Department) this.queryOne(dataMap);
        if(department == null){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return department;
    }

    public TreePojo queryTreeDepartment(Long parentCode){
        if(parentCode==null || parentCode==0){
            parentCode=-1L;
        }
        TreePojo treePojo = new TreePojo();
        List<TreeBean> resultTree=new ArrayList<>() ;
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("parentCode",parentCode);
        dataMap.put("status",Constants.NORMAL_STATUS);
        List<Department> departmentList = this.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC.getAction());
        if(departmentList == null || departmentList.size()==0){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        for (Department d: departmentList){
            Map<String,Object> tempMap = new HashMap<>();
            tempMap.put("parentCode",d.getDepartmentCode());
            tempMap.put("status",Constants.NORMAL_STATUS);
            List<Department> tempList = this.queryList(tempMap,CodeFactoryUtil.ORDER_BY_FIELD,SqlOrderEnum.DESC.getAction());
            if(tempList!=null && tempList.size()>0){
                resultTree.add(new TreeBean(d.getDepartmentCode(),d.getDepartmentName(),true));
            }else{
                resultTree.add(new TreeBean(d.getDepartmentCode(),d.getDepartmentName(),false));
            }
        }
        treePojo.setTreeBeanList(resultTree);
        treePojo.setTreeName("部门树");
        return treePojo;
    }


    public  List<TreeBean> recursionTree(Long parentCode){
        if(parentCode==null || parentCode==0){
            parentCode=-1L;
        }
        List<TreeBean>  resultTree=new ArrayList<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("parentCode",parentCode);
        dataMap.put("status",Constants.NORMAL_STATUS);
        List<Department> departmentList=this.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC.getAction());
        if(departmentList!=null && departmentList.size()>0){
            for (Department d:departmentList) {
                resultTree.add(new TreeBean(d.getDepartmentCode(), d.getDepartmentName(),recursionTree(d.getDepartmentCode())));
            }
        }
        return resultTree;
    }
//    @Override
//    public void insert(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void update(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void updateNull(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void deleteById(Long id) {
//
//    }
//
//    @Override
//    public void deleteByProperty(String property, Object value) {
//
//    }
//
//    @Override
//    public BaseDomain fetch(Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain findOne(@Param("property") String property, @Param("value") Object value) {
//        return null;
//    }
//
//    @Override
//    public List findList(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public void deleteByCondition(Map condition) {
//
//    }
//
//    @Override
//    public void updateMap(@Param("map") Map entityMap) {
//
//    }
//
//    @Override
//    public List<Department> findAll() {
//        return departmentDAO.findAll();
//    }
//
//    @Override
//    public List like(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public Long selectMaxId() {
//        return null;
//    }
//
//    @Override
//    public BaseDomain updateOrSave(BaseDomain baseDomain, Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain selectOne(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public List selectList(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public Class getEntityClass() {
//        return null;
//    }
//
//    @Override
//    public int count(Map condition) {
//        return 0;
//    }
//
//    @Override
//    public BaseDomain queryOne(Map condition) {
//        return null;
//    }
//
//    @Override
//    public List queryList(@Param("condition") Map condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy) {
//        return null;
//    }
//
//    @Override
//    public List queryPage(@Param("condition") Map condition, @Param("offset") int offset, @Param("rows") int rows) {
//        return null;
//    }
//
//    @Override
//    protected DepartmentDAO getDao() {
//        return departmentDAO;
//    }
//
//    @Override
//    public BizData4Page queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows) {
//        return super.doQueryPageByDataPerm(resUri, conditions, curPage, offset, rows);
//    }


}

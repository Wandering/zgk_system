/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  PostServiceImpl.java 2015-08-31 11:11:29 $
 */
package cn.thinkjoy.zgk.zgksystem.service.post.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.dao.IPostDAO;
import cn.thinkjoy.zgk.zgksystem.domain.Post;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.post.IPostService;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import cn.thinkjoy.zgk.zgksystem.util.IdentityUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("PostServiceImpl")
public class PostServiceImpl extends AbstractPageService<IBaseDAO<Post>, Post> implements IPostService<IBaseDAO<Post>,Post>{
    @Autowired
    private IPostDAO postDAO;

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public IBaseDAO<Post> getDao() {
        return postDAO;
    }


    public Page<Post> queryPost(String currentPageNo,String pageSize,String departmentCode){
        if(StringUtils.isBlank(departmentCode)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        Map<String,Object> statusMap=new HashMap<String,Object>();
        Map<String,Object> departmentMap=new HashMap<String,Object>();
        departmentMap.put("op"," = ");
        departmentMap.put("data",Long.parseLong(departmentCode));
        statusMap.put("op"," = ");
        statusMap.put("data", Constants.NORMAL_STATUS);
        dataMap.put("groupOp", " AND ");
        dataMap.put("status", statusMap);
        dataMap.put("departmentCode",departmentMap);
        int count=this.count(dataMap);
        if(count>0){
            List<Post> postList = this.queryPage(dataMap, ((Integer.valueOf(currentPageNo) - 1) * Integer.valueOf(pageSize)), Integer.valueOf(pageSize), CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC);
            Page<Post> page=new Page<>();
            page.setCount(count);
            page.setList(postList);
            return page;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    public Post getPost(String postId){
        if (StringUtils.isBlank(postId)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", Long.parseLong(postId));
        dataMap.put("status", Constants.NORMAL_STATUS);
        Post post = (Post)this.queryOne(dataMap);
        if (post == null) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return post;
    }


    public Map<String,String> queryComboxPost(String departmentCode){
        if(StringUtils.isBlank(departmentCode)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String, String> resultMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("status",Constants.NORMAL_STATUS);
        dataMap.put("departmentCode",departmentCode);
        List<Post> postList = this.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC.getAction());
        for(Post p :postList){
            Map<String,Object> dataMap1 = new HashMap<>();
            dataMap1.put("status",Constants.NORMAL_STATUS);
            dataMap1.put("postCode",p.getPostCode());
            if(userInfoService.queryOne(dataMap1)==null) {
                resultMap.put(p.getPostCode() + "", p.getPostName());
            }
        }
        if(resultMap.size()==0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return resultMap;
    }

    public Page<Post> getManagerPost(Long postCode){
        if(postCode==null || postCode==0){
            postCode=-1L;
        }
        List<Post> list=new ArrayList<>();
        if(postCode== IdentityUtil.ADMIN_MANAGER_POST){
            Post productPost=new Post();
            productPost.setDepartmentCode((long) IdentityUtil.PRODUCT_MANAGER_DEPARTMENT);
            productPost.setPostCode((long) IdentityUtil.PRODUCT_MANAGER_POST);
            productPost.setPostName("产品管理员岗位");
            Post companyPost=new Post();
            companyPost.setDepartmentCode((long) IdentityUtil.COMPANY_MANAGER_DEPARTMENT);
            companyPost.setPostCode((long) IdentityUtil.COMPANY_MANAGER_POST);
            companyPost.setPostName("代理公司管理员岗位");
            list.add(productPost);
            list.add(companyPost);
            Page<Post> page=new Page<>();
            page.setCount(list.size());
            page.setList(list);
            return page;
        }else{
            throw  new BizException(ERRORCODE.NO_PERMISSION.getCode(),ERRORCODE.NO_PERMISSION.getMessage());
        }
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
//    public List<Post> findAll() {
//        return postDAO.findAll();
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
//    protected PostDAO getDao() {
//        return postDAO;
//    }
//
//    @Override
//    public BizData4Page queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows) {
//        return super.doQueryPageByDataPerm(resUri, conditions, curPage, offset, rows);
//    }


}

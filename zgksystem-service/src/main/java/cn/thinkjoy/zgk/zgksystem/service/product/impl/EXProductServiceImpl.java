/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  ProductServiceImpl.java 2015-08-31 11:11:29 $
 */
package cn.thinkjoy.zgk.zgksystem.service.product.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXProductDAO;
import cn.thinkjoy.zgk.zgksystem.pojo.ProductPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.company.ICompanyService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.service.product.IEXProductService;
import cn.thinkjoy.zgk.zgksystem.service.product.IProductService;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import cn.thinkjoy.zgk.zgksystem.util.IdentityUtil;
import cn.thinkjoy.zgk.zgksystem.domain.*;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("EXProductServiceImpl")
public class EXProductServiceImpl implements IEXProductService {
    @Autowired
    private IEXProductDAO iexProductDAO;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IProductService productService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserAccountService userAccountService;

    /**
     * 分页
     * @param map
     * @param offset
     * @param rows
     * @param orderBy
     * @param sortBy
     * @return
     */
    public Page<Product> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy){
        Page<Product> page=new Page<Product>();
        Integer count=iexProductDAO.totalCount(map);
        if(count>0){
            page.setCount(count);
            page.setList(iexProductDAO.queryByPageAndCondition(map, offset, rows, orderBy, sortBy));
            return page;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 根据用户获取产品树
     *
     * @param userPojo
     * @return
     */
    @Override
    public List<TreeBean> queryTreeProduct(UserPojo userPojo) {
        List<TreeBean> resultTree=new ArrayList<>() ;
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("status", Constants.NORMAL_STATUS);
        if(IdentityUtil.ADMIN_MANAGER_POST!=userPojo.getPostCode() && IdentityUtil.SYSTEM_MANAGER_POST!=userPojo.getPostCode() && IdentityUtil.PRODUCT_MANAGER_POST!=userPojo.getPostCode()){
            Map<String,Object> departmentMap=new HashMap<>();
            departmentMap.put("departmentCode",userPojo.getDepartmentCode());
            Department department=(Department)departmentService.queryOne(departmentMap);
            Map<String,Object> companyMap=new HashMap<>();
            companyMap.put("companyCode",department.getCompanyCode());
            Company company=(Company)companyService.queryOne(companyMap);
            dataMap.put("productCode",company.getProductCode());
        }
        List<Product> listProduct = productService.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, "DESC");
        for(Product p :listProduct){
            TreeBean treeBean=new TreeBean();
            treeBean.setId(p.getProductCode());
            treeBean.setName(p.getProductName());
            resultTree.add(treeBean);
        }
        if(resultTree==null || resultTree.size()==0 ) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return resultTree;
    }

   public Page<ProductPojo> queryProduct(int currentPageNo ,int pageSize){
       Map<String,Object> dataMap=new HashMap<>();
       Map<String,Object> statusMap=new HashMap<String,Object>();
       statusMap.put("op", " = ");
       statusMap.put("data",Constants.NORMAL_STATUS);
       dataMap.put("groupOp", " AND ");
       dataMap.put("status", statusMap);
       int count=productService.count(dataMap);
       if(count>0){
           List<Product> productList = productService.queryPage(dataMap, ((Integer.valueOf(currentPageNo) - 1) * Integer.valueOf(pageSize)), Integer.valueOf(pageSize), CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC);
           List<ProductPojo> productPojos = new ArrayList<>();
           for(Product p: productList){
               ProductPojo productPojo=new ProductPojo();
               productPojo.setProductId(p.getId());
               productPojo.setProductCode(p.getProductCode());
               productPojo.setProductName(p.getProductName());
               productPojo.setProductLogo(p.getProductLogo());
               productPojo.setProductIndexPage(p.getProductIndexPage());
               productPojo.setDescription(p.getDescription());

               Map<String,Object> userInfoMap = new HashMap<>();
               userInfoMap.put("userCode", CodeFactoryUtil.getInitProductAccount(p.getProductCode()));
               userInfoMap.put("status", Constants.NORMAL_STATUS);
               UserInfo userInfo = (UserInfo)userInfoService.queryOne(userInfoMap);
               Map<String,Object> userAccountMap = new HashMap<>();
               userAccountMap.put("accountCode",CodeFactoryUtil.getInitProductAccount(p.getProductCode()));
               userAccountMap.put("status", Constants.NORMAL_STATUS);
               UserAccount userAccount = (UserAccount)userAccountService.queryOne(userAccountMap);
               if(userInfo == null || userAccount == null){
                   productPojo.setUserName("该用户已被删除");
                   productPojo.setLoginNumber("该账号已被删除");
               }else {
                   productPojo.setUserName(userInfo.getUserName());
                   productPojo.setLoginNumber(userAccount.getLoginNumber());
               }
               productPojos.add(productPojo);
           }
           Page<ProductPojo> page= new Page<>();
           page.setCount(count);
           page.setList(productPojos);
           return page;
       }else{
           throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
       }
   }


    public Product getProduct(String productId){
        if(StringUtils.isBlank(productId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",Long.parseLong(productId));
        dataMap.put("status",Constants.NORMAL_STATUS);
        Product product = (Product)productService.queryOne(dataMap);
        if(product == null){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return product;
    }

    public Map<String,String> queryComboxProduct() {
        Map<String,String> resultMap=new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("status",Constants.NORMAL_STATUS);
        List<Product> listProduct = productService.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, "DESC");
        for(Product p :listProduct){
            resultMap.put(p.getProductCode() + "", p.getProductName());
        }
        if(resultMap.size()==0 ) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return resultMap;
    }


}

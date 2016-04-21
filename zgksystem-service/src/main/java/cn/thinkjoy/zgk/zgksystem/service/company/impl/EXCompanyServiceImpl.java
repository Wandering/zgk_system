/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  ProductServiceImpl.java 2015-08-31 11:11:29 $
 */
package cn.thinkjoy.zgk.zgksystem.service.company.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXCompanyDAO;
import cn.thinkjoy.zgk.zgksystem.domain.Company;
import cn.thinkjoy.zgk.zgksystem.domain.UserAccount;
import cn.thinkjoy.zgk.zgksystem.domain.UserInfo;
import cn.thinkjoy.zgk.zgksystem.pojo.CompanyPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.company.ICompanyService;
import cn.thinkjoy.zgk.zgksystem.service.company.IEXCompanyService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
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


@Service("EXCompanyServiceImpl")
public class EXCompanyServiceImpl implements IEXCompanyService{
    @Autowired
    private IEXCompanyDAO iexCompanyDAO;
    @Autowired
    private ICompanyService<IBaseDAO<Company>, Company> companyService;
    @Autowired
    private IDepartmentService departmentService;//部门Service

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserAccountService  userAccountService;
    /**
     * 分页
     * @param map
     * @param offset
     * @param rows
     * @param orderBy
     * @param sortBy
     * @return
     */
    public Page<Company> queryByPage(Map<String, Object> map, int offset, int rows, String orderBy, String sortBy){
        Page<Company> page=new Page<Company>();
        Integer count=iexCompanyDAO.totalCount(map);
        if(count>0){
            page.setCount(count);
            page.setList(iexCompanyDAO.queryByPageAndCondition(map, offset, rows, orderBy, sortBy));
            return page;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    /**
     * 查询代理公司
     *
     * @param userPojo
     * @param currentPageNo
     * @param pageSize
     * @param parentCode
     * @return Page<T>
     */
    @Override
    public Page<CompanyPojo> queryCompany(UserPojo userPojo, int currentPageNo, int pageSize, String parentCode) {
        if(StringUtils.isBlank(parentCode)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Long productCode=Long.parseLong(parentCode);
        Map<String,Object> dataMap=new HashMap<>();
        Map<String,Object> statusMap=new HashMap<>();
        Map<String,Object> productMap=new HashMap<>();
        productMap.put("op"," = ");
        productMap.put("data",productCode);
        statusMap.put("op", " = ");
        statusMap.put("data", Constants.NORMAL_STATUS);
        dataMap.put("groupOp", " AND ");
        dataMap.put("status", statusMap);
        dataMap.put("productCode",productMap);
        if(IdentityUtil.ADMIN_MANAGER_POST!=userPojo.getPostCode() && IdentityUtil.SYSTEM_MANAGER_POST!=userPojo.getPostCode() && IdentityUtil.PRODUCT_MANAGER_POST!=userPojo.getPostCode()){
            Map<String,Object> companyMap=new HashMap<>();
            companyMap.put("op"," = ");
            companyMap.put("data", userPojo.getAccountCode().toString().substring(0,5));
            dataMap.put("companyCode",companyMap);
        }
        int count = companyService.count(dataMap);

        List<Company> companyList = companyService.queryPage(dataMap, ((Integer.valueOf(currentPageNo) - 1) * Integer.valueOf(pageSize)), Integer.valueOf(pageSize), CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC);
        List<CompanyPojo> companyPojos = new ArrayList<>();
        for(Company p: companyList){
            CompanyPojo companyPojo=new CompanyPojo();
            companyPojo.setCompanyId(p.getId());
            companyPojo.setCompanyCode(p.getCompanyCode());
            companyPojo.setCompanyName(p.getCompanyName());
            companyPojo.setCompanyAddress(p.getCompanyAddress());
            companyPojo.setCompanyEmail(p.getEmail());
            companyPojo.setCompanyLogo(p.getCompanyLogo());
            companyPojo.setCompanyShortName(p.getCompanyShortName());
            companyPojo.setContactFax(p.getContactFax());
            companyPojo.setContactPhone(p.getContactPhone());
            companyPojo.setContactPerson(p.getContactPerson());
            companyPojo.setDescription(p.getDescription());
            companyPojo.setProductCode(p.getProductCode());
            companyPojo.setZipCode(p.getZipCode());

            Map<String,Object> userInfoMap = new HashMap<>();
            userInfoMap.put("userCode", CodeFactoryUtil.getInitCompanyAccount(p.getCompanyCode()));
            userInfoMap.put("status", Constants.NORMAL_STATUS);
            UserInfo userInfo = (UserInfo)userInfoService.queryOne(userInfoMap);
            Map<String,Object> userAccountMap = new HashMap<>();
            userAccountMap.put("accountCode", CodeFactoryUtil.getInitCompanyAccount(p.getCompanyCode()));
            userAccountMap.put("status", Constants.NORMAL_STATUS);
            UserAccount userAccount = (UserAccount)userAccountService.queryOne(userAccountMap);
            if(userPojo == null){
                companyPojo.setUserName("该用户已被删除");
                companyPojo.setLoginNumber("该账号已被删除");
            }else {
                companyPojo.setUserName(userInfo.getUserName());
                companyPojo.setLoginNumber(userAccount.getLoginNumber());
            }
            companyPojos.add(companyPojo);
        }
        Page<CompanyPojo> page= new Page<>();
        page.setCount(count);
        page.setList(companyPojos);
        return page;
    }

    public Company getCompany(String companyId){
        if(StringUtils.isBlank(companyId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",Long.parseLong(companyId));
        dataMap.put("status",Constants.NORMAL_STATUS);//获取正常
        Company company = companyService.queryOne(dataMap);
        if(company == null){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return company;
    }

    public Map<String,String> queryComboxCompany(){
        Map<String, String> resultMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("status",Constants.NORMAL_STATUS);
        List<Company> listCompany = companyService.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC.getAction());
        for(Company p :listCompany){
            resultMap.put(p.getCompanyCode() + "", p.getCompanyName());
        }
        if(resultMap.size()==0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return resultMap;
    }

   public  List<TreeBean> queryTreeCompany(){
       List<TreeBean> resultTree=new ArrayList<>() ;
       Map<String,Object> dataMap = new HashMap<>();
       dataMap.put("status",Constants.NORMAL_STATUS);
       List<Company> listCompany = companyService.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC.getAction());
       if(listCompany == null || listCompany.size() == 0){
           throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
       }
       for(Company p :listCompany){
           resultTree.add(new TreeBean(p.getCompanyCode(), p.getCompanyName()));
       }
       return resultTree;
    }

}

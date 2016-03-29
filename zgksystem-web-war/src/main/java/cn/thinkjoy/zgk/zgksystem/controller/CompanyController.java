package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.*;
import cn.thinkjoy.zgk.zgksystem.pojo.CompanyPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import cn.thinkjoy.zgk.zgksystem.service.company.ICompanyService;
import cn.thinkjoy.zgk.zgksystem.service.company.IEXCompanyService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.service.post.IPostService;
import cn.thinkjoy.zgk.zgksystem.service.system.IK12systemPostService;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import cn.thinkjoy.zgk.zgksystem.util.IdentityUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wangyongqiang on 15/9/1
 */
@Controller
@RequestMapping("/system/company")
public class CompanyController {
    @Autowired
    private ICompanyService<cn.thinkjoy.common.dao.IBaseDAO<Company>, Company> companyService;
    @Autowired
    private IDepartmentService departmentService;//部门Service

    @Autowired
    private IPostService postService;//岗位Service

    @Autowired
    private IUserInfoService userInfoService;//人员Service


    @Autowired
    private IUserAccountService userAccountService;//人员Service

    @Autowired
    private IK12systemPostService k12systemPostService;

    @Autowired
    private IEXCodeService excodeService;//codeService
    @Autowired
    private IEXCompanyService iexCompanyService;

    private static Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);
    /**
     * 新增和修改代理公司
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "addOrEditCompany", method = RequestMethod.POST)
    public String addOrEditCompany(HttpServletRequest request){
        UserPojo userPojo = (UserPojo)HttpUtil.getSession(request,"user");
        String companyJson = request.getParameter("companyJson");
        if(StringUtils.isBlank(companyJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        CompanyPojo company=null;
        try{
            company =  JsonMapper.buildNormalMapper().fromJson(companyJson, CompanyPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        Company c=new Company();
        c.setProductCode(company.getProductCode());
        c.setId(company.getCompanyId());
        c.setCompanyName(company.getCompanyName());
        c.setCompanyShortName(company.getCompanyShortName());
        c.setCompanyLogo(company.getCompanyLogo());
        c.setCompanyAddress(company.getCompanyAddress());
        c.setContactFax(company.getContactFax());
        c.setContactPerson(company.getContactPerson());
        c.setContactPhone(company.getContactPhone());
        c.setDescription(company.getDescription());
        c.setEmail(company.getCompanyEmail());
        c.setZipCode(company.getZipCode());
        c.setSeqSort(company.getSeqSort());
        if(company.getCompanyId()==null || company.getCompanyId()==0 ){

            //新建代理公司、创建代理公司管理员信息以及账户信息
            c.setCreator(userPojo.getAccountCode());
            c.setCreateDate(System.currentTimeMillis());
            Long maxCompanyCode=excodeService.selectMaxCodeByParent(CodeFactoryUtil.COMPANY_CODE,CodeFactoryUtil.COMPANY_TABLE,  CodeFactoryUtil.PRODUCT_CODE, company.getProductCode());
            if(maxCompanyCode==null||maxCompanyCode==0){
                maxCompanyCode= CodeFactoryUtil.getInitCompany(company.getProductCode())+1;//公司Code初始生成规则 所属产品信息的Code*1000+1
            }else{
                ++maxCompanyCode;
            }
            c.setCompanyCode(maxCompanyCode);

            //添加公司的时候默认添加一个以公司名称等信息为部门的一级部门
            Department department=new Department();
            department.setCompanyCode(maxCompanyCode);
            department.setParentCode(-1L);
            department.setDepartmentName(company.getCompanyName());
            department.setDescription(company.getDescription());
            department.setDepartmentFax(company.getContactFax());
            department.setDepartmentPhone(company.getContactPhone());
            department.setDepartmentPrincipal(company.getContactPerson());
            department.setSeqSort(0);
            Long maxDepartmentCode=CodeFactoryUtil.getInitDepartment(maxCompanyCode);//部门Code初始生成规则 所属代理公司信息的Code*1000+1
            department.setDepartmentCode(maxDepartmentCode);
            departmentService.updateOrSave(department,null);


            Long  maxAccountCode= CodeFactoryUtil.getInitCompanyAccount(maxCompanyCode);
            UserInfo userInfo=new UserInfo();
            userInfo.setUserName(company.getUserName());
            userInfo.setDepartmentCode(department.getDepartmentCode());
            userInfo.setPostCode((long) IdentityUtil.COMPANY_MANAGER_POST);
            userInfo.setEmail(company.getEmail());
            userInfo.setPhone(company.getPhone());
            userInfo.setUserCode(maxAccountCode);
            String areaCode;
            if (userPojo.getRoleType().equals(1)){
//                areaCode=department.getAreaCode().substring(0,2);
                areaCode="00";
            } else if (userPojo.getRoleType().equals(2)){
                areaCode=department.getAreaCode().substring(0,4);
            } else if (userPojo.getRoleType().equals(3)){
                areaCode=department.getAreaCode().substring(0,6);
            } else {
                throw  new BizException(ERRORCODE.INSERT_ERROR.getCode(),ERRORCODE.INSERT_ERROR.getMessage());
            }
            userInfo.setAreaCode(areaCode);
            userInfo.setRoleType(1);
            userInfoService.updateOrSave(userInfo, null);
            //创建产品同时创建公司管理员账户
            UserAccount userAccount=new UserAccount();
            userAccount.setLoginNumber(company.getLoginNumber());
            userAccount.setPassword(company.getPassword());
            userAccount.setUserType(Constants.USER_TYPE_NORMAL);
            userAccount.setIdentityCode((long) IdentityUtil.COMPANY_MANAGER_POST);
            userAccount.setUserCode(maxAccountCode);
            userAccount.setAccountCode(maxAccountCode);
            userAccountService.updateOrSave(userAccount,null);
            //创建代理公司管理员默认登录K12系统的关联关系权限

            Map<String,Object> k12systemPostMap=new HashMap<>();
            k12systemPostMap.put("postCode", (long) IdentityUtil.COMPANY_MANAGER_POST);
            k12systemPostMap.put("systemCode", Constants.SYSTEM_CODE);
            List<K12systemPost> k12systemPosts=k12systemPostService.queryList(k12systemPostMap,null,null);
            if(k12systemPosts==null ||  k12systemPosts.size()==0) {
                K12systemPost k12systemPost = new K12systemPost();
                k12systemPost.setPostCode((long) IdentityUtil.COMPANY_MANAGER_POST);
                k12systemPost.setSystemCode(Constants.SYSTEM_CODE);
                k12systemPost.setCreateDate(System.currentTimeMillis());
                k12systemPost.setCreator(userPojo.getAccountCode());
                k12systemPostService.insert(k12systemPost);
            }

        }else{
            c.setLastModifier(userPojo.getAccountCode());
            c.setLastModDate(System.currentTimeMillis());

            Map<String,Object> dataMap=new HashMap<>();
            dataMap.put("companyCode",company.getCompanyCode());
            dataMap.put("status",Constants.NORMAL_STATUS);
            dataMap.put("parentCode","-1");
            Department department =(Department)departmentService.queryOne(dataMap);
            if(department!=null){
                department.setDepartmentName(company.getCompanyName());
                departmentService.updateOrSave(department,department.getId());//级联修改公司下的一级部门名称
            }
        }
        companyService.updateOrSave(c, c.getId());
        return "ok";

    }

    /**
     * 删除代理公司
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "delCompany",method = RequestMethod.GET)
    public String delCompany(HttpServletRequest request){
        String companyId = request.getParameter("id");
        if(StringUtils.isBlank(companyId)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Company c = companyService.findOne("id", Long.parseLong(companyId));
        c.setStatus(Constants.DELETEED_STATUS);
        companyService.update(c);

        //删除代理公司下的账户信息和账户详情
        Map<String,Object> userDataMap=new HashMap<>();
        userDataMap.put("status",Constants.NORMAL_STATUS);
        userDataMap.put("deparmentCode",IdentityUtil.COMPANY_MANAGER_DEPARTMENT);
        userDataMap.put("postCode",IdentityUtil.COMPANY_MANAGER_POST);
        userDataMap.put("userCode", CodeFactoryUtil.getInitCompanyAccount(c.getCompanyCode()));
        UserInfo userInfo= (UserInfo) userInfoService.queryOne(userDataMap);
        if(userInfo!=null) {
            userInfo.setStatus(Constants.DELETEED_STATUS);
            userInfoService.update(userInfo);
        }
        //删除账户
        userDataMap.put("accountCode", CodeFactoryUtil.getInitCompanyAccount(c.getCompanyCode()));
        UserAccount account=(UserAccount)userAccountService.queryOne(userDataMap);
        if(account!=null) {
            account.setStatus(Constants.DELETEED_STATUS);
            userAccountService.update(account);
        }

        //级联删除公司下的部门
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("companyCode",c.getCompanyCode());
        dataMap.put("status", Constants.NORMAL_STATUS);
        List<Department> departmentList= departmentService.queryList(dataMap, null, null);
        if(departmentList!=null && departmentList.size()>0){
            for (Department d:departmentList){
                d.setStatus(Constants.DELETEED_STATUS);
                departmentService.update(d);
                Map<String,Object> dataPostMap=new HashMap<>();
                dataPostMap.put("departmentCode", d.getDepartmentCode());
                dataPostMap.put("status", Constants.NORMAL_STATUS);
                List<Post> postList= postService.queryList(dataPostMap,null,null);
                if(postList!=null && postList.size()>0){
                    for (Post p: postList){
                        p.setStatus(Constants.DELETEED_STATUS);
                        postService.update(p);
                        //级联删除岗位下的人员
                        Map<String,Object> dataUserInfoMap=new HashMap<>();
                        dataUserInfoMap.put("postCode", p.getPostCode());
                        dataUserInfoMap.put("status", Constants.NORMAL_STATUS);
                        List<UserInfo> userInfoList= userInfoService.queryList(dataUserInfoMap,null,null);
                        if(userInfoList!=null && userInfoList.size()>0){
                            for (UserInfo u :userInfoList){
                                u.setStatus(Constants.DELETEED_STATUS);
                                userInfoService.update(u);
                                Map<String,Object> dataAccountMap=new HashMap<>();
                                dataAccountMap.put("userCode", u.getUserCode());
                                dataAccountMap.put("status", Constants.NORMAL_STATUS);
                                List<UserAccount> userAccounts= userAccountService.queryList(dataAccountMap,null,null);
                                if(userAccounts!=null&& userAccounts.size()>0){
                                    for (UserAccount ua:userAccounts){
                                        ua.setStatus(Constants.DELETEED_STATUS);
                                        userAccountService.update(ua);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        //删除代理公司管理员岗位与系统的关联关系表信息
        Map<String,Object> k12systemPostMap=new HashMap<>();
        dataMap.put("status",Constants.NORMAL_STATUS);
        dataMap.put("systemCode",Constants.SYSTEM_CODE);
        dataMap.put("postCode",IdentityUtil.COMPANY_MANAGER_POST);
        K12systemPost k12systemPost=(K12systemPost)k12systemPostService.queryOne(k12systemPostMap);
        if(k12systemPost!=null){
            k12systemPost.setStatus(Constants.DELETEED_STATUS);
            k12systemPostService.update(k12systemPost);
        }

        return "ok";
    }

    /**
     * 查询代理公司
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryCompany",method = RequestMethod.GET)
    public Page<CompanyPojo> queryCompany(HttpServletRequest request){
        UserPojo userPojo = (UserPojo)HttpUtil.getSession(request,"user");
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize =Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        String parentCode=request.getParameter("productCode");
        return  iexCompanyService.queryCompany(userPojo,currentPageNo,pageSize,parentCode);
}
    /**
     * 获取单个代理公司
     * @param request request
     * @return T
     */
    @ResponseBody
    @RequestMapping(value = "getCompany",method = RequestMethod.GET)
    public Company getCompany(HttpServletRequest request){
        String companyId = request.getParameter("id");
        return  iexCompanyService.getCompany(companyId);
    }

    /**
     * 获取代理公司Code和代理公司名称
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "queryComboxCompany",method = RequestMethod.GET)
    public Map<String,String> queryComboxCompany(){
        return iexCompanyService.queryComboxCompany();
    }
    /**
     * 获取代理公司Code和代理公司名称树形
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "queryTreeCompany",method = RequestMethod.GET)
    public List<TreeBean> queryTreeCompany(){
        return iexCompanyService.queryTreeCompany();
    }



}

package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.restful.apigen.annotation.ApiDesc;
import cn.thinkjoy.common.utils.ObjectFactory;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.PostApiService;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.*;
import cn.thinkjoy.zgk.zgksystem.common.TreePojo;
import cn.thinkjoy.zgk.zgksystem.edomain.UserRoleEnum;
import cn.thinkjoy.zgk.zgksystem.pojo.DepartmentPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.PostPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.dataDictionary.IDataDictionaryService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentProductRelationService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.service.department.IEXDeparmentService;
import cn.thinkjoy.zgk.zgksystem.service.post.IEXPostDataauthorityService;
import cn.thinkjoy.zgk.zgksystem.service.post.IEXPostService;
import cn.thinkjoy.zgk.zgksystem.service.post.IPostService;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import cn.thinkjoy.zgk.zgksystem.service.role.IRolePostService;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import cn.thinkjoy.zgk.zgksystem.util.ModelUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
 *
 *
 *
 */
@Controller
@RequestMapping("/system/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IEXDeparmentService iexDeparmentService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IEXPostService  iexPostService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IEXCodeService excodeService;

    @Autowired
    private IEXPostDataauthorityService iexPostDataauthorityService;

    @Autowired
    private IRolePostService rolePostService;

    @Autowired
    private PostApiService postApiService;

    @Autowired
    private IDataDictionaryService dataDictionaryService;

    @Autowired
    private IDepartmentProductRelationService departmentProductRelationService;

    private static Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @ResponseBody
    @RequestMapping(value = "checkDepartmentNameIsExist",method = RequestMethod.POST)
    public String checkDepartmentNameIsExist(HttpServletRequest request){
        String departmentName = request.getParameter("departmentName");
        String departmentId = request.getParameter("departmentId");
        Map<String,Object> condition=new HashMap<>();
        condition.put("departmentName",departmentName);
        condition.put("status", 0);
        Department department = (Department)departmentService.queryOne(condition);
        // 新增
        if(departmentId == null || "0".equals(departmentId)){
            return department==null?"1":"0";
        }else {
            // 修改
            if(department != null && !department.getId().toString().equals(departmentId)){
                return "0";
            }
            return "1";
        }
    }

    /**
     * 逻辑删除部门
     * @param request request
     * @return String ok
     */
    @ResponseBody
    @RequestMapping(value = "delDepartment",method = RequestMethod.GET)
    public String delDepartment(HttpServletRequest request){
        String departmentId = request.getParameter("id");
        if(StringUtils.isBlank(departmentId)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Department d = (Department) departmentService.findOne("id", departmentId);
        d.setStatus(Constants.DELETEED_STATUS);
        updateAreaCode(d,"0");
        departmentService.update(d);
        deleteDepartmentPost(d.getDepartmentCode().toString());
        deleteDepartmentUser(d.getDepartmentCode().toString());
        //递归删除部门下的部门
        recursionDelDeparment(d.getDepartmentCode());
        //删除部门下的岗位

        return "ok";
    }

    /**
     * 查询部门
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryDepartment",method = RequestMethod.GET)
    public Page<DepartmentPojo> queryDepartment(HttpServletRequest request){
        String currentPageNo = HttpUtil.getParameter(request, "currentPageNo", "1");
        String pageSize = HttpUtil.getParameter(request, "pageSize", "10");
        String parentCode=request.getParameter("parentCode");
        if(StringUtils.isBlank(parentCode)){
            ModelUtil.throwException(ERRORCODE.PARAM_ISNULL);
        }
        return  departmentService.queryDepartment(currentPageNo,pageSize,parentCode);

    }
    /**
     * 获取单个部门
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "getDepartment",method = RequestMethod.GET)
    public DepartmentPojo getDepartment(HttpServletRequest request){
        String departmentId = request.getParameter("id");
        return departmentService.getDepartmentById(departmentId);
    }

    /**
     * 获取部门Code和部门名称树形结构
     * @param request request
     * @return List
     */
    @ResponseBody
    @RequestMapping(value = "queryTreeDepartment",method = RequestMethod.GET)
    public TreePojo queryTreeDepartment(HttpServletRequest request){
        Long parentCode=Long.parseLong(HttpUtil.getParameter(request, "parentCode", "-1"));
        return departmentService.queryTreeDepartment(parentCode);
    }
    /**
     * 获取部门Code和部门名称树形结构
     * @param request request
     * @return List
     */
    @ResponseBody
    @RequestMapping(value = "queryTreeDepartmentAll",method = RequestMethod.GET)
    public List<TreeBean> queryTreeDepartmentAll(HttpServletRequest request){
        //当前用户的岗位code
        UserPojo userPojo=(UserPojo)HttpUtil.getSession(request,"user");
        Long userDepartmentCode=userPojo.getDepartmentCode();
        String type = request.getParameter("navType");
        // TODO 这里好像有坑有坑有坑
        if (type!=null&&type.equals("2")){
            return departmentService.recursionSubTree(userDepartmentCode);
        }
        if (userDepartmentCode==-1){
            return departmentService.recursionTreeAll(userDepartmentCode);
        }
        return departmentService.recursionTree(userDepartmentCode);
    }

    /**
     * 递归删除部门以及部门下岗位，岗位下的人员
     * @param parentCode
     * @return
     */
    public void recursionDelDeparment(Long parentCode){
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("parentCode", parentCode);
        dataMap.put("status", Constants.NORMAL_STATUS);
        List<Department> departmentList= departmentService.queryList(dataMap,null,null);
        if(departmentList!=null&& departmentList.size()>0){
            for (Department d: departmentList){
                d.setStatus(Constants.DELETEED_STATUS);
                updateAreaCode(d,"0");
                departmentService.update(d);
                deleteDepartmentPost(d.getId().toString());
                deleteDepartmentUser(d.getId().toString());
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
                recursionDelDeparment(d.getDepartmentCode());
            }
        }
    }

    private String addPost(Department department,Long accountCode){
        PostPojo postPojo = new PostPojo();
        postPojo.setDepartmentCode(department.getDepartmentCode());
        postPojo.setPostName(department.getDepartmentName());
        postPojo.setDescription(department.getDescription());
        postPojo.setSeqSort(department.getSeqSort());
        postPojo.setStatus(department.getStatus());
        Post post = new Post();
        if (postPojo.getId() == null || postPojo.getId() == 0) {
            post.setDepartmentCode(postPojo.getDepartmentCode());
            post.setPostName(postPojo.getPostName());
            post.setDescription(postPojo.getDescription());
            post.setSeqSort(postPojo.getSeqSort());
            post.setStatus(Constants.NORMAL_STATUS);
            post.setCreator(accountCode);
            Long maxPostCode = excodeService.selectMaxCodeByParent(
                    CodeFactoryUtil.POSITION_CODE,
                    CodeFactoryUtil.POSITION_TABLE,
                    CodeFactoryUtil.DEPARTMENT_CODE,
                    post.getDepartmentCode());
            if (maxPostCode == null || maxPostCode == 0) {
                maxPostCode = CodeFactoryUtil.getInitPosition(postPojo.getDepartmentCode()) ;
            } else {
                ++maxPostCode;
            }
            post.setPostCode(maxPostCode);
            postService.updateOrSave(post, null);
            distributionRole(post,department.getRoleType());
        } else {
            BeanUtils.copyProperties(postPojo, post);
            postService.updateOrSave(post, post.getId());
            iexPostDataauthorityService.deleteByPost(post.getPostCode());
        }
        return "ok";
    }

    private String distributionRole(Post post,int roleCode){
        RolePost rolePost=new RolePost();
        rolePost.setPostCode(post.getPostCode());
        Long roleCodeLong=0L;
        switch (roleCode){
            case 1:roleCodeLong=10L;break;
            case 2:roleCodeLong=11L;break;
            case 3:roleCodeLong=12L;break;
            case 4:roleCodeLong=13L;break;
        }
        rolePost.setRoleCode(roleCodeLong);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("postCode",rolePost.getPostCode());
        dataMap.put("status", Constants.NORMAL_STATUS);
        List<RolePost> rolePosts = rolePostService.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC.getAction());
        if(rolePosts!=null && rolePosts.size()>0){
            for (RolePost rp: rolePosts){
                rp.setStatus(Constants.DELETEED_STATUS);//先删除原有的角色，后增加新选择的角色
                rolePostService.update(rp);
            }
        }
        RolePost rp=new RolePost();
        rp.setPostCode(rolePost.getPostCode());
        rp.setRoleCode(rolePost.getRoleCode());
        rp.setStatus(Constants.NORMAL_STATUS);
        rolePostService.updateOrSave(rp, null);
        if (roleCode == 1||roleCode == 2||roleCode == 3) {
            distributionSystemCode(rp.getPostCode(), 1L);
        }
        distributionSystemCode(rp.getPostCode(),2L);
        return "ok";
    }

    private String distributionSystemCode(long postCode,long systemCode){
        postApiService.postSystemAuthority(postCode,systemCode);
        return "ok";
    }

    private void updateAreaCode(Department d,String status){
        String areaCode=null;
        if (d.getRoleType()==null||d.getRoleType().equals("")){

        } else if (d.getRoleType() == 2){
            areaCode=d.getAreaCode()+"0000";
            dataDictionaryService.updateProvince(areaCode,status);
        } else if (d.getRoleType() == 3){
            areaCode=d.getAreaCode()+"00";
            dataDictionaryService.updateCity(areaCode,status);
        } else if (d.getRoleType() == 4){
            areaCode=d.getAreaCode();
            dataDictionaryService.updateCounty(areaCode,status);
        }
    }

    /**
     * 删除部门下的人员
     * @param departmentId
     */
    private void deleteDepartmentUser(String departmentId){
        UserInfo userInfo = (UserInfo) userInfoService.findOne("departmentCode", departmentId);
        if (userInfo!=null) {
            userInfo.setStatus(Constants.DELETEED_STATUS);
            userInfoService.update(userInfo);
            UserAccount userAccount=(UserAccount)userAccountService.findOne("userCode", userInfo.getUserCode());
            userAccount.setStatus(Constants.DELETEED_STATUS);
            userAccountService.update(userAccount);
        }
    }

    /**
     * 删除部门下的岗位
     * @param departmentId
     */
    private void deleteDepartmentPost(String departmentId){
        Post post = (Post) postService.findOne("departmentCode", departmentId);
        if (post!=null) {
            post.setStatus(Constants.DELETEED_STATUS);
            postService.update(post);
        }
    }


    /**
     * TODO 后期将增加和修改代理商业务逻辑迁移至service中
     *
     */

    @ResponseBody
    @ApiDesc(owner = "杨国荣",value = "新增代理商")
    @RequestMapping(value = "addDepartment", method = RequestMethod.POST)
    public Object addDepartment(HttpServletRequest request){

        String departmentJson = request.getParameter("departmentJson");
        if(StringUtils.isBlank(departmentJson)){
            ModelUtil.throwException(ERRORCODE.PARAM_ISNULL);
        }
        DepartmentPojo departmentPojo =  JsonMapper.buildNormalMapper().fromJson(departmentJson, DepartmentPojo.class);
        if(departmentPojo == null){
            ModelUtil.throwException(ERRORCODE.JSONCONVERT_ERROR);
        }

        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("departmentName",departmentPojo.getDepartmentName());
        queryMap.put("status", 0);
        if(departmentService.queryOne(queryMap)!=null){
            ModelUtil.throwException(ERRORCODE.ALREADY_EXIST_ERROR);
        }

        queryMap.clear();
        queryMap.put("departmentCode",departmentPojo.getParentCode());
        queryMap.put("status", Constants.NORMAL_STATUS);//获取正常
        Department parentDeparentment =(Department) departmentService.queryOne(queryMap);

        Department childDeparentment = new Department();
        BeanUtils.copyProperties(departmentPojo,childDeparentment);
        childDeparentment.setCompanyCode(parentDeparentment.getCompanyCode());

        UserPojo userPojo=(UserPojo)HttpUtil.getSession(request,"user");
        int roleType = userPojo.getRoleType();
        String areaCode = "";
        if (roleType == UserRoleEnum.SUPER_MANAGE.getValue()){
            areaCode = departmentPojo.getAreaCode().substring(0,2);
            dataDictionaryService.updateProvince(areaCode+"0000","-1");
        } else if (roleType == UserRoleEnum.PROVICE_AGENT.getValue()){
            areaCode = departmentPojo.getAreaCode().substring(0,4);
            dataDictionaryService.updateCity(areaCode+"00","-1");
        } else if (roleType == UserRoleEnum.CITY_AGENT.getValue()){
            areaCode = departmentPojo.getAreaCode().substring(0,6);
            dataDictionaryService.updateCounty(areaCode,"-1");
        } else {
            ModelUtil.throwException(ERRORCODE.INSERT_ERROR);
        }

        childDeparentment.setAreaCode(areaCode);
        childDeparentment.setRoleType(userPojo.getRoleType()+1);
        Long maxDepartmentCode=excodeService.selectMaxCodeByParent(
                CodeFactoryUtil.DEPARTMENT_CODE,
                CodeFactoryUtil.DEPARTMENT_TABLE,
                CodeFactoryUtil.COMPANY_CODE,
                childDeparentment.getCompanyCode());
        if(maxDepartmentCode==null||maxDepartmentCode==0){
            //部门Code初始生成规则 所属公司信息的Code*1000+1
            maxDepartmentCode= CodeFactoryUtil.getInitDepartment(childDeparentment.getCompanyCode());
        }else{
            ++maxDepartmentCode;
        }
        childDeparentment.setDepartmentCode(maxDepartmentCode);

        departmentService.insert(childDeparentment);
        addProductRelation(
                departmentPojo,
                roleType == UserRoleEnum.SUPER_MANAGE.getValue(),
                maxDepartmentCode);
        addPost(childDeparentment,userPojo.getAccountCode());

        return ObjectFactory.getSingle();
    }

    /**
     * 添加商品价格
     *
     * @param departmentPojo
     * @param isSuperManage  是否是超级管理员
     * @param departmentCode  当前用户部门编号
     */
    private void addProductRelation(DepartmentPojo departmentPojo,boolean isSuperManage,long departmentCode){

        List<DepartmentProductRelation> products = departmentPojo.getProducts();
        if(products == null || products.size() == 0){
            return;
        }

        for(DepartmentProductRelation product : products){
            ModelUtil.initBuild(product);
            product.setDepartmentCode(departmentCode);

            if(!isSuperManage){
                // 若不是创建省代,则需要关联查出上级代理商对各产品的售价(一省一价原则)
                Map<String,Object> queryMap = Maps.newHashMap();
                queryMap.put("departmentCode",departmentPojo.getParentCode());
                queryMap.put("productId",product.getProductId());
                DepartmentProductRelation productPrice = (DepartmentProductRelation) departmentProductRelationService.queryOne(queryMap);
                if(productPrice != null){
                    product.setSalePrice(productPrice.getSalePrice());
                }
            }
            departmentProductRelationService.insert(product);
        }
    }


    @ResponseBody
    @ApiDesc(owner = "杨国荣",value = "修改代理商")
    @RequestMapping(value = "updateDepartment", method = RequestMethod.POST)
    public Object updateDepartment(HttpServletRequest request){

        String departmentJson = request.getParameter("departmentJson");
        if(StringUtils.isBlank(departmentJson)){
            ModelUtil.throwException(ERRORCODE.PARAM_ISNULL);
        }

        DepartmentPojo departmentPojo = JsonMapper.buildNormalMapper().fromJson(
                departmentJson,
                DepartmentPojo.class);
        if(departmentPojo == null){
            ModelUtil.throwException(ERRORCODE.JSONCONVERT_ERROR);
        }

        // 修改代理商基本信息
        Department department = new Department();
        BeanUtils.copyProperties(departmentPojo,department);
        department.setAreaCode(null);
        department.setDepartmentCode(null);
        department.setCompanyCode(null);
        department.setParentCode(null);
        department.setRoleType(null);
        department.setLastModDate(System.currentTimeMillis());
        departmentService.update(department);

        Department tempDepart = (Department) departmentService.findOne(
                "id",
                department.getId());
        // 判断名称是否修改
        if(!tempDepart.getDepartmentName().equals(department.getDepartmentName())){
            iexPostService.updatePostNameByDeparntmentId(
                    Long.valueOf(department.getId().toString()),
                    department.getDepartmentName());
        }

        List<DepartmentProductRelation> products = departmentPojo.getProducts();
        if(products == null || products.size() == 0){
            return ObjectFactory.getSingle();
        }

        // 循环修改商品价格
        for(DepartmentProductRelation product : products){
            Map<String, Object> updateMap = Maps.newHashMap();
            updateMap.put("productId", product.getProductId());
            updateMap.put("departmentCode", tempDepart.getDepartmentCode());
            Map<String, Object> condition = Maps.newHashMap();
            // 拿货价
            condition.put("pickupPrice", product.getSalePrice());
            // 售价(只有超级管理员可以改省级代理商的售价)
            if(tempDepart.getRoleType() == UserRoleEnum.PROVICE_AGENT.getValue() &&
                    product.getSalePrice() != null){
                condition.put("salePrice",product.getSalePrice());
                // 级联修改省级代理商下边的分销商售价
                // TODO 修改逻辑后期需迁移至service中,需要添加事物
                iexDeparmentService.updateDepartmentPrice(
                        tempDepart.getDepartmentCode(),
                        product.getProductId(),
                        product.getSalePrice());
            }

            departmentProductRelationService.updateByCondition(updateMap, condition);
        }

        return ObjectFactory.getSingle();
    }

}

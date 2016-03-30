package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.PostApiService;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.*;
import cn.thinkjoy.zgk.zgksystem.common.TreePojo;
import cn.thinkjoy.zgk.zgksystem.pojo.PostPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.dataDictionary.IDataDictionaryService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.service.post.IEXPostDataauthorityService;
import cn.thinkjoy.zgk.zgksystem.service.post.IPostDataauthorityService;
import cn.thinkjoy.zgk.zgksystem.service.post.IPostService;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import cn.thinkjoy.zgk.zgksystem.service.role.IRolePostService;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import com.alibaba.dubbo.common.utils.StringUtils;
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
    private IPostService postService;//岗位Service

    @Autowired
    private IUserInfoService userInfoService;//人员Service

    @Autowired
    private IUserAccountService userAccountService;//账户Service

    @Autowired
    private IEXCodeService excodeService;

    @Autowired
    private IEXPostDataauthorityService iexPostDataauthorityService;

    @Autowired
    private IPostDataauthorityService iPostDataauthorityService;

    @Autowired
    private IRolePostService rolePostService;

    @Autowired
    private PostApiService postApiService;

    @Autowired
    private IDataDictionaryService dataDictionaryService;

    private static Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    /**
     * 新增和修改部门
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "addOrEditDepartment", method = RequestMethod.POST)
    public Object addOrEditDepartment(HttpServletRequest request){

        String departmentJson = request.getParameter("departmentJson");
        if(StringUtils.isBlank(departmentJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Department department =  JsonMapper.buildNormalMapper().fromJson(departmentJson, Department.class);
        if(department == null){
            throw  new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }

        UserPojo userPojo=(UserPojo)HttpUtil.getSession(request,"user");

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("departmentCode",department.getParentCode());
        dataMap.put("status", Constants.NORMAL_STATUS);//获取正常
        Department temp =(Department) departmentService.queryOne(dataMap);
        if(department.getId()==null || department.getId().equals(0) ){
            Department d=new Department();
            d.setCompanyCode(temp.getCompanyCode());
            d.setDepartmentName(department.getDepartmentName());
            d.setParentCode(department.getParentCode());
            d.setDescription(department.getDescription());
            d.setDepartmentFax(department.getDepartmentFax());
            d.setDepartmentPhone(department.getDepartmentPhone());
            d.setDepartmentPrincipal(department.getDepartmentPrincipal());
            d.setSeqSort(department.getSeqSort());
            d.setGoodsAddress(department.getGoodsAddress());
            d.setSalePrice(department.getSalePrice());
            d.setStatus(Constants.NORMAL_STATUS);
            String areaCode;
            if (userPojo.getRoleType().equals(1)){
                areaCode=department.getAreaCode().substring(0,2);
                dataDictionaryService.updateProvince(areaCode+"0000");
            } else if (userPojo.getRoleType().equals(2)){
                areaCode=department.getAreaCode().substring(0,4);
                dataDictionaryService.updateCity(areaCode+"00");
            } else if (userPojo.getRoleType().equals(3)){
                areaCode=department.getAreaCode().substring(0,6);
                dataDictionaryService.updateCounty(areaCode);
            } else {
                throw  new BizException(ERRORCODE.INSERT_ERROR.getCode(),ERRORCODE.INSERT_ERROR.getMessage());
            }
            d.setAreaCode(areaCode);
            d.setRoleType(String.valueOf(userPojo.getRoleType()+1));
            Long maxDepartmentCode=excodeService.selectMaxCodeByParent(CodeFactoryUtil.DEPARTMENT_CODE,CodeFactoryUtil.DEPARTMENT_TABLE,CodeFactoryUtil.COMPANY_CODE, temp.getCompanyCode());
            if(maxDepartmentCode==null||maxDepartmentCode==0){
                maxDepartmentCode= CodeFactoryUtil.getInitDepartment(temp.getCompanyCode());//部门Code初始生成规则 所属公司信息的Code*1000+1
            }else{
                ++maxDepartmentCode;
            }
            d.setDepartmentCode(maxDepartmentCode);

            departmentService.updateOrSave(d, null);
            addPost(d,userPojo.getAccountCode());
            return d;
        }else{
            departmentService.updateOrSave(department, department.getId());
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("id", department.getId());
            Department depart = (Department) departmentService.queryOne(queryMap);
            dataMap.put("departmentCode",String.valueOf(depart.getDepartmentCode()));
            List<Post> postList = postService.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC.getAction());
            for(Post p :postList){
                p.setPostName(department.getDepartmentName());
                postService.update(p);
            }
            return "ok";
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
        departmentService.update(d);
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
    public Page<Department> queryDepartment(HttpServletRequest request){
        String currentPageNo = HttpUtil.getParameter(request, "currentPageNo", "1");
        String pageSize = HttpUtil.getParameter(request, "pageSize", "10");
        String parentCode=request.getParameter("parentCode");
        return  departmentService.queryDepartment(currentPageNo,pageSize,parentCode);

    }
    /**
     * 获取单个部门
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "getDepartment",method = RequestMethod.GET)
    public Department getDepartment(HttpServletRequest request){
        String departmentId = request.getParameter("id");
        return departmentService.getDepartment(departmentId);
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
            Long maxPostCode = excodeService.selectMaxCodeByParent(CodeFactoryUtil.POSITION_CODE,CodeFactoryUtil.POSITION_TABLE,CodeFactoryUtil.DEPARTMENT_CODE,post.getDepartmentCode());
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

    private String distributionRole(Post post,String roleCode){
        RolePost rolePost=new RolePost();
        rolePost.setPostCode(post.getPostCode());
        Long roleCodeLong=0L;
        switch (roleCode){
            case "1":roleCodeLong=10L;break;
            case "2":roleCodeLong=11L;break;
            case "3":roleCodeLong=12L;break;
            case "4":roleCodeLong=13L;break;
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
        if (roleCode.equals("1")||roleCode.equals("2")||roleCode.equals("3")) {
            distributionSystemCode(rp.getPostCode(), 1L);
        }
        distributionSystemCode(rp.getPostCode(),2L);
        return "ok";
    }

    private String distributionSystemCode(long postCode,long systemCode){
        postApiService.postSystemAuthority(postCode,systemCode);
        return "ok";
    }

}

package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.domain.Post;
import cn.thinkjoy.zgk.zgksystem.domain.UserAccount;
import cn.thinkjoy.zgk.zgksystem.domain.UserInfo;
import cn.thinkjoy.zgk.zgksystem.common.TreePojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.service.post.IPostService;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
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

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("departmentCode",department.getParentCode());
        dataMap.put("status", Constants.NORMAL_STATUS);//获取正常
        Department temp =(Department) departmentService.queryOne(dataMap);
        if(department.getId()==null || department.getId()==0 ){
            Department d=new Department();
            d.setCompanyCode(temp.getCompanyCode());
            d.setDepartmentName(department.getDepartmentName());
            d.setParentCode(department.getParentCode());
            d.setDescription(department.getDescription());
            d.setDepartmentFax(department.getDepartmentFax());
            d.setDepartmentPhone(department.getDepartmentPhone());
            d.setDepartmentPrincipal(department.getDepartmentPrincipal());
            d.setSeqSort(department.getSeqSort());
            d.setStatus(Constants.NORMAL_STATUS);
            Long maxDepartmentCode=excodeService.selectMaxCodeByParent(CodeFactoryUtil.DEPARTMENT_CODE,CodeFactoryUtil.DEPARTMENT_TABLE,CodeFactoryUtil.COMPANY_CODE, temp.getCompanyCode());
            if(maxDepartmentCode==null||maxDepartmentCode==0){
                maxDepartmentCode= CodeFactoryUtil.getInitDepartment(temp.getCompanyCode());//部门Code初始生成规则 所属公司信息的Code*1000+1
            }else{
                ++maxDepartmentCode;
            }
            d.setDepartmentCode(maxDepartmentCode);

            departmentService.updateOrSave(d, null);
            return d;
        }else{
            departmentService.updateOrSave(department, department.getId());
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
        Department d = (Department)departmentService.findOne("id", departmentId);
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
        Long parentCode=Long.parseLong(HttpUtil.getParameter(request, "parentCode", "-1"));
        return departmentService.recursionTree(parentCode);
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

}

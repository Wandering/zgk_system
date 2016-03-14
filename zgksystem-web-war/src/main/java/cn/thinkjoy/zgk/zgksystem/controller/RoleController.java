package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.RoleMenu;
import cn.thinkjoy.zgk.zgksystem.domain.RolePost;
import cn.thinkjoy.zgk.zgksystem.domain.RoleResources;
import cn.thinkjoy.zgk.zgksystem.domain.Roles;
import cn.thinkjoy.zgk.zgksystem.pojo.RolesPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.menu.IEXMenuService;
import cn.thinkjoy.zgk.zgksystem.service.resource.IEXResourceService;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import cn.thinkjoy.zgk.zgksystem.service.role.*;
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
import java.util.ArrayList;
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
@RequestMapping("/system/role")
public class RoleController {
    private static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private IRolesService rolesService;
    @Autowired
    private IEXCodeService excodeService;
    @Autowired
    private IRoleResourcesService iRoleResourcesService;
    @Autowired
    private IRoleMenuService iRoleMenuService;
    @Autowired
    private IEXRoleService iexRoleService;
    @Autowired
    private IRolePostService rolePostService;
    @Autowired
    private IEXResourceService iexResourceService;
    @Autowired
    private IEXMenuService iexMenuService;
    /**
     * 新增和修改角色
     * @return String
     *
     */
    @ResponseBody
    @RequestMapping(value = "addOrEditRoles", method = RequestMethod.POST)
    public String addOrEditRoles(HttpServletRequest request){
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        String rolesPojoJson = request.getParameter("rolesPojoJson");
        if(StringUtils.isBlank(rolesPojoJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        RolesPojo rolesPojo=null;
        try{
            rolesPojo =  JsonMapper.buildNormalMapper().fromJson(rolesPojoJson, RolesPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        if(rolesPojo == null ){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        List<Long> resourceList = rolesPojo.getResourceList();

        if(resourceList == null ||resourceList.size() == 0){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        List<Long>  menuCodeList = rolesPojo.getMenuCodeList();
        if(menuCodeList == null || menuCodeList.size() == 0 ){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());

        }

        //创建新角色
        Roles roles = new Roles();
        roles.setId(rolesPojo.getRolesId());
        roles.setRoleCode(rolesPojo.getRoleCode());
        roles.setRoleName(rolesPojo.getRoleName());
        roles.setDescription(rolesPojo.getDescription());
        roles.setSeqSort(rolesPojo.getSeqSort());
        if(roles.getId() == null || roles.getId() == 0 ){

            roles.setCreateDate(System.currentTimeMillis());
            roles.setCreator(userPojo.getAccountCode());
            Long maxCode=excodeService.selectMaxCode("roleCode","k12system_roles");
            if(maxCode==null||maxCode==0){
                maxCode= CodeFactoryUtil.getInitOtherCode();//初始化角色Code
            }else{
                ++maxCode;
            }
            roles.setRoleCode(maxCode);

        }else{
            //修改产品
            roles.setLastModDate(System.currentTimeMillis());
            roles.setLastModifier(userPojo.getAccountCode());
            //删除角色菜单表
            iexRoleService.updateRoleMenu(roles.getRoleCode());
            //删除角色资源表
            iexRoleService.updateRoleResource(roles.getRoleCode());
        }
        //添加角色资源表
        for(Long resourceCode:resourceList){
            RoleResources roleResources = new RoleResources();
            roleResources.setResourceCode(resourceCode);
            roleResources.setRoleCode(roles.getRoleCode());
            roleResources.setStatus(Constants.NORMAL_STATUS);
            roleResources.setCreator(userPojo.getAccountCode());
            roleResources.setCreateDate(System.currentTimeMillis());
            iRoleResourcesService.updateOrSave(roleResources,null);
        }
        //添加角色菜单表
        for(Long menuCode:menuCodeList){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuCode(menuCode);
            roleMenu.setRoleCode(roles.getRoleCode());
            roleMenu.setCreateDate(System.currentTimeMillis());
            roleMenu.setStatus(Constants.NORMAL_STATUS);
            roleMenu.setCreator(userPojo.getAccountCode());
            iRoleMenuService.updateOrSave(roleMenu,null);
        }
        rolesService.updateOrSave(roles, roles.getId());
        return "ok";

    }

    /**
     * 通过角色名称检查是否已存在的角色信息
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value="checkRoles",method=RequestMethod.POST)
    public String checkRoles(HttpServletRequest request){
        String roleName=request.getParameter("roleName");
        if(StringUtils.isBlank(roleName)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        List<Roles> rolesList= rolesService.findList("roleName", roleName);
        if(rolesList!=null && rolesList.size()>0){
            throw  new BizException(ERRORCODE.ALREADY_EXIST_ERROR.getCode(),ERRORCODE.ALREADY_EXIST_ERROR.getMessage());
        }else{
            return  "ok";
        }
    }


    /**
     * 删除角色
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "delRoles",method = RequestMethod.GET)
    public String delRoles(HttpServletRequest request){
        String rolesId = request.getParameter("rolesId");
        if(StringUtils.isBlank(rolesId)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Roles roles = (Roles)rolesService.findOne("id", rolesId);

        roles.setStatus(Constants.DELETEED_STATUS);
        rolesService.update(roles);
        //删除角色菜单表
        iexRoleService.updateRoleMenu(roles.getRoleCode());
        //删除角色资源表
        iexRoleService.updateRoleResource(roles.getRoleCode());

        return "ok";
    }

    /**
     * 查询角色
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryRoles",method = RequestMethod.GET)
    public Page<Roles> queryRoles(HttpServletRequest request){
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize =Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
        Map<String,Object>  conditions = new HashMap<>();
        Map<String,Object> statusMap = new HashMap<>();
        statusMap.put("op","=");
        statusMap.put("data",Constants.NORMAL_STATUS);
        conditions.put("groupOp", " AND ");
        conditions.put("status", statusMap);
        int count=rolesService.count(conditions);
        if(count>0){
            List<Roles> rolesList = rolesService.queryPage(conditions, (currentPageNo - 1) * pageSize, pageSize, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC);
            Page<Roles> page=new Page<>();
            page.setCount(count);
            page.setList(rolesList);
            return page;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }

    }
    /**
     * 获取单个角色
     * @param request request
     * @return T
     */
    @ResponseBody
    @RequestMapping(value = "getRoles",method = RequestMethod.GET)
    public RolesPojo getRoles(HttpServletRequest request){
        String rolesId = request.getParameter("id");
        if(StringUtils.isBlank(rolesId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id", Long.parseLong(rolesId));
        dataMap.put("status", Constants.NORMAL_STATUS);
        Roles roles = (Roles)rolesService.queryOne(dataMap);
        if(roles == null){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        RolesPojo rolesPojo = new RolesPojo();
        rolesPojo.setRolesId(roles.getId());
        rolesPojo.setRoleCode(roles.getRoleCode());
        rolesPojo.setRoleName(roles.getRoleName());
        rolesPojo.setDescription(roles.getDescription());
        List<Long> menuCode = iexMenuService.getMenuCodeByRole(roles.getRoleCode());
        rolesPojo.setMenuCodeList(menuCode);
        List<Long> resourceCode = iexResourceService.resourceCodeListByRole(roles.getRoleCode());
        rolesPojo.setResourceList(resourceCode);
        return rolesPojo;
    }

    /**
     * 获取角色Code和角色名称
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "queryComboxRoles",method = RequestMethod.GET)
    public Map<String,String> queryComboxRoles(){
        Map<String,String> resultMap=new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("status", Constants.NORMAL_STATUS);
        List<Roles> rolesList = rolesService.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC.getAction());
        for(Roles r :rolesList){
            resultMap.put(r.getRoleCode() + "", r.getRoleName());
        }
        if(resultMap.size()==0 ) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return resultMap;
    }
    /**
     * 获取角色Code和角色名称树形结构
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "queryTreeRoles",method = RequestMethod.GET)
    public List<TreeBean> queryTreeRoles(){
        List<TreeBean> resultTree=new ArrayList<>() ;
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("status", Constants.NORMAL_STATUS);
        List<Roles> rolesList  = rolesService.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, "DESC");
        for(Roles roles :rolesList){
            TreeBean treeBean=new TreeBean();
            treeBean.setId(roles.getRoleCode());
            treeBean.setName(roles.getRoleName());
            resultTree.add(treeBean);
        }
        if(resultTree==null || resultTree.size()==0 ) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return resultTree;
    }
    /**
     * 给岗位分配角色
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "distributionRoles",method = RequestMethod.POST)
    public String  distributionRole(HttpServletRequest request){
        UserPojo userPojo = (UserPojo)HttpUtil.getSession(request,"user");
        String rolePostJson = request.getParameter("rolePostJson");
        if(StringUtils.isBlank(rolePostJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        RolePost rolePost=null;
        try{
            rolePost =  JsonMapper.buildNormalMapper().fromJson(rolePostJson, RolePost.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("postCode",rolePost.getPostCode());
        dataMap.put("status", Constants.NORMAL_STATUS);
        List<RolePost> rolePosts = rolePostService.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD,SqlOrderEnum.DESC.getAction());
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
        rolePostService.updateOrSave(rp,null);
        return "ok";
    }

    /**
     * 获取当前岗位的角色
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "getPostRoles",method = RequestMethod.GET)
    public Roles getPostRoles(HttpServletRequest request){
        String postCode=request.getParameter("postCode");
        if(StringUtils.isBlank(postCode)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("postCode",Long.valueOf(postCode));
        dataMap.put("status", Constants.NORMAL_STATUS);
        RolePost rolePosts  = (RolePost)rolePostService.queryOne(dataMap);
        if(rolePosts!=null){
            Map<String,Object> roleMap = new HashMap<>();
            roleMap.put("roleCode",rolePosts.getRoleCode());
            roleMap.put("status", Constants.NORMAL_STATUS);
            Roles roles= (Roles)rolesService.queryOne(roleMap);
            if(roles!=null) {
                return roles;
            }
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }

        return null;
    }

}

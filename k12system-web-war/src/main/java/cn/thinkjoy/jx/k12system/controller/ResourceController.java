package cn.thinkjoy.jx.k12system.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.jx.k12system.common.ERRORCODE;
import cn.thinkjoy.jx.k12system.common.HttpUtil;
import cn.thinkjoy.jx.k12system.common.Page;
import cn.thinkjoy.jx.k12system.domain.*;
import cn.thinkjoy.jx.k12system.pojo.ResourcesPojo;
import cn.thinkjoy.jx.k12system.pojo.UserPojo;
import cn.thinkjoy.jx.k12system.service.account.IUserInfoService;
import cn.thinkjoy.jx.k12system.service.resource.IResourcesService;
import cn.thinkjoy.jx.k12system.service.role.IRolePostService;
import cn.thinkjoy.jx.k12system.service.role.IRoleResourcesService;
import cn.thinkjoy.jx.k12system.service.role.IRolesService;
import cn.thinkjoy.jx.k12system.service.code.IEXCodeService;
import cn.thinkjoy.jx.k12system.util.Constants;
import cn.thinkjoy.jx.k12system.util.IdentityUtil;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.apache.commons.lang.StringUtils;
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
 * Created by yhwang on 15/9/16.
 */
@Controller
@RequestMapping("/system/resource")
public class ResourceController {
    @Autowired
    private IResourcesService iResourcesService;
    @Autowired
    private IRoleResourcesService iRoleResourcesService;
    @Autowired
    private IEXCodeService excodeService;//codeService

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IRolePostService rolePostService;

    @Autowired
    private IRolesService rolesService;


    /**
     * 根据菜单code获取资源分页列表
     * @param request
     * @return
     */
    @RequestMapping(value = "getResourcePage",method = RequestMethod.GET)
    @ResponseBody
    public Page<ResourcesPojo> getResourcePage(HttpServletRequest request){
        String menuCode = request.getParameter("menuCode");
        String currentPageNo = HttpUtil.getParameter(request, "currentPageNo", "1");
        String pageSize = HttpUtil.getParameter(request, "pageSize", "10");
        if(StringUtils.isBlank(menuCode)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object>  conditions = new HashMap<>();
        Map<String,Object> statusMap = new HashMap<>();
        statusMap.put("op","=");
        statusMap.put("data", Constants.NORMAL_STATUS);
        conditions.put("status",statusMap);
        Map<String,Object> menuCodeMap = new HashMap<>();
        menuCodeMap.put("op","=");
        menuCodeMap.put("data",Long.valueOf(menuCode));
        conditions.put("menuCode",menuCodeMap);
        conditions.put("groupOp","and");
        List<Resources> resourcesList= iResourcesService.queryPage(conditions, (Integer.parseInt(currentPageNo) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        int count  = iResourcesService.count(conditions);
        List<ResourcesPojo> resourcesPojos = new ArrayList<>();
        for(Resources resources1:resourcesList){
            ResourcesPojo resourcesPojo = new ResourcesPojo();
            resourcesPojo.setMenuCode(resources1.getMenuCode());
            resourcesPojo.setResourceCode(resources1.getResourceCode());
            resourcesPojo.setResourceIcon(resources1.getResourceIcon());
            resourcesPojo.setResourceId(resources1.getId());
            resourcesPojo.setResourceName(resources1.getResourceName());
            resourcesPojo.setResourceUrl(resources1.getResourceUrl());
            resourcesPojo.setSeqSort(resources1.getSeqSort());
            resourcesPojo.setDescription(resources1.getDescription());
            resourcesPojos.add(resourcesPojo);
        }
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("currentPageNo", currentPageNo);
        queryMap.put("menuCode", menuCode);
        queryMap.put("pageSize", pageSize);
        Page<ResourcesPojo> resourcesPojoPage = new Page<>();
        resourcesPojoPage.setList(resourcesPojos);
        resourcesPojoPage.setCount(count);
        resourcesPojoPage.setQueryMap(queryMap);
        return resourcesPojoPage;
    }

    /**
     * 增加或修改资源
     * @param request
     * @return
     */
    @RequestMapping(value = "addOrEditResource",method = RequestMethod.POST)
    @ResponseBody
    public String addOrEditResource(HttpServletRequest request){
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request,"user");
        String resourceJson = request.getParameter("resourceJson");
        if(StringUtils.isBlank(resourceJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        ResourcesPojo resourcesPojo = null;
        try {
            resourcesPojo = JsonMapper.buildNormalMapper().fromJson(resourceJson,ResourcesPojo.class);
        }catch (Exception e){
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        if(resourcesPojo == null){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Resources resources = new Resources();
        resources.setMenuCode(resourcesPojo.getMenuCode());
        resources.setResourceIcon(resourcesPojo.getResourceIcon());
        resources.setResourceName(resourcesPojo.getResourceName());
        resources.setResourceUrl(resourcesPojo.getResourceUrl());
        resources.setDescription(resourcesPojo.getDescription());
        resources.setId(resourcesPojo.getResourceId());
        if(resourcesPojo.getResourceId() == null ){//新增
            Long maxResourceCode = excodeService.selectMaxCode("resourceCode","k12system_resources");
            if(maxResourceCode == null || maxResourceCode == 0){
                maxResourceCode = 1l;
            }else {
                ++maxResourceCode;
            }
            resources.setResourceCode(maxResourceCode);
            resources.setCreateDate(System.currentTimeMillis());
            resources.setCreator(userPojo.getAccountCode());
        }else {
            resources.setResourceCode(resourcesPojo.getResourceCode());
            resources.setLastModDate(System.currentTimeMillis());
            resources.setLastModifier(userPojo.getAccountCode());
        }
        iResourcesService.updateOrSave(resources, resources.getId());
        return "ok";
    }

    /**
     * 查询资源详情
     * @param request
     * @return
     */
    @RequestMapping(value = "getResourcesDetail",method = RequestMethod.GET)
    @ResponseBody
    public ResourcesPojo getResourcesDetail(HttpServletRequest request){
        String resourceId = request.getParameter("resourceId");
        if(StringUtils.isBlank(resourceId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Resources resources = (Resources)iResourcesService.findOne("id",resourceId);
        if(resources == null ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        ResourcesPojo resourcesPojo = new ResourcesPojo();
        resourcesPojo.setResourceId(resources.getId());
        resourcesPojo.setResourceName(resources.getResourceName());
        resourcesPojo.setResourceIcon(resources.getResourceIcon());
        resourcesPojo.setResourceCode(resources.getMenuCode());
        resourcesPojo.setMenuCode(resources.getMenuCode());
        resourcesPojo.setResourceUrl(resources.getResourceUrl());
        resourcesPojo.setSeqSort(resources.getSeqSort());
        return resourcesPojo;
    }

    /**
     * 删除资源
     * @param request
     * @return
     */
    @RequestMapping(value = "delResources",method = RequestMethod.POST)
    @ResponseBody
    public String delResources(HttpServletRequest request){
        String resourceId = request.getParameter("resourceId");
        if(StringUtils.isBlank(resourceId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Resources resources = (Resources)iResourcesService.findOne("id",resourceId);
        if(resources == null ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        Map<String,Object> conditions = new HashMap<>();
        conditions.put("status", Constants.NORMAL_STATUS);
        conditions.put("resourceCode", resources.getResourceCode());

        List<RoleResources> roleResources = iRoleResourcesService.queryList(conditions,null,null);
        if(roleResources != null){
            //删除资源与角色表
            for(RoleResources resources1:roleResources){
                resources1.setStatus(Constants.DELETEED_STATUS);
                iRoleResourcesService.update(resources1);
            }
        }
        //删除资源
        resources.setStatus(Constants.DELETEED_STATUS);
        iResourcesService.update(resources);
        return "ok";
    }



    /**
     * 获取当前登录人拥有的菜单资源
     * @param request
     * @return
     */
    @RequestMapping(value = "getAccountResources",method = RequestMethod.GET)
    @ResponseBody
    public List<Resources> getAccountResources(HttpServletRequest request) {
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        String menuCode = request.getParameter("menuCode");
        if (StringUtils.isBlank(menuCode)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        List<Resources> resultList = new ArrayList<>();
        if (userPojo == null) {
            throw new BizException(ERRORCODE.USER_EXPRIED_RELOGIN.getCode(), ERRORCODE.USER_EXPRIED_RELOGIN.getMessage());
        } else if (IdentityUtil.ADMIN_MANAGER_POST == userPojo.getPostCode() || IdentityUtil.SYSTEM_MANAGER_POST == userPojo.getPostCode()) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("status", Constants.NORMAL_STATUS);
            dataMap.put("menuCode", Long.valueOf(menuCode));
            resultList = iResourcesService.queryList(dataMap, null, null);
            return resultList;
        } else {
            Map<String, Object> rolePostMap = new HashMap<>();
            rolePostMap.put("status", Constants.NORMAL_STATUS);
            rolePostMap.put("postCode", userPojo.getPostCode());
            RolePost rolePost = (RolePost) rolePostService.queryOne(rolePostMap);
            if (rolePost != null) {
                Map<String, Object> roleMap = new HashMap<>();
                roleMap.put("status", Constants.NORMAL_STATUS);
                roleMap.put("roleCode", rolePost.getRoleCode());
                Roles roles = (Roles) rolesService.queryOne(roleMap);
                if (roles != null) {
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("status", Constants.NORMAL_STATUS);
                    dataMap.put("menuCode", Long.valueOf(menuCode));
                    List<Resources> resourcesList = iResourcesService.queryList(dataMap, null, null);
                    for (Resources r : resourcesList) {
                        Map<String, Object> roleResourcesMap = new HashMap<>();
                        roleResourcesMap.put("status", Constants.NORMAL_STATUS);
                        roleResourcesMap.put("roleCode", roles.getRoleCode());
                        roleResourcesMap.put("resourceCode", r.getResourceCode());
                        List<RoleResources> roleResources = iRoleResourcesService.queryList(roleResourcesMap, null, null);
                        if (roleResources != null) {
                            for (RoleResources rr : roleResources) {
                                Map<String, Object> reMap = new HashMap<>();
                                reMap.put("status", Constants.NORMAL_STATUS);
                                reMap.put("resourceCode", rr.getResourceCode());
                                Resources resources = (Resources) iResourcesService.queryOne(reMap);
                                resultList.add(resources);
                            }
                        }
                    }

                }
            }
            return resultList;
        }
    }
}

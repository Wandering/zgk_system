package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.Menu;
import cn.thinkjoy.zgk.zgksystem.pojo.ClientInfoPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.MenuPojo;
import cn.thinkjoy.zgk.zgksystem.common.TreePojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.menu.IEXMenuService;
import cn.thinkjoy.zgk.zgksystem.service.menu.IMenuService;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.apache.commons.lang.StringUtils;
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
 * Created by yhwang on 15/9/7.
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IEXCodeService excodeService;//codeService
    @Autowired
    private IEXMenuService iexMenuService;
    /**
     * 根据岗位拉取菜单树
     * @param request
     * @return
     */
    @RequestMapping(value = "getMenuTree",method = RequestMethod.GET)
    @ResponseBody
    public TreePojo getMenuTree(HttpServletRequest request){
        String systemCode = request.getParameter("systemCode");
        if(StringUtils.isBlank(systemCode)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        String isFlag = request.getParameter("hasResource");
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        ClientInfoPojo clientInfoPojo = new ClientInfoPojo();
        clientInfoPojo.setSystemCode(Long.valueOf(systemCode));
        userPojo.setClientInfoPojo(clientInfoPojo);
        TreePojo treePojo = new TreePojo();
        boolean hasResource = false;
        if(StringUtils.isNotBlank(isFlag)){
            hasResource = true;
        }
        List<TreeBean> treeBeanList = iexMenuService.menuList(userPojo.getPostCode(), hasResource);
        treePojo.setTreeBeanList(treeBeanList);
        treePojo.setTreeName("菜单树");
        treePojo.setDesciption("根据角色查询菜单树");
        return treePojo;
    }
    /**
     * 增加或修改菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "addOrEditMenu",method = RequestMethod.POST)
    @ResponseBody
    public MenuPojo addOrEditMenu(HttpServletRequest request){
        UserPojo userPojo = (UserPojo)HttpUtil.getSession(request,"user");
        String menuJson = request.getParameter("menuJson");
        if(StringUtils.isBlank(menuJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        MenuPojo menuPojo = null;
        try {
            menuPojo = JsonMapper.buildNormalMapper().buildNormalMapper().fromJson(menuJson,MenuPojo.class);
        }catch (Exception e){
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        if(menuPojo == null){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Menu menu = new Menu();
        menu.setId(menuPojo.getMenuId());
        menu.setMenuName(menuPojo.getMenuName());
        menu.setMenuIcon(menuPojo.getMenuIcon());
        menu.setMenuUrl(menuPojo.getMenuUrl());
        menu.setParentCode(menuPojo.getParentCode());
        menu.setSeqSort(menuPojo.getSeqSort());
        menu.setDescription(menuPojo.getDescription());
        if(menu.getId() == null){//创建
            Long maxCompanyCode = excodeService.selectMaxCode("menuCode", "k12system_menu");
            menu.setMenuCode(maxCompanyCode+1);
            menu.setCreateDate(System.currentTimeMillis());
            menu.setCreator(userPojo.getAccountCode());
        }else {//修改
            menu.setLastModDate(System.currentTimeMillis());
            menu.setLastModifier(userPojo.getAccountCode());
        }
        menuService.updateOrSave(menu, menu.getId());
        MenuPojo menuPojoReturn = new MenuPojo();
        menuPojoReturn.setMenuCode(menu.getMenuCode());
        menuPojoReturn.setMenuName(menu.getMenuName());
        return menuPojoReturn;
    }

    /**
     * 分页查询菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "queryPage",method = RequestMethod.GET)
    @ResponseBody
    public Page<Menu> queryPage(HttpServletRequest request){
        String menuCode = request.getParameter("menuCode");
        String currentPageNo = HttpUtil.getParameter(request, "currentPageNo", "1");
        String pageSize = HttpUtil.getParameter(request, "pageSize", "10");
        if(StringUtils.isBlank(menuCode)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        Map<String,Object>  conditions = new HashMap<>();
        Map<String,Object> statusMap = new HashMap<>();
        statusMap.put("op","=");
        statusMap.put("data", Constants.NORMAL_STATUS);
        conditions.put("status",statusMap);
        Map<String,Object> parenCodeMap = new HashMap<>();
        parenCodeMap.put("op","=");
        parenCodeMap.put("data",Long.valueOf(menuCode));
        conditions.put("parentCode",parenCodeMap);
        conditions.put("groupOp","and");
        List<Menu> menuList = menuService.queryPage(conditions, (Integer.parseInt(currentPageNo) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        if(menuList == null || menuList.size() == 0){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        int count = menuService.count(conditions);
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("currentPageNo", currentPageNo);
        queryMap.put("menuCode",menuCode);
        queryMap.put("pageSize",pageSize);
        Page<Menu> menuPage = new Page<>();
        menuPage.setList(menuList);
        menuPage.setCount(count);
        menuPage.setQueryMap(queryMap);
        return menuPage;
    }

    /**
     * 根据menuID 查询菜单详情
     * @param request
     * @return
     */
    @RequestMapping(value = "getMenuDetail",method = RequestMethod.GET)
    @ResponseBody
    public MenuPojo getMenuDetail(HttpServletRequest request){
        String menuId = request.getParameter("menuId");
        if(StringUtils.isBlank(menuId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Menu menu = (Menu)menuService.findOne("id",Long.valueOf(menuId));
        if(menu == null){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        MenuPojo menuPojo = new MenuPojo();
        menuPojo.setMenuName(menu.getMenuName());
        menuPojo.setMenuUrl(menu.getMenuUrl());
        menuPojo.setParentCode(menu.getParentCode());
        menuPojo.setMenuIcon(menu.getMenuIcon());
        menuPojo.setMenuCode(menu.getMenuCode());
        menuPojo.setDescription(menu.getDescription());
        menuPojo.setMenuId(menu.getId());
        menuPojo.setSeqSort(menu.getSeqSort());
        return menuPojo;
    }
    /**
     * 删除菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "delMenu",method = RequestMethod.POST)
    @ResponseBody
    public String delMenu(HttpServletRequest request){
        String menuId = HttpUtil.getParameter(request,"menuId","0");
        if(StringUtils.isBlank(menuId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        Menu menu = (Menu)menuService.findOne("id",Long.valueOf(menuId));
        if(menu == null ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        menu.setStatus(Constants.DELETEED_STATUS);
        menuService.update(menu);

        //删除菜单之后删除角色菜单表
        iexMenuService.updateMenuRole(menu.getMenuCode());
        return "ok";
    }

}

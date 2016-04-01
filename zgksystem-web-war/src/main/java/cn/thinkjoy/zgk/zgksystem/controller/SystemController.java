package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.domain.K12system;
import cn.thinkjoy.zgk.zgksystem.domain.K12systemPost;
import cn.thinkjoy.zgk.zgksystem.domain.UserAccount;
import cn.thinkjoy.zgk.zgksystem.domain.UserInfo;
import cn.thinkjoy.zgk.zgksystem.pojo.K12SystemPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import cn.thinkjoy.zgk.zgksystem.service.system.IEXK12SystemService;
import cn.thinkjoy.zgk.zgksystem.service.system.IK12systemPostService;
import cn.thinkjoy.zgk.zgksystem.service.system.IK12systemService;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import cn.thinkjoy.zgk.zgksystem.util.IdentityUtil;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.apache.commons.lang.StringUtils;
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
 * Created by yhwang on 15/9/1.
 */
@Controller
@RequestMapping("/system/system")
public class SystemController {
    @Autowired
    private IK12systemService ik12systemService;
    @Autowired
    private IEXK12SystemService iexk12SystemService;
    @Autowired
    private IEXCodeService excodeService;//codeService
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IK12systemPostService ik12systemPostService;
//
//    @Resource
//    private AgentService agentService;


    private static Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    /**
     * 根据岗位查询系统列表
     * @param request
     * @return
     */
    @RequestMapping(value = "getSystemList",method = RequestMethod.GET)
    @ResponseBody
    public List<K12SystemPojo> getSystemList(HttpServletRequest request){
        UserPojo userPojo = (UserPojo) HttpUtil.getSession(request, "user");
        List<K12SystemPojo> k12SystemPojos = iexk12SystemService.getSystemListByPost(userPojo.getPostCode());
        if(k12SystemPojos == null){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return k12SystemPojos;
    }

    /**
     * 查询全部系统
     * @param request
     * @return
     */
    @RequestMapping(value = "getAllSystemList",method = RequestMethod.GET)
    @ResponseBody
    public List<K12SystemPojo> getAllSystemList(HttpServletRequest request){
        List<K12system> k12systemList = ik12systemService.findList("status", Constants.NORMAL_STATUS);
        if(k12systemList == null || k12systemList.size() == 0){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        List<K12SystemPojo> k12SystemPojos = new ArrayList<>();
        for(K12system k12system:k12systemList){
            K12SystemPojo k12SystemPojo = new K12SystemPojo();
            k12SystemPojo.setSystemId(k12system.getId());
            k12SystemPojo.setSystemCode(k12system.getSystemCode());
            k12SystemPojo.setSystemLogo(k12system.getSystemLogo());
            k12SystemPojo.setDescription(k12system.getDescription());
            k12SystemPojo.setSystemName(k12system.getSystemName());
            k12SystemPojo.setSystemUrl(k12system.getSystemUrl());
            k12SystemPojos.add(k12SystemPojo);
        }
     return k12SystemPojos;
    }
    /**
     * 分页查询业务系统
     * @param request
     * @return
     */
    @RequestMapping(value = "querySystemPage",method = RequestMethod.GET)
    @ResponseBody
    public Page<K12SystemPojo> querySystemPage(HttpServletRequest request){

        String currentPageNo = HttpUtil.getParameter(request, "currentPageNo", "1");

        String pageSize = HttpUtil.getParameter(request, "pageSize", "10");

        Map<String,Object>  conditions = new HashMap<>();
        Map<String,Object> statusMap = new HashMap<>();
        statusMap.put("op","=");
        statusMap.put("data",Constants.NORMAL_STATUS);
        conditions.put("status",statusMap);
        conditions.put("groupOp","and");
        List<K12system>  k12systemList = ik12systemService.queryPage(conditions,(Integer.parseInt(currentPageNo)-1)*Integer.parseInt(pageSize),Integer.parseInt(pageSize));

        if(k12systemList == null ||k12systemList.size() == 0){

            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());

        }
        int count = ik12systemService.count(conditions);

        Page<K12SystemPojo> k12systemPage = new Page<>();

        List<K12SystemPojo> k12SystemPojos = new ArrayList<>();
        for(K12system k12system:k12systemList){
            K12SystemPojo k12SystemPojo = new K12SystemPojo();
            k12SystemPojo.setSystemCode(k12system.getSystemCode());
            k12SystemPojo.setSystemId(k12system.getId());
            k12SystemPojo.setSystemName(k12system.getSystemName());
            k12SystemPojo.setSystemUrl(k12system.getSystemUrl());
            k12SystemPojo.setSystemLogo(k12system.getSystemLogo());
            k12SystemPojo.setDescription(k12system.getDescription());
            Map<String,Object> userInfoMap = new HashMap<>();
            userInfoMap.put("userCode", k12system.getSystemCode() * 100 + 1);
            userInfoMap.put("status", Constants.NORMAL_STATUS);
            UserInfo userInfo = (UserInfo)userInfoService.queryOne(userInfoMap);
            Map<String,Object> userAccountMap = new HashMap<>();
            userAccountMap.put("accountCode",k12system.getSystemCode()*100+1);
            userAccountMap.put("status", Constants.NORMAL_STATUS);
            UserAccount userAccount = (UserAccount)userAccountService.queryOne(userAccountMap);
            if(userInfo == null || userAccount == null){
                k12SystemPojo.setUserName("该用户已被删除");

            }else {
                k12SystemPojo.setUserName(userInfo.getUserName());
                k12SystemPojo.setLoginNumber(userAccount.getLoginNumber());
            }

            k12SystemPojos.add(k12SystemPojo);
        }
        k12systemPage.setList(k12SystemPojos);

        k12systemPage.setCount(count);

        Map<String,Object> queryMap = new HashMap<>();

        queryMap.put("currentPageNo", currentPageNo);

        k12systemPage.setQueryMap(queryMap);

        return k12systemPage;
    }
    /**
     * 保存或修改系统
     * @param request
     * @return
     */
    @RequestMapping(value = "addOrEditSytem",method = RequestMethod.POST)
    @ResponseBody
    public String addOrEditSytem(HttpServletRequest request ){
        UserPojo userPojo = (UserPojo)HttpUtil.getSession(request,"user");
        String systemJson = request.getParameter("systemJson");
        if(StringUtils.isBlank(systemJson)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        K12SystemPojo k12SystemPojo =null;
        try {
            k12SystemPojo = JsonMapper.buildNormalMapper().fromJson(systemJson, K12SystemPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }

        if(k12SystemPojo == null){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        //创建系统
        K12system k12system = new K12system();
        k12system.setId(k12SystemPojo.getSystemId());
        k12system.setSystemLogo(k12SystemPojo.getSystemLogo());
        k12system.setSystemName(k12SystemPojo.getSystemName());
        k12system.setSystemUrl(k12SystemPojo.getSystemUrl());
        k12system.setDescription(k12SystemPojo.getDescription());
        if(k12SystemPojo.getSystemId() == null){
            k12system.setCreateDate(System.currentTimeMillis());
            k12system.setCreator(userPojo.getAccountCode());
            Long maxCompanyCode = excodeService.selectMaxCode("systemCode", "k12system_k12system");
            if(maxCompanyCode == null || maxCompanyCode == 0){
                k12system.setSystemCode(1l);
            }else {
                k12system.setSystemCode(maxCompanyCode+1);
            }
            //创建系统管理员
            UserAccount userAccount = new UserAccount();
            userAccount.setUserType(Constants.USER_TYPE_NORMAL);
            userAccount.setAccountCode(k12system.getSystemCode() * 100 + 1);
            userAccount.setCreateDate(System.currentTimeMillis());
            userAccount.setCreator(userPojo.getAccountCode());
            userAccount.setDescription("系统管理员");
            userAccount.setLoginNumber(k12SystemPojo.getLoginNumber());
            userAccount.setPassword(k12SystemPojo.getPassword());
            userAccount.setStatus(Constants.NORMAL_STATUS);
            userAccount.setUserCode(k12system.getSystemCode() * 100 + 1);
            userAccountService.updateOrSave(userAccount, null);
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(k12SystemPojo.getEmail());
            userInfo.setPhone(k12SystemPojo.getPhone());
            userInfo.setCreateDate(System.currentTimeMillis());
            userInfo.setCreator(userPojo.getAccountCode());
            userInfo.setStatus(Constants.NORMAL_STATUS);
            userInfo.setUserName(k12SystemPojo.getUserName());
            userInfo.setUserCode(k12system.getSystemCode() * 100 + 1);
            userInfo.setDepartmentCode(Long.valueOf(IdentityUtil.SYSTEM_MANAGER_DEPARTMENT));
            userInfo.setPostCode(Long.valueOf(IdentityUtil.SYSTEM_MANAGER_POST));
            userInfoService.updateOrSave(userInfo,null);
            //给系统管理员赋予该系统的权限
            K12systemPost k12systemPost = new K12systemPost();
            k12systemPost.setSystemCode(k12system.getSystemCode());
            k12systemPost.setPostCode(Long.valueOf(IdentityUtil.SYSTEM_MANAGER_POST));
            k12systemPost.setCreator(userPojo.getAccountCode());
            k12systemPost.setCreateDate(System.currentTimeMillis());
            k12systemPost.setStatus(Constants.NORMAL_STATUS);
            ik12systemPostService.updateOrSave(k12systemPost,null);
        }else {
            k12system.setSystemCode(k12SystemPojo.getSystemCode());
            k12system.setLastModDate(System.currentTimeMillis());
            k12system.setLastModifier(userPojo.getAccountCode());
        }
        ik12systemService.updateOrSave(k12system, k12system.getId());
        return "ok";
    }

    /**
     * 删除系统
     * @param request
     * @return
     */
    @RequestMapping(value = "delSystem" ,method = RequestMethod.POST)
    @ResponseBody
    public String delSystem(HttpServletRequest request){
        String systemId = request.getParameter("systemId");
        if(StringUtils.isBlank(systemId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        K12system k12system = (K12system)ik12systemService.findOne("id",Long.parseLong(systemId));
        if(k12system == null){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        //标记删除系统
        k12system.setStatus(Constants.DELETEED_STATUS);
        ik12systemService.update(k12system);
        //标记删除系统管理员
        Map<String,Object> userAccountMap = new HashMap<>();
        userAccountMap.put("accountCode",k12system.getSystemCode()*100+1);
        userAccountMap.put("status", Constants.NORMAL_STATUS);
        UserAccount userAccount = (UserAccount)userAccountService.queryOne(userAccountMap);
        if(userAccount == null){
            throw new BizException(ERRORCODE.DELETE_ERROR.getCode(),ERRORCODE.DELETE_ERROR.getMessage());
        }
        Map<String,Object> userInfoMap = new HashMap<>();
        userInfoMap.put("userCode",k12system.getSystemCode()*100+1);
        userInfoMap.put("status", Constants.NORMAL_STATUS);

        UserInfo userInfo = (UserInfo)userInfoService.queryOne(userInfoMap);
        if(userInfo == null){
            throw new BizException(ERRORCODE.DELETE_ERROR.getCode(),ERRORCODE.DELETE_ERROR.getMessage());
        }
        userInfo.setStatus(Constants.DELETEED_STATUS);
        userAccount.setStatus(Constants.DELETEED_STATUS);
        userAccountService.update(userAccount);
        userInfoService.update(userInfo);
        //标记删除系统与岗位关系表
        Map<String,Object> systemPostMap = new HashMap<>();
        systemPostMap.put("status",Constants.NORMAL_STATUS);
        systemPostMap.put("systemCode",k12system.getSystemCode());
        List<K12systemPost> k12systemPosts = ik12systemPostService.queryList(systemPostMap,null,null);
        for(K12systemPost k12systemPost:k12systemPosts){
            k12systemPost.setStatus(Constants.DELETEED_STATUS);
            ik12systemPostService.update(k12systemPost);
        }
        return "ok";
    }

    /**
     *拉取系统详情
     * @param request
     * @return
     */
    @RequestMapping(value = "getK12System",method = RequestMethod.GET)
    @ResponseBody
    public K12SystemPojo getK12System(HttpServletRequest request){
        String systemId = request.getParameter("systemId");
        if(StringUtils.isBlank(systemId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        K12system k12system = (K12system)ik12systemService.findOne("id",Long.parseLong(systemId));
        if(k12system == null ){
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        K12SystemPojo k12SystemPojo = new K12SystemPojo();
        k12SystemPojo.setSystemId(k12system.getId());
        k12SystemPojo.setSystemName(k12system.getSystemName());
        k12SystemPojo.setSystemUrl(k12system.getSystemUrl());
        k12SystemPojo.setSystemLogo(k12system.getSystemLogo());
        k12SystemPojo.setSystemCode(k12system.getSystemCode());
        k12SystemPojo.setDescription(k12system.getDescription());

        return k12SystemPojo;
    }
//    @RequestMapping(value = "agentTest",method = RequestMethod.GET)
//    @ResponseBody
//    public boolean test() {
//        SplitPricePojo splitPricePojo = new SplitPricePojo();
//        splitPricePojo.setAccountId(134);
//        splitPricePojo.setAccountPhone("13679136748");
//        splitPricePojo.setAgentLevel(0);
//
//        SplitPricePojo splitPricePojo1 = new SplitPricePojo();
//        splitPricePojo1.setAccountId(123);
//        splitPricePojo1.setAccountPhone("13679136748");
//        splitPricePojo1.setAgentLevel(1);
//
//        SplitPricePojo splitPricePojo2 = new SplitPricePojo();
//        splitPricePojo2.setAccountId(13);
//        splitPricePojo2.setAccountPhone("13679136748");
//        splitPricePojo2.setAgentLevel(2);
//        List<SplitPricePojo> splitPricePojoList = new ArrayList<>();
//
//        splitPricePojoList.add(splitPricePojo);
//        splitPricePojoList.add(splitPricePojo1);
//        splitPricePojoList.add(splitPricePojo2);
//
//        Integer payPrice = 20000;
//        String orderNo = "134019310358103571083571035";
//        boolean result = agentService.SplitPriceExec(splitPricePojoList, payPrice, orderNo);
//        return result;
//    }
}

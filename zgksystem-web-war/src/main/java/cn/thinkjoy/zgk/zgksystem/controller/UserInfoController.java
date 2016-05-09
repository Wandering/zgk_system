package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.ObjectFactory;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.domain.UserAccount;
import cn.thinkjoy.zgk.zgksystem.pojo.UserAndDepartmentPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IEXUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.util.*;
import cn.thinkjoy.zgk.zgksystem.domain.UserInfo;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/system/userInfo")
public class UserInfoController {


    private static Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IEXUserAccountService exUserAccountService;

    @Autowired
    private IEXCodeService excodeService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private CacheService cacheService;

    private static  final int COOKIE_EXPIRE_TIME = 7 * 24 * 60 * 60;
    /**
     * 登录账户验证
     */
    @ResponseBody
    @RequestMapping(value = "checkLoginNumberIsExist",method = RequestMethod.POST)
    public String checkLoginNumberIsExist(HttpServletRequest request){
        String loginNumber = request.getParameter("loginNumber");
        Map<String,Object> condition=new HashMap<>();
        condition.put("loginNumber",loginNumber);
        condition.put("status", 0);
        UserAccount userAccount = (UserAccount)userAccountService.queryOne(condition);
        if (userAccount!=null){
            return "0";
        }
        return "1";
    }


    /**
     * 新增和修改人员信息
     * @return String
     *
     */
    @ResponseBody
    @RequestMapping(value = "addOrEditUserInfo", method = RequestMethod.POST)
    public String addOrEditUserInfo(HttpServletRequest request){
        String userPojoJson = request.getParameter("userPojoJson");
        if(StringUtils.isBlank(userPojoJson)){
            ModelUtil.throwException(ERRORCODE.PARAM_ISNULL);
        }
        UserPojo userPojo =  null;
        try {
            userPojo=JsonMapper.buildNormalMapper().fromJson(userPojoJson, UserPojo.class);
        }catch (Exception e){
            LOGGER.error("convert json to userPojo error :",e);
            ModelUtil.throwException(ERRORCODE.JSONCONVERT_ERROR);
        }
        UserInfo userInfo =  null;
        try {
            userInfo=JsonMapper.buildNormalMapper().fromJson(userPojoJson, UserInfo.class);
        }catch (Exception e){
            LOGGER.error("convert json to userInfo error :",e);
            ModelUtil.throwException(ERRORCODE.JSONCONVERT_ERROR);
        }
        //修改用户信息
        if (userInfo!=null&&userInfo.getId()!=null){
            UserInfo u=new UserInfo();
            u.setUserName(userInfo.getUserName());
            u.setDescription(userInfo.getDescription());
            u.setEmail(userInfo.getEmail());
            u.setPhone(userInfo.getPhone());
            u.setId(userInfo.getId());
            userInfoService.updateOrSave(u, u.getId());
            return "ok";
        }
        Map<String,Object> map=new HashMap<>();
        map.put("loginNumber",userPojo.getLoginNumber());
        map.put("status",0);
        UserAccount userAccount = (UserAccount)userAccountService.queryOne(map);
        if (userAccount!=null){
            ModelUtil.throwException(ERRORCODE.ACCOUNT_ISEXIST);
        }
        if(userPojo == null){
            ModelUtil.throwException(ERRORCODE.JSONCONVERT_ERROR);
        }

        //添加用户账户及信息
        if(userPojo.getUserInfoId()==null || userPojo.getUserInfoId()==0){
            Long maxUserInfoCode=excodeService.selectMaxCodeByScope(CodeFactoryUtil.USERINFO_CODE, CodeFactoryUtil.USERINFO_TABLE,CodeFactoryUtil.getMinUserInfoCode(userPojo.getDepartmentCode()),CodeFactoryUtil.getMaxUserInfoCode(userPojo.getDepartmentCode()));
            if(maxUserInfoCode==null||maxUserInfoCode==0){
                maxUserInfoCode= CodeFactoryUtil.getInitUserInfo(userPojo.getDepartmentCode());
            }else{
                ++maxUserInfoCode;
            }
            UserInfo u=new UserInfo();
            u.setUserName(userPojo.getUserName());
            u.setDepartmentCode(userPojo.getDepartmentCode());
            u.setPostCode(userPojo.getPostCode());
            u.setDescription(userPojo.getDescription());
            u.setEmail(userPojo.getEmail());
            u.setPhone(userPojo.getPhone());
            u.setUserCode(maxUserInfoCode);
            u.setId(userPojo.getUserInfoId());
            Department department = (Department) departmentService.findOne("departmentCode",userPojo.getDepartmentCode());
            if(null != department)
            {
                u.setAreaCode(department.getAreaCode());
                u.setRoleType(department.getRoleType());
                u.setSalePrice(String.valueOf(department.getSalePrice()));
                u.setGoodsAddress(department.getGoodsAddress());
            }
            userInfoService.updateOrSave(u, null);

            UserAccount account =new UserAccount();
            account.setLoginNumber(userPojo.getLoginNumber());//账户名
            account.setUserCode(maxUserInfoCode);
            account.setPassword(userPojo.getPassword());
            account.setUserType(Constants.USER_TYPE_NORMAL);
            account.setIdentityCode((long)IdentityUtil.NOMAL_MANAGER_IDENTITY);
            Long maxAccountCode=excodeService.selectMaxCodeByScope(CodeFactoryUtil.ACCOUNT_CODE, CodeFactoryUtil.ACCOUNT_TABLE,CodeFactoryUtil.getMinUserInfoCode(userPojo.getDepartmentCode()),CodeFactoryUtil.getMaxUserInfoCode(userPojo.getDepartmentCode()));
            if(maxAccountCode==null||maxAccountCode==0){
                maxAccountCode= CodeFactoryUtil.getInitAccount(userPojo.getDepartmentCode());
            }else{
                ++maxAccountCode;
            }
            account.setAccountCode(maxAccountCode);
            userAccountService.updateOrSave(account,null);
//        }else{
//            UserInfo u=new UserInfo();
//            u.setUserName(userPojo.getUserName());
//            u.setDescription(userPojo.getDescription());
//            u.setEmail(userPojo.getEmail());
//            u.setPhone(userPojo.getPhone());
//            u.setId(userPojo.getUserInfoId());
//            userInfoService.updateOrSave(u, userPojo.getUserInfoId());
        }
        return "ok";

    }


    /**
     * 修改个人信息
     */
    @ResponseBody
    @RequestMapping(value="updateUserInfo",method=RequestMethod.POST)
    public Object updateUserInfo(HttpServletRequest request, HttpServletResponse response){
        String token = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if("bizData".equals(cookie.getName()))
            {
                token = cookie.getValue();
            }
//            if("userInfo".equals(cookie.getName()))
//            {
//                cookie.setValue(null);
//                cookie.setPath("/");
//                cookie.setMaxAge(0);
//                response.setContentType("text/html");
//                response.addCookie(cookie);
//            }
        }
        String oldUserInfo = cacheService.getValue(token);
        UserPojo oldUserPojo;
        try {
            oldUserPojo=JsonMapper.buildNormalMapper().fromJson(oldUserInfo, UserPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        String userPojoJson = request.getParameter("userPojoJson");
        if(StringUtils.isBlank(userPojoJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        UserPojo newUserPojo;
        try {
            newUserPojo=JsonMapper.buildNormalMapper().fromJson(userPojoJson, UserPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        if(newUserPojo == null){
            throw  new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        if(null != newUserPojo.getPhone())
        {
            oldUserPojo.setPhone(newUserPojo.getPhone());
        }
        if(null != newUserPojo.getEmail())
        {
            oldUserPojo.setEmail(newUserPojo.getEmail());
        }
        if(null != newUserPojo.getUserName())
        {
            oldUserPojo.setUserName(newUserPojo.getUserName());
        }
        if(null != newUserPojo.getPassword())
        {
            oldUserPojo.setPassword(newUserPojo.getPassword());
        }
        cacheService.addCache(token, JSON.toJSONString(oldUserPojo));
        UserInfo u=new UserInfo();
        u.setUserName(newUserPojo.getUserName());
        u.setEmail(newUserPojo.getEmail());
        u.setPhone(newUserPojo.getPhone());
        u.setId(newUserPojo.getUserInfoId());
        userInfoService.updateOrSave(u, newUserPojo.getUserInfoId());
        if(newUserPojo.getPassword()!=null || !"".equals(newUserPojo.getPassword())) {
            UserAccount account = new UserAccount();
            account.setPassword(newUserPojo.getPassword());
            account.setId(newUserPojo.getAccountId());
            userAccountService.updateOrSave(account, account.getId());
        }
        return JSON.toJSONString(oldUserPojo);
    }


    @ResponseBody
    @RequestMapping(value="checkOldPassword",method=RequestMethod.POST)
    public String checkOldPassword(HttpServletRequest request){
        String userPojoJson = request.getParameter("userPojoJson");
        if(StringUtils.isBlank(userPojoJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        UserPojo userPojo =  null;
        try {
            userPojo=JsonMapper.buildNormalMapper().fromJson(userPojoJson, UserPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("password", userPojo.getPassword());
        dataMap.put("id", userPojo.getAccountId());
        UserAccount account=(UserAccount) userAccountService.queryOne(dataMap);
        if(account!=null){
            return  "ok";
        }else{
            throw  new BizException(ERRORCODE.USER_NAME_OR_PASSWORD_DO_NOT_MATCH.getCode(),ERRORCODE.USER_NAME_OR_PASSWORD_DO_NOT_MATCH.getMessage());
        }
    }



    /**
     * 通过人员信息名称检查是否已存在的人员信息信息
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value="checkUserInfo",method=RequestMethod.POST)
    public String checkUserInfo(HttpServletRequest request){
        String userName=request.getParameter("userName");
        if(StringUtils.isBlank(userName)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
       List<UserInfo> userInfoList= userInfoService.findList("productName", userName);
        if(userInfoList!=null && userInfoList.size()>0){
            throw  new BizException(ERRORCODE.ALREADY_EXIST_ERROR.getCode(),ERRORCODE.ALREADY_EXIST_ERROR.getMessage());
        }else{
            return  "ok";
        }
    }


    /**
     * 删除人员信息
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "delUserInfo",method = RequestMethod.GET)
    public Object delUserInfo(HttpServletRequest request){
        String userId = request.getParameter("id");
        if(StringUtils.isBlank(userId)){
            ModelUtil.throwException(ERRORCODE.PARAM_ISNULL);
        }
//        UserInfo userInfo = (UserInfo)userInfoService.findOne("id", userInfoId);
//        userInfo.setStatus(Constants.DELETEED_STATUS);
//        userInfoService.update(userInfo);
//        Long userCode = userInfo.getUserCode();
//        UserAccount userAccount = (UserAccount) userAccountService.findOne("userCode",userCode);
//        userAccount.setStatus(Constants.DELETEED_STATUS);
//        userAccountService.update(userAccount);

        boolean result = exUserAccountService.delUserInfo(Long.valueOf(userId));

        if(!result){
            ModelUtil.throwException(ERRORCODE.DELETE_ERROR);
        }

        return ObjectFactory.getSingle();
    }

    /**
     * 查询人员信息
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryUserInfo",method = RequestMethod.GET)
    public Page<UserInfo> queryUserInfo(HttpServletRequest request){
        String currentPageNo = HttpUtil.getParameter(request, "currentPageNo", "1");
        String pageSize = HttpUtil.getParameter(request, "pageSize", "10");
        String departmentCode=request.getParameter("departmentCode");
        if(StringUtils.isBlank(departmentCode)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        Map<String,Object> statusMap=new HashMap<String,Object>();
        Map<String,Object> departmentMap=new HashMap<String,Object>();
        departmentMap.put("op"," = ");
        departmentMap.put("data",Long.parseLong(departmentCode));
        statusMap.put("op"," = ");
        statusMap.put("data",Constants.NORMAL_STATUS);
        dataMap.put("groupOp", " AND ");
        dataMap.put("status", statusMap);
        dataMap.put("departmentCode", departmentMap);
        int count=userInfoService.count(dataMap);
        if(count>0){
            List<UserInfo> userInfoList = userInfoService.queryPage(dataMap, ((Integer.valueOf(currentPageNo) - 1) * Integer.valueOf(pageSize)), Integer.valueOf(pageSize), CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC);
            Page<UserInfo> page=new Page<>();
            page.setCount(count);
            page.setList(userInfoList);
            return page;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
    }
    /**
     * 获取单个人员信息
     * @param request request
     * @return T
     */
    @ResponseBody
    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
    public UserInfo getUserInfo(HttpServletRequest request){

        String userInfoId = request.getParameter("id");

        if (StringUtils.isBlank(userInfoId)){
            ModelUtil.throwException(ERRORCODE.PARAM_ISNULL);
        }

        UserInfo userInfo = (UserInfo)userInfoService.findOne(
                "id",
                Long.parseLong(userInfoId));
        if(userInfo == null){
            ModelUtil.throwException(ERRORCODE.NO_MESSAGE);
        }

        Department department = (Department) departmentService.findOne(
                "departmentCode",
                userInfo.getDepartmentCode());

        UserAndDepartmentPojo pojo = new UserAndDepartmentPojo();
        BeanUtils.copyProperties(userInfo,pojo);
        pojo.setDepartmentName(department.getDepartmentName());

        return pojo;
    }


}

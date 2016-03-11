package cn.thinkjoy.jx.k12system.service.account.impl;

import cn.thinkjoy.jx.k12system.domain.LoginInfo;
import cn.thinkjoy.jx.k12system.domain.PostDataauthority;
import cn.thinkjoy.jx.k12system.domain.UserAccount;
import cn.thinkjoy.jx.k12system.domain.UserInfo;
import cn.thinkjoy.jx.k12system.pojo.UserPojo;
import cn.thinkjoy.jx.k12system.service.account.IEXLoginService;
import cn.thinkjoy.jx.k12system.service.account.ILoginInfoService;
import cn.thinkjoy.jx.k12system.service.account.IUserAccountService;
import cn.thinkjoy.jx.k12system.service.account.IUserInfoService;
import cn.thinkjoy.jx.k12system.service.post.IPostDataauthorityService;
import cn.thinkjoy.jx.k12system.util.CacheService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by yhwang on 15/9/6.
 */
@Service("EXLoginServiceImpl")
public class EXLoginServiceImpl implements IEXLoginService{
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IPostDataauthorityService iPostDataauthorityService;
    /**
     * 检查登陆并生成access_token
     *
     * @param loginNumber
     * @param pwd
     * @return
     */
    @Override
    public UserPojo checkLogin(String loginNumber, String pwd) {


        Map<String,Object>  accountdataMap  = new HashMap<>();

        accountdataMap.put("loginNumber",loginNumber);

        accountdataMap.put("password",pwd);

        UserAccount userAccount = (UserAccount)userAccountService.queryOne(accountdataMap);

        if(userAccount == null){
            return null;
        }

        UserPojo userPojo = new UserPojo();
        Map<String,Object> userInfoMap = new HashMap<>();
        userInfoMap.put("userCode",userAccount.getUserCode());
        UserInfo userInfo = (UserInfo)userInfoService.queryOne(userInfoMap);
        if(userInfo == null){
            return null;
        }
        userPojo.setUserInfoId(userInfo.getId());
        userPojo.setUserName(userInfo.getUserName());
        userPojo.setEmail(userInfo.getEmail());
        userPojo.setPhone(userInfo.getPhone());
        userPojo.setPostCode(userInfo.getPostCode());
        userPojo.setDepartmentCode(userInfo.getDepartmentCode());

        userPojo.setAccountCode(userAccount.getAccountCode());
        userPojo.setLoginNumber(userAccount.getLoginNumber());
        userPojo.setUserType(userAccount.getUserType());
        userPojo.setIdentityCode(userAccount.getIdentityCode());
        userPojo.setAccountId(userAccount.getId());
        String token = cacheService.getValue(userAccount.getAccountCode().toString());
        if(StringUtils.isBlank(token)){
            token = createAccessToken(userAccount.getAccountCode());
        }
        userPojo.setToken(token);
        //查询用户所在岗位的数据权限
        List<PostDataauthority> postDataauthorities = iPostDataauthorityService.findList("postCode", userPojo.getPostCode());
        List<Long> areaIds = new ArrayList<>();
        List<Long> schoolIds = new ArrayList<>();
        int i = 0;
        if(postDataauthorities != null){
            for(PostDataauthority postData:postDataauthorities){
                if(postData.getSchoolId() == null || postData.getSchoolId() == 0){
                    areaIds.add(postData.getAreaId());
                }else {
                    if(i == 0){
                        areaIds.add(postData.getAreaId());
                    }
                    i++;
                    schoolIds.add(postData.getSchoolId());
                }
            }
        }
        userPojo.setAreaIds(areaIds);
        userPojo.setSchoolIds(schoolIds);

        cacheService.addCache(token,JSON.toJSONString(userPojo));
        return userPojo;
    }

    protected String  createAccessToken (Long accountCode){

        String uuid = UUID.randomUUID().toString();

        LoginInfo loginInfo = new LoginInfo();

        loginInfo.setAccessToken(uuid);

        loginInfo.setAccountCode(accountCode);

        loginInfo.setLoginTime(System.currentTimeMillis());

        loginInfo.setCreateDate(System.currentTimeMillis());

        loginInfo.setCreator(accountCode);

        loginInfo.setInvalidTime(System.currentTimeMillis() + 24 * 60 * 60 * 1000);

        loginInfoService.updateOrSave(loginInfo, null);

        cacheService.addCache(String.valueOf(accountCode), uuid);

        return uuid;
    }
}

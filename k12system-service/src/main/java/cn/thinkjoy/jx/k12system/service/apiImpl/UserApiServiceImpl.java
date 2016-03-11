package cn.thinkjoy.jx.k12system.service.apiImpl;

import cn.thinkjoy.jx.k12system.UserApiService;
import cn.thinkjoy.jx.k12system.domain.UserAccount;
import cn.thinkjoy.jx.k12system.domain.UserInfo;
import cn.thinkjoy.jx.k12system.pojo.UserPojo;
import cn.thinkjoy.jx.k12system.service.account.IEXLoginInfoService;
import cn.thinkjoy.jx.k12system.service.account.IUserAccountService;
import cn.thinkjoy.jx.k12system.service.account.IUserInfoService;
import cn.thinkjoy.jx.k12system.util.CacheService;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yhwang on 15/9/21.
 */
@Service("UserApiServiceImpl")
public class UserApiServiceImpl implements UserApiService{
    @Autowired
    private CacheService cacheService;
    @Autowired
    private IEXLoginInfoService iexLoginInfoService;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public UserPojo getUserPojo(String token) {
        String userJson = cacheService.getValue(token);
        UserPojo  userPojo = new UserPojo();
        if(StringUtils.isNotBlank(userJson)){
            userPojo = JsonMapper.buildNormalMapper().fromJson(userJson,UserPojo.class);
        }else {
            Long accontCode = iexLoginInfoService.getAccountCodeByToken(token,System.currentTimeMillis());
            if(accontCode != null && accontCode != 0){
                UserAccount userAccount = (UserAccount)userAccountService.findOne("accountCode",accontCode);
                UserInfo userInfo = (UserInfo)userInfoService.findOne("userCode",accontCode);
                if(userAccount != null && userInfo != null){
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
                    userPojo.setToken(token);
                }
            }

        }
        return userPojo;
    }
}

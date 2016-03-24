package cn.thinkjoy.zgk.zgksystem.pojo;

import java.util.List;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/6.
 */
public class UserPojo implements Serializable{
    private Long accountId; //账户Id
    private Long userInfoId; //用户Id
    private Long userCode;//用户code
    private Long accountCode;//账户code
    private String userName;//用户名称
    private String loginNumber;//登录号码
    private String password;//密码
    private Long identityCode;//身份code
    private Integer userType;//用户类型
    private Long departmentCode;//部门code
    private Long postCode;//职位code
    private String phone;//联系电话
    private String token;//
    private String description;//描述
    private String email;//邮箱
    private List<Long> areaIds;//区域ID列表
    private List<Long> schoolIds;//学校ID列表
    private ClientInfoPojo clientInfoPojo;
    private Integer roleType;
    private String areaCode;

    public ClientInfoPojo getClientInfoPojo() {
        return clientInfoPojo;
    }

    public void setClientInfoPojo(ClientInfoPojo clientInfoPojo) {
        this.clientInfoPojo = clientInfoPojo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public Long getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Long accountCode) {
        this.accountCode = accountCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(String loginNumber) {
        this.loginNumber = loginNumber;
    }

    public Long getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(Long identityCode) {
        this.identityCode = identityCode;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(Long departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Long getPostCode() {
        return postCode;
    }

    public void setPostCode(Long postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public List<Long> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Long> areaIds) {
        this.areaIds = areaIds;
    }

    public List<Long> getSchoolIds() {
        return schoolIds;
    }

    public void setSchoolIds(List<Long> schoolIds) {
        this.schoolIds = schoolIds;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}

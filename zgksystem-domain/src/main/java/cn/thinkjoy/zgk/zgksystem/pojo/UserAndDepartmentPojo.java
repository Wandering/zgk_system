package cn.thinkjoy.zgk.zgksystem.pojo;

import cn.thinkjoy.zgk.zgksystem.domain.UserInfo;

/**
 * Created by yangguorong on 16/5/6.
 */
public class UserAndDepartmentPojo extends UserInfo {

    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}

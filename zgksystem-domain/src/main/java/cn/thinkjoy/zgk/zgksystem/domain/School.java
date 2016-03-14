package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by yhwang on 15/10/19.
 */
public class School extends BaseDomain{
    private String schoolName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}

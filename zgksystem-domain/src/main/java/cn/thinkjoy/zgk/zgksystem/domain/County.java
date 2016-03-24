package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by zuohao on 16/3/24.
 */
public class County extends BaseDomain {

    private String countyName;

    private String cityId;

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}

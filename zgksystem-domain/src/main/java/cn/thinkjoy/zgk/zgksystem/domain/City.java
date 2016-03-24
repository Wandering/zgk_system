package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by zuohao on 16/3/24.
 */
public class City extends BaseDomain {

    private String cityName;

    private String provinceId;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}

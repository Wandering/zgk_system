package cn.thinkjoy.zgk.zgksystem.edomain;

/**
 * Created by yangguorong on 16/4/14.
 *
 * 用户身份枚举类型
 */
public enum UserRoleEnum {

    /**
     * 超级管理员
     */
    SUPER_MANAGE(1),

    /**
     * 省级代理
     */
    PROVICE_AGENT(2),

    /**
     * 市级代理
     */
    CITY_AGENT(3),

    /**
     * 县级代理
     */
    COUNTY_AGENT(4);

    private final int value;

    UserRoleEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}

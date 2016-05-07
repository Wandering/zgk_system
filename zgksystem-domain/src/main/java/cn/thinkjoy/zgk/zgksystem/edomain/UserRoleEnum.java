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
    SUPER_MANAGE(1,"系统管理员"),

    /**
     * 省级代理
     */
    PROVICE_AGENT(2,"省代"),

    /**
     * 市级代理
     */
    CITY_AGENT(3,"市代"),

    /**
     * 县级代理
     */
    COUNTY_AGENT(4,"县代");

    private final int value;

    private final String name;

    UserRoleEnum(int value,String name){
        this.value = value;
        this.name = name;
    }

    public int getValue(){
        return value;
    }

    public String getName() {
        return name;
    }
}

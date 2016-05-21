package cn.thinkjoy.zgk.zgksystem.edomain;

/**
 * Created by yangguorong on 16/5/18.
 *
 * 产品枚举
 */
public enum ProductTypeEnum {


    JBDK(1,"金榜登科"),


    ZYJD(2,"状元及第"),


    JBTM(3,"金榜题名");

    private final int value;

    private final String name;

    ProductTypeEnum(int value, String name){
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

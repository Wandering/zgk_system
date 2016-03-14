package cn.thinkjoy.zgk.zgksystem.util;

import cn.thinkjoy.cloudstack.dynconfig.DynConfigClientFactory;

public class StaticSource {
    public  static String getSource(String sourceName){

        String source="http://media.xy189.cn";
        try {
            source= DynConfigClientFactory.getClient().getConfig("common", sourceName);
        }catch (Exception e){
            return  source;
        }
            return source;
    }

}
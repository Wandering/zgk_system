package cn.thinkjoy.zgk.zgksystem.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clei on 15/8/8.
 */
public class ServletPathConst {

    public static List<String> MAPPING_URLS = new ArrayList<String>();

    static{
        MAPPING_URLS.add("/system/agent/getAgentInfo.do");
        MAPPING_URLS.add("/system/agent/getDepartment.do");
        MAPPING_URLS.add("/system/agent/getSplitPrices.do");

        MAPPING_URLS.add("/system/system/agentTest.do");
    }
}

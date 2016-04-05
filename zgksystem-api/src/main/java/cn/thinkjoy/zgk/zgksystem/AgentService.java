package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.domain.SplitPrice;
import cn.thinkjoy.zgk.zgksystem.domain.SplitPricePojo;

import java.util.List;

/**
 * Created by liusven on 16/3/31.
 */
public interface AgentService {

    Department getAgentInfo(String accountId);

    Department getDepartment(String departmentId);

    boolean SplitPriceExec(List<SplitPricePojo> splitPricePojoList,Integer payPrice,String orderNo);

    List<SplitPrice> getSplitPriceInfo(String accountId);
}

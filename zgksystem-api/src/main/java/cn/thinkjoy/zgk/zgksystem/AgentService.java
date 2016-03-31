package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.domain.Department;

/**
 * Created by liusven on 16/3/31.
 */
public interface AgentService {

    Department getAgentInfo(String accountId);

    Department getDepartment(String departmentId);
}

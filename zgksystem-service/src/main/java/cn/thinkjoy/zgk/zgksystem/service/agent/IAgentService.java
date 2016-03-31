package cn.thinkjoy.zgk.zgksystem.service.agent;

import cn.thinkjoy.zgk.zgksystem.domain.Department;

/**
 * Created by liusven on 16/3/31.
 */
public interface IAgentService {

    Department getAgentInfo(String accountId);

    Department getDepartment(String departmentId);
}

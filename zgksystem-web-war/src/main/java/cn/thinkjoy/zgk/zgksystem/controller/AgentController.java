package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.service.account.impl.EXUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liusven on 16/3/29.
 */
@Controller
@RequestMapping("/system/agent")
public class AgentController {
    @Autowired
    private EXUserAccountService exUserAccountService;
    @Autowired
    private IDepartmentService departmentService;

    /**
     * 下订单时获取离用户最近的取货代理商信息
     * @param accountId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="getAgentInfo")
    public Department getAgentInfo(@RequestParam(value = "accountId")String accountId)
    {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("accountId", accountId);
        Map<String, Object> userAccountMap =  exUserAccountService.findUserInfo(paramMap);
        if(null == userAccountMap)
        {
            throw new BizException("100001", "帐号ID错误!");
        }
        Object countyId = userAccountMap.get("countyId");
        Object cityId = userAccountMap.get("cityId");
        Object provinceId = userAccountMap.get("provinceId");
        Department countyDepartment;
        Department cityDepartment;
        Department provinceDepartment;
        if(isValideAreaId(countyId))
        {
            String countyIdStr = String.valueOf(countyId);
            countyDepartment = (Department) departmentService.findOne("areaCode", countyIdStr);
            if(null != countyDepartment)
            {
                return fixReturnValue(countyDepartment);
            }
            cityDepartment = (Department) departmentService.findOne("areaCode", countyIdStr.substring(0,4));
            if(null != cityDepartment)
            {
                return fixReturnValue(cityDepartment);
            }
            provinceDepartment = (Department) departmentService.findOne("areaCode", countyIdStr.substring(0,2));
            if(null != provinceDepartment)
            {
                return fixReturnValue(provinceDepartment);
            }
            throw new BizException("100001", "未查找到相关代理商!");
        }else if(isValideAreaId(cityId))
        {
            String cityIdStr = String.valueOf(cityId);
            cityDepartment = (Department) departmentService.findOne("areaCode", cityIdStr.substring(0,4));
            if(null != cityDepartment)
            {
                return fixReturnValue(cityDepartment);
            }
            provinceDepartment = (Department) departmentService.findOne("areaCode", cityIdStr.substring(0,2));
            if(null != provinceDepartment)
            {
                return fixReturnValue(provinceDepartment);
            }
            throw new BizException("100001", "未查找到相关代理商!");
        }else if(isValideAreaId(provinceId))
        {
            String provinceIdStr = String.valueOf(provinceId);
            provinceDepartment = (Department) departmentService.findOne("areaCode", provinceIdStr.substring(0,2));
            if(null != provinceDepartment)
            {
                return fixReturnValue(provinceDepartment);
            }
            throw new BizException("100001", "未查找到相关代理商!");
        }
        throw new BizException("100001", "未查找到相关代理商,用户区域信息有误!");
    }

    /**
     * 获取单个部门
     * @param  departmentId 部门ID
     * @return Department
     */
    @ResponseBody
    @RequestMapping(value = "getDepartment",method = RequestMethod.GET)
    public Department getDepartment(@RequestParam(value = "departmentId")String departmentId){
        return fixReturnValue(departmentService.getDepartment(departmentId));
    }


    private Department fixReturnValue(Department countyDepartment) {
        countyDepartment.setCompanyCode(null);
        countyDepartment.setDepartmentCode(null);
        countyDepartment.setDepartmentPrincipal(null);
        countyDepartment.setDescription(null);
        countyDepartment.setParentCode(null);
        countyDepartment.setSeqSort(null);
        countyDepartment.setCreator(null);
        countyDepartment.setRoleType(null);
        countyDepartment.setCreateDate(null);
        countyDepartment.setCreatorName(null);
        countyDepartment.setLastModDate(null);
        countyDepartment.setLastModifier(null);
        countyDepartment.setLastModifierName(null);
        countyDepartment.setStatus(null);
        countyDepartment.setAreaCode(null);
        countyDepartment.setDepartmentFax(null);
        return countyDepartment;
    }

    private boolean isValideAreaId(Object countyId) {
        return null != countyId && !"00".equals(countyId) && String.valueOf(countyId).length() == 6;
    }
}

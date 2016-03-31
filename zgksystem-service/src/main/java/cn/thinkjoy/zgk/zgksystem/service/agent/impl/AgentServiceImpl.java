package cn.thinkjoy.zgk.zgksystem.service.agent.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.AgentService;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.domain.MarketParmas;
import cn.thinkjoy.zgk.zgksystem.pojo.SplitPricePojo;
import cn.thinkjoy.zgk.zgksystem.service.account.impl.EXUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.service.market.IMarketParmasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusven on 16/3/31.
 */
@Service("AgentServiceImpl")
public class AgentServiceImpl implements AgentService {

    @Autowired
    private EXUserAccountService exUserAccountService;
    @Autowired
    private IDepartmentService departmentService;

    @Resource
    private IMarketParmasService iMarketParmasService;

    @Override
    public Department getAgentInfo(String accountId) {
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
     * 分成
     * @param splitPricePojoList  关系链
     * @param payPrice     成交价
     * @return
     */
    @Override
    public boolean SplitPrice(List<SplitPricePojo> splitPricePojoList,Integer payPrice) {
        if (splitPricePojoList == null)
            throw new BizException("100001", "未输入关系链数据!");

        MarketParmas marketParmas = iMarketParmasService.getMarketParmas(null);

        if (marketParmas == null)
            throw new BizException("100001", "系统参数获取失败!");

        BigDecimal maxBigDecimal = new BigDecimal(100);

        //分转元
        double pPrice = new BigDecimal(payPrice).multiply(maxBigDecimal).doubleValue();

        //成交价>=成本价
        if (pPrice >= marketParmas.getCostPrice().doubleValue()) {

            Collections.sort(splitPricePojoList); //按层级排序

            SplitPricePojo splitPricePojo=splitPricePojoList.get(splitPricePojoList.size()-1);
            if(splitPricePojo==null)
                throw new BizException("100001", "获取当前购买用户失败!");

            Department department= getAgentInfo(splitPricePojo.getAccountId().toString());
            if(department==null)
                throw new BizException("100001", "获取供货商失败!");
            //department.getId()
            //department:"0"  "1"

            //size()-1 :过滤当前支付用户
//            for(int i=0;i<splitPricePojoList.size()-1;i++) {
//
//                SplitPricePojo splitPriceP = splitPricePojoList.get(i);
//
//            }


        } else
            throw new BizException("100001", "成交价必须大于成本价!");

        return true;
    }

    @Override
    public Department getDepartment(String departmentId) {
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

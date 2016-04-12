package cn.thinkjoy.zgk.zgksystem.service.agent.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.AgentService;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.domain.MarketParmas;
import cn.thinkjoy.zgk.zgksystem.domain.SplitPrice;
import cn.thinkjoy.zgk.zgksystem.pojo.SplitPricePojo;
import cn.thinkjoy.zgk.zgksystem.service.account.impl.EXUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.service.market.IMarketParmasService;
import cn.thinkjoy.zgk.zgksystem.service.market.ISplitPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by liusven on 16/3/31.
 */
@Service("AgentServiceImpl")
public class AgentServiceImpl implements AgentService {



    private static final Logger LOGGER= LoggerFactory.getLogger(AgentServiceImpl.class);
    @Autowired
    private EXUserAccountService exUserAccountService;
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IMarketParmasService iMarketParmasService;

    @Autowired
    private ISplitPriceService iSplitPriceService;

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
     * @param orderNo 订单号
     * @return
     */
    @Override
    public boolean SplitPriceExec(List<SplitPricePojo> splitPricePojoList,Integer payPrice,String orderNo) {
        LOGGER.info("***********************分成 Start***********************");
        Map map = new HashMap();
        map.put("orderNo", orderNo);
        LOGGER.info("订单号[orderNo]:" + orderNo);
        LOGGER.info("售价[payPrice]:" + payPrice);

        List<SplitPrice> splitPrices = iSplitPriceService.getSplitPriceList(map);
        if (splitPrices.size() > 0)
            throw new BizException("100001", "该笔订单已经分成!");

        boolean result = false;
        if (splitPricePojoList == null)
            throw new BizException("100001", "未输入关系链数据!");

        Integer len = splitPricePojoList.size() - 1;

        Map splitPriceRoleMap=new HashMap();
        splitPriceRoleMap.put("splitLevel",len);

        MarketParmas marketParmas = iMarketParmasService.getMarketParmas(splitPriceRoleMap);

        if (marketParmas == null)
            throw new BizException("100001", "系统参数获取失败!");

        BigDecimal maxBigDecimal = new BigDecimal(100);

        //分转元
//        double pPrice = new BigDecimal(payPrice).divide(maxBigDecimal).doubleValue();
        LOGGER.info("成本价[costPrice]:" + marketParmas.getCostPrice());
        //成交价>=成本价
//        if (payPrice >= marketParmas.getCostPrice()) {

            Collections.sort(splitPricePojoList); //按层级排序



            LOGGER.info("层级数[splitPricePojoList.size()]:" + len);

            SplitPricePojo splitPricePojo = splitPricePojoList.get(len);

            if (splitPricePojo == null)
                throw new BizException("100001", "获取当前购买用户失败!");

            Department department = getAgentInfo(splitPricePojo.getAccountId().toString());
            if (department == null)
                throw new BizException("100001", "获取供货商失败!");

            ArrayList<Integer> splitPriceArr = getLevelSplitPrice(len, payPrice, marketParmas);
            switch (len) {
                case 0:
                    LOGGER.info("层级数为:" + len + "供货商:" + department.getId() + "分成总额为:" + payPrice);
                    result = insertDepartmentSplitPrice(department, orderNo, payPrice);
                    break;
                case 1:
                    SplitPricePojo splitPrice = splitPricePojoList.get(0);
                    if (splitPrice == null)
                        throw new BizException("100001", "关系链获取失败!");
                    //用户分成
                    result = insertUserSplitPrice(splitPrice, orderNo, splitPriceArr.get(0));
                    //供货商分成
                    result = insertDepartmentSplitPrice(department, orderNo, splitPriceArr.get(1));
                    LOGGER.info("层级数为:" + len + "用户:" + splitPrice.getAccountId() + "分成总额为:" + splitPriceArr.get(0));
                    LOGGER.info("层级数为:" + len + "供货商:" + department.getId() + "分成总额为:" + splitPriceArr.get(1));
                    break;
                case 2:
                    SplitPricePojo splitPri0 = splitPricePojoList.get(0);
                    result = insertUserSplitPrice(splitPri0, orderNo, splitPriceArr.get(0));
                    SplitPricePojo splitPri1 = splitPricePojoList.get(1);
                    result = insertUserSplitPrice(splitPri1, orderNo, splitPriceArr.get(1));

                    result = insertDepartmentSplitPrice(department, orderNo, splitPriceArr.get(2));

                    LOGGER.info("层级数为:" + len + "用户:" + splitPri0.getAccountId() + "分成总额为:" + splitPriceArr.get(0));
                    LOGGER.info("层级数为:" + len + "用户:" + splitPri1.getAccountId() + "分成总额为:" + splitPriceArr.get(1));
                    LOGGER.info("层级数为:" + len + "供货商:" + department.getId() + "分成总额为:" + splitPriceArr.get(2));

                    break;
            }
//        } else
//            throw new BizException("100001", "成交价必须大于成本价!");

        LOGGER.info("***********************分成 End***********************");
        return result;
    }

    @Override
    public List<SplitPrice> getSplitPriceInfo(String accountId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", accountId);
        paramMap.put("type", "1");
        return iSplitPriceService.getSplitPriceList(paramMap);
    }

    @Override
    public Department getDepartment(String departmentId) {
        return fixReturnValue(departmentService.getDepartment(departmentId));
    }

    /**
     * 普通用户分成  实体
     * @return
     */
    private boolean insertUserSplitPrice(SplitPricePojo splitPricePojo,String orderNo,Integer profitPrice) {
        SplitPrice splitPrice = new SplitPrice();
        splitPrice.setStatus((byte) 0);
        splitPrice.setOrderNo(orderNo);
        splitPrice.setType((byte) 1);
        splitPrice.setCreateTime(System.currentTimeMillis());
        splitPrice.setUserId(Long.valueOf(splitPricePojo.getAccountId()));
        splitPrice.setUserPhone(splitPricePojo.getAccountPhone());
        splitPrice.setPrice(Double.parseDouble(profitPrice+""));
        Integer result = iSplitPriceService.insert(splitPrice);

        return result > 0 ? true : false;
    }
    /**
     * 供货商分成  实体
     * @param department   供货商实体
     * @param orderNo 订单号
     * @param profitPrice  分成价
     * @return
     */
    private boolean insertDepartmentSplitPrice(Department department,String orderNo,Integer profitPrice) {
        SplitPrice splitPrice = new SplitPrice();
        splitPrice.setStatus((byte) 0);
        splitPrice.setOrderNo(orderNo);
        splitPrice.setType((byte) 0);
        splitPrice.setCreateTime(System.currentTimeMillis());
        splitPrice.setUserId(Long.valueOf(department.getId().toString()));
        splitPrice.setUserPhone(department.getDepartmentPhone());
        splitPrice.setPrice(Double.parseDouble(profitPrice+""));
        Integer result = iSplitPriceService.insert(splitPrice);
        return result > 0 ? true : false;
    }
    /**
     * 获取各层级分成利润
     * @return
     */
    private ArrayList<Integer> getLevelSplitPrice(Integer level,Integer payPrice,MarketParmas marketParmas) {
        LOGGER.info("***********************获取各层级分成利润 Start***********************");

        LOGGER.info("层级:"+level);
        LOGGER.info("成交价:"+payPrice);


        ArrayList<Integer> levelSplitPriceArr=new ArrayList<Integer>();
//        Integer splitPercent=marketParmas.getSplitPercentage();
//        Integer costPrice=marketParmas.getCostPrice();
//        LOGGER.info("层级分成比例:"+splitPercent);
//        LOGGER.info("成本价:"+ costPrice);

        BigDecimal bigDecimal = new BigDecimal(100);

        String userPercent=marketParmas.getLevelProfits();

        //供货商分成比例
        Integer supplyPercent=Integer.valueOf(marketParmas.getSplitPercentage());


        LOGGER.info("用户分成规则:"+userPercent);
        LOGGER.info("供货商分成比例:"+supplyPercent);
        switch (level) {
            case 0:
                Integer suppPrice0 = new BigDecimal(payPrice).multiply(new BigDecimal(supplyPercent).divide(bigDecimal)).intValue();
                LOGGER.info("供货商分成:" + suppPrice0);
                levelSplitPriceArr.add(suppPrice0);
                break;
            case 1:
                Integer splitPercent= Integer.valueOf(userPercent);

                Integer price0 = new BigDecimal(payPrice).multiply(new BigDecimal(splitPercent).divide(bigDecimal)).intValue();
                Integer price1 = new BigDecimal(payPrice).multiply(new BigDecimal(supplyPercent).divide(bigDecimal)).intValue();

                levelSplitPriceArr.add(price0);//用户分成
                levelSplitPriceArr.add(price1);  //供货商分成

                LOGGER.info("用户分成:" + price0);
                LOGGER.info("供货商分成:" + price1);
                break;
            case 2:
                 ArrayList<Integer> splitRole= getSplitArr(userPercent);
                Integer pr0 = new BigDecimal(payPrice).multiply(new BigDecimal(splitRole.get(0)).divide(bigDecimal)).intValue();
                LOGGER.info("用户分成0:" + pr0);
                Integer pr1 = new BigDecimal(payPrice).multiply(new BigDecimal(splitRole.get(1)).divide(bigDecimal)).intValue();
                LOGGER.info("用户分成1:" + pr1);
                Integer pr2=new BigDecimal(payPrice).multiply(new BigDecimal(supplyPercent).divide(bigDecimal)).intValue();
                LOGGER.info("供货商分成2:" + pr2);
//
//                Integer profit = payPrice;
//                //(成交价-成本价)*分成比例*30%
//                double p0 = new BigDecimal(profit).multiply(new BigDecimal(splitPercent).divide(bigDecimal)).multiply(new BigDecimal(0.3)).doubleValue();
//
//                Integer pr0 = new BigDecimal(p0).intValue();
//                LOGGER.info("用户分成0:" + pr0);
//                //(成交价-成本价)*分成比例*70%
//                double p1 = new BigDecimal(profit).multiply(new BigDecimal(splitPercent).divide(bigDecimal)).multiply(new BigDecimal(0.7)).doubleValue();
//
//                Integer pr1 = new BigDecimal(p1).intValue();
//                LOGGER.info("用户分成1:" + pr1);

                //成交价-B-C
//                Integer pr2 = payPrice - pr0 - pr1;
                LOGGER.info("供货商分成2:" + pr2);
                levelSplitPriceArr.add(pr0);
                levelSplitPriceArr.add(pr1);
                levelSplitPriceArr.add(pr2);
                break;
        }
        LOGGER.info("***********************获取各层级分成利润 End***********************");
        return levelSplitPriceArr;
    }

    /**
     * 转换规则串为Int数组
     * @param str
     * @return
     */
    private ArrayList<Integer> getSplitArr(String str) {
        ArrayList<Integer> splitArr = new ArrayList<>();

        String[] splits = str.split("-");
        for (String s : splits) {
            splitArr.add(Integer.valueOf(s));
        }

        return splitArr;
    }

//    private ArrayList<Integer>
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

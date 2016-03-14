package cn.thinkjoy.zgk.zgksystem.service.company;

import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.Company;
import cn.thinkjoy.zgk.zgksystem.pojo.CompanyPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyongqiang on 15/9/8.
 */
public interface IEXCompanyService {
    Page<Company> queryByPage(Map<String,Object> map,int offset,int rows,String orderBy,String sortBy);
    /**
     * 查询代理公司
     * @return Page<T>
     */
    Page<CompanyPojo> queryCompany( UserPojo userPojo,int currentPageNo,int pageSize,String parentCode);

    /**
     * 获取单个代理公司
     * @param companyId
     * @return
     */
    Company getCompany(String companyId);

    /**
     * 获取代理公司Code和代理公司名称
     * @return
     */
    Map<String,String> queryComboxCompany();

    /**
     * 获取代理公司Code和代理公司名称树形
     * @return
     */
    List<TreeBean> queryTreeCompany();

}

package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.CompanyPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;

/**
 * Created by yhwang on 15/10/8.
 */
public interface CompanyApiService {
    /**
     * 查询代理公司
     * @return Page<T>
     */
    Page<CompanyPojo> queryCompany( UserPojo userPojo,int currentPageNo,int pageSize,String parentCode);

}

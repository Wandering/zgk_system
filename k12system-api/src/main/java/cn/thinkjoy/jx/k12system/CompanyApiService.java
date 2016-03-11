package cn.thinkjoy.jx.k12system;

import cn.thinkjoy.jx.k12system.common.Page;
import cn.thinkjoy.jx.k12system.pojo.CompanyPojo;
import cn.thinkjoy.jx.k12system.pojo.UserPojo;

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

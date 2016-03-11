package cn.thinkjoy.jx.k12system.service.apiImpl;

import cn.thinkjoy.jx.k12system.CompanyApiService;
import cn.thinkjoy.jx.k12system.common.Page;
import cn.thinkjoy.jx.k12system.pojo.CompanyPojo;
import cn.thinkjoy.jx.k12system.pojo.UserPojo;
import cn.thinkjoy.jx.k12system.service.company.IEXCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yhwang on 15/10/8.
 */
@Service("CompanyApiServiceImpl")
public class CompanyApiServiceImpl implements CompanyApiService {

    @Autowired
    private IEXCompanyService iexCompanyService;
    /**
     * 查询代理公司
     *
     * @param userPojo
     * @param currentPageNo
     * @param pageSize
     * @param parentCode
     * @return Page<T>
     */
    @Override
    public Page<CompanyPojo> queryCompany(UserPojo userPojo, int currentPageNo, int pageSize, String parentCode) {
        return iexCompanyService.queryCompany(userPojo,currentPageNo,pageSize,parentCode);
    }
}

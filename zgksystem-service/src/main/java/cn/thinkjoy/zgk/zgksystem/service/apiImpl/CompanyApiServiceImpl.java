package cn.thinkjoy.zgk.zgksystem.service.apiImpl;

import cn.thinkjoy.zgk.zgksystem.CompanyApiService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.pojo.CompanyPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.company.IEXCompanyService;
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

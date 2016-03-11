package cn.thinkjoy.jx.k12system.service.apiImpl;

import cn.thinkjoy.jx.k12system.CompanyApiService;
import cn.thinkjoy.jx.k12system.DeparmentApiService;
import cn.thinkjoy.jx.k12system.common.Page;
import cn.thinkjoy.jx.k12system.common.TreeBean;
import cn.thinkjoy.jx.k12system.pojo.CompanyPojo;
import cn.thinkjoy.jx.k12system.pojo.UserPojo;
import cn.thinkjoy.jx.k12system.service.company.IEXCompanyService;
import cn.thinkjoy.jx.k12system.service.department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yhwang on 15/10/8.
 */
@Service("DeparmentApiServiceImpl")
public class DeparmentApiServiceImpl implements DeparmentApiService {

    @Autowired
    private IDepartmentService iDepartmentService;

    /**
     * 查询代理公司
     *
     * @param parentCode
     * @return Page<T>
     */
    @Override
    public List<TreeBean> recursionTree(Long parentCode) {
        return iDepartmentService.recursionTree(parentCode);
    }
}

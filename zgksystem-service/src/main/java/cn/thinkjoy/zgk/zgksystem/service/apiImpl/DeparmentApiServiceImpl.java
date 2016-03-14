package cn.thinkjoy.zgk.zgksystem.service.apiImpl;

import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
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

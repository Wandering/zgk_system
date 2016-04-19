package cn.thinkjoy.zgk.zgksystem.service.apiImpl;

import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.edomain.UserRoleEnum;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.service.department.IEXDeparmentService;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private IEXDeparmentService exDeparmentService;

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

    @Override
    public List<Department> queryDepartmentsByAreaCode(String areaCode,int roleType,int currentPage,int pageSize) {
        String tmpAreaCode = "";
//        if(roleType == UserRoleEnum.SUPER_MANAGE.getValue()){
//            tmpAreaCode = "";
//        }else
        if (roleType == UserRoleEnum.PROVICE_AGENT.getValue()){
            tmpAreaCode = StringUtils.substring(areaCode,0,2);
        }else if (roleType == UserRoleEnum.CITY_AGENT.getValue()){
            tmpAreaCode = StringUtils.substring(areaCode,0,4);
        }else if (roleType == UserRoleEnum.COUNTY_AGENT.getValue()){
            tmpAreaCode = StringUtils.substring(areaCode,0,6);
        }

        List<Department> departments = exDeparmentService.queryDepartmentsByAreaCode(
                tmpAreaCode,
                currentPage,
                pageSize);

        return departments;
    }
}

package cn.thinkjoy.zgk.zgksystem.service.apiImpl;

import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
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
    public List<Department> queryDepartmentsByAreaCode(String areaCode,int areaType,int currentPage,int pageSize) {

        String tmpAreaCode = "";
        if(areaType != -1){
            tmpAreaCode = StringUtils.substring(areaCode,0,2*areaType);
        }

        List<Department> departments = exDeparmentService.queryDepartmentsByAreaCode(
                tmpAreaCode,
                currentPage,
                pageSize);

        return departments;
    }

    @Override
    public int getDepartmentCountByAreaCode(String areaCode, int areaType) {
        String tmpAreaCode = "";
        if(areaType != -1){
            tmpAreaCode = StringUtils.substring(areaCode,0,2*areaType);
        }
        return exDeparmentService.getDepartmentCountByAreaCode(tmpAreaCode);
    }

    @Override
    public Department quertDepartmentInfoByCode(long departmentCode) {
        return (Department) iDepartmentService.findOne("departmentCode",departmentCode);
    }
}

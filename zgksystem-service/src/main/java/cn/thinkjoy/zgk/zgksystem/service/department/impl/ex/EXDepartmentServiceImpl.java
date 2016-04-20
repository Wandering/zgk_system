package cn.thinkjoy.zgk.zgksystem.service.department.impl.ex;

import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXDepartmentDAO;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.service.department.IEXDeparmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangguorong on 16/4/19.
 */
@Service("eXDepartmentService")
public class EXDepartmentServiceImpl implements IEXDeparmentService{

    @Autowired
    private IEXDepartmentDAO exDepartmentDAO;

    @Override
    public List<Department> queryDepartmentsByAreaCode(String areaCode, int currentPage, int pageSize) {
        return exDepartmentDAO.queryDepartmentsByAreaCode(areaCode,currentPage*pageSize,pageSize);
    }

    @Override
    public int getDepartmentCountByAreaCode(String areaCode) {
        Integer count = exDepartmentDAO.getDepartmentCountByAreaCode(areaCode);
        return count == null ? 0:count;
    }
}

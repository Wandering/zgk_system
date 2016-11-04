package cn.thinkjoy.zgk.zgksystem.service.apiImpl;

import cn.thinkjoy.zgk.zgksystem.DeparmentApiService;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXDepartmentDAO;
import cn.thinkjoy.zgk.zgksystem.domain.Department;
import cn.thinkjoy.zgk.zgksystem.domain.DepartmentProductRelation;
import cn.thinkjoy.zgk.zgksystem.domain.SaleProduct;
import cn.thinkjoy.zgk.zgksystem.edomain.ProductTypeEnum;
import cn.thinkjoy.zgk.zgksystem.edomain.UserRoleEnum;
import cn.thinkjoy.zgk.zgksystem.pojo.DepartmentProductRelationPojo;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentProductRelationService;
import cn.thinkjoy.zgk.zgksystem.service.department.IDepartmentService;
import cn.thinkjoy.zgk.zgksystem.service.department.IEXDeparmentService;
import cn.thinkjoy.zgk.zgksystem.service.department.ISaleProductService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by yhwang on 15/10/8.
 */
@Service("DeparmentApiServiceImpl")
public class DeparmentApiServiceImpl implements DeparmentApiService {

    @Autowired
    private IDepartmentService iDepartmentService;

    @Autowired
    private IEXDeparmentService exDeparmentService;

    @Autowired
    private IEXDepartmentDAO exDepartmentDAO;

    @Autowired
    private ISaleProductService saleProductService;

    @Autowired
    private IDepartmentProductRelationService departmentProductRelationService;

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
    public List<Department> queryDepartmentsByAreaCode(String areaCode, int areaType, int currentPage, int pageSize) {

        String tmpAreaCode = "";
        if (areaType != -1) {
            tmpAreaCode = StringUtils.substring(areaCode, 0, 2 * areaType);
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
        if (areaType != -1) {
            tmpAreaCode = StringUtils.substring(areaCode, 0, 2 * areaType);
        }
        return exDeparmentService.getDepartmentCountByAreaCode(tmpAreaCode);
    }

    @Override
    public Department queryDepartmentInfoByCode(long departmentCode) {
        return (Department) iDepartmentService.findOne("departmentCode", departmentCode);
    }

    @Override
    public List<DepartmentProductRelation> queryProductPriceByDepartmentCode(long departmentCode) {
        return exDepartmentDAO.queryProductPriceByCode(departmentCode);
    }

    @Override
    public List<DepartmentProductRelationPojo> queryProductPriceByAreaId(String areaId) throws InvocationTargetException, IllegalAccessException {
        /**
         * 1.查询出所有产品
         * 2.判断用户所在区域是否有省级代理商
         * 3.没有:直接显示产品默认价格
         * 4.有:查询省级代理商是否有代理产品
         * 5.没有:默认价格
         * 6.有:返回代理商定价
         */

        List<SaleProduct> saleProducts = saleProductService.findAll();

        Map<String, Object> queryMap = Maps.newHashMap();
        queryMap.put("areaCode", StringUtils.substring(areaId, 0, 2));
        queryMap.put("status", 0);
        queryMap.put("roleType", UserRoleEnum.PROVICE_AGENT.getValue());
        Department department = (Department) iDepartmentService.queryOne(queryMap);

        List<DepartmentProductRelationPojo> relations = Lists.newArrayList();
        for (SaleProduct product : saleProducts) {
            // 忽略金榜题名套餐
            if (product.getType() == ProductTypeEnum.JBTM.getValue()) {
                continue;
            }

            // 忽略非当前省的套餐
            if (!"0".equals(product.getAreaId()) && product.getAreaId().indexOf(areaId) == -1) {
                continue;
            }

            DepartmentProductRelation relation = null;
            if (department != null) {
                queryMap.clear();
                queryMap.put("status", 0);
                queryMap.put("productId", product.getId());
                queryMap.put("departmentCode", department.getDepartmentCode());
                relation = (DepartmentProductRelation) departmentProductRelationService.queryOne(queryMap);
            }

            if (department == null || relation == null) {
                relation = convert2DepartmentProductRelation(product);
            }

            DepartmentProductRelationPojo relationPojo = new DepartmentProductRelationPojo();
            BeanUtils.copyProperties(relationPojo,relation);
            relationPojo.setIntro(product.getIntro());
            relationPojo.setProductType(product.getType());
            relations.add(relationPojo);
        }
        return relations;
    }

    @Override
    public DepartmentProductRelationPojo queryProductPriceByAreaId(String areaId, Integer productId) throws InvocationTargetException, IllegalAccessException {
        /**
         * 1.查询出所有产品
         * 2.判断用户所在区域是否有省级代理商
         * 3.没有:直接显示产品默认价格
         * 4.有:查询省级代理商是否有代理产品
         * 5.没有:默认价格
         * 6.有:返回代理商定价
         */

        Map<String, Object> map = new HashedMap();
        map.put("type", productId);
        SaleProduct saleProduct = (SaleProduct) saleProductService.queryOne(map);

        Map<String, Object> queryMap = Maps.newHashMap();
        queryMap.put("areaCode", StringUtils.substring(areaId, 0, 2));
        queryMap.put("status", 0);
        queryMap.put("roleType", UserRoleEnum.PROVICE_AGENT.getValue());
        Department department = (Department) iDepartmentService.queryOne(queryMap);

        DepartmentProductRelation relation = null;
        if (department != null) {
            queryMap.clear();
            queryMap.put("status", 0);
            queryMap.put("productId", saleProduct.getId());
            queryMap.put("departmentCode", department.getDepartmentCode());
            relation = (DepartmentProductRelation) departmentProductRelationService.queryOne(queryMap);
        }
        DepartmentProductRelationPojo relationPojo = new DepartmentProductRelationPojo();

        if (department == null || relation == null) {
            relation = convert2DepartmentProductRelation(saleProduct);
        }
        BeanUtils.copyProperties(relationPojo, relation);
        relationPojo.setProductType(saleProduct.getType());
        relationPojo.setIntro(saleProduct.getIntro());
        return relationPojo;
    }


    /**
     * 对象转换  SaleProduct --> DepartmentProductRelation
     *
     * @param product
     * @return
     */
    private DepartmentProductRelation convert2DepartmentProductRelation(SaleProduct product) {
        DepartmentProductRelation relation = new DepartmentProductRelation();
        relation.setSalePrice(product.getDefaultSalePrice());
        relation.setProductName(product.getProductName());
        relation.setPickupPrice(product.getDefaultPickupPrice());
        relation.setProductId(Long.valueOf(product.getId().toString()));
        relation.setProductType(product.getType());
        return relation;
    }
}

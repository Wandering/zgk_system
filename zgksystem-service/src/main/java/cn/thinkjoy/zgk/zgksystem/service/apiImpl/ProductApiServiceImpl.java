package cn.thinkjoy.zgk.zgksystem.service.apiImpl;

import cn.thinkjoy.zgk.zgksystem.ProductApiService;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.product.IEXProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yhwang on 15/10/8.
 */
@Service("ProductApiServiceImpl")
public class ProductApiServiceImpl implements ProductApiService{
    @Autowired
    private IEXProductService iexProductService;
    /**
     * 根据用户获取产品树
     *
     * @param userPojo
     * @return
     */
    @Override
    public List<TreeBean> queryTreeProduct(UserPojo userPojo) {
        return iexProductService.queryTreeProduct(userPojo);
    }
}

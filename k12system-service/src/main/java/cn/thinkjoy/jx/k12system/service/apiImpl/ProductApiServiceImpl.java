package cn.thinkjoy.jx.k12system.service.apiImpl;

import cn.thinkjoy.jx.k12system.ProductApiService;
import cn.thinkjoy.jx.k12system.common.TreeBean;
import cn.thinkjoy.jx.k12system.pojo.UserPojo;
import cn.thinkjoy.jx.k12system.service.product.IEXProductService;
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

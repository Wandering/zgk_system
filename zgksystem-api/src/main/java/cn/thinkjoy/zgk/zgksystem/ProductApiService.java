package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;

import java.util.List;

/**
 * Created by yhwang on 15/10/8.
 */
public interface ProductApiService {
    /**
     * 根据用户获取产品树
     * @param userPojo
     * @return
     */
    List<TreeBean> queryTreeProduct(UserPojo userPojo);
}

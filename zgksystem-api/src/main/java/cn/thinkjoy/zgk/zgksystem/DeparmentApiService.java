package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.common.TreeBean;

import java.util.List;

/**
 * Created by yhwang on 15/10/8.
 */
public interface DeparmentApiService {
    /**
     * 查询部门树  parentCode
     * @return Page<T>
     */
    List<TreeBean> recursionTree(Long parentCode);

}

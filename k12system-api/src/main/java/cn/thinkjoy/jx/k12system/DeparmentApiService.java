package cn.thinkjoy.jx.k12system;

import cn.thinkjoy.jx.k12system.common.TreeBean;

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

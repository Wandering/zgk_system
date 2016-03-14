package cn.thinkjoy.zgk.zgksystem.service.resource;

import java.util.List;

/**
 * Created by yhwang on 15/9/16.
 */
public interface IEXResourceService {
    /**
     * 根据角色code查询资源code列表
     * @param roleCode
     * @return
     */
    List<Long> resourceCodeListByRole(Long roleCode);
}

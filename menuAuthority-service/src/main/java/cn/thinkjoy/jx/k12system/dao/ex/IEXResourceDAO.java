package cn.thinkjoy.jx.k12system.dao.ex;

import cn.thinkjoy.jx.k12system.pojo.ResourcesPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/9/16.
 */
public interface IEXResourceDAO {
    /**
     * 根据岗位和菜单查询资源列表
     * @param menuCode
     * @param postCode
     * @return
     */
    List<ResourcesPojo> getResourcesByMenuPost(@Param("menuCode")Long menuCode,@Param("postCode")Long postCode);

    /**
     * 查询菜单下的所有资源
     * @return
     */
    List<ResourcesPojo> getResourcesAll(@Param("menuCode") Long menuCode);

    /**
     * 根据角色code获取资源code列表
     * @param roleCode
     * @return
     */
    List<Long> resourceCodeListByRole(@Param("roleCode")Long roleCode);
}

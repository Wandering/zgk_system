package cn.thinkjoy.zgk.zgksystem.dao.ex;

import cn.thinkjoy.zgk.zgksystem.pojo.MenuPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/9/15.
 */
public interface IEXMenuDAO {
    /**
     * 根据岗位code获取菜单树
     * @param postCode
     * @return
     */
    List<MenuPojo> getMenuPojoListByPost(@Param("parentCode") Long parentCode,@Param("postCode") Long postCode);

    /**
     * 查询所有菜单
     * @return
     */
    List<MenuPojo> getMenuAllPojoList(@Param("parentCode") Long parentCode);

    /**
     * 按照菜单code修改菜单角色表
     * @param menuCode
     */
    void updateMenuRole(@Param("menuCode")Long menuCode);

    /**
     * 根据角色code获取菜单code列表
     * @param roleCode
     * @return
     */
    List<Long> getMenuCodeByRole(@Param("roleCode")Long roleCode);
}

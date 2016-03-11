package cn.thinkjoy.jx.k12system.dao.ex;

import cn.thinkjoy.jx.k12system.domain.Area;
import cn.thinkjoy.jx.k12system.domain.School;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/10/19.
 */
public interface IEXDataDictionaryDAO {
    /**
     * 获取西安市下的所有区域
     * @return
     */
    List<Area> findAreaList();

    /**
     * 根据区域ID获取区域下的所有学校
     * @param areaId
     * @return
     */
    List<School> findSchoolList(@Param("areaId") Long areaId);
}

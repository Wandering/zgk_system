package cn.thinkjoy.zgk.zgksystem.dao.ex;

import cn.thinkjoy.zgk.zgksystem.domain.*;
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

    List<Province> findProvinceList();
    List<City> findCityList(@Param("provinceId") Long provinceId);
    List<County> findCountyList(@Param("cityId") Long cityId);
}

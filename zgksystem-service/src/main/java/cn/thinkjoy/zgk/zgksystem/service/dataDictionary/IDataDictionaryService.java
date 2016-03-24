package cn.thinkjoy.zgk.zgksystem.service.dataDictionary;

import cn.thinkjoy.zgk.zgksystem.domain.*;

import java.util.List;

/**
 * Created by yhwang on 15/10/19.
 */
public interface IDataDictionaryService {
    /**
     *
     * @return
     */
    List<Area> findAreaList();
    /**
     * 根据区域ID获取区域下的所有学校
     * @param areaId
     * @return
     */
    List<School> findSchoolList(Long areaId);

    List<Province> findProvinceList();
    List<City> findCityList(Long provinceId);
    List<County> findCountyList(Long cityId);
}

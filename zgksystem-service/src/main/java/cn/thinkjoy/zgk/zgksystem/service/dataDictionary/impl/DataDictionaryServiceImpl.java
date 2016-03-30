package cn.thinkjoy.zgk.zgksystem.service.dataDictionary.impl;

import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXDataDictionaryDAO;
import cn.thinkjoy.zgk.zgksystem.domain.*;
import cn.thinkjoy.zgk.zgksystem.service.dataDictionary.IDataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.CharacterIterator;
import java.util.List;

/**
 * Created by yhwang on 15/10/19.
 */
@Service("DataDictionaryServiceImpl")
public class DataDictionaryServiceImpl implements IDataDictionaryService{
    @Autowired
    private IEXDataDictionaryDAO iDataDictionaryDAO;
    /**
     * @return
     */
    @Override
    public List<Area> findAreaList() {
        return iDataDictionaryDAO.findAreaList();
    }

    @Override
    public List<Province> findProvinceList(){
        return iDataDictionaryDAO.findProvinceList();
    }

    @Override
    public List<City> findCityList(Long cityId) {
        return iDataDictionaryDAO.findCityList(cityId);
    }

    @Override
    public List<County> findCountyList(Long countyId) {
        return iDataDictionaryDAO.findCountyList(countyId);
    }

    @Override
    public void updateProvince(String provinceId) {
        iDataDictionaryDAO.updateProvince(provinceId);
    }

    @Override
    public void updateCity(String cityId) {
        iDataDictionaryDAO.updateCity(cityId);
    }

    @Override
    public void updateCounty(String countyId) {
        iDataDictionaryDAO.updateCounty(countyId);
    }

    /**
     * 根据区域ID获取区域下的所有学校
     *
     * @param areaId
     * @return
     */
    @Override
    public List<School> findSchoolList(Long areaId) {
        return iDataDictionaryDAO.findSchoolList(areaId);
    }
}

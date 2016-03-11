package cn.thinkjoy.jx.k12system.service.dataDictionary.impl;

import cn.thinkjoy.jx.k12system.dao.ex.IEXDataDictionaryDAO;
import cn.thinkjoy.jx.k12system.domain.Area;
import cn.thinkjoy.jx.k12system.domain.School;
import cn.thinkjoy.jx.k12system.service.dataDictionary.IDataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

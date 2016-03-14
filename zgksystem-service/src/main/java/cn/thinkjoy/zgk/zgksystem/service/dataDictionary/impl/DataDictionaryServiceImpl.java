package cn.thinkjoy.zgk.zgksystem.service.dataDictionary.impl;

import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXDataDictionaryDAO;
import cn.thinkjoy.zgk.zgksystem.domain.Area;
import cn.thinkjoy.zgk.zgksystem.domain.School;
import cn.thinkjoy.zgk.zgksystem.service.dataDictionary.IDataDictionaryService;
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

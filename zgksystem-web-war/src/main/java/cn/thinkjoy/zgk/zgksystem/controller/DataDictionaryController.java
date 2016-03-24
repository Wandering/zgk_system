package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.domain.*;
import cn.thinkjoy.zgk.zgksystem.service.dataDictionary.IDataDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhwang on 15/10/19.
 */
@Controller
@RequestMapping("/system/dataDictionary")
public class DataDictionaryController {
    @Autowired
    private IDataDictionaryService iDataDictionaryService;

    /**
     * 按照市ID获取市下的所
     * @param request
     * @return
     */
    @RequestMapping(value = "findAreaList",method = RequestMethod.GET)
    @ResponseBody
    public List<Area> findAreaList(HttpServletRequest request){
        List<Area> areas = iDataDictionaryService.findAreaList();
        return areas;
    }

    @RequestMapping(value = "findProvinceList",method = RequestMethod.GET)
    @ResponseBody
    public List<Province> findProvinceList(){
        return iDataDictionaryService.findProvinceList();
    }
    @RequestMapping(value = "findCityList",method = RequestMethod.GET)
    @ResponseBody
    public List<City> findCityList(HttpServletRequest request){
        String provinceId = request.getParameter("provinceId");
        if(StringUtils.isBlank(provinceId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        return iDataDictionaryService.findCityList(Long.valueOf(provinceId));
    }
    @RequestMapping(value = "findCountyList",method = RequestMethod.GET)
    @ResponseBody
    public List<County> findCountyList(HttpServletRequest request){
        String cityId = request.getParameter("cityId");
        if(StringUtils.isBlank(cityId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        return iDataDictionaryService.findCountyList(Long.valueOf(cityId));
    }

    /**
     * 根据区域id获取学校列表
     * @param request
     * @return
     */
    @RequestMapping(value = "findSchoolList",method = RequestMethod.GET)
    @ResponseBody
    public List<School> findSchoolList(HttpServletRequest request){
        String areaId = request.getParameter("areaId");
        if(StringUtils.isBlank(areaId)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        return iDataDictionaryService.findSchoolList(Long.valueOf(areaId));
    }
}

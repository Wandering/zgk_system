package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.domain.Area;
import cn.thinkjoy.zgk.zgksystem.domain.School;
import cn.thinkjoy.zgk.zgksystem.service.dataDictionary.IDataDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

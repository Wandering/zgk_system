package cn.thinkjoy.zgk.zgksystem.service.market.impl;

import cn.thinkjoy.zgk.zgksystem.dao.IMarketParmasDao;
import cn.thinkjoy.zgk.zgksystem.domain.MarketParmas;
import cn.thinkjoy.zgk.zgksystem.service.market.IMarketParmasService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by douzy on 16/3/31.
 */
@Service("MarketParmasServiceImpl")
public class MarketParmasServiceImpl implements IMarketParmasService {
    @Resource
    IMarketParmasDao iMarketParmasDao;

    /**
     * 获取分成参数
     * @param map
     * @return
     */
    @Override
    public MarketParmas getMarketParmas(Map map) {
        return iMarketParmasDao.selectModel(map);
    }
}

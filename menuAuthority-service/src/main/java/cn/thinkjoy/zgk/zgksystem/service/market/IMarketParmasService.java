package cn.thinkjoy.zgk.zgksystem.service.market;

import cn.thinkjoy.zgk.zgksystem.domain.MarketParmas;

import java.util.Map;

/**
 * Created by douzy on 16/3/31.
 */
public interface IMarketParmasService {

    /**
     * 获取分成参数
     * @param map
     * @return
     */
    MarketParmas getMarketParmas(Map map);
}

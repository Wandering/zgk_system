package cn.thinkjoy.zgk.zgksystem.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.zgk.zgksystem.domain.MarketParmas;

import java.util.Map;

/**
 * Created by douzy on 16/3/31.
 */
public interface IMarketParmasDao extends IBaseDAO<MarketParmas> {

    MarketParmas selectModel(Map map);
}

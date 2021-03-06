package cn.thinkjoy.zgk.zgksystem.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.zgk.zgksystem.domain.SplitPrice;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/31.
 */
public interface ISplitPriceDAO extends IBaseDAO<SplitPrice> {

    List<SplitPrice> selectSplitPriceList(Map map);
}

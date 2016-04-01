package cn.thinkjoy.zgk.zgksystem.service.market;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.zgk.zgksystem.domain.SplitPrice;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/31.
 */
public interface ISplitPriceService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
    List<SplitPrice> getSplitPriceList(Map map);
}

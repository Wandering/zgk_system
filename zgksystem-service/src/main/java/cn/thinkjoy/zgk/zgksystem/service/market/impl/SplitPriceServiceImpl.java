package cn.thinkjoy.zgk.zgksystem.service.market.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.zgk.zgksystem.dao.ISplitPriceDAO;
import cn.thinkjoy.zgk.zgksystem.domain.SplitPrice;
import cn.thinkjoy.zgk.zgksystem.service.market.ISplitPriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/31.
 */
@Service("SplitPriceServiceImpl")
public class SplitPriceServiceImpl extends AbstractPageService<IBaseDAO<SplitPrice>, SplitPrice> implements ISplitPriceService<IBaseDAO<SplitPrice>,SplitPrice> {

    @Resource
    ISplitPriceDAO iSplitPriceDAO;

    @Override
    public IBaseDAO<SplitPrice> getDao() {
        return iSplitPriceDAO;
    }

    @Override
    public List<SplitPrice> getSplitPriceList(Map map){
         return  iSplitPriceDAO.selectSplitPriceList(map);
    }
}

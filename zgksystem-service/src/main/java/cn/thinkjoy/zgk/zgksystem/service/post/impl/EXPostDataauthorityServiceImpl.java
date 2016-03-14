package cn.thinkjoy.zgk.zgksystem.service.post.impl;

import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXPostDataauthorityDAO;
import cn.thinkjoy.zgk.zgksystem.service.post.IEXPostDataauthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yhwang on 15/10/20.
 */
@Service("EXPostDataauthorityServiceImpl")
public class EXPostDataauthorityServiceImpl implements IEXPostDataauthorityService{
   @Autowired
   private IEXPostDataauthorityDAO iexPostDataauthorityDAO;
    /**
     * 根据postCode删除岗位数据权限表
     *
     * @param postCode
     */
    @Override
    public void deleteByPost(Long postCode) {
        iexPostDataauthorityDAO.deleteByPost(postCode);
    }
}

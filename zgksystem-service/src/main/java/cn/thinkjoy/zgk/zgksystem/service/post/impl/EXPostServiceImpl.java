package cn.thinkjoy.zgk.zgksystem.service.post.impl;

import cn.thinkjoy.zgk.zgksystem.dao.IK12systemPostDAO;
import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXPostDAO;
import cn.thinkjoy.zgk.zgksystem.domain.Post;
import cn.thinkjoy.zgk.zgksystem.service.post.IEXPostService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by yhwang on 15/10/25.
 */
@Service("EXPostServiceImpl")
public class EXPostServiceImpl implements IEXPostService{
    @Autowired
    private IEXPostDAO iexPostDAO;
    @Autowired
    private IK12systemPostDAO ik12systemPostDAO;
    /**
     * 查询岗位
     *
     * @param companyCode
     * @return
     */
    @Override
    public List<Post> queryPostBycomPanyCode(Long companyCode,Integer offset,Integer rows) {
        return iexPostDAO.queryPostBycomPanyCode(companyCode,offset,rows);
    }

    /**
     * 岗位数量
     *
     * @param companyCode
     * @return
     */
    @Override
    public Integer countPostBycomPanyCode(Long companyCode) {
        return iexPostDAO.countPostBycomPanyCode(companyCode);
    }

    @Override
    public List<Post> queryPostByCreator(String creator,Integer offset,Integer rows){
        return iexPostDAO.queryPostByCreator(creator,offset,rows);
    }

    @Override
    public Integer countPostByCreator(String creator) {
        return iexPostDAO.countPostByCreator(creator);
    }

}

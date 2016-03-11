package cn.thinkjoy.jx.k12system.service.post.impl;

import cn.thinkjoy.jx.k12system.dao.IK12systemPostDAO;
import cn.thinkjoy.jx.k12system.dao.ex.IEXPostDAO;
import cn.thinkjoy.jx.k12system.domain.Post;
import cn.thinkjoy.jx.k12system.service.post.IEXPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}

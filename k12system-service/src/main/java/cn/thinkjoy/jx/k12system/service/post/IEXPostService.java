package cn.thinkjoy.jx.k12system.service.post;

import cn.thinkjoy.jx.k12system.domain.Post;

import java.util.List;

/**
 * Created by yhwang on 15/10/25.
 */
public interface IEXPostService {
    /**
     * 查询岗位
     * @param companyCode
     * @return
     */
    List<Post> queryPostBycomPanyCode(Long companyCode,Integer offset,Integer rows);

    /**
     * 岗位数量
     * @param companyCode
     * @return
     */
    Integer countPostBycomPanyCode(Long companyCode);

}

package cn.thinkjoy.zgk.zgksystem.service.post;

import cn.thinkjoy.zgk.zgksystem.domain.Post;

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

    List<Post> queryPostByCreator(String creator,Integer offset,Integer rows);
    Integer countPostByCreator(String creator);

}

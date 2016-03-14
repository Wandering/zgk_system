package cn.thinkjoy.zgk.zgksystem.dao.ex;

import cn.thinkjoy.zgk.zgksystem.domain.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/10/25.
 */
public interface IEXPostDAO {
    /**
     * 查询岗位
     * @param companyCode
     * @return
     */
    List<Post> queryPostBycomPanyCode(@Param("companyCode") Long companyCode,@Param("offset")Integer offset,@Param("rows")Integer rows);

    /**
     * 岗位数量
     * @param companyCode
     * @return
     */
    Integer countPostBycomPanyCode(@Param("companyCode") Long companyCode);
}

package cn.thinkjoy.zgk.zgksystem.service.post;

import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.domain.Post;

import java.util.List;
import java.util.Map;

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

    Page<Post> queryPost(String currentPageNo, String pageSize, String departmentCode);

    Post getPost(String postId);

    /**
     * 获取岗位Code和岗位名称
     * @param departmentCode
     * @return
     */
    Map<String,String> queryComboxPost(String departmentCode);


    /**
     * 获取管理岗位
     * @param postCode
     * @return
     */
    Page<Post> getManagerPost(Long postCode);

    /**
     * 根据代理商ID修改部门名称
     *
     * @param departmentId
     * @param departmentName
     */
    void updatePostNameByDeparntmentId(long departmentId,String departmentName);

}

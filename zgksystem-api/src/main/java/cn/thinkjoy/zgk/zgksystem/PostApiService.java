package cn.thinkjoy.zgk.zgksystem;

import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.domain.K12systemPost;
import cn.thinkjoy.zgk.zgksystem.domain.Post;

/**
 * Created by yhwang on 15/10/23.
 */
public interface PostApiService {
    /**
     * 给岗位分配平台权限
     * @param postCode
     * @param systemCode
     */
    void postSystemAuthority(Long postCode,Long systemCode);

    /**
     * 获取管理岗位
     * @param postCode
     * @return
     */
    Page<Post> getManagerPost(Long postCode);

    /**
     * 根据用户部门code获取部门树
     * @param currentPageNo
     * @param pageSize
     * @param departmentCode
     * @return
     */
    Page<Post> queryPost(String currentPageNo,String pageSize,String departmentCode);

    /**
     * 通过公司code查询岗位
     * @param currentPageNo
     * @param pageSize
     * @param companyCode
     * @return
     */
    Page<Post> queryPostBycompanyCode(String currentPageNo,String pageSize,Long companyCode);

    Page<Post> queryPostByCreator(String currentPageNo,String pageSize,String companyCode);

    K12systemPost queryK12systemPost(Long postCode, Long systemCode);
}

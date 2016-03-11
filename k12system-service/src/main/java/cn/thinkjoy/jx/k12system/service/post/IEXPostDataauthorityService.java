package cn.thinkjoy.jx.k12system.service.post;

/**
 * Created by yhwang on 15/10/20.
 */

public interface IEXPostDataauthorityService {
    /**
     * 根据postCode删除岗位数据权限表
     * @param postCode
     */
    void deleteByPost(Long postCode);
}

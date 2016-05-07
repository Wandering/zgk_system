package cn.thinkjoy.zgk.zgksystem.service.apiImpl;

import cn.thinkjoy.zgk.zgksystem.PostApiService;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.domain.K12systemPost;
import cn.thinkjoy.zgk.zgksystem.domain.Post;
import cn.thinkjoy.zgk.zgksystem.service.post.IEXPostService;
import cn.thinkjoy.zgk.zgksystem.service.post.IPostService;
import cn.thinkjoy.zgk.zgksystem.service.system.IK12systemPostService;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by yhwang on 15/10/23.
 */
@Service("PostApiServiceImpl")
public class PostApiServiceImpl implements PostApiService {
    @Autowired
    private IK12systemPostService ik12systemPostService;

    @Autowired
    private IEXPostService iexPostService;
    /**
     * 给岗位分配平台权限
     *
     * @param postCode
     * @param systemCode
     */
    @Override
    public void postSystemAuthority(Long postCode, Long systemCode) {
        K12systemPost k12systemPost = new K12systemPost();
        k12systemPost.setPostCode(postCode);
        k12systemPost.setSystemCode(systemCode);
        k12systemPost.setStatus(Constants.NORMAL_STATUS);
        ik12systemPostService.updateOrSave(k12systemPost,null);
    }
    /**
     * 获取管理岗位
     * @param postCode
     * @return
     */
    public Page<Post> getManagerPost(Long postCode){
        return  iexPostService.getManagerPost(postCode);
    }
    /**
     * 根据用户部门code获取部门树
     * @param currentPageNo
     * @param pageSize
     * @param departmentCode
     * @return
     */
    public Page<Post> queryPost(String currentPageNo,String pageSize,String departmentCode){
       return iexPostService.queryPost(currentPageNo,pageSize,departmentCode);
    }

    /**
     * 通过公司code查询岗位
     *
     * @param currentPageNo
     * @param pageSize
     * @param companyCode
     * @return
     */
    @Override
    public Page<Post> queryPostBycompanyCode(String currentPageNo, String pageSize, Long companyCode) {
        Page<Post> postPage = new Page<>();
        postPage.setList(iexPostService.queryPostBycomPanyCode(companyCode,(Integer.parseInt(currentPageNo)-1)*Integer.parseInt(pageSize),Integer.parseInt(pageSize)));
        postPage.setCount(iexPostService.countPostBycomPanyCode(companyCode));
        return postPage;
    }

    @Override
    public Page<Post> queryPostByCreator(String currentPageNo, String pageSize, String creator) {
        Page<Post> postPage = new Page<>();
        postPage.setList(iexPostService.queryPostByCreator(creator, (Integer.parseInt(currentPageNo) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize)));
        postPage.setCount(iexPostService.countPostByCreator(creator));
        return postPage;
    }

    @Override
    public K12systemPost queryK12systemPost(Long postCode, Long systemCode){
        Map<String, Object> condition = Maps.newHashMap();
        condition.put("postCode", postCode);
        condition.put("systemCode", systemCode);
        K12systemPost k12systemPost = (K12systemPost) ik12systemPostService.queryOne(condition);
        return k12systemPost;
    }


}

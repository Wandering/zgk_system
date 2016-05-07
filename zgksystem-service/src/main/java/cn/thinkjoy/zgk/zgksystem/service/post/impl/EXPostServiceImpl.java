package cn.thinkjoy.zgk.zgksystem.service.post.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.dao.IK12systemPostDAO;
import cn.thinkjoy.zgk.zgksystem.dao.IPostDAO;
import cn.thinkjoy.zgk.zgksystem.dao.ex.IEXPostDAO;
import cn.thinkjoy.zgk.zgksystem.domain.Post;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.post.IEXPostService;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import cn.thinkjoy.zgk.zgksystem.util.IdentityUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by yhwang on 15/10/25.
 */
@Service("EXPostServiceImpl")
public class EXPostServiceImpl implements IEXPostService{

    @Autowired
    private IEXPostDAO iexPostDAO;

    @Autowired
    private IPostDAO postDAO;

    @Autowired
    private IUserInfoService userInfoService;

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

    public Page<Post> queryPost(String currentPageNo, String pageSize, String departmentCode){
        if(StringUtils.isBlank(departmentCode)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String,Object> dataMap=new HashMap<>();
        Map<String,Object> statusMap=new HashMap<String,Object>();
        Map<String,Object> departmentMap=new HashMap<String,Object>();
        departmentMap.put("op"," = ");
        departmentMap.put("data",Long.parseLong(departmentCode));
        statusMap.put("op"," = ");
        statusMap.put("data", Constants.NORMAL_STATUS);
        dataMap.put("groupOp", " AND ");
        dataMap.put("status", statusMap);
        dataMap.put("departmentCode",departmentMap);
        int count=postDAO.count(dataMap);
        if(count>0){
            List<Post> postList = postDAO.queryPage(dataMap, ((Integer.valueOf(currentPageNo) - 1) * Integer.valueOf(pageSize)), Integer.valueOf(pageSize), CodeFactoryUtil.ORDER_BY_FIELD, "desc");
            Page<Post> page=new Page<>();
            page.setCount(count);
            page.setList(postList);
            return page;
        }else{
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
    }

    public Post getPost(String postId){
        if (StringUtils.isBlank(postId)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", Long.parseLong(postId));
        dataMap.put("status", Constants.NORMAL_STATUS);
        Post post = postDAO.queryOne(dataMap,null,null);
        if (post == null) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(), ERRORCODE.NO_MESSAGE.getMessage());
        }
        return post;
    }


    public Map<String,String> queryComboxPost(String departmentCode){
        if(StringUtils.isBlank(departmentCode)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Map<String, String> resultMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("status",Constants.NORMAL_STATUS);
        dataMap.put("departmentCode",departmentCode);
        List<Post> postList = postDAO.queryList(dataMap, CodeFactoryUtil.ORDER_BY_FIELD, SqlOrderEnum.DESC.getAction());
        for(Post p :postList){
            Map<String,Object> dataMap1 = new HashMap<>();
            dataMap1.put("status",Constants.NORMAL_STATUS);
            dataMap1.put("postCode",p.getPostCode());
            if(userInfoService.queryOne(dataMap1)==null) {
                resultMap.put(p.getPostCode() + "", p.getPostName());
            }
        }
        if(resultMap.size()==0) {
            throw new BizException(ERRORCODE.NO_MESSAGE.getCode(),ERRORCODE.NO_MESSAGE.getMessage());
        }
        return resultMap;
    }

    public Page<Post> getManagerPost(Long postCode){
        if(postCode==null || postCode==0){
            postCode=-1L;
        }
        List<Post> list=new ArrayList<>();
        if(postCode== IdentityUtil.ADMIN_MANAGER_POST){
            Post productPost=new Post();
            productPost.setDepartmentCode((long) IdentityUtil.PRODUCT_MANAGER_DEPARTMENT);
            productPost.setPostCode((long) IdentityUtil.PRODUCT_MANAGER_POST);
            productPost.setPostName("产品管理员岗位");
            Post companyPost=new Post();
            companyPost.setDepartmentCode((long) IdentityUtil.COMPANY_MANAGER_DEPARTMENT);
            companyPost.setPostCode((long) IdentityUtil.COMPANY_MANAGER_POST);
            companyPost.setPostName("代理公司管理员岗位");
            list.add(productPost);
            list.add(companyPost);
            Page<Post> page=new Page<>();
            page.setCount(list.size());
            page.setList(list);
            return page;
        }else{
            throw  new BizException(ERRORCODE.NO_PERMISSION.getCode(),ERRORCODE.NO_PERMISSION.getMessage());
        }
    }

    @Override
    public void updatePostNameByDeparntmentId(long departmentId, String departmentName) {
        iexPostDAO.updatePostNameByDeparntmentId(departmentId,departmentName);
    }
}

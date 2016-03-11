package cn.thinkjoy.jx.k12system.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.jx.k12system.common.ERRORCODE;
import cn.thinkjoy.jx.k12system.common.HttpUtil;
import cn.thinkjoy.jx.k12system.common.Page;
import cn.thinkjoy.jx.k12system.domain.Post;
import cn.thinkjoy.jx.k12system.domain.PostDataauthority;
import cn.thinkjoy.jx.k12system.domain.UserAccount;
import cn.thinkjoy.jx.k12system.domain.UserInfo;
import cn.thinkjoy.jx.k12system.pojo.PostPojo;
import cn.thinkjoy.jx.k12system.pojo.UserPojo;
import cn.thinkjoy.jx.k12system.service.account.IUserAccountService;
import cn.thinkjoy.jx.k12system.service.account.IUserInfoService;
import cn.thinkjoy.jx.k12system.service.code.IEXCodeService;
import cn.thinkjoy.jx.k12system.service.post.IEXPostDataauthorityService;
import cn.thinkjoy.jx.k12system.service.post.IPostDataauthorityService;
import cn.thinkjoy.jx.k12system.service.post.IPostService;
import cn.thinkjoy.jx.k12system.util.CodeFactoryUtil;
import cn.thinkjoy.jx.k12system.util.Constants;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wangyongqiang on 15/9/1
 */
@Controller
@RequestMapping("/system/post")
public class PostController {
    @Autowired
    private IPostService postService;


    @Autowired
    private IEXCodeService excodeService;

    @Autowired
    private IUserInfoService userInfoService;//人员Service

    @Autowired
    private IUserAccountService userAccountService;//账户Service

    @Autowired
    private IPostDataauthorityService iPostDataauthorityService;

    @Autowired
    private IEXPostDataauthorityService iexPostDataauthorityService;
    /**
     * 新增和修改岗位
     *
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "addOrEditPost", method = RequestMethod.POST)
    public String addOrEditPost(HttpServletRequest request) {

        UserPojo user = (UserPojo) HttpUtil.getSession(request, "user");
        String postJson = request.getParameter("postJson");
        if (StringUtils.isBlank(postJson)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        PostPojo postPojo = null;
        try {
            postPojo = JsonMapper.buildNormalMapper().fromJson(postJson, PostPojo.class);
        }catch (Exception e){
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(), ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }

        if(postPojo == null ||postPojo.getAreaIds() == null || postPojo.getAreaIds().size() == 0){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
            Post post = new Post();
            if (postPojo.getId() == null || postPojo.getId() == 0) {
                post.setDepartmentCode(postPojo.getDepartmentCode());
                post.setPostName(postPojo.getPostName());
                post.setDescription(postPojo.getDescription());
                post.setSeqSort(postPojo.getSeqSort());
                post.setStatus(Constants.NORMAL_STATUS);
            Long maxPostCode = excodeService.selectMaxCodeByParent(CodeFactoryUtil.POSITION_CODE,CodeFactoryUtil.POSITION_TABLE,CodeFactoryUtil.DEPARTMENT_CODE,post.getDepartmentCode());
            if (maxPostCode == null || maxPostCode == 0) {
                maxPostCode = CodeFactoryUtil.getInitPosition(postPojo.getDepartmentCode()) ;
            } else {
                ++maxPostCode;
            }
                post.setPostCode(maxPostCode);
            postService.updateOrSave(post, null);
        } else {
            BeanUtils.copyProperties(postPojo,post);
            postService.updateOrSave(post, post.getId());
            iexPostDataauthorityService.deleteByPost(post.getPostCode());
        }
        if(postPojo.getSchoolIds() == null || postPojo.getSchoolIds().size() == 0){//说明是分部主任或者是耿杰薛璐等最高权限的人
            PostDataauthority postDataauthority = new PostDataauthority();
            postDataauthority.setAreaId(postPojo.getAreaIds().get(0));
            postDataauthority.setPostCode(post.getPostCode());
            iPostDataauthorityService.updateOrSave(postDataauthority,null);
        }else {//说明是客户经理
            for(Long schoolId:postPojo.getSchoolIds()){
                PostDataauthority postDataauthority = new PostDataauthority();
                postDataauthority.setPostCode(post.getPostCode());
                postDataauthority.setAreaId(postPojo.getAreaIds().get(0));
                postDataauthority.setSchoolId(schoolId);
                iPostDataauthorityService.updateOrSave(postDataauthority, null);
            }
        }
        return "ok";

    }


    /**
     * 删除岗位
     *
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "delPost", method = RequestMethod.GET)
    public String delPost(HttpServletRequest request) {
        String postId = request.getParameter("id");
        if (StringUtils.isBlank(postId)) {
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Post p = (Post) postService.findOne("id", postId);
        p.setStatus(Constants.DELETEED_STATUS);
        postService.update(p);
        Map<String,Object> dataUserInfoMap=new HashMap<>();
        dataUserInfoMap.put("postCode", p.getPostCode());
        dataUserInfoMap.put("status", Constants.NORMAL_STATUS);
        List<UserInfo> userInfoList= userInfoService.queryList(dataUserInfoMap,null,null);
        if(userInfoList!=null && userInfoList.size()>0){
            for (UserInfo u :userInfoList){
                u.setStatus(Constants.DELETEED_STATUS);
                userInfoService.update(u);
                Map<String,Object> dataAccountMap=new HashMap<>();
                dataAccountMap.put("userCode", u.getUserCode());
                dataAccountMap.put("status", Constants.NORMAL_STATUS);
                List<UserAccount> userAccounts= userAccountService.queryList(dataAccountMap,null,null);
                if(userAccounts!=null&& userAccounts.size()>0){
                    for (UserAccount ua:userAccounts){
                        ua.setStatus(Constants.DELETEED_STATUS);
                        userAccountService.update(ua);
                    }
                }
            }
        }
        iexPostDataauthorityService.deleteByPost(p.getPostCode());//删除岗位数据关系表

        return "ok";
    }

    /**
     * 查询岗位
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryPost", method = RequestMethod.GET)
    public Page<Post> queryPost(HttpServletRequest request) {
        String currentPageNo = HttpUtil.getParameter(request, "currentPageNo", "1");
        String pageSize = HttpUtil.getParameter(request, "pageSize", "10");
        String departmentCode=request.getParameter("departmentCode");
       return  postService.queryPost(currentPageNo,pageSize,departmentCode);
    }

    /**
     * 获取单个岗位
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getPost", method = RequestMethod.GET)
    public Post getPost(HttpServletRequest request) {
        String postId = request.getParameter("id");
        Post post = postService.getPost(postId);
        List<PostDataauthority> postDataauthorities = iPostDataauthorityService.findList("postCode", post.getPostCode());
        PostPojo postPojo = new PostPojo();
        BeanUtils.copyProperties(post,postPojo);
        List<Long> areaIds = new ArrayList<>();
        List<Long> schoolIds = new ArrayList<>();
        if(postDataauthorities != null){
            for(PostDataauthority postData:postDataauthorities){
                if(postData.getSchoolId() == null || postData.getSchoolId() == 0){
                    areaIds.add(postData.getAreaId());
                }else {
                    int i = 0;
                    if(i == 0){
                        areaIds.add(postData.getAreaId());
                    }
                    i++;
                    schoolIds.add(postData.getSchoolId());
                }
            }
        }
        postPojo.setSchoolIds(schoolIds);
        postPojo.setAreaIds(areaIds);

        return postPojo;
    }

    /**
     * 获取岗位Code和岗位名称
     *
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "queryComboxPost",method = RequestMethod.GET)
    public Map<String,String> queryComboxPost(HttpServletRequest request){
        String departmentCode= request.getParameter("departmentCode");
        return  postService.queryComboxPost(departmentCode);
    }

    /**
     *获取管理岗位
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getManagerPost",method = RequestMethod.GET)
    public Page<Post> getManagerPost(HttpServletRequest request){
        Long postCode=Long.valueOf(HttpUtil.getParameter(request, "postCode", "-1"));
       return  postService.getManagerPost(postCode);
    }

}

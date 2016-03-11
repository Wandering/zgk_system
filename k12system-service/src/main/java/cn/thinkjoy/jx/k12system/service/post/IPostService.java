/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  PostService.java 2015-08-31 11:11:29 $
 */

package cn.thinkjoy.jx.k12system.service.post;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.jx.k12system.common.Page;
import cn.thinkjoy.jx.k12system.domain.Post;

import java.util.Map;

public interface IPostService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{

    Page<Post> queryPost(String currentPageNo,String pageSize,String departmentCode);

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

}

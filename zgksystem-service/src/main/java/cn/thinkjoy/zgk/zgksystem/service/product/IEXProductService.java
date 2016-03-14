/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  ProductService.java 2015-08-31 11:11:29 $
 */

package cn.thinkjoy.zgk.zgksystem.service.product;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.Product;
import cn.thinkjoy.zgk.zgksystem.pojo.ProductPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;

import java.util.List;
import java.util.Map;

public interface IEXProductService{
    /**
     * 分页获取产品
     * @param map
     * @param offset
     * @param rows
     * @param orderBy
     * @param sortBy
     * @return
     */
    Page<Product> queryByPage(Map<String,Object> map,int offset,int rows,String orderBy,String sortBy);

    /**
     * 根据用户获取产品树
     * @param userPojo
     * @return
     */
    List<TreeBean> queryTreeProduct(UserPojo userPojo);

    /**
     * 查询产品
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    Page<ProductPojo> queryProduct(int currentPageNo ,int pageSize);

    /**
     * 获取单个产品
     * @param productId
     * @return
     */
    Product getProduct(String productId);

    /**
     * 获取产品Code和产品名称
     * @return
     */
     Map<String,String> queryComboxProduct();

}

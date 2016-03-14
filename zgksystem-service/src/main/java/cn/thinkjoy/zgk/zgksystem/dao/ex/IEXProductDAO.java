/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  ProductDAO.java 2015-08-31 11:11:29 $
 */
package cn.thinkjoy.zgk.zgksystem.dao.ex;

import cn.thinkjoy.zgk.zgksystem.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IEXProductDAO {
    Integer totalCount(@Param("condition") Map<String,Object> var1 );

    List<Product> queryByPageAndCondition(@Param("condition") Map<String, Object> var1, @Param("offset") int var2, @Param("rows") int var3, @Param("orderBy") String var4, @Param("sortBy") String var5);

}

/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  ProductDAO.java 2015-08-31 11:11:29 $
 */
package cn.thinkjoy.zgk.zgksystem.dao.ex;

import java.util.Map;

public interface IEXUserAccountDAO {
    Map<String, Object> queryUserInfo(Map<String,String> paramMap);
}

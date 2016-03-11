/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Post.java 2015-09-01 11:47:24 $
 */



package cn.thinkjoy.jx.k12system.pojo;


import cn.thinkjoy.jx.k12system.domain.Post;
import java.util.List;

public class PostPojo extends Post{
    private List<Long> areaIds;
    private List<Long> schoolIds;

    public List<Long> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Long> areaIds) {
        this.areaIds = areaIds;
    }

    public List<Long> getSchoolIds() {
        return schoolIds;
    }

    public void setSchoolIds(List<Long> schoolIds) {
        this.schoolIds = schoolIds;
    }
}


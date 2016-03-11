package cn.thinkjoy.jx.k12system.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/10.
 */
public class MenuPojo implements Serializable{

    private Long menuId;//菜单ID

    private Long menuCode;//菜单CODE

    private Long parentCode;//父菜单CODE

    private String menuName;//菜单名称

    private String menuUrl;//菜单链接，当parentCode 为-1时menuUrl为空

    private String menuIcon;//菜单标识

    private Integer seqSort;//排序

    private String description;//描述

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Long menuCode) {
        this.menuCode = menuCode;
    }

    public Long getParentCode() {
        return parentCode;
    }

    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getSeqSort() {
        return seqSort;
    }

    public void setSeqSort(Integer seqSort) {
        this.seqSort = seqSort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

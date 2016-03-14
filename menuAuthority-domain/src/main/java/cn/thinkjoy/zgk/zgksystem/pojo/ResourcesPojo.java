package cn.thinkjoy.zgk.zgksystem.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/15.
 */
public class ResourcesPojo implements Serializable{
    private Long resourceId;//资源ID
    private Long resourceCode;//资源code
    private Long menuCode;//菜单code
    private String resourceName;//资源名称
    private String resourceUrl;//资源URL
    private String resourceIcon;//资源ICON
    private Integer seqSort;
    private String description;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(Long resourceCode) {
        this.resourceCode = resourceCode;
    }

    public Long getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(Long menuCode) {
        this.menuCode = menuCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
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

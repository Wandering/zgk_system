package cn.thinkjoy.jx.k12system.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangyongqiang on 15/9/9.
 */
public class TreeBean implements Serializable {

    private  Long id;//树CODE 这里放的是业务code

    private  String name;//名称

    private String leafIcon;
    private String leafUrl;
    private Boolean isParent;//是否为父节点
    private Boolean isChecked;//是否被选中  用于修改
    private Boolean isResources=Boolean.FALSE; //是否是资源 1、是，0否
    private  List<TreeBean> children;

    public TreeBean() {
    }

    public TreeBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TreeBean(Long id, String name, List<TreeBean> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }
    public TreeBean(Long id, String name,String leafIcon, List<TreeBean> children) {
        this.id = id;
        this.name = name;
        this.leafIcon = leafIcon;
        this.children = children;
    }
    public TreeBean(Long id, String name,String leafIcon,String leafUrl) {
        this.id = id;
        this.name = name;
        this.leafIcon = leafIcon;
        this.leafUrl = leafUrl;
    }
    public TreeBean(Long id, String name,String leafIcon,Boolean isResources) {
        this.id = id;
        this.name = name;
        this.leafIcon = leafIcon;
        this.isResources = isResources;
    }
    public TreeBean(Long id, String name,String leafIcon,String leafUrl, List<TreeBean> children) {
        this.id = id;
        this.name = name;
        this.leafIcon = leafIcon;
        this.leafUrl = leafUrl;
        this.children = children;
    }

    public TreeBean(Long id, String name, Boolean isParent) {
        this.id = id;
        this.name = name;
        this.isParent = isParent;
    }
    public TreeBean(Long id, String name, Boolean isParent,List<TreeBean> children) {
        this.id = id;
        this.name = name;
        this.isParent = isParent;
        this.children = children;
    }
    public TreeBean(Long id, String name, Boolean isParent, Boolean isChecked,List<TreeBean> children) {
        this.id = id;
        this.name = name;
        this.isParent = isParent;
        this.isChecked = isChecked;
        this.children = children;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeBean> getChildren() {
        return children;
    }

    public void setChildren(List<TreeBean> children) {
        this.children = children;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getLeafIcon() {
        return leafIcon;
    }

    public void setLeafIcon(String leafIcon) {
        this.leafIcon = leafIcon;
    }

    public String getLeafUrl() {
        return leafUrl;
    }

    public void setLeafUrl(String leafUrl) {
        this.leafUrl = leafUrl;
    }

    public Boolean getIsResources() {
        return isResources;
    }

    public void setIsResources(Boolean isResources) {
        this.isResources = isResources;
    }
}
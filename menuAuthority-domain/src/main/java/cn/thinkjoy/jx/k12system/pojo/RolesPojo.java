package cn.thinkjoy.jx.k12system.pojo;

import cn.thinkjoy.jx.k12system.common.TreeBean;
import java.util.List;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/16.
 */
public class RolesPojo implements Serializable{
    private Long rolesId;//角色ID
    private Long roleCode;//角色code
    private Long parentCode;//父角色code
    private String roleName;//角色名称
    private Integer seqSort;//排序
    private String description;
    private List<TreeBean> treeBeans;//菜单资源树
    private List<Long> menuCodeList;//菜单code列表
    private List<Long> resourceList;//资源code列表

    public Long getRolesId() {
        return rolesId;
    }

    public void setRolesId(Long rolesId) {
        this.rolesId = rolesId;
    }

    public Long getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Long roleCode) {
        this.roleCode = roleCode;
    }

    public Long getParentCode() {
        return parentCode;
    }

    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public List<TreeBean> getTreeBeans() {
        return treeBeans;
    }

    public void setTreeBeans(List<TreeBean> treeBeans) {
        this.treeBeans = treeBeans;
    }

    public List<Long> getMenuCodeList() {
        return menuCodeList;
    }

    public void setMenuCodeList(List<Long> menuCodeList) {
        this.menuCodeList = menuCodeList;
    }

    public List<Long> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Long> resourceList) {
        this.resourceList = resourceList;
    }
}

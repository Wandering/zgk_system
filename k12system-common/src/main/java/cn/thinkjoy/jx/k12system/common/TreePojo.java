package cn.thinkjoy.jx.k12system.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yhwang on 15/9/8.
 */
public class TreePojo implements Serializable{

    private List<TreeBean>  treeBeanList;

    private String treeName;

    private String desciption;

    public List<TreeBean> getTreeBeanList() {
        return treeBeanList;
    }

    public void setTreeBeanList(List<TreeBean> treeBeanList) {
        this.treeBeanList = treeBeanList;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}

package cn.thinkjoy.zgk.zgksystem.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Created by thinkjoy on 15/6/16.
 */
public class Page<T> implements Serializable {
    private Map<String,Object>  queryMap;// 查询参数
    private List<T> list;//数据List
    private Integer count;//总记录数
    public List<T> getList() {
        return list;
    }

    public Map<String, Object> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, Object> queryMap) {
        this.queryMap = queryMap;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}

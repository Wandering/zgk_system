package cn.thinkjoy.zgk.zgksystem.service.system;

import cn.thinkjoy.zgk.zgksystem.pojo.K12SystemPojo;
import java.util.List;

/**
 * Created by yhwang on 15/9/1.
 */
public interface IEXK12SystemService {
    /**
     * 根据岗位查询系统列表
     * @param postCode
     * @return
     */
    List<K12SystemPojo>  getSystemListByPost(Long postCode);

}

package cn.thinkjoy.zgk.zgksystem.util;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 短信验证码缓存
 *
 * key value 格式自定义
 *
 * @author yhwang
 *
 */
@Service
public class CacheService {

    @Autowired
    RedisRepository<String, String> redis;

    private static final String K12SYSTEM_STR_FORMAT = "k12system.%s";
    /**
     * 验证码超时时间 单位 小时
     */
    public static final int   expireTime  = 24;

    /**
     * 添加或替换缓存内容
     *
     */

    public void addCache(String key, String value) {

        String realKey = String.format(K12SYSTEM_STR_FORMAT,key);

        this.delKey(realKey);
        redis.set(realKey, value);
        redis.expire(realKey, expireTime, TimeUnit.HOURS);

    }

    /**
     * 获取缓存中key对应的值
     *
     * @return
     */
    public String getValue(String key) {

        String realKey = String.format(K12SYSTEM_STR_FORMAT,key);
        return redis.get(realKey);
    }
    /**
     * 删除缓存中的key
     *
     */
    public void delKey(String  key) {
        String realKey = String.format(K12SYSTEM_STR_FORMAT,key);
        redis.del(realKey);
    }
    public boolean isTimeOut(String key) {
        String realKey = String.format(K12SYSTEM_STR_FORMAT,key);
        boolean back = false;
        back = redis.get(realKey) == null;
        return back;
    }
}

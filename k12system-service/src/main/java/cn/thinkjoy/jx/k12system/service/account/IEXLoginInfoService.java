package cn.thinkjoy.jx.k12system.service.account;

/**
 * Created by yhwang on 15/9/21.
 */
public interface IEXLoginInfoService {
    /**
     * 根据token和当前时间获取用户code
     * @param token
     * @param nowTime
     * @return
     */
    Long getAccountCodeByToken(String token ,long nowTime);
}

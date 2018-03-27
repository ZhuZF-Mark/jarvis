package com.jarvis.user.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存工具类
 * Created by ZZF on 2018/3/22.
 */
@Component
public class RedisTemplateUtils {
    @Resource(name = "redisTemplate")
    RedisTemplate<String, Object> redisTemplate;
    /** * 获取缓存的地址
     * @param cacheKey cacheKey
     * @return String
     */
    public Object getCacheValue(String cacheKey){
        Object cacheValue=redisTemplate.opsForValue().get(cacheKey);
        return cacheValue;
    }

    /**
      * 设置缓存值
     * @param key key
     * @param value value
     */
    public void setCacheValue(String key,Object value){
        redisTemplate.opsForValue().set(key, value);
    }
    /**
     * 设置缓存值并设置有效期
     * @param key key
     * @param value value
     */
    public void setCacheValueForTime(String key,Object value,long time,TimeUnit unit){
        redisTemplate.opsForValue().set(key, value, time, unit);
    }
    /**
     * 删除key值
     * @param key key
     */
    public void delCacheByKey(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
        redisTemplate.opsForHash().delete("");
    }
    /**
     * 获取token的有效期
     * @param  key key
     */
    public long getExpireTime(String key){
        long time = redisTemplate.getExpire(key);
        return time;
    }
    /**
     * 指定时间类型---秒
     * @param key key
     * @return 秒
     */
    public long getExpireTimeType(String key){
        long time = redisTemplate.getExpire(key,TimeUnit.SECONDS);
        return time;
    }
    /**
     * @param key---分
     * @return 分
     */
    public long getExpireTimeTypeForMin(String key){
        long time = redisTemplate.getExpire(key,TimeUnit.MINUTES);
        return time;
    }
    /**
     * * 设置一个自增的数据
     * * @param key
     * * @param growthLength
     * */
    public void testInc(String key,Long growthLength){
        redisTemplate.opsForValue().increment(key, growthLength);
    }


}

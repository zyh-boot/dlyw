package com.cx.monitor.service;

import com.cx.monitor.entity.RedisInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator·
 */
public interface IRedisService {

    /**
     * 获取 redis 的详细信息
     * @return
     * @
     */
    List<RedisInfo> getRedisInfo() ;

    /**
     * 获取 redis key 数量
     *
     * @return Map
     * @
     */
    Map<String, Object> getKeysSize() ;

    /**
     * 获取 redis 内存信息
     *
     * @return Map
     * @
     */
    Map<String, Object> getMemoryInfo() ;

    /**
     * 获取 key
     *
     * @param pattern 正则
     * @return Set
     * @
     */
    Set<String> getKeys(String pattern) ;

    /**
     * get命令
     *
     * @param key key
     * @return String
     * @
     */
    String get(String key) ;

    /**
     * set命令
     *
     * @param key   key
     * @param value value
     * @return String
     * @
     */
    String set(String key, String value) ;

    /**
     * set 命令
     *
     * @param key         key
     * @param value       value
     * @param milliscends 毫秒
     * @return String
     * @
     */
    String set(String key, String value, Long milliscends) ;

    /**
     * del命令
     *
     * @param key key
     * @return Long
     * @
     */
    Long del(String... key) ;

    /**
     * exists命令
     *
     * @param key key
     * @return Boolean
     * @
     */
    Boolean exists(String key) ;

    /**
     * pttl命令
     *
     * @param key key
     * @return Long
     * @
     */
    Long pttl(String key) ;

    /**
     * pexpire命令
     *
     * @param key         key
     * @param milliscends 毫秒
     * @return Long
     * @
     */
    Long pexpire(String key, Long milliscends) ;


    /**
     * zadd 命令
     *
     * @param key    key
     * @param score  score
     * @param member value
     * @return
     * @
     */
    Long zadd(String key, Double score, String member) ;

    /**
     * zrangeByScore 命令
     *
     * @param key key
     * @param min min
     * @param max max
     * @return Set<String>
     * @
     */
    Set<String> zrangeByScore(String key, String min, String max) ;

    /**
     * zremrangeByScore 命令
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return Long
     * @
     */
    Long zremrangeByScore(String key, String start, String end) ;

    /**
     * zrem 命令
     *
     * @param key     key
     * @param members members
     * @return Long
     * @
     */
    Long zrem(String key, String... members) ;
}

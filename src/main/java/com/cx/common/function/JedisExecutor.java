package com.cx.common.function;

import com.cx.common.exception.RedisConnectException;

/**
 * @author Administrator·
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    /**
     * redis
     *
     * @param t
     * @return
     * @throws RedisConnectException
     */
    R excute(T t) throws RedisConnectException;
}

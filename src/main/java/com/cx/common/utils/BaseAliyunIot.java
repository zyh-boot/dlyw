package com.cx.common.utils;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.RpcAcsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 阿里云物联网云平台基础类
 *
 * @author Administrator
 */
public class BaseAliyunIot {
    private static Logger LogUtil = LoggerFactory.getLogger(BaseAliyunIot.class);

    private static DefaultAcsClient client;

    static {
        client = IotClient.getClient();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static AcsResponse executeTest(RpcAcsRequest request) {
        AcsResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (Exception e) {
            LogUtil.debug("执行失败：e:" + e.getMessage());
        }
        return response;
    }
}

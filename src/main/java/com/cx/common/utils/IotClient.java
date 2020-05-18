package com.cx.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IotClient {
    private static Logger LogUtil = LoggerFactory.getLogger(IotClient.class);

    public static DefaultAcsClient getClient() {
        DefaultAcsClient client = null;
        try {
            IClientProfile profile = DefaultProfile.getProfile(IOTConfigUtil.IOT_RegionId, IOTConfigUtil.IOT_accessKeyID, IOTConfigUtil.IOT_accessKeySecret);
            DefaultProfile.addEndpoint(IOTConfigUtil.IOT_RegionId, IOTConfigUtil.IOT_RegionId, IOTConfigUtil.IOT_productCode,
                    IOTConfigUtil.IOT_domain);
            // 初始化client
            client = new DefaultAcsClient(profile);
        } catch (Exception e) {
            LogUtil.debug("初始化client失败！exception:" + e.getMessage());
        }

        return client;
    }

}

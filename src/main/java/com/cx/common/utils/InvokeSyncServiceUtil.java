package com.cx.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.iot.model.v20180120.InvokeThingServiceRequest;
import com.aliyuncs.iot.model.v20180120.InvokeThingServiceResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;


/**
 * 同步服务调用<br>   辅助系统下发命令道设备上，例如：修改阈值
 * https://help.aliyun.com/document_detail/69584.html
 */
public class InvokeSyncServiceUtil {
    // ===================需要用户填写的参数开始===========================
    private static String regionId = IOTConfigUtil.IOT_RegionId;
    // 用户账号AccessKey
    private static String accessKeyID = IOTConfigUtil.IOT_accessKeyID;
    // 用户账号AccesseKeySecret
    private static String accessKeySecret = IOTConfigUtil.IOT_accessKeySecret;
    // 产品productKey，执行服务的设备三元组之一
    private static String productKey = IOTConfigUtil.IOT_ProductKey;

    // ===================需要用户填写的参数结束===========================

    /**
     * 1、进料/滚筒服务设置同步服务
     * @param deviceName  设备名
     * @param set_frequence 设置频率
     * @param set_state   设置状态
     * @return
     */
    public static Boolean FeedSetService(String deviceName,String server_type, float set_frequence, int set_state) {
        Boolean bool = false;
        // 获取服务端请求客户端
        DefaultAcsClient client = null;
        try {
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyID, accessKeySecret);
            DefaultProfile.addEndpoint(regionId, regionId, "Iot", "iot." + regionId + ".aliyuncs.com");
            client = new DefaultAcsClient(profile);
            // 填充服务调用的参数
            InvokeThingServiceRequest request = new InvokeThingServiceRequest();
            request.setProductKey(productKey); // 设备三元组之productKey
            request.setDeviceName(deviceName); // 设备三元组之deviceName
            request.setIdentifier(server_type); // 要调用的服务标识符，取决于服务定义
            JSONObject json = new JSONObject(); // 构造服务入参，服务入参是一个JSON String
            json.put("set_frequence", set_frequence);
            json.put("set_state", set_state);
            request.setArgs(json.toString());
            // 获得服务调用响应
            InvokeThingServiceResponse response = client.getAcsResponse(request);
            if (response != null && response.getSuccess()) { // 服务调用成功，仅代表发送服务指令的成功，不代表执行服务本身是否成功
                String result = response.getData().getResult();
                if (result != null) {
                    JSONObject json1 = JSONObject.parseObject(result);
                    int res = (int) json1.get("SetReturn");
                    if (res == 0) {
                        bool = true;
                    }
                }
            }
        } catch (Exception e) {
            bool = false;
            System.out.println("create Open API Client failed !! exception:" + e.getMessage());
        }
        return bool;
    }


    /**
     * 1、加热设置同步服务
     * @param deviceName  设备名
     * @param set_state   设置状态
     * @return
     */
    public static Boolean Heart_set_service(String deviceName,int set_power,int work_type,int set_state,int set_target) {
        Boolean bool = false;
        // 获取服务端请求客户端
        DefaultAcsClient client = null;
        try {
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyID, accessKeySecret);
            DefaultProfile.addEndpoint(regionId, regionId, "Iot", "iot." + regionId + ".aliyuncs.com");
            client = new DefaultAcsClient(profile);
            // 填充服务调用的参数
            InvokeThingServiceRequest request = new InvokeThingServiceRequest();
            request.setProductKey(productKey); // 设备三元组之productKey
            request.setDeviceName(deviceName); // 设备三元组之deviceName
            request.setIdentifier("heart_set_service"); // 要调用的服务标识符，取决于服务定义
            JSONObject json = new JSONObject(); // 构造服务入参，服务入参是一个JSON String
            json.put("set_power", set_power);
            json.put("set_state", set_state);
            json.put("set_target", set_target);
            json.put("work_type", work_type);
            request.setArgs(json.toString());
            // 获得服务调用响应
            InvokeThingServiceResponse response = client.getAcsResponse(request);
            if (response != null && response.getSuccess()) { // 服务调用成功，仅代表发送服务指令的成功，不代表执行服务本身是否成功
                String result = response.getData().getResult();
                if (result != null) {
                    JSONObject json1 = JSONObject.parseObject(result);
                    int res = (int) json1.get("SetReturn");
                    if (res == 0) {
                        bool = true;
                    }
                }
            }
        } catch (Exception e) {
            bool = false;
            System.out.println("create Open API Client failed !! exception:" + e.getMessage());
        }
        return bool;
    }


}

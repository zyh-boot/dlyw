package com.cx.common.utils;

import com.aliyuncs.iot.model.v20170420.PubRequest;
import com.aliyuncs.iot.model.v20170420.PubResponse;
import com.aliyuncs.iot.model.v20180120.*;
import com.aliyuncs.iot.model.v20180120.QueryDeviceDetailResponse.Data;
import com.aliyuncs.iot.model.v20180120.QueryDeviceEventDataResponse.Data.EventInfo;
import com.aliyuncs.iot.model.v20180120.QueryDevicePropertyDataResponse.Data.PropertyInfo;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云接口调用工具类
 *
 * @author Administrator
 */
public class AliyunIotUtil extends BaseAliyunIot {
    private static Logger LogUtil = LoggerFactory.getLogger(AliyunIotUtil.class);

    /**
     * 注册设备
     *
     * @param productKey 产品PK 必需
     * @param deviceName 产品名称 非必需
     */
    public static String RegisterDevice(String productKey, String deviceName) {
        String str = null;
        RegisterDeviceRequest request = new RegisterDeviceRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        RegisterDeviceResponse response = (RegisterDeviceResponse) executeTest(request);
        if (response != null && response.getSuccess() != false) {
            str = response.getData().getDeviceSecret();
        }
        return str;

    }

    /**
     * 删除设备
     *
     * @param productKey 产品PK 必需
     * @param deviceName 产品名称 非必需
     */
    public static String DeleteDevice(String productKey, String deviceName) {
        String str = "0";
        DeleteDeviceRequest request = new DeleteDeviceRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        DeleteDeviceResponse response = (DeleteDeviceResponse) executeTest(request);
        if (response != null && response.getSuccess() != false) {
            str = "1";
        }
        return str;

    }

    /**
     * 调用该接口查询指定设备的详细信息。
     *
     * @param productKey 产品key
     * @param deviceName 设备名称
     * @return 设备的Data
     */
    public static Data queryDeviceDetail(String productKey, String deviceName) {

        QueryDeviceDetailRequest request = new QueryDeviceDetailRequest();
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        QueryDeviceDetailResponse response = (QueryDeviceDetailResponse) executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.debug("设备的详细信息！deviceKey:" + response.getData().getIotId());
            return response.getData();
        } else {
            LogUtil.error("设备的详细信息！requestId:" + response.getRequestId() + "原因:" + response.getErrorMessage());
        }
        return null;
    }


    /**
     * pub消息
     *
     * @param productKey pk
     * @param topic      topic
     * @param msg        消息内容
     */
    public static void pubTest(String productKey, String topic, String msg) {
        PubRequest request = new PubRequest();
        request.setProductKey(productKey);
        request.setTopicFullName(topic);
        request.setMessageContent(Base64.encodeBase64String(msg.getBytes()));
        request.setQos(1);
        PubResponse response = (PubResponse) executeTest(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.debug("发送消息成功！messageId：" + response.getMessageId());
        } else {
            LogUtil.error("发送消息失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
        }
    }

    /**
     * 设置设备属性值
     *
     * @param productKey
     * @param deviceName
     * @param items
     * @return
     */
    public static String SetDeviceProperty(String productKey, String deviceName, String items) {
        String str = "1";
        SetDevicePropertyRequest requestPR = new SetDevicePropertyRequest();
        requestPR.setProductKey(productKey);
        requestPR.setDeviceName(deviceName);
        requestPR.setItems(items);
        SetDevicePropertyResponse spResponse = (SetDevicePropertyResponse) executeTest(requestPR);
        if (spResponse != null && spResponse.getSuccess() != false) {
            str = "2";
        } else {
            LogUtil.error("发送消息失败！requestId:" + spResponse.getRequestId() + "原因：" + spResponse.getErrorMessage());
        }
        return str;
    }


    /**
     * 获取设备事件列表
     *
     * @param productKey
     * @param deviceName
     * @param Identifier
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<EventInfo> QueryDeviceEventData(String productKey, String deviceName, String Identifier, long startTime, long endTime) {
        List<EventInfo> list = new ArrayList<>();
        QueryDeviceEventDataRequest requestPR = new QueryDeviceEventDataRequest();
        requestPR.setProductKey(productKey);
        requestPR.setDeviceName(deviceName);
        requestPR.setIdentifier(Identifier);
        requestPR.setStartTime(startTime);
        requestPR.setEndTime(endTime);
        requestPR.setPageSize(20);
        requestPR.setAsc(0);
        QueryDeviceEventDataResponse spResponse = (QueryDeviceEventDataResponse) executeTest(requestPR);
        if (spResponse != null && spResponse.getSuccess() != false) {
            list = spResponse.getData().getList();
        }
        return list;
    }

    /**
     * 查询设备指定属性的历史数据
     *
     * @param productKey//产品ID
     * @param deviceName//产品名称
     * @param identifier//属性值
     * @param startTime//开始时间
     * @param endTime//结束时间
     * @param PageSize//数据量
     * @return
     */
    public static List<PropertyInfo> queryDevicePropertyData(String productKey, String deviceName, String identifier, long startTime, long endTime, int PageSize) {
        QueryDevicePropertyDataRequest request = new QueryDevicePropertyDataRequest();
        request.setAsc(0);
        request.setProductKey(productKey);
        request.setDeviceName(deviceName);
        request.setIdentifier(identifier);
        request.setStartTime(startTime);
        request.setPageSize(PageSize);
        request.setEndTime(endTime);
        QueryDevicePropertyDataResponse response = (QueryDevicePropertyDataResponse) executeTest(request);
        if (response != null && response.getSuccess() != false) {
            return response.getData().getList();
        } else {
            LogUtil.debug("查询设备历史数据！requestId:" + response.getRequestId() + "原因:" + response.getErrorMessage());
        }
        return null;
    }
}

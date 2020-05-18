package com.cx.common.utils;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by qhong on 2018/7/3 16:55
 **/
public class OkHttpUtil {

    private static final String appKey = "6202a0e49db8478e9d6cb4401bddea4c";

    private static final String secret = "ddac279ebdf429b2f56f604c35242da6";

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    //根据appKey和secret获取accessToken
    private static final String getToken = "https://open.ys7.com/api/lapp/token/get";

    //根据用户下监控点列表信息列表
    private static final String getCameraList = "https://open.ys7.com/api/lapp/camera/list";
    //根据接口用于根据序列号通道号获取设备状态信息
    private static final String getCameraStatus = "https://open.ys7.com/api/lapp/device/status/get";
    //根据接口用户根据设备序列号查询设备能力集
    private static final String getCameraCapacity = "https://open.ys7.com/api/lapp/device/capacity";
    //账号下的所有告警消息列表
    private static final String getAlarmList = "https://open.ys7.com/api/lapp/alarm/device/list";
    //添加设备
    private static final String addDevice = "https://open.ys7.com/api/lapp/device/add";

    /**
     * 根据map获取get请求参数
     *
     * @param queries
     * @return
     */
    public static StringBuffer getQueryString(String url, Map<String, String> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        return sb;
    }

    /**
     * 调用okhttp的newCall方法
     *
     * @param request
     * @return
     */
    private static String execNewCall(Request request) {
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 put error >> ex = {}", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    /**
     * get
     *
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static String get(String url, Map<String, String> queries) {
        StringBuffer sb = getQueryString(url, queries);
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        return execNewCall(request);
    }

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public static String postFormParams(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return execNewCall(request);
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appKey", "6202a0e49db8478e9d6cb4401bddea4c");
        params.put("appSecret", "ddac279ebdf429b2f56f604c35242da6");
        //String tokenres=OkHttpUtil.postFormParams(getToken,params);
        //params.put("accessToken","at.aciipe1napv1ro6d2rngcfrz2eb9g7q5-8abdh7ktxq-0rq8jdy-gozaybxvs");
        System.out.println(postFormParams(getToken, params));

    }

}
package com.cx.common.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/mobileserver/{khId}")
@Slf4j
public class MobileSocketServer {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, MobileSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收客户Id
     */
    private String khId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("khId") String khId) {
        this.session = session;
        this.khId = khId;
        if (webSocketMap.containsKey(khId)) {
            webSocketMap.remove(khId);
            webSocketMap.put(khId, this);
            //加入set中
        } else {
            webSocketMap.put(khId, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        log.info("用户连接:" + khId + ",当前在线人数为:" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("用户:" + khId + ",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(khId)) {
            webSocketMap.remove(khId);
            //从set中删除
            subOnlineCount();
        }
//      log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
//            log.info("用户消息:" + userId + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId", this.khId);
                String toUserId = jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket
                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                } else {
//                        log.error("请求的userId:" + toUserId + "不在该服务器上");
                    //否则不在这个服务器上，发送到mysql或者redis
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
//            log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("khId") String khId) throws IOException {
//            log.info("发送消息到:" + userId + "，报文:" + message);
        if (StringUtils.isNotBlank(khId) && webSocketMap.containsKey(khId)) {
            webSocketMap.get(khId).sendMessage(message);
        } else {
//                log.error("用户" + userId + ",不在线！");
        }
    }

    /**
     * 批量发送自定义消息
     */
    public static void sendInfoEver(String message) throws IOException {
//        for(ConcurrentHashMap.Entry<String, WebSocketServer> entry : webSocketMap.entrySet()){
//            String mapKey = entry.getKey();
//            WebSocketServer mapValue = entry.getValue();
//            webSocketMap.get(mapKey).sendMessage(message);
//        }
        if(webSocketMap!=null){
            for(String key : webSocketMap.keySet()){
                webSocketMap.get(key).sendMessage(message);
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MobileSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MobileSocketServer.onlineCount--;
    }
}

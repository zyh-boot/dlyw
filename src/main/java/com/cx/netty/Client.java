package com.cx.netty;

import com.cx.common.utils.DateUtil;
import com.cx.common.utils.Hex2Float;
import com.cx.common.utils.HexToDec;
import com.cx.module.mobile.entity.Equipment;
import com.cx.module.mobile.entity.EquipmentTobaccoAttr;
import com.cx.module.mobile.entity.EquipmentWorkPeriod;
import com.cx.module.mobile.entity.SendCommand;
import com.cx.module.mobile.service.IEquipmentService;
import com.cx.module.mobile.service.IEquipmentTobaccoAttrService;
import com.cx.module.mobile.service.IEquipmentWorkPeriodService;
import com.cx.monitor.service.IRedisService;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
@Component
public class Client {
    @Autowired
    private  IEquipmentService equipmentService;
    @Autowired
    private  IRedisService redisService;
    @Autowired
    private  IEquipmentWorkPeriodService equipmentWorkPeriodService;
    @Autowired
    private  IEquipmentTobaccoAttrService iEquipmentTobaccoAttrService;

    //private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    public static Map<Integer, ChannelFuture> channels = new HashMap<>();
    private static ConcurrentHashMap<String, ChannelHandlerContext> map  = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Object> messageMap = new ConcurrentHashMap<String,Object>();

    @PostConstruct
    public  void start() throws InterruptedException {
        List<Integer> PORTS = new ArrayList<>();
        //多个tcp服务器的ip
        List<String> HOSTS = new ArrayList<>();
        Equipment e=new Equipment();
        e.setType(2);
        List<Equipment> list=equipmentService.list(e);
        if(list!=null&&list.size()>0){
           for (Equipment equ:list){
               PORTS.add(Integer.valueOf(equ.getCode()));
              HOSTS.add("119.3.209.48");
               //  HOSTS.add("127.0.0.1");
           }
            Client.getMessageMap().put("server1",equipmentService);//存入IP
            Client.getMessageMap().put("server2",redisService);//存入IP
            Client.getMessageMap().put("server3",equipmentWorkPeriodService);//存入IP
            Client.getMessageMap().put("server4",iEquipmentTobaccoAttrService);//存入IP
            getChannel(HOSTS, PORTS);
        }
    }
    public static ConcurrentHashMap<String, ChannelHandlerContext> getMap() {
        return map;
    }
    public static void setMap(ConcurrentHashMap<String, ChannelHandlerContext> map) {
        Client.map = map;
    }
    public static ConcurrentHashMap<String, Object> getMessageMap() {
        return messageMap;
    }
    public static void setMessageMap(ConcurrentHashMap<String, Object> messageMap) {
        Client.messageMap = messageMap;
    }

    /**
     * 初始化Bootstrap
     */
    public  Bootstrap getBootstrap() {
         EventLoopGroup  group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel  ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // 添加自定义协议的编解码工具
                pipeline.addLast("decoder",new MyDecoder());
                pipeline.addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new IdleStateHandler(0, 30, 0, TimeUnit.SECONDS));
                //pipeline.addLast(new ClientHandler(equipmentService,redisService,equipmentWorkPeriodService,iEquipmentTobaccoAttrService));
                 pipeline.addLast(new ClientHandler());
            }
        });
        return bootstrap;
    }

    //   获取所有连接
    public  Map<Integer, ChannelFuture> getChannel(List<String> hosts, List<Integer> ports) {
        Map<Integer, ChannelFuture> result = new HashMap<>();
        Bootstrap bootstrap =getBootstrap();
        for (int i = 0; i < ports.size(); i++) {
            String host = hosts.get(i);
            int port = ports.get(i);
            bootstrap.remoteAddress(host, port);
            //异步连接tcp服务端
            ChannelFuture future = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
                final EventLoop eventLoop = futureListener.channel().eventLoop();
                if (!futureListener.isSuccess()) {
                    //服务器未启动 连接tcp服务器不成功
                    System.out.println(port + "第一次连接与服务端断开连接!在10s之后准备尝试重连!");
                    //10秒后重连
                    eventLoop.schedule(() -> doConnect(bootstrap, host, port), 10, TimeUnit.SECONDS);
                }
            });
            result.put(port, future);
        }
        return result;
    }


    /**
     * 重新连接tcp服务端
     */

    public  void doConnect(Bootstrap bootstrap, String host, int port) {
        try {
            if (bootstrap != null) {
                //
                bootstrap.remoteAddress(host, port);
                ChannelFuture f = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
                    final EventLoop eventLoop = futureListener.channel().eventLoop();
                    if (!futureListener.isSuccess()) {

                        System.out.println(port + "服务器断线-----与服务端断开连接!在10s之后准备尝试重连!");
                        eventLoop.schedule(() -> doConnect(bootstrap, host, port), 10, TimeUnit.SECONDS);
                    }
                });
                channels.put(port, f);
            }
        } catch (Exception e) {
            System.out.println("客户端连接失败!" + e.getMessage());
        }

    }



}

package com.cx.netty;

import com.cx.module.mobile.entity.Equipment;
import com.cx.module.mobile.service.IEquipmentService;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class Client {
    @Autowired
    private IEquipmentService equipmentService;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    public static Map<Integer, ChannelFuture> channels = new HashMap<>();

    @PostConstruct
    public void start() throws InterruptedException {
        List<Integer> PORTS = new ArrayList<>();
        //多个tcp服务器的ip
        List<String> HOSTS = new ArrayList<>();
        Equipment e=new Equipment();
        e.setType(2);
        List<Equipment> list= equipmentService.list(e);
        if(list!=null&&list.size()>0){
           for (Equipment equ:list){
               PORTS.add(Integer.valueOf(equ.getCode()));
               HOSTS.add("127.0.0.1");
           }
            Client.getChannel(HOSTS, PORTS);
        }
    }


    /**
     * 初始化Bootstrap
     */

    public static final Bootstrap getBootstrap(EventLoopGroup group) {
        if (null == group) {
            group = new NioEventLoopGroup();
        }
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                // 添加自定义协议的编解码工具
                pipeline.addLast(new IdleStateHandler(0, 30, 0, TimeUnit.SECONDS));
                pipeline.addLast(new ClientHandler());
            }
        });
        return bootstrap;
    }

    //   获取所有连接

    public static final Map<Integer, ChannelFuture> getChannel(List<String> hosts, List<Integer> ports) {
        Map<Integer, ChannelFuture> result = new HashMap<>();
        Bootstrap bootstrap = getBootstrap(null);
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
    public static void doConnect(Bootstrap bootstrap , String host, int port) {
        try {
            if (bootstrap != null) {
                //
                bootstrap.remoteAddress(host, port);
                ChannelFuture f = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
                    final EventLoop eventLoop = futureListener.channel().eventLoop();
                    if (!futureListener.isSuccess()) {
                        //连接tcp服务器不成功 10后重连
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
    public void stop(){
        eventLoopGroup.shutdownGracefully();
        System.out.println("stop");
    }
}


class ClientHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("echo server!\n", CharsetUtil.UTF_8));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        //包含此客户端才去删除
        System.out.println("服务端【" + clientIp + "】退出netty服务器[端口:"+ insocket.getPort() + "]");
        final EventLoop eventLoop = ctx.channel().eventLoop();
        //断线重连
        Bootstrap bootstrap =Client.getBootstrap(eventLoop);
        Client.doConnect(bootstrap,clientIp,insocket.getPort());
        super.channelInactive(ctx);
    }


        //用十六进制记录接收到的消息

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
       System.out.println("服务端【" + clientIp + "】加入netty服务器[端口" + insocket.getPort() + "]");
            ByteBuf buf = (ByteBuf)msg;
            //创建目标大小的数组
            byte[] barray = new byte[buf.readableBytes()];
            //把数据从bytebuf转移到byte[]
            buf.getBytes(0,barray);
            //将byte[]转成字符串用于打印
            String str=new String(barray);

            if (str.length()>0)
            {
                System.out.println(str);
                System.out.flush();
            }
            else
            {
                System.out.println("不能读啊");
            }
            buf.release();


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public static void main(String[] args) {
       List<Integer> PORTS = Arrays.asList(6003,6001,6002);
        //多个tcp服务器的ip
       List<String> HOSTS = Arrays.asList("127.0.0.1","127.0.0.1","127.0.0.1");
       Client.getChannel(HOSTS, PORTS);

    }

}

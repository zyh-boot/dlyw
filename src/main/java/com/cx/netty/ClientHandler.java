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
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientHandler extends ChannelInboundHandlerAdapter {

   /* @Autowired
    private  IEquipmentService iequipmentService;
    @Autowired
    private  IRedisService redisService;
    @Autowired
    private  IEquipmentWorkPeriodService equipmentWorkPeriodService;
    @Autowired
    private  IEquipmentTobaccoAttrService iEquipmentTobaccoAttrService;*/

  /* private IEquipmentService iequipmentService;

    private  IRedisService redisService;

    private IEquipmentWorkPeriodService equipmentWorkPeriodService;

    private IEquipmentTobaccoAttrService iEquipmentTobaccoAttrService;

    public ClientHandler(IEquipmentService iequipmentService,IRedisService redisService,
                         IEquipmentWorkPeriodService equipmentWorkPeriodService,IEquipmentTobaccoAttrService iEquipmentTobaccoAttrService){
                   this.redisService=redisService;
        this.equipmentWorkPeriodService=equipmentWorkPeriodService;
        this.iequipmentService=iequipmentService;
        this.iEquipmentTobaccoAttrService=iEquipmentTobaccoAttrService;
    }
*/

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        int clientPort = insocket.getPort();
        //如果map中不包含此连接，就保存连接
        Client.getMap().put(clientPort + "", ctx);//存入IP

        if (!Client.getMap().containsKey(clientPort)) {
            IEquipmentService iequipmentService= (IEquipmentService) Client.getMessageMap().get("server1");//存入IP
            //保存连接
            Equipment entity = iequipmentService.findSbByCode(clientPort + "");
            if (entity != null) {
                entity.setSbStatus(2);
                iequipmentService.update(entity);
            }
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        //包含此客户端才去删除
        System.out.println("服务端【" + clientIp + "】退出netty服务器[端口:"+ insocket.getPort() + "]");
       IEquipmentService iequipmentService= (IEquipmentService) Client.getMessageMap().get("server1");//存入IP
        Equipment entity = iequipmentService.findSbByCode(insocket.getPort() + "");
        if (entity != null) {
            entity.setSbStatus(1);
            iequipmentService.update(entity);
        }
       final EventLoop eventLoop = ctx.channel().eventLoop();
        //断线重连
        Client c=new Client();
        Bootstrap bootstrap =c.getBootstrap();
        c.doConnect(bootstrap,clientIp,insocket.getPort());
        super.channelInactive(ctx);
        Client.getMap().remove(insocket.getPort() + "");

    }

    //用十六进制记录接收到的消息
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        int clientPort = insocket.getPort();
        String body = (String)msg;
        if(StringUtils.isNotEmpty(body)){
            String _value=null;
            //获取当前发送的数据
            SendCommand command=(SendCommand)Client.getMessageMap().get(clientPort+"");
            if(command!=null){
                int gs=Integer.valueOf(command.getGs());
                int num=command.getJcqnum();
                String lengthNum= body.substring(4,6);//获取值的16进制
               // String value= body.substring(6,body.length()-4);//获取值的16进制
                int unitType=Integer.parseInt(command.getUnitType());//判断数据类型，根据不同的数据类型处理不同的数据
                //整数类型
                if("0C".equals(lengthNum)){
                    List<String> values=new ArrayList<>();
                    for(int i=0;i<num;i++){
                        String _value1=null; String _value2=null;
                        if(unitType==2 || unitType==3){
                            _value1=body.substring(6+i*2,6+(i+1)*2);
                            _value2=(HexToDec.HexToInt(_value1)/gs)+"";
                        }else if(unitType==4 ||  unitType==5){
                            _value1=body.substring(6+i*4,6+(i+1)*4);
                            _value2=(HexToDec.HexToInt(_value1)/gs)+"";
                        }else if(unitType==8 || unitType==14){
                            _value1=body.substring(6+i*8,6+(i+1)*8);
                            Long l = Hex2Float.parseLong(_value1, 16);
                            _value2= (Float.intBitsToFloat(l.intValue())/gs)+"";
                        }else if(unitType==9){
                            _value1=body.substring(6+i*8,6+(i+1)*8);
                            String hei=_value1.substring(0,2);
                            String enb=_value1.substring(2,4);
                            String value1=enb+hei;
                            Long l = Hex2Float.parseLong(value1, 16);
                            _value2= (Float.intBitsToFloat(l.intValue())/gs)+"";
                        }
                        values.add(_value2);
                    }

                    if(values!=null&&values.size()>0) {
                        IRedisService redisService= (IRedisService) Client.getMessageMap().get("server2");
                       /* String period_no= redisService.get(clientPort+"");
                        if(period_no==null){
                            EquipmentWorkPeriod wp=new EquipmentWorkPeriod();
                            wp.setSbCode(clientPort+"");
                            wp.setIsPeriod(1);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                            String period_no1 = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
                            wp.setPeriodNo(period_no1);
                            wp.setStartTime(LocalDateTime.now());
                            IEquipmentWorkPeriodService equipmentWorkPeriodService= (IEquipmentWorkPeriodService) Client.getMessageMap().get("server3");
                            equipmentWorkPeriodService.add(wp);
                            redisService.set(clientPort+"",period_no1);
                        }else{*/
                            EquipmentTobaccoAttr attr=new EquipmentTobaccoAttr();
                            attr.setCode(clientPort+"");
                        attr.setCreateTime(new Date());
                            attr.setPeriodNo("20200520114120211");
                            attr.setFanCurrent(0);
                            attr.setCompressorCurrentOne(0);
                            attr.setCompressorCurrentTwo(0);
                            attr.setSystemVoltage(0);
                            attr.setUpTemperature(Integer.valueOf(values.get(0)));
                            attr.setUpHumidity(Integer.valueOf(values.get(1)));
                            attr.setDownTemperature(Integer.valueOf(values.get(2)));
                            attr.setDownHumidity(Integer.valueOf(values.get(3)));
                            attr.setPipelineTemperatureOne(Integer.valueOf(values.get(4)));
                            attr.setPipelineTemperatureTwo(Integer.valueOf(values.get(5)));
                            attr.setResCommon(body);
                            IEquipmentTobaccoAttrService iEquipmentTobaccoAttrService= (IEquipmentTobaccoAttrService) Client.getMessageMap().get("server4");
                            iEquipmentTobaccoAttrService.addOrUpdate(attr);
                       // }
                    }
                    Client.getMessageMap().remove(clientPort+"");
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

package com.cx.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cx.common.entity.Constant;
import com.cx.common.webSocket.WebSocketServer;
import com.cx.module.mobile.entity.*;
import com.cx.module.mobile.service.*;
import com.cx.monitor.service.IRedisService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;


/**
 * AMqp多线程程序
 *
 * @author Administrator
 */
@Component
public class AmqpClientThread implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(IotAmqpClient.class);
    private final static String pk = IOTConfigUtil.IOT_ProductKey;
    @Autowired
    IRedisService redisService;
    @Autowired
    IEquipmentWorkPeriodService  workPeriodService;
    @Autowired
    IEquipmentService  equipmentService;
    @Autowired
    IWorkPeriodYcService  workPeriodYcService;
    @Autowired
    IEquipmentFeedInfoService feedInfoService;
    @Autowired
    IEquipmentCoolStatusService coolStatusService;
    @Autowired
    IEquipmentEnergyInfoService energyInfoService;
    @Autowired
    IEquipmentHeartInfoService heartInfoService;
    @Autowired
    IEquipmentRollerInfoService rollerInfoService;
    @Override
    public void run(String... args) throws Exception {
        String accessKey = "LTAI4FhmXV3nCF2MtpgmuaC1";
        String accessSecret = "CRSwltC4ww6HFmwlk8z9X0M1Oi9IAr";
        String consumerGroupId = "Dw3KDhaN6mjnIA11vB2y000100";
        String clientId = "3m1fm7ZiC5fCtAcA";
        long timeStamp = System.currentTimeMillis();
        //签名方法：支持hmacmd5，hmacsha1和hmacsha256
        String signMethod = "hmacsha1";
        //控制台服务端订阅中消费组状态页客户端ID一栏将显示clientId参数。
        //建议使用机器UUID、MAC地址、IP等唯一标识等作为clientId。便于您区分识别不同的客户端。

        //UserName组装方法，请参见上一篇文档：AMQP客户端接入说明。
        String userName = clientId + "|authMode=aksign"
                + ",signMethod=" + signMethod
                + ",timestamp=" + timeStamp
                + ",authId=" + accessKey
                + ",consumerGroupId=" + consumerGroupId
                + "|";
        //password组装方法，请参见上一篇文档：AMQP客户端接入说明。
        String signContent = "authId=" + accessKey + "&timestamp=" + timeStamp;
        String password = doSign(signContent, accessSecret, signMethod);
        //按照qpid-jms的规范，组装连接URL。1950696194510916
        String connectionUrl = "failover:(amqps://" + IOTConfigUtil.IOT_UID + ".iot-amqp."
                + IOTConfigUtil.IOT_RegionId + ".aliyuncs.com?amqp.idleTimeout=80000)"
                + "?failover.maxReconnectAttempts=10&failover.reconnectDelay=30";
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("connectionfactory.SBCF", connectionUrl);
        hashtable.put("queue.QUEUE", "default");
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        Context context = new InitialContext(hashtable);
        ConnectionFactory cf = (ConnectionFactory) context.lookup("SBCF");
        Destination queue = (Destination) context.lookup("QUEUE");
        // Create Connection
        Connection connection = cf.createConnection(userName, password);
        ((JmsConnection) connection).addConnectionListener(myJmsConnectionListener);
        // Create Session
        // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()
        // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        // Create Receiver Link
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(messageListener);

    }

    private static JmsConnectionListener myJmsConnectionListener = new JmsConnectionListener() {
        /**
         * 连接成功建立。
         */
        @Override
        public void onConnectionEstablished(URI remoteURI) {
            logger.info("onConnectionEstablished, remoteUri:{}", remoteURI);
        }

        /**
         * 尝试过最大重试次数之后，最终连接失败。
         */
        @Override
        public void onConnectionFailure(Throwable error) {
            logger.error("onConnectionFailure, {}", error.getMessage());
        }

        /**
         * 连接中断。
         */
        @Override
        public void onConnectionInterrupted(URI remoteURI) {
            logger.info("onConnectionInterrupted, remoteUri:{}", remoteURI);
        }

        /**
         * 连接中断后又自动重连上。
         */
        @Override
        public void onConnectionRestored(URI remoteURI) {
            logger.info("onConnectionRestored, remoteUri:{}", remoteURI);
        }

        @Override
        public void onInboundMessage(JmsInboundMessageDispatch envelope) {
        }

        @Override
        public void onSessionClosed(Session session, Throwable cause) {
        }

        @Override
        public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {
        }

        @Override
        public void onProducerClosed(MessageProducer producer, Throwable cause) {
        }
    };

    /**
     * password签名计算方法，请参见上一篇文档：AMQP客户端接入说明。
     */
    private static String doSign(String toSignString, String secret, String signMethod) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), signMethod);
        Mac mac = Mac.getInstance(signMethod);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(toSignString.getBytes());
        return Base64.encodeBase64String(rawHmac);
    }
    private MessageListener messageListener = new MessageListener() {
        @Override
        public void onMessage(Message message) {
            try {
                byte[] body = message.getBody(byte[].class);
                String content = new String(body);
                String topic = message.getStringProperty("topic");
                String messageId = message.getStringProperty("messageId");
                logger.info("receive message"
                        + ", topic = " + topic
                        + ", messageId = " + messageId
                        + ", content = " + content);
                //属性解析
                JSONObject json = JSONObject.parseObject(content);
                if (json != null) {
                    String productKey = json.getString("productKey");//产品
                    String deviceName = json.getString("deviceName");//设备
                    if (productKey.equals(pk)) {
                        String status = json.getString("status");//设备
                          //设备状态
                        if (StringUtils.isNotBlank(status)) {
                            //"lastTime":"2019-12-09 16:11:36.275"
                            String lastTime = json.getString("lastTime");
                            if (lastTime == null) {
                                lastTime =LocalDateTime.now().toString() ;
                            }

                            //设备关闭
                            if ("offline".equals(status)) {
                                UpdateWrapper<Equipment> updateWrapper = new UpdateWrapper<>();
                                updateWrapper.eq("code",deviceName);//设备Id
                                Equipment t = new Equipment();
                                t.setLastTime(lastTime);
                                t.setSbStatus(1);
                                equipmentService.updateByWrapper(t,updateWrapper);
                                redisService.del(deviceName);
                            }
                            //设备开启
                            if ("online".equals(status)) {
                                UpdateWrapper<Equipment> updateWrapper = new UpdateWrapper<>();
                                updateWrapper.eq("code",deviceName);//设备Id
                                Equipment t = new Equipment();
                                t.setCreateDate(LocalDateTime.now());
                                t.setSbStatus(2);
                                equipmentService.updateByWrapper(t,updateWrapper);
                            }
                        } else {
                            //为空的话判断是有属性更改或事件发生
                            //事件处理区域
                            String identifier = json.getString("identifier");//是否是事件
                            if (StringUtils.isNotBlank(identifier)) {
                                //事件
                                JSONObject abjson = json.getJSONObject("value");
                                //品种湿度，默认炒制开始
                                if ("variety_hum".equals(identifier)) {
                                int  variety=abjson.getInteger("variety");//进料品种
                                float  humidity=abjson.getFloat("humidity");//进料湿度
                                int  startflag=abjson.getInteger("startflag");//startflag\
                                if(startflag==0){//周期结束
                                    EquipmentWorkPeriod ewp=new EquipmentWorkPeriod();
                                    ewp.setSbCode(deviceName);
                                    ewp.setEndTime(null);
                                    EquipmentWorkPeriod  entity= workPeriodService.list(ewp).get(0);
                                    entity.setIsPeriod(0);
                                    entity.setEndTime(LocalDateTime.now());
                                    workPeriodService.update(entity);
                                    redisService.del(deviceName);
                                }else{//周期开始
                                    QueryWrapper<EquipmentWorkPeriod> qw=new QueryWrapper<>();
                                    qw.eq("sb_code",deviceName);
                                    qw.eq("is_period",1);
                                    EquipmentWorkPeriod ewp=  workPeriodService.selectOne(qw);
                                    if(ewp!=null){
                                        ewp.setEndTime(LocalDateTime.now());
                                        ewp.setIsPeriod(0);
                                        workPeriodService.update(ewp);
                                        redisService.del(deviceName);
                                        EquipmentWorkPeriod  wp=new EquipmentWorkPeriod();
                                        wp.setSbCode(deviceName);
                                        wp.setWorkType(variety);
                                        wp.setWorkSd(humidity);
                                        wp.setIsPeriod(1);
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                                        String period_no = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
                                        wp.setPeriodNo(period_no);
                                        wp.setStartTime(LocalDateTime.now());
                                        workPeriodService.add(wp);
                                        redisService.set(deviceName,period_no);
                                    }else{
                                        EquipmentWorkPeriod  wp=new EquipmentWorkPeriod();
                                        wp.setSbCode(deviceName);
                                        wp.setWorkType(variety);
                                        wp.setWorkSd(humidity);
                                        wp.setIsPeriod(1);
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                                        String period_no = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
                                        wp.setPeriodNo(period_no);
                                        wp.setStartTime(LocalDateTime.now());
                                        workPeriodService.add(wp);
                                        redisService.set(deviceName,period_no);
                                    }

                                }
                                //滚筒错误
                                } else if ("roller_error".equals(identifier)) {
                                    WorkPeriodYc  yc=new WorkPeriodYc();
                                    yc.setSbCode(deviceName);
                                    yc.setYcStatus(1);
                                    yc.setYcType(1);
                                    List<WorkPeriodYc>  list=workPeriodYcService.list(yc);
                                    if(list==null){
                                        WorkPeriodYc ycEntity=new WorkPeriodYc();
                                        ycEntity.setSbCode(deviceName);
                                        ycEntity.setYcStatus(1);
                                        ycEntity.setStartTime(LocalDateTime.now());
                                        ycEntity.setYcType(1);
                                        ycEntity.setYcInfo("滚筒运行错误");
                                        ycEntity.setIsDeal(1);
                                        workPeriodYcService.add(ycEntity);
                                    }
                                //加热错误
                                } else if ("heart_error".equals(identifier)) {
                                    int  error_code=abjson.getInteger("error_code");
                                    WorkPeriodYc  yc=new WorkPeriodYc();
                                    yc.setSbCode(deviceName);
                                    yc.setYcStatus(1);
                                    yc.setYcType(2);
                                    List<WorkPeriodYc>  list=workPeriodYcService.list(yc);
                                    if(list==null){
                                        WorkPeriodYc ycEntity=new WorkPeriodYc();
                                        ycEntity.setSbCode(deviceName);
                                        ycEntity.setYcStatus(1);
                                        ycEntity.setStartTime(LocalDateTime.now());
                                        ycEntity.setYcType(2);
                                        ycEntity.setYcInfo("加热运行错误");
                                        ycEntity.setIsDeal(1);
                                        workPeriodYcService.add(ycEntity);
                                    }
                                //进料错误
                                } else if ("feed_error".equals(identifier)) {
                                    int  error_code=abjson.getInteger("error_code");
                                    WorkPeriodYc  yc=new WorkPeriodYc();
                                    yc.setSbCode(deviceName);
                                    yc.setYcStatus(1);
                                    yc.setYcType(3);
                                    List<WorkPeriodYc>  list=workPeriodYcService.list(yc);
                                    if(list==null){
                                        WorkPeriodYc ycEntity=new WorkPeriodYc();
                                        ycEntity.setSbCode(deviceName);
                                        ycEntity.setYcStatus(1);
                                        ycEntity.setStartTime(LocalDateTime.now());
                                        ycEntity.setYcType(3);
                                        ycEntity.setYcInfo("进料运行错误");
                                        ycEntity.setIsDeal(1);
                                        workPeriodYcService.add(ycEntity);
                                    }
                                } else if ("device_info".equals(identifier)) {
                                    //开机事件，上传经纬度信息，电话卡信息，信号信息
                                    String lat=null;String lon=null; String address=null;
                                    String  ICCID=abjson.getString("ICCID");//手机卡信息
                                    String  location=abjson.getString("location");//经纬度
                                    if(StringUtils.isNotEmpty(location)){
                                         String location_=  redisService.get(location);
                                         if(location_!=null&&location_.equals(location)){
                                             String location_JWD=  redisService.get(location+"JWD");
                                             String[] locationStr=  location_JWD.split("&");
                                             lat=locationStr[0];
                                             lon=locationStr[1];
                                             address=locationStr[2];
                                         }else{
                                             String[] locationStr=  location.split(",");
                                             String mnc=locationStr[0];String lac=locationStr[1];String cid=locationStr[2];
                                             String  url="http://api.cellocation.com:81/cell/?mcc=460&mnc="+mnc+"&lac="+lac+"&ci="+cid+"&output=json";
                                             String res= (String) HttpClientUtils.doGet(url);
                                             JSONObject location_json= JSONObject.parseObject(res);
                                             int errcode=location_json.getInteger("errcode");
                                             if(errcode==0){
                                                 lat=location_json.getString("lat");
                                                 lon=location_json.getString("lon");
                                                 address=location_json.getString("address");
                                                 redisService.set(location,location);
                                                 redisService.set(location+"JWD",lat+"&"+lon+"&"+address);
                                             }
                                         }
                                    }
                                    int  signal=abjson.getInteger("signal");//信号
                                    Equipment equEntity=equipmentService.findSbByCode(deviceName);
                                    //如果没有就默认是新增的设备
                                    if(equEntity==null){
                                        Equipment sb=new Equipment();
                                        sb.setCode(deviceName);
                                        sb.setSbInfo(ICCID);
                                        sb.setSbStatus(1);
                                        sb.setSignal1(signal);
                                        sb.setType(1);
                                        sb.setSbJd(lon);
                                        sb.setSbWd(lat);
                                        sb.setSbAddress(address);
                                        sb.setCreateDate(LocalDateTime.now());
                                        equipmentService.add(sb);
                                    }else{
                                        equEntity.setSbInfo(ICCID);
                                        equEntity.setSbStatus(1);
                                        equEntity.setSignal1(signal);
                                        if(StringUtils.isNotEmpty(lat)&&StringUtils.isNotEmpty(lon)){
                                            equEntity.setSbJd(lon);
                                            equEntity.setSbWd(lat);
                                            equEntity.setSbAddress(address);
                                        }
                                        equipmentService.update(equEntity);
                                    }
                                }
                            } else {
                                //属性处理区域
                                if (json.containsKey("items")) {
                                    JSONObject jsonValue = json.getJSONObject("items");
                                    if (jsonValue.containsKey("roller_info")) {
                                        JSONObject json_value = jsonValue.getJSONObject("roller_info").getJSONObject("value");
                                        float  run_frequence=  json_value.getFloat("run_frequence");
                                        float  out_power=  json_value.getFloat("out_power");
                                        int state=   json_value.getInteger("run_state");
                                        String peroid_id=redisService.get(deviceName);
                                        if(StringUtils.isNotEmpty(peroid_id)){
                                            EquipmentRollerInfo rollinfo=new EquipmentRollerInfo();
                                            rollinfo.setSbCode(deviceName);
                                            rollinfo.setOutPower(out_power);
                                            rollinfo.setRunFrequence(run_frequence);
                                            rollinfo.setRunState(state);
                                            rollinfo.setPeriodId(Long.valueOf(peroid_id));
                                            rollerInfoService.add(rollinfo);
                                        }
                                    }
                                    if (jsonValue.containsKey("heart_info")) {
                                        JSONObject json_value = jsonValue.getJSONObject("heart_info").getJSONObject("value");
                                        int  power=  json_value.getInteger("power");
                                        float  heart_temperature=  json_value.getFloat("heart_temperature");
                                        int heart_state=   json_value.getInteger("heart_state");
                                        String peroidId=redisService.get(deviceName);
                                        if(StringUtils.isNotEmpty(peroidId)){
                                            EquipmentHeartInfo hInfo=new EquipmentHeartInfo();
                                            hInfo.setSbCode(deviceName);
                                            hInfo.setPeriodId(Long.valueOf(peroidId));
                                            hInfo.setPower(power);
                                            hInfo.setHeartTemperature(heart_temperature);
                                            hInfo.setHeartState(heart_state);
                                            heartInfoService.add(hInfo);
                                        }
                                    }
                                    if (jsonValue.containsKey("feed_info")) {
                                        JSONObject json_value = jsonValue.getJSONObject("feed_info").getJSONObject("value");
                                        float  out_power=  json_value.getFloat("out_power");
                                        float  run_frequence=  json_value.getFloat("run_frequence");
                                        int run_state=   json_value.getInteger("run_state");
                                        String peroidId=redisService.get(deviceName);
                                        if(StringUtils.isNotEmpty(peroidId)){
                                            EquipmentFeedInfo fInfo=new EquipmentFeedInfo();
                                            fInfo.setSbCode(deviceName);
                                            fInfo.setPeriodId(Long.valueOf(peroidId));
                                            fInfo.setOutPower(out_power);
                                            fInfo.setRunFrequence(run_frequence);
                                            fInfo.setRunState(run_state);
                                            feedInfoService.add(fInfo);
                                        }
                                    }
                                    if (jsonValue.containsKey("energy_info")) {
                                        JSONObject json_value = jsonValue.getJSONObject("energy_info").getJSONObject("value");
                                        float  current_a=  json_value.getFloat("current_a");
                                        float  current_b=  json_value.getFloat("current_b");
                                        float  current_c=  json_value.getFloat("current_c");
                                        float  voltage_a=  json_value.getFloat("voltage_a");
                                        float  voltage_b=  json_value.getFloat("voltage_b");
                                        float  voltage_c=  json_value.getFloat("voltage_c");
                                        float  power_a=  json_value.getFloat("power_a");
                                        float  power_b=  json_value.getFloat("power_b");
                                        float  power_c=  json_value.getFloat("power_c");
                                        String peroidId=redisService.get(deviceName);
                                        if(StringUtils.isNotEmpty(peroidId)){
                                            EquipmentEnergyInfo eInfo=new EquipmentEnergyInfo();
                                            eInfo.setSbCode(deviceName);
                                            eInfo.setPeriodId(Long.valueOf(peroidId));
                                            eInfo.setCurrentA(current_a);  eInfo.setCurrentB(current_b);  eInfo.setCurrentC(current_c);
                                            eInfo.setVoltageA(voltage_a);  eInfo.setVoltageB(voltage_b);  eInfo.setVoltageC(voltage_c);
                                            eInfo.setPowerA(power_a);      eInfo.setPowerB(power_b);      eInfo.setPowerC(power_c);
                                            energyInfoService.add(eInfo);
                                        }
                                    }
                                    if (jsonValue.containsKey("Cool_Status")) {
                                        JSONObject Cool= (JSONObject) jsonValue.get("Cool_Status");
                                      int  Cool_Status= Cool.getInteger("value");
                                        String peroidId=redisService.get(deviceName);
                                        if(StringUtils.isNotEmpty(peroidId)){
                                            EquipmentCoolStatus fInfo=new EquipmentCoolStatus();
                                            fInfo.setSbCode(deviceName);
                                            fInfo.setPeriodId(Long.valueOf(peroidId));
                                            fInfo.setCoolStatus(Cool_Status);
                                            coolStatusService.add(fInfo);
                                        }

                                    }

                                }
                            }
                            //解析结束
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}

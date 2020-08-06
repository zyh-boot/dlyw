package com.cx.module.mobile.controller;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.common.utils.CRC16;
import com.cx.module.mobile.entity.Equipment;
import com.cx.module.mobile.entity.SendCommand;
import com.cx.module.mobile.service.IEquipmentService;
import com.cx.module.mobile.service.ISendCommandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*   控制器
*
* @author admin
* @Description Created on 2020-05-19
*/
@RestController
@Slf4j
@RequestMapping("mobile/sendCommand")
        public class SendCommandController extends BaseController{

    @Autowired
    ISendCommandService iSendCommandService;
    @Autowired
    IEquipmentService equipmentService;
    /**
    * 查询详情
    */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException{
        try {
            return getCommonResponse(iSendCommandService.selectOne(id));
        } catch (Exception e) {
            String message = "查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
    * 分页查询
    */
    @GetMapping("pageList")
    public CommonResponse pageList(SendCommand obj,QueryRequest query)  throws CommonException{
        try {
            List<SendCommand> records = iSendCommandService.page(obj, query).getRecords();
            ArrayList<Object> list = new ArrayList<>();
            for(SendCommand sendCommand : records){
                LambdaQueryWrapper<Equipment> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Equipment::getCode,sendCommand.getSbCode());
                Equipment equipment = equipmentService.selectOne(wrapper);

                Map map = JSON.parseObject(JSON.toJSONString(sendCommand), Map.class);
                map.put("code",equipment.getSbStatus());
                list.add(map);
            }
            Page<Object> page = new Page<>();
            page.setRecords(list);
            return getTableData(page);
//            return  getTableData(iSendCommandService.page(obj,query));
        } catch (Exception e) {
            String message = "分页查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
    * 查询列表
    */
    @GetMapping("list")
    public CommonResponse pageList(SendCommand obj)  throws CommonException{
        try {
            return getCommonResponse(iSendCommandService.list(obj));
        } catch (Exception e) {
            String message = "列表查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
    * 新增
    */
    @PostMapping("")
    @PreAuthorize("hasRole('sendCommand:add')")
    public CommonResponse add(SendCommand obj) throws CommonException{
        try {
            /***封装发送内容***/
            StringBuffer sb = new StringBuffer();
            //仪表地址
            String address=obj.getPlcAddress();
            BigInteger target1 = new BigInteger(address);
            String ad = init16_2(target1.toString(16));
            sb.append(ad.toUpperCase());
            //功能码
            sb.append(obj.getFunctionCode());
            //起始地址
            String qsAddress = obj.getQsAddress();
            BigInteger target = new BigInteger(qsAddress);
            String qs = init16_4(target.toString(16));
            sb.append(qs.toUpperCase());
            //寄存器数量
            int num = obj.getJcqnum();
            BigInteger target3 = new BigInteger(num+"");
            String jcNum = init16_4(target3.toString(16));
            sb.append(jcNum.toUpperCase());
            //CRC校验
            String CRC = CRC16.checkCRC(sb.toString());
            sb.append(CRC.toUpperCase());
            obj.setCommand(sb.toString());
            /***封装采集通道ID***/
            return getCommonResponse(iSendCommandService.add(obj));
        } catch (Exception e) {
            String message = "新增失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }
    public static String init16_4(String date){
        String res=null;
        int length = date.length();
        if(length==1){
            res="000"+date;
        }else if(length==2){
            res="00"+date;
        }else if(length==3){
            res="0"+date;
        }else{
            res=date;
        }
        return res;
    }
    public static String init16_2(String date){
        String res=null;
        int length = date.length();
        if(length==1){
            res="0"+date;
        }else{
            res=date;
        }
        return res;
    }

    /**
    * 修改
    */
    @PutMapping("")
    @PreAuthorize("hasRole('sendCommand:mod')")
    public CommonResponse update(SendCommand obj) throws CommonException{
        try {
            return getCommonResponse(iSendCommandService.update(obj));
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
    * 删除
    * @param ids
    * @return
    */
    @DeleteMapping("")
    @PreAuthorize("hasRole('sendCommand:del')")
    public CommonResponse delete(String ids) throws CommonException{
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iSendCommandService.batchDel(ids);
                } else {
                    iSendCommandService.delete(Long.valueOf(ids));
                }
            }
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "删除失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

}

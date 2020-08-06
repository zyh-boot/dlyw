package com.cx.module.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.common.utils.AliyunIotUtil;
import com.cx.common.utils.IOTConfigUtil;
import com.cx.module.mobile.entity.Account;
import com.cx.module.mobile.entity.AccountEquipment;
import com.cx.module.mobile.entity.Equipment;
import com.cx.module.mobile.service.IAccountEquipmentService;
import com.cx.module.mobile.service.IAccountService;
import com.cx.module.mobile.service.IEquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
@RestController
@Slf4j
@RequestMapping("mobile/equipment")
public class EquipmentController extends BaseController {
    @Autowired
    IEquipmentService iEquipmentService;
    @Autowired
    IAccountService accountService;
    @Autowired
    IAccountEquipmentService accountEquipmentService;

    /**
     * 查询详情
     */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException {
        try {
            return getCommonResponse(iEquipmentService.selectOne(id));
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
    public CommonResponse pageList(Equipment obj, QueryRequest query) throws CommonException {
        try {
            return getTableData(iEquipmentService.page(obj, query));
        } catch (Exception e) {
            String message = "分页查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }



    /**
     * 分页查询
     */
    @GetMapping("data")
    public CommonResponse pageData(Equipment obj, QueryRequest query) throws CommonException {
        try {
            List<Equipment> records = iEquipmentService.page(obj, query).getRecords();

            List<Object> list = new ArrayList<>();
            for (Equipment equipment : records) {
                LambdaQueryWrapper<AccountEquipment> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(AccountEquipment::getSbCode,equipment.getCode());

                AccountEquipment accountEquipment = accountEquipmentService.selectOne(wrapper);
                Account account = accountService.selectOne(accountEquipment.getKhId());

                Map map = JSON.parseObject(JSON.toJSONString(equipment), Map.class);
//                Map map  = (Map) equipment;
                map.put("account",account.getOrgName());
                System.out.println(">>>>>>>>>>>>>>>>>>>>" + map);
                list.add(map);
            }
            Page<Object> page = new Page<>();
            page.setRecords(list);
            return getTableData(page);
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
    public CommonResponse pageList(Equipment obj) throws CommonException {
        try {
            return getCommonResponse(iEquipmentService.list(obj));
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
    @PreAuthorize("hasRole('equipment:add')")
    public CommonResponse add(Equipment obj) throws CommonException {
        try {
            Equipment entity = iEquipmentService.findSbByCode(obj.getCode());
            if (entity == null) {
                int type = obj.getType();
                //物联网交互
                if (type == 1) {
                    AliyunIotUtil.RegisterDevice(IOTConfigUtil.IOT_ProductKey, obj.getCode());
                }
                return getCommonResponse(iEquipmentService.add(obj));
            } else {
                String message = "主机编号已存在";
                throw new CommonException(message);
            }
        } catch (Exception e) {
            String message = "新增失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }


    /**
     * 修改
     */
    @PutMapping("")
    @PreAuthorize("hasRole('equipment:mod')")
    public CommonResponse update(Equipment obj) throws CommonException {
        try {
            return getCommonResponse(iEquipmentService.update(obj));
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("")
    @PreAuthorize("hasRole('equipment:del')")
    public CommonResponse delete(String ids) throws CommonException {
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iEquipmentService.batchDel(ids);
                } else {
                    iEquipmentService.delete(Long.valueOf(ids));
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

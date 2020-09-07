package com.cx.module.mydept.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.common.utils.CommonUtil;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
import com.cx.module.myUser.entity.TUser;
import com.cx.module.myUser.service.ITUserService;
import com.cx.module.mydept.entity.Mydept;
import com.cx.module.mydept.service.IMydeptService;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 控制器
 *
 * @author admin
 * @Description Created on 2020-08-05
 */
@RestController
@Slf4j
@RequestMapping("mydept/mydept")
public class MydeptController extends BaseController {
    @Autowired
    IMydeptService iMydeptService;
    @Autowired
    IMyequipmentService myequipmentService;
    @Autowired
    ITUserService itUserService;


    /**
     * 查询详情
     */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException {
        try {
            return getCommonResponse(iMydeptService.selectOne(id));
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
    public CommonResponse pageList(Mydept obj, QueryRequest query) throws CommonException {
        try {
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>" + obj);
//            IPage<Mydept> page = iMydeptService.page(obj, query);
//            for (Mydept mydept : page.getRecords()) {
//                System.out.println(">>>>>>>>>>>>>>>>>>>>>" + mydept);
//            }

            return getTableData(iMydeptService.page(obj, query));
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
    public CommonResponse pageList(Mydept obj) throws CommonException {
        try {
            return getCommonResponse(iMydeptService.list(obj));
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
    @PreAuthorize("hasRole('mydept:add')")
    public CommonResponse add(Mydept obj) throws CommonException {
        try {
            return getCommonResponse(iMydeptService.add(obj));
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
    @PreAuthorize("hasRole('mydept:mod')")
    public CommonResponse update(Mydept obj) throws CommonException {
        judgmentCategory(obj.getId().toString());
        try {
            //同步设备表 关于机构的字段
            updataMyequipment(obj.getId().toString(), "updata", obj);
            //同步用户表
            updataUser(obj.getId().toString(), "updata", obj);

            return getCommonResponse(iMydeptService.update(obj));
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
    @PreAuthorize("hasRole('mydept:del')")
    public CommonResponse delete(String ids) throws CommonException {
        judgmentCategory(ids);
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iMydeptService.batchDel(ids);
                    updataUser(ids, "del", null);
                    updataMyequipment(ids, "del", null);
                } else {
                    iMydeptService.delete(Long.valueOf(ids));
                    updataUser(ids, "del", null);
                    updataMyequipment(ids, "del", null);
                }
            }
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "删除失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
     * 删除机构时 同步设备表字段
     *
     * @param ids
     */
    private void updataMyequipment(String ids, String type, Mydept obj) {
        //同步设备表 关于机构的字段
        LambdaQueryWrapper<Myequipment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Myequipment::getEqDeptId, ids);
        List<Myequipment> list = myequipmentService.list(wrapper);
        for (Myequipment myequipment : list) {
            if ("updata".equals(type)) {
                myequipment.setEqDeptName(obj.getName());
                myequipment.setEqDeptCategory(obj.getCategory());
            } else if ("del".equals(type)) {
                myequipment.setEqDeptId(Long.valueOf(-1));
                myequipment.setEqDeptName("无部门");
            }
            myequipmentService.update(myequipment);
        }
    }

    /**
     * 删除机构时 同步用户表字段 重置用户表的机构信息
     *
     * @param ids
     */
    private void updataUser(String ids, String type, Mydept obj) {
        LambdaQueryWrapper<TUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TUser::getDeptId, ids);
        List<TUser> list = itUserService.list(wrapper);
        for (TUser user : list) {
            if ("updata".equals(type)) {
                user.setDeptName(obj.getName());
            } else if ("del".equals(type)) {
                user.setDeptId(Long.valueOf(-1));
                user.setDeptName("无部门");
            }
            itUserService.update(user);
        }
    }

    /**
     * 校验当前用户是否有权限操作
     * 规则: 查找目标机构级别,判断当前用户的机构类别大小
     * 机构级别: 1:市 2:县 3:乡 4:村
     *
     * @param ids 目标ID
     * @throws CommonException
     */
    private void judgmentCategory(String ids) throws CommonException {
        //当前用户机构级别
        User user = CommonUtil.getCurrentUser();
        Mydept mydept = iMydeptService.selectOne(user.getDeptId());
        if(mydept == null){
            throw new CommonException("权限不足");
        }
        //目标用户机构级别
//        Mydept mydept1 = mydeptService.selectOne(Long.parseLong(ids));
        //判断用户级别是否拥有权限
//        return mydept.getCategory() > mydept1.getCategory();


        //批量校验
        if (StringUtils.isNotBlank(ids)) {
            if (ids.contains(StringPool.COMMA)) {
                for (String str : ids.split(StringPool.COMMA)) {
                    //目标用户机构级别
                    Mydept mydept1 = iMydeptService.selectOne(Long.parseLong(ids));
                    if (mydept1 != null) {
                        if (mydept.getCategory() > mydept1.getCategory()) {
                            String message = "权限不足, 列表存在上级机构!";
                            throw new CommonException(message);
                        }
                    }
                }
            } else {
                Mydept mydept1 = iMydeptService.selectOne(Long.parseLong(ids));
                if (mydept1 != null) {
                    if (mydept.getCategory() > mydept1.getCategory()) {
                        String message = "权限不足!";
                        throw new CommonException(message);
                    }
                }
            }
        }
    }


    @GetMapping("category")
    public CommonResponse getDeptCategory(String category){

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("index", "1");

        String value = "{'index':'1','value':'市'}";
        String value1 = "{'index':'2','value':'县'}";
        String value2 = "{'index':'3','value':'乡'}";
        String value3 = "{'index':'4','value':'村'}";

        ArrayList<Object> list = new ArrayList<>();
        list.add(JSON.parse(value));
        list.add(JSON.parse(value1));
        list.add(JSON.parse(value2));
        list.add(JSON.parse(value3));

        List<Object> subList = list.subList(Integer.parseInt(category) < 0 ? 0 : Integer.parseInt(category)-1, list.size());
        return new CommonResponse().code(HttpStatus.OK).data(subList);
    }
}

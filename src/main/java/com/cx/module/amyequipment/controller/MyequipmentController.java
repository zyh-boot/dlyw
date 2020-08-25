package com.cx.module.amyequipment.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.common.utils.CommonUtil;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 存放设备相关数据  控制器
 *
 * @author admin
 * @Description Created on 2020-08-06
 */
@RestController
@Slf4j
@RequestMapping("amyequipment/myequipment")
public class MyequipmentController extends BaseController {
    @Autowired
    IMyequipmentService iMyequipmentService;

    /**
     * 查询详情
     */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException {
        try {
            return getCommonResponse(iMyequipmentService.selectOne(id));
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
    public CommonResponse pageList(Myequipment obj, QueryRequest query) throws CommonException {
        try {
            return getTableData(iMyequipmentService.page(obj, query));
        } catch (Exception e) {
            String message = "分页查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
     * 分页查询其他
     */
    @Autowired
    IMydeptService mydeptService;

    @GetMapping("pageOtherList/{categroy}")
    public CommonResponse pageOtherList(Myequipment obj, QueryRequest query, @PathVariable String categroy) throws CommonException {
        try {
//            LambdaQueryWrapper<Mydept> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Mydept::getCategory, categroy);
//            List<Mydept> mydepts = mydeptService.list(wrapper);
//
//            ArrayList<Object> list = new ArrayList<>();
//            for (Mydept mydept : mydepts) {
//                list.add(mydept.getId());
//            }
//            if (list.isEmpty()){
//                return getTableData(new Page<>());
//            }
//            return getTableData(iMyequipmentService.myPage(obj, query, list));
            obj.setEqDeptCategory(Integer.parseInt(categroy));
            IPage<Myequipment> myequipmentIPage = iMyequipmentService.myPage(obj, query, new ArrayList());
            List<Myequipment> records = myequipmentIPage.getRecords();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + records);
            return getTableData(iMyequipmentService.myPage(obj, query, new ArrayList()));

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
    public CommonResponse pageList(Myequipment obj) throws CommonException {
        try {
            return getCommonResponse(iMyequipmentService.list(obj));
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
    @PreAuthorize("hasRole('myequipment:add')")
    public CommonResponse add(Myequipment obj) throws CommonException {
        obj.setEqAddTime(LocalDateTime.now());
        try {
            return getCommonResponse(iMyequipmentService.add(obj));
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
    @PreAuthorize("hasRole('myequipment:mod')")
    public CommonResponse update(Myequipment obj) throws CommonException {
        try {
            return getCommonResponse(iMyequipmentService.update(obj));
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
    @Autowired
    ITUserService iTUserService;

    @DeleteMapping("")
    @PreAuthorize("hasRole('myequipment:del')")
    public CommonResponse delete(String ids) throws CommonException {
        //批量校验
        if (StringUtils.isNotBlank(ids)) {
            if (ids.contains(StringPool.COMMA)) {
                for (String str : ids.split(StringPool.COMMA)) {
                    if (judgmentCategory(str)) {
                        String message = "权限不足, 列表存在上级机构设备!";
                        throw new CommonException(message);
                    }
                }
            } else {
                if (judgmentCategory(ids)) {
                    String message = "权限不足!";
                    throw new CommonException(message);
                }
            }
        }

        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iMyequipmentService.batchDel(ids);
                } else {
                    iMyequipmentService.delete(Long.valueOf(ids));
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
     * 校验当前用户是否有权限操作
     * 规则: 通过设备找到管理用户的机构级别,判断当前用户的机构级别与设备的类别大小
     * 机构级别: 1:市 2:县 3:乡 4:村
     * @param ids
     * @return
     * @throws CommonException
     */
    private Boolean judgmentCategory(String ids) throws CommonException {
        //当前用户机构级别
        User user = CommonUtil.getCurrentUser();
        Mydept mydept = mydeptService.selectOne(user.getDeptId());

        //目标设备机构级别
        Myequipment myequipment = iMyequipmentService.selectOne(Long.parseLong(ids));

        //判断用户级别是否拥有权限
        return mydept.getCategory() > myequipment.getEqDeptCategory();
//        if (mydept.getCategory() > myequipment.getEqDeptCategory()){
//            String message = "权限不足";
//            throw new CommonException(message);
//        }
    }

    /**
     * 返回当前用户机构类别下的机构
     * @return
     */
    @GetMapping("catetory")
    public CommonResponse getCategory() {
        HashMap<String, List> map = new HashMap<>();

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


        User user = CommonUtil.getCurrentUser();
        Mydept mydept = mydeptService.selectOne(user.getDeptId());
        Integer category = mydept.getCategory() - 1;
        List<Object> subList = list.subList(category < 0 ? 0 : category, list.size());
        return new CommonResponse().code(HttpStatus.OK).data(subList);
    }

}

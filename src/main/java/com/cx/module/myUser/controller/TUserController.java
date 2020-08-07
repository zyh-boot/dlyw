package com.cx.module.myUser.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.common.utils.Md5Util;
import com.cx.module.mobile.entity.CodeBean;
import com.cx.module.myUser.entity.TUser;
import com.cx.module.myUser.service.ITUserService;
import com.cx.module.userEq.entity.UserMyequipment;
import com.cx.module.userEq.service.IUserMyequipmentService;
import com.cx.system.entity.Role;
import com.cx.system.entity.UserRole;
import com.cx.system.service.IRoleService;
import com.cx.system.service.IUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户表  控制器
 *
 * @author admin
 * @Description Created on 2020-08-05
 */
@RestController
@Slf4j
@RequestMapping("myUser/tUser")
public class TUserController extends BaseController {
    @Autowired
    ITUserService iTUserService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    IUserMyequipmentService userMyequipmentService;

    /**
     * 查询详情
     */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException {
        try {
            return getCommonResponse(iTUserService.selectOne(id));
        } catch (Exception e) {
            String message = "查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @GetMapping("qx")
    public CommonResponse qw() {
        List<Role> list = roleService.list();
        return new CommonResponse().code(HttpStatus.OK).data(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("pageList")
    public CommonResponse pageList(TUser obj, QueryRequest query) throws CommonException {
        try {
            return getTableData(iTUserService.page(obj, query));
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
    public CommonResponse pageList(TUser obj) throws CommonException {
        try {
            return getCommonResponse(iTUserService.list(obj));
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
    @PreAuthorize("hasRole('tUser:add')")
    public CommonResponse add(TUser obj, String time, String myRole) throws CommonException {

        System.out.println(">>>>>>>>>>>>>>" + obj);
        try {
            LocalDateTime now = LocalDateTime.now();
            obj.setCreateTime(now);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(time, df);
            LocalTime localTime = LocalTime.now();
            LocalDateTime parse = LocalDateTime.of(localDate, localTime);
            obj.setOpen(parse);

            String encrypt = Md5Util.encrypt(obj.getUsername(), obj.getPassword());
            obj.setPassword(encrypt);
            int add = iTUserService.add(obj);


            LambdaQueryWrapper<TUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TUser::getUsername, obj.getUsername())
                    .eq(TUser::getAccountName, obj.getAccountName())
                    .eq(TUser::getIdcardNum, obj.getIdcardNum());

            TUser tUser = iTUserService.selectOne(wrapper);
            UserRole userRole = new UserRole();
            userRole.setUserId(tUser.getUserId());
            userRole.setRoleId(Long.parseLong(myRole));
            userRoleService.save(userRole);
            System.out.println(">>>>>>>>>>>>>>>>>>>" + tUser);
            return getCommonResponse(add);
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
    @PreAuthorize("hasRole('tUser:mod')")
    public CommonResponse update(TUser obj) throws CommonException {
        try {
            return getCommonResponse(iTUserService.update(obj));
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    @DeleteMapping("")
    @PreAuthorize("hasRole('tUser:del')")
    public CommonResponse delete(String ids) throws CommonException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>" + ids);
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iTUserService.batchDel(ids);
                } else {
                    iTUserService.delete(Long.valueOf(ids));
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
     * 设备绑定
     *
     * 功能: 添加设备; 删除名下设备
     * @param request
     * @param tUser
     * @param sbCode
     * @return
     * @throws Exception
     */
    @PostMapping("bind")
    @PreAuthorize("hasRole('tUser:bind')")
    public CommonResponse bind(HttpServletRequest request, TUser tUser, String sbCode) throws Exception {
        try {
            List<CodeBean> codeBeans = null;
            if (StringUtils.isNotBlank(sbCode)) {
                codeBeans = JSONArray.parseArray(sbCode, CodeBean.class);
            }
            LambdaQueryWrapper<UserMyequipment> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(UserMyequipment::getUserId, tUser.getUserId());
            List<UserMyequipment> list1 = userMyequipmentService.list(wrapper1);


            /************************实现穿梭框左移删除功能***************************/
            LinkedList<String> linkedList = new LinkedList<>();
            for (UserMyequipment userMyequipment : list1) {
                linkedList.add(userMyequipment.getMyequipmentId().toString());
            }

            LinkedList<String> linkedList1 = new LinkedList<>();
            for (CodeBean userMyequipment : codeBeans) {
                linkedList1.add(userMyequipment.getValue());
            }

            linkedList.removeAll(linkedList1);

            for (String str : linkedList) {
                LambdaQueryWrapper<UserMyequipment> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(UserMyequipment::getUserId, tUser.getUserId())
                        .eq(UserMyequipment::getMyequipmentId, str);
                userMyequipmentService.delete(wrapper);
            }
            /************************************************************/


            if (codeBeans.size() > 0) {
                for (CodeBean codeBean : codeBeans) {
                    LambdaQueryWrapper<UserMyequipment> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(UserMyequipment::getUserId, tUser.getUserId())
                            .eq(UserMyequipment::getMyequipmentId, codeBean.getValue());
                    List<UserMyequipment> list = userMyequipmentService.list(wrapper);

                    if (list.size() == 0) {
                        UserMyequipment userMyequipment = new UserMyequipment();
                        userMyequipment.setUserId(tUser.getUserId().intValue());
                        userMyequipment.setMyequipmentId(Integer.parseInt(codeBean.getValue()));
                        userMyequipmentService.add(userMyequipment);
                    }
                }
            }
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

}

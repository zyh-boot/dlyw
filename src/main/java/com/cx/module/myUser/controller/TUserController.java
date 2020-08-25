package com.cx.module.myUser.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.common.utils.CommonUtil;
import com.cx.common.utils.Md5Util;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
import com.cx.module.mobile.entity.CodeBean;
import com.cx.module.myUser.entity.TUser;
import com.cx.module.myUser.service.ITUserService;
import com.cx.module.mydept.entity.Mydept;
import com.cx.module.mydept.service.IMydeptService;
import com.cx.module.userEq.entity.UserMyequipment;
import com.cx.module.userEq.service.IUserMyequipmentService;
import com.cx.system.entity.Role;
import com.cx.system.entity.User;
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
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * 页面用户操作
 *
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

    @Autowired
    IMydeptService mydeptService;

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

    /**
     * 角色树
     *
     * @return
     */
    @GetMapping("qx")
    public CommonResponse qw() {
        List<Role> list = roleService.list();

//        //返回当前用户角色 机构下的列表. 例如:当前县级管理员 : 返回(县,乡,村)
//        User currentUser = CommonUtil.getCurrentUser();
//        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(UserRole::getUserId,currentUser.getUserId());
//        UserRole one = userRoleService.getOne(wrapper);
//        Role role = roleService.getById(one.getRoleId());
//
//        List<Role> list1 = new ArrayList<>();
//        for(int i=0;i<list.size();i++){
//            if(list.get(i).getRoleId()>=role.getRoleId()){
//                list1.add(list.get(i));
//            }
//        }
//        return new CommonResponse().code(HttpStatus.OK).data(list1);
        return new CommonResponse().code(HttpStatus.OK).data(list);
    }

    /**
     * 部门树
     *
     * @return
     */
    @GetMapping("dept")
    public CommonResponse dept() {
        List<Mydept> list = mydeptService.list(new Mydept());
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

            //添加用户到期时间
            LocalDateTime of = LocalDateTime.of(localDate.minusYears(-1), localTime);
            obj.setRemaing(of);
            obj.setLastLoginTime(parse);
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
     * 修改用户
     */
    @PutMapping("")
    @PreAuthorize("hasRole('tUser:mod')")
    public CommonResponse update(TUser obj,String myRemaing, @NotBlank(message = "{required}") String oldPassword, @NotBlank(message = "{required}") String newPassword) throws Exception {
        judgmentCategory(obj.getUserId().toString());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(myRemaing, df);
        obj.setRemaing(LocalDateTime.of(localDate,LocalTime.now()));

        if (!Md5Util.decrypt(oldPassword, obj.getPassword())) {
            throw new CommonException("原密码不正确");
        }

        try {
            obj.setPassword(Md5Util.encrypt(obj.getUsername(), newPassword));
            iTUserService.update(obj);
            return getCommonResponse().success();
        } catch (Exception e) {
            String message = "修改失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
     * 删除用户
     *
     * @param ids 用户ID
     * @return
     */

    @DeleteMapping("")
    @PreAuthorize("hasRole('tUser:del')")
    public CommonResponse delete(String ids) throws CommonException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>" + ids);

        judgmentCategory(ids);
//        //批量校验
//        if (StringUtils.isNotBlank(ids)) {
//            if (ids.contains(StringPool.COMMA)) {
//                for (String str : ids.split(StringPool.COMMA)) {
//                    if (judgmentCategory(str)) {
//                        String message = "权限不足, 列表存在上级机构人员!";
//                        throw new CommonException(message);
//                    }
//                }
//            } else {
//                if (judgmentCategory(ids)) {
//                    String message = "权限不足!";
//                    throw new CommonException(message);
//                }
//            }
//        }

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
     * 校验当前用户是否有权限操作
     * 规则: 查找目标用户级别,判断当前用户的机构级别大小
     * 无部门的人员机构id为-1, 任何人都可以操作
     * 机构级别: 1:市 2:县 3:乡 4:村
     *
     * @param ids
     * @throws CommonException
     */
    private void judgmentCategory(String ids) throws CommonException {
        //当前用户机构级别
        User user = CommonUtil.getCurrentUser();
        Mydept mydept = mydeptService.selectOne(user.getDeptId());

//        //目标用户机构级别
//        TUser tUser = iTUserService.selectOne(Long.parseLong(ids));
//        Mydept mydept1 = mydeptService.selectOne(tUser.getDeptId());

        //判断用户级别是否拥有权限
//        return mydept.getCategory() > mydept1.getCategory();

        //批量校验
        if (StringUtils.isNotBlank(ids)) {
            if (ids.contains(StringPool.COMMA)) {
                for (String str : ids.split(StringPool.COMMA)) {
                    //目标用户机构级别
                    TUser tUser = iTUserService.selectOne(Long.parseLong(str));
                    Mydept mydept1 = mydeptService.selectOne(tUser.getDeptId());
                   if (mydept1 != null){
                       if (mydept.getCategory() > mydept1.getCategory()) {
                           String message = "权限不足, 列表存在上级机构人员!";
                           throw new CommonException(message);
                       }
                   }
                }
            } else {
                //目标用户机构级别
                TUser tUser = iTUserService.selectOne(Long.parseLong(ids));
                Mydept mydept1 = mydeptService.selectOne(tUser.getDeptId());
                if (mydept1 != null){
                    if (mydept.getCategory() > mydept1.getCategory()) {
                        String message = "权限不足!";
                        throw new CommonException(message);
                    }
                }

            }
        }

    }

    /**
     * 设备绑定
     * <p>
     * 功能: 添加设备; 删除名下设备
     *
     * @param request
     * @param tUser
     * @param sbCode
     * @return
     * @throws Exception
     */

    @Autowired
    IMyequipmentService myequipmentService;

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

                        /*********************************************/
                        //向设备表添加机构Id,name,category
                        Myequipment myequipment = new Myequipment();
                        myequipment.setEqDeptId(tUser.getDeptId());
                        myequipment.setEqDeptName(tUser.getDeptName());

                        Mydept mydept = mydeptService.selectOne(tUser.getDeptId());
                        myequipment.setEqDeptCategory(mydept.getCategory());

                        LambdaQueryWrapper<Myequipment> myeqWrapper = new LambdaQueryWrapper<>();
                        myeqWrapper.eq(Myequipment::getId, Long.parseLong(codeBean.getValue()));
                        myequipmentService.updateByWrapper(myequipment, myeqWrapper);
                        /*********************************************/
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

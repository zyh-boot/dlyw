package com.cx.module.mobile.controller;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cx.module.mobile.entity.AccountEquipment;
import com.cx.module.mobile.service.IAccountEquipmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cx.module.mobile.entity.Account;
import com.cx.module.mobile.service.IAccountService;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import com.cx.common.controller.BaseController;

/**
 * 控制器
 *
 * @author admin
 * @Description Created on 2020-05-08
 */
@RestController
@Slf4j
@RequestMapping("mobile/account")
public class AccountController extends BaseController {
    @Autowired
    IAccountService iAccountService;
    @Autowired
    IAccountEquipmentService  accountEquipmentService;
    /**
     * 查询详情
     */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException {
        try {
            return getCommonResponse(iAccountService.selectOne(id));
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
    public CommonResponse pageList(Account obj, QueryRequest query) throws CommonException {
        try {
            return getTableData(iAccountService.page(obj, query));
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
    public CommonResponse pageList(Account obj) throws CommonException {
        try {
            return getCommonResponse(iAccountService.list(obj));
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
    @PreAuthorize("hasRole('account:add')")
    public CommonResponse add(Account obj) throws CommonException {
        try {
            return getCommonResponse(iAccountService.add(obj));
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
    @PreAuthorize("hasRole('account:mod')")
    public CommonResponse update(Account obj) throws CommonException {
        try {
            return getCommonResponse(iAccountService.update(obj));
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
    @PreAuthorize("hasRole('account:del')")
    public CommonResponse delete(String ids) throws CommonException {
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iAccountService.batchDel(ids);
                } else {
                    iAccountService.delete(Long.valueOf(ids));
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
     * 绑定设备
     */
    @PostMapping("/sb")
    @PreAuthorize("hasRole('account:sb')")
    public CommonResponse khcode(HttpServletRequest request, AccountEquipment ae) throws CommonException {
        try {
            accountEquipmentService.delectByKh(ae.getKhId()+"");
            String sbCode= ae.getSbCode();
            JSONArray ja=JSONArray.parseArray(sbCode);
            if(ja!=null&&ja.size()>0){
                for (int i = 0; i <ja.size() ; i++) {
                    JSONObject json= (JSONObject) ja.get(i);
                    AccountEquipment khsb=new AccountEquipment();
                    String code=   json.getString("title");
                    khsb.setKhId(ae.getKhId());
                    khsb.setSbCode(code);
                    accountEquipmentService.add(khsb);
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

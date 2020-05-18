package com.cx.system.controller;


import com.cx.common.annotation.Log;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.system.entity.Role;
import com.cx.system.service.IRoleService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator·
 */
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public CommonResponse getAllRoles(Role role) {
        return new CommonResponse().success().data(roleService.findRoles(role));
    }

    @GetMapping("list")
    @PreAuthorize("hasRole('role:view')")
    public CommonResponse roleList(Role role, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.roleService.findRoles(role, request));
        return new CommonResponse().success().data(dataTable);
    }

    @Log("新增角色")
    @PostMapping
    @PreAuthorize("hasRole('role:add')")
    public CommonResponse addRole(@Valid Role role) throws CommonException {
        try {
            this.roleService.createRole(role);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "新增角色失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("删除角色")
    @GetMapping("delete/{roleIds}")
    @PreAuthorize("hasRole('role:delete')")
    public CommonResponse deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) throws CommonException {
        try {
            this.roleService.deleteRoles(roleIds);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "删除角色失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("修改角色")
    @PostMapping("update")
    @PreAuthorize("hasRole('role:update')")
    public CommonResponse updateRole(Role role) throws CommonException {
        try {
            this.roleService.updateRole(role);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改角色失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @GetMapping("excel")
    @PreAuthorize("hasRole('role:export')")
    public void export(QueryRequest queryRequest, Role role, HttpServletResponse response) throws CommonException {
        try {
            List<Role> roles = this.roleService.findRoles(role, queryRequest).getRecords();
            ExcelKit.$Export(Role.class, response).downXlsx(roles, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

}

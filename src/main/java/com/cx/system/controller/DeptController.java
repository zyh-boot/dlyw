package com.cx.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.annotation.Log;
import com.cx.common.entity.DeptTree;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.system.entity.Dept;
import com.cx.system.service.IDeptService;
import com.cx.common.annotation.Log;
import com.cx.system.entity.Dept;
import com.cx.system.service.IDeptService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator·
 */
@Slf4j
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private IDeptService deptService;

    /**
     * @param level 1显示    123级
     * @return
     * @throws CommonException
     */
    @GetMapping("select/tree")
    @ResponseBody
    public List<DeptTree<Dept>> getDeptTree(Integer levelFlag) throws CommonException {
        try {
            return this.deptService.findDepts(levelFlag);
        } catch (Exception e) {
            String message = "获取部门树失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
     * 只获取机构（不含下属部门）树（下拉选使用）
     *
     * @return
     * @throws CommonException
     */
    @GetMapping("select/treeone")
    @ResponseBody
    public List<DeptTree<Dept>> treeone() throws CommonException {
        try {
            return this.deptService.findOneDepts();
        } catch (Exception e) {
            String message = "获取机构失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }


    @GetMapping("tree")
    @ApiModelProperty("tree")
    public CommonResponse getDeptTree(Dept dept) throws CommonException {
        try {
            List<DeptTree<Dept>> depts = this.deptService.findDepts(dept);
            return new CommonResponse().success().data(depts);
        } catch (Exception e) {
            String message = "获取部门失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("新增部门")
    @PostMapping
    @PreAuthorize("hasRole('dept:add')")
    public CommonResponse addDept(@Valid Dept dept) throws CommonException {
        try {
            if (dept.getParentId() == null) {
                dept.setLevel(Dept.LEVEL_0);
            }
            this.deptService.createDept(dept);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "新增部门失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("删除部门")
    @GetMapping("delete/{deptIds}")
    @PreAuthorize("hasRole('dept:delete')")
    public CommonResponse deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) throws CommonException {
        try {
            String[] ids = deptIds.split(StringPool.COMMA);
            this.deptService.deleteDepts(ids);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "删除部门失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("修改部门")
    @PostMapping("update")
    @PreAuthorize("hasRole('dept:update')")
    public CommonResponse updateDept(@Valid Dept dept) throws CommonException {
        try {
            this.deptService.updateDept(dept);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改部门失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @GetMapping("excel")
    @PreAuthorize("hasRole('dept:export')")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) throws CommonException {
        try {
            List<Dept> depts = this.deptService.findDepts(dept, request);
            ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }
}

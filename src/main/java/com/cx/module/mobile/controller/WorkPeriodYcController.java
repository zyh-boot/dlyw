package com.cx.module.mobile.controller;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cx.module.mobile.entity.WorkPeriodYc;
import com.cx.module.mobile.service.IWorkPeriodYcService;

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
*   控制器
*
* @author admin
* @Description Created on 2020-05-08
*/
@RestController
@Slf4j
@RequestMapping("mobile/workPeriodYc")
        public class WorkPeriodYcController extends BaseController{
    @Autowired
    IWorkPeriodYcService iWorkPeriodYcService;

    /**
    * 查询详情
    */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException{
        try {
            return getCommonResponse(iWorkPeriodYcService.selectOne(id));
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
    public CommonResponse pageList(WorkPeriodYc obj,QueryRequest query)  throws CommonException{
        try {
            return  getTableData(iWorkPeriodYcService.page(obj,query));
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
    public CommonResponse pageList(WorkPeriodYc obj)  throws CommonException{
        try {
            return getCommonResponse(iWorkPeriodYcService.list(obj));
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
    @PreAuthorize("hasRole('workPeriodYc:add')")
    public CommonResponse add(WorkPeriodYc obj) throws CommonException{
        try {
            return getCommonResponse(iWorkPeriodYcService.add(obj));
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
    @PreAuthorize("hasRole('workPeriodYc:mod')")
    public CommonResponse update(WorkPeriodYc obj) throws CommonException{
        try {
            return getCommonResponse(iWorkPeriodYcService.update(obj));
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
    @PreAuthorize("hasRole('workPeriodYc:del')")
    public CommonResponse delete(String ids) throws CommonException{
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iWorkPeriodYcService.batchDel(ids);
                } else {
                    iWorkPeriodYcService.delete(Long.valueOf(ids));
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

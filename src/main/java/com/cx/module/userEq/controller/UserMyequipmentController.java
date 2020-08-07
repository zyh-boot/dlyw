package com.cx.module.userEq.controller;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cx.module.userEq.entity.UserMyequipment;
import com.cx.module.userEq.service.IUserMyequipmentService;

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
* @Description Created on 2020-08-07
*/
@RestController
@Slf4j
@RequestMapping("userEq/userMyequipment")
        public class UserMyequipmentController extends BaseController{
    @Autowired
    IUserMyequipmentService iUserMyequipmentService;

    /**
    * 查询详情
    */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException{
        try {
            return getCommonResponse(iUserMyequipmentService.selectOne(id));
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
    public CommonResponse pageList(UserMyequipment obj,QueryRequest query)  throws CommonException{
        try {
            return  getTableData(iUserMyequipmentService.page(obj,query));
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
    public CommonResponse pageList(UserMyequipment obj)  throws CommonException{
        try {
            return getCommonResponse(iUserMyequipmentService.list(obj));
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
    @PreAuthorize("hasRole('userMyequipment:add')")
    public CommonResponse add(UserMyequipment obj) throws CommonException{
        try {
            return getCommonResponse(iUserMyequipmentService.add(obj));
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
    @PreAuthorize("hasRole('userMyequipment:mod')")
    public CommonResponse update(UserMyequipment obj) throws CommonException{
        try {
            return getCommonResponse(iUserMyequipmentService.update(obj));
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
    @PreAuthorize("hasRole('userMyequipment:del')")
    public CommonResponse delete(String ids) throws CommonException{
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iUserMyequipmentService.batchDel(ids);
                } else {
                    iUserMyequipmentService.delete(Long.valueOf(ids));
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

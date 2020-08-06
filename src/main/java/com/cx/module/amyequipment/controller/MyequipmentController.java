package com.cx.module.amyequipment.controller;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.module.amyequipment.entity.Myequipment;
import com.cx.module.amyequipment.service.IMyequipmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
* 存放设备相关数据  控制器
*
* @author admin
* @Description Created on 2020-08-06
*/
@RestController
@Slf4j
@RequestMapping("amyequipment/myequipment")
        public class MyequipmentController extends BaseController{
    @Autowired
    IMyequipmentService iMyequipmentService;

    /**
    * 查询详情
    */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException{
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
    public CommonResponse pageList(Myequipment obj,QueryRequest query)  throws CommonException{
        try {
            return  getTableData(iMyequipmentService.page(obj,query));
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
    public CommonResponse pageList(Myequipment obj)  throws CommonException{
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
    public CommonResponse add(Myequipment obj) throws CommonException{
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
    public CommonResponse update(Myequipment obj) throws CommonException{
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
    * @param ids
    * @return
    */
    @DeleteMapping("")
    @PreAuthorize("hasRole('myequipment:del')")
    public CommonResponse delete(String ids) throws CommonException{
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

}

package com.cx.module.mobile.controller;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cx.module.mobile.entity.EquipmentTobaccoHistroy;
import com.cx.module.mobile.service.IEquipmentTobaccoHistroyService;

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
* 烟炕设备历史数据  控制器
*
* @author admin
* @Description Created on 2020-05-18
*/
@RestController
@Slf4j
@RequestMapping("mobile/equipmentTobaccoHistroy")
        public class EquipmentTobaccoHistroyController extends BaseController{
    @Autowired
    IEquipmentTobaccoHistroyService iEquipmentTobaccoHistroyService;

    /**
    * 查询详情
    */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException{
        try {
            return getCommonResponse(iEquipmentTobaccoHistroyService.selectOne(id));
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
    public CommonResponse pageList(EquipmentTobaccoHistroy obj,QueryRequest query)  throws CommonException{
        try {
            return  getTableData(iEquipmentTobaccoHistroyService.page(obj,query));
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
    public CommonResponse pageList(EquipmentTobaccoHistroy obj)  throws CommonException{
        try {
            return getCommonResponse(iEquipmentTobaccoHistroyService.list(obj));
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
    @PreAuthorize("hasRole('equipmentTobaccoHistroy:add')")
    public CommonResponse add(EquipmentTobaccoHistroy obj) throws CommonException{
        try {
            return getCommonResponse(iEquipmentTobaccoHistroyService.add(obj));
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
    @PreAuthorize("hasRole('equipmentTobaccoHistroy:mod')")
    public CommonResponse update(EquipmentTobaccoHistroy obj) throws CommonException{
        try {
            return getCommonResponse(iEquipmentTobaccoHistroyService.update(obj));
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
    @PreAuthorize("hasRole('equipmentTobaccoHistroy:del')")
    public CommonResponse delete(String ids) throws CommonException{
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iEquipmentTobaccoHistroyService.batchDel(ids);
                } else {
                    iEquipmentTobaccoHistroyService.delete(Long.valueOf(ids));
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

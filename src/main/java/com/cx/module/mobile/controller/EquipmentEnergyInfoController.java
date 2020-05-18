package com.cx.module.mobile.controller;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cx.module.mobile.entity.EquipmentEnergyInfo;
import com.cx.module.mobile.service.IEquipmentEnergyInfoService;

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
@RequestMapping("mobile/equipmentEnergyInfo")
        public class EquipmentEnergyInfoController extends BaseController{
    @Autowired
    IEquipmentEnergyInfoService iEquipmentEnergyInfoService;

    /**
    * 查询详情
    */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException{
        try {
            return getCommonResponse(iEquipmentEnergyInfoService.selectOne(id));
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
    public CommonResponse pageList(EquipmentEnergyInfo obj,QueryRequest query)  throws CommonException{
        try {
            return  getTableData(iEquipmentEnergyInfoService.page(obj,query));
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
    public CommonResponse pageList(EquipmentEnergyInfo obj)  throws CommonException{
        try {
            return getCommonResponse(iEquipmentEnergyInfoService.list(obj));
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
    @PreAuthorize("hasRole('equipmentEnergyInfo:add')")
    public CommonResponse add(EquipmentEnergyInfo obj) throws CommonException{
        try {
            return getCommonResponse(iEquipmentEnergyInfoService.add(obj));
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
    @PreAuthorize("hasRole('equipmentEnergyInfo:mod')")
    public CommonResponse update(EquipmentEnergyInfo obj) throws CommonException{
        try {
            return getCommonResponse(iEquipmentEnergyInfoService.update(obj));
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
    @PreAuthorize("hasRole('equipmentEnergyInfo:del')")
    public CommonResponse delete(String ids) throws CommonException{
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iEquipmentEnergyInfoService.batchDel(ids);
                } else {
                    iEquipmentEnergyInfoService.delete(Long.valueOf(ids));
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

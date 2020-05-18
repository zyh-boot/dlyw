package com.cx.module.mobile.controller;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cx.module.mobile.entity.EquipmentTobaccoAttr;
import com.cx.module.mobile.service.IEquipmentTobaccoAttrService;

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
* 烟炕设备属性  控制器
*
* @author admin
* @Description Created on 2020-05-18
*/
@RestController
@Slf4j
@RequestMapping("mobile/equipmentTobaccoAttr")
        public class EquipmentTobaccoAttrController extends BaseController{
    @Autowired
    IEquipmentTobaccoAttrService iEquipmentTobaccoAttrService;

    /**
    * 查询详情
    */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException{
        try {
            return getCommonResponse(iEquipmentTobaccoAttrService.selectOne(id));
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
    public CommonResponse pageList(EquipmentTobaccoAttr obj,QueryRequest query)  throws CommonException{
        try {
            return  getTableData(iEquipmentTobaccoAttrService.page(obj,query));
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
    public CommonResponse pageList(EquipmentTobaccoAttr obj)  throws CommonException{
        try {
            return getCommonResponse(iEquipmentTobaccoAttrService.list(obj));
        } catch (Exception e) {
            String message = "列表查询失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
     * 新增或修改
     */
    @PostMapping("addOrUpdate")
    public CommonResponse addOrUpdate(EquipmentTobaccoAttr obj) throws CommonException {
        try {
            return getCommonResponse(iEquipmentTobaccoAttrService.addOrUpdate(obj));
        } catch (Exception e) {
            String message = "新增或修改";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    /**
    * 新增
    */
    @PostMapping("")
    @PreAuthorize("hasRole('equipmentTobaccoAttr:add')")
    public CommonResponse add(EquipmentTobaccoAttr obj) throws CommonException{
        try {
            return getCommonResponse(iEquipmentTobaccoAttrService.add(obj));
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
    @PreAuthorize("hasRole('equipmentTobaccoAttr:mod')")
    public CommonResponse update(EquipmentTobaccoAttr obj) throws CommonException{
        try {
            return getCommonResponse(iEquipmentTobaccoAttrService.update(obj));
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
    @PreAuthorize("hasRole('equipmentTobaccoAttr:del')")
    public CommonResponse delete(String ids) throws CommonException{
        try {
            if (StringUtils.isNotBlank(ids)) {
                if (ids.contains(StringPool.COMMA)) {
                    iEquipmentTobaccoAttrService.batchDel(ids);
                } else {
                    iEquipmentTobaccoAttrService.delete(Long.valueOf(ids));
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

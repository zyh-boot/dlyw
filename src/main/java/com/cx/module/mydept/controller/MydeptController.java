package com.cx.module.mydept.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.common.utils.CommonUtil;
import com.cx.module.myUser.service.ITUserService;
import com.cx.module.mydept.entity.Mydept;
import com.cx.module.mydept.service.IMydeptService;
import com.cx.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器
 *
 * @author admin
 * @Description Created on 2020-08-05
 */
@RestController
@Slf4j
@RequestMapping("mydept/mydept")
public class MydeptController extends BaseController {
    @Autowired
    IMydeptService iMydeptService;

    /**
     * 查询详情
     */
    @GetMapping("detail")
    public CommonResponse add(Long id) throws CommonException {
        try {
            return getCommonResponse(iMydeptService.selectOne(id));
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
    public CommonResponse pageList(Mydept obj, QueryRequest query) throws CommonException {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>" + obj);
            IPage<Mydept> page = iMydeptService.page(obj, query);
            for (Mydept mydept : page.getRecords()) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>" + mydept);
            }

            return getTableData(iMydeptService.page(obj, query));
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
    public CommonResponse pageList(Mydept obj) throws CommonException {
        try {
            return getCommonResponse(iMydeptService.list(obj));
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
    @PreAuthorize("hasRole('mydept:add')")
    public CommonResponse add(Mydept obj) throws CommonException {
        try {
            return getCommonResponse(iMydeptService.add(obj));
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
    @PreAuthorize("hasRole('mydept:mod')")
    public CommonResponse update(Mydept obj) throws CommonException {
        judgmentCategory(obj.getCategory().toString());
        try {
            return getCommonResponse(iMydeptService.update(obj));
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
    @Autowired
    IMydeptService mydeptService;
    @Autowired
    ITUserService iTUserService;

    @DeleteMapping("")
    @PreAuthorize("hasRole('mydept:del')")
    public CommonResponse delete(String ids) throws CommonException {
        judgmentCategory(ids);
//        //批量校验
//        if (StringUtils.isNotBlank(ids)) {
//            if (ids.contains(StringPool.COMMA)) {
//                for (String str : ids.split(StringPool.COMMA)) {
//                    if (judgmentCategory(str)) {
//                        String message = "权限不足, 列表存在上级机构!";
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
                    iMydeptService.batchDel(ids);
                } else {
                    iMydeptService.delete(Long.valueOf(ids));
                }
            }
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "删除失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    private void judgmentCategory(String ids) throws CommonException {
        //当前用户机构级别
        User user = CommonUtil.getCurrentUser();
        Mydept mydept = mydeptService.selectOne(user.getDeptId());

        //目标用户机构级别
//        Mydept mydept1 = mydeptService.selectOne(Long.parseLong(ids));
        //判断用户级别是否拥有权限
//        return mydept.getCategory() > mydept1.getCategory();


        //批量校验
        if (StringUtils.isNotBlank(ids)) {
            if (ids.contains(StringPool.COMMA)) {
                for (String str : ids.split(StringPool.COMMA)) {
                    //目标用户机构级别
                    Mydept mydept1 = mydeptService.selectOne(Long.parseLong(ids));
                    if (mydept1 != null){
                        if (mydept.getCategory() > mydept1.getCategory()) {
                            String message = "权限不足, 列表存在上级机构!";
                            throw new CommonException(message);
                        }
                    }
                }
            } else {
                Mydept mydept1 = mydeptService.selectOne(Long.parseLong(ids));
                if (mydept1 != null){
                    if (mydept.getCategory() > mydept1.getCategory()) {
                        String message = "权限不足!";
                        throw new CommonException(message);
                    }
                }
            }
        }
    }

}

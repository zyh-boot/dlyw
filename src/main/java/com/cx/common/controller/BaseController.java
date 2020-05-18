package com.cx.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cx.common.entity.Constant;
import com.cx.common.entity.CommonResponse;
import com.cx.common.utils.SecurityUtils;
import com.cx.system.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator·
 */
public class BaseController {

    protected User getCurrentUser() {

        return SecurityUtils.getUser();
    }

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        return getDataTable(pageInfo, Constant.DATA_MAP_INITIAL_CAPACITY);
    }
    protected Map<String, Object> getDataTableList(List<?> pageInfo) {
        return getDataTable(pageInfo, Constant.DATA_MAP_INITIAL_CAPACITY);
    }

    protected CommonResponse getTableData(IPage<?> pageInfo) {
        return new CommonResponse().success().data(getDataTable(pageInfo, Constant.DATA_MAP_INITIAL_CAPACITY));
    }

    protected CommonResponse getCommonResponse() {
        return new CommonResponse().success();
    }

    protected CommonResponse getCommonResponse(Object obj) {
        return new CommonResponse().success().data(obj);
    }

    protected CommonResponse getCommonResponseMap(Map obj) {
        Map<String, Object> data = new HashMap<>();
        Map pa = (Map) obj.get("page");
        data.put("rows",obj.get("data"));
        data.put("total",pa.get("total"));
        return new CommonResponse().success().data(data);
    }

    protected CommonResponse delete(Object obj) {
        return new CommonResponse().success().data(obj);
    }

    protected Map<String, Object> getDataTable(IPage<?> pageInfo, int dataMapInitialCapacity) {
        Map<String, Object> data = new HashMap<>(dataMapInitialCapacity);
        data.put("rows", pageInfo.getRecords());
        data.put("total", pageInfo.getTotal());
        return data;
    }

    protected Map<String, Object> getDataTable(List<?> pageInfo, int dataMapInitialCapacity) {
        Map<String, Object> data = new HashMap<>(dataMapInitialCapacity);
        data.put("rows", pageInfo);
        data.put("total", pageInfo.size());
        return data;
    }
}

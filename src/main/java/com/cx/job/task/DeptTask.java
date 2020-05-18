package com.cx.job.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cx.common.utils.HttpClientUtils;
import com.cx.common.webSocket.WebSocketServer;
import com.cx.job.entity.Job;
import com.cx.job.service.IJobService;
import com.cx.system.entity.Dept;
import com.cx.system.service.IDeptService;
import com.cx.system.service.IDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 班组部门同步
 *
 * @author Administrator
 * @date 2019/09/28
 */
@Slf4j
@Component
public class DeptTask {

    @Autowired
    IJobService iJobService;

    public void test() throws IOException {
        System.out.println("--------------------------------------------------------");
        WebSocketServer.sendInfoEver("10s发一次");
    }
}

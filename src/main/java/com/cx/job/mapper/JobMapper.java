package com.cx.job.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cx.job.entity.Job;

import java.util.List;

/**
 * @author Administrator·
 */
public interface JobMapper extends BaseMapper<Job> {
    /**
     * 查询任务列表
     *
     * @return
     */
    List<Job> queryList();
}
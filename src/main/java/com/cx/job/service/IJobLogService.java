package com.cx.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cx.common.entity.QueryRequest;
import com.cx.job.entity.JobLog;

/**
 * @author Administrator·
 */
public interface IJobLogService extends IService<JobLog> {
    /**
     * 查询日志
     *
     * @param request
     * @param jobLog
     * @return
     */
    IPage<JobLog> findJobLogs(QueryRequest request, JobLog jobLog);

    /**
     * 保存job日志
     *
     * @param log
     */
    void saveJobLog(JobLog log);

    /**
     * 批量删除job日志
     *
     * @param jobLogIds
     */
    void deleteJobLogs(String[] jobLogIds);
}

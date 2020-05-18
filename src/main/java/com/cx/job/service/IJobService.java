package com.cx.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cx.common.entity.QueryRequest;
import com.cx.job.entity.Job;

/**
 * @author Administrator·
 */
public interface IJobService extends IService<Job> {

    /**
     * 查询job
     *
     * @param jobId
     * @return
     */
    Job findJob(Long jobId);

    /**
     * 查询job列表
     *
     * @param request
     * @param job
     * @return
     */
    IPage<Job> findJobs(QueryRequest request, Job job);

    /**
     * 创建job
     *
     * @param job
     */
    void createJob(Job job);


    /**
     * 更新job
     *
     * @param job
     */
    void updateJob(Job job);

    /**
     * 删除job
     *
     * @param jobIds
     */
    void deleteJobs(String[] jobIds);

    /**
     * 批量更新job
     *
     * @param jobIds
     * @param status
     * @return
     */
    int updateBatch(String jobIds, String status);


    /**
     * 运行job
     *
     * @param jobIds
     */
    void run(String jobIds);


    /**
     * 插入job
     *
     * @param jobIds
     */
    void pause(String jobIds);

    /**
     * 暂停job
     *
     * @param jobIds
     */
    void resume(String jobIds);

}

package com.cx.job.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.annotation.Log;
import com.cx.common.controller.BaseController;
import com.cx.common.entity.CommonResponse;
import com.cx.common.entity.QueryRequest;
import com.cx.common.exception.CommonException;
import com.cx.job.entity.Job;
import com.cx.job.service.IJobService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator·
 */
@Slf4j
@Validated
@RestController
@RequestMapping("job")
public class JobController extends BaseController {

    @Autowired
    private IJobService jobService;

    @GetMapping
    @PreAuthorize("hasRole('job:view')")
    public CommonResponse jobList(QueryRequest request, Job job) {
        Map<String, Object> dataTable = getDataTable(this.jobService.findJobs(request, job));
        return new CommonResponse().success().data(dataTable);
    }

    @GetMapping("cron/check")
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    @Log("新增定时任务")
    @PostMapping
    @PreAuthorize("hasRole('job:add')")
    public CommonResponse addJob(@Valid Job job) throws CommonException {
        try {
            this.jobService.createJob(job);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "新增定时任务失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("删除定时任务")
    @GetMapping("delete/{jobIds}")
    @PreAuthorize("hasRole('job:delete')")
    public CommonResponse deleteJob(@NotBlank(message = "{required}") @PathVariable String jobIds) throws CommonException {
        try {
            String[] ids = jobIds.split(StringPool.COMMA);
            this.jobService.deleteJobs(ids);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "删除定时任务失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("修改定时任务")
    @PostMapping("update")
    public CommonResponse updateJob(@Valid Job job) throws CommonException {
        try {
            this.jobService.updateJob(job);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "修改定时任务失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("执行定时任务")
    @PreAuthorize("hasRole('job:run')")
    @GetMapping("run/{jobIds}")
    public CommonResponse runJob(@NotBlank(message = "{required}") @PathVariable String jobIds) throws CommonException {
        try {
            this.jobService.run(jobIds);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "执行定时任务失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("暂停定时任务")
    @GetMapping("pause/{jobIds}")
    @PreAuthorize("hasRole('job:pause')")
    public CommonResponse pauseJob(@NotBlank(message = "{required}") @PathVariable String jobIds) throws CommonException {
        try {
            this.jobService.pause(jobIds);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "暂停定时任务失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @Log("恢复定时任务")
    @GetMapping("resume/{jobIds}")
    @PreAuthorize("hasRole('job:resume')")
    public CommonResponse resumeJob(@NotBlank(message = "{required}") @PathVariable String jobIds) throws CommonException {
        try {
            this.jobService.resume(jobIds);
            return new CommonResponse().success();
        } catch (Exception e) {
            String message = "恢复定时任务失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }

    @GetMapping("excel")
    @PreAuthorize("hasRole('job:export')")
    public void export(QueryRequest request, Job job, HttpServletResponse response) throws CommonException {
        try {
            List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
            ExcelKit.$Export(Job.class, response).downXlsx(jobs, false);
        } catch (Exception e) {
            String message = "导出Excel失败";
            log.error(message, e);
            throw new CommonException(message);
        }
    }
}

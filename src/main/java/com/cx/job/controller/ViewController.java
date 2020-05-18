package com.cx.job.controller;

import com.cx.common.entity.Constant;
import com.cx.common.utils.CommonUtil;
import com.cx.job.entity.Job;
import com.cx.job.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotBlank;

/**
 * @author Administrator·
 */
@Controller("jobView")
@RequestMapping(Constant.VIEW_PREFIX + "job")
public class ViewController {

    @Autowired
    private IJobService jobService;

    @GetMapping("job")
    @PreAuthorize("hasRole('job:view')")
    public String online() {
        return CommonUtil.view("job/job");
    }

    @GetMapping("job/add")
    @PreAuthorize("hasRole('job:add')")
    public String jobAdd() {
        return CommonUtil.view("job/jobAdd");
    }

    @GetMapping("job/update/{jobId}")
    @PreAuthorize("hasRole('job:update')")
    public String jobUpdate(@NotBlank(message = "{required}") @PathVariable Long jobId, Model model) {
        Job job = jobService.findJob(jobId);
        model.addAttribute("job", job);
        return CommonUtil.view("job/jobUpdate");
    }

    @GetMapping("log")
    @PreAuthorize("hasRole('job:log:view')")
    public String log() {
        return CommonUtil.view("job/jobLog");
    }

}

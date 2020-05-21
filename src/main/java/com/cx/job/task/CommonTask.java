package com.cx.job.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cx.job.entity.Job;
import com.cx.job.service.IJobService;
import com.cx.module.mobile.entity.SendCommand;
import com.cx.module.mobile.service.ISendCommandService;
import com.cx.netty.Client;
import com.cx.netty.NettyWriteUtils;
import com.cx.system.service.IDeptService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator·
 */
@Slf4j
@Component
public class CommonTask {
    @Autowired
    ISendCommandService iSendCommandService;
    @Autowired
    IJobService iJobService;
    public void send(String params) {
        if(StringUtils.isNotEmpty(params)){
            List<SendCommand> list= iSendCommandService.list(null);
            if(list!=null&&list.size()>0){
                int paramNum=Integer.parseInt(params);
                int numSize=list.size()-1;
                SendCommand command=null;
                if(paramNum>numSize){
                    paramNum=0;
                    command=list.get(paramNum);
                }else{
                    command=list.get(paramNum);
                }
                ChannelHandlerContext ctx= Client.getMap().get(command.getSbCode());
                if(ctx!=null){
                    NettyWriteUtils.writeToClient(command.getCommand(),ctx,"CXZN");

                    Client.getMessageMap().put(command.getSbCode(),command);
                }
                LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Job::getBeanName,"commonTask");
                queryWrapper.eq(Job::getMethodName,"send");
                Job job = iJobService.getOne(queryWrapper);
                job.setParams((paramNum+1)+"");
                iJobService.updateJob(job);
            }
        }
    }

}

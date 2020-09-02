package com.fyc.bos.jobs;

import com.fyc.bos.service.take_delivery.PromotionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.util.Date;

public class PromotionJob implements Job {
        @Resource
        private PromotionService promotionService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //结束时间是否小于系统时间，如果是的话，自动更新status，改为0。
        System.out.println("PromotionJob任务被触发...");
        promotionService.updateStatus(new Date());
    }
}

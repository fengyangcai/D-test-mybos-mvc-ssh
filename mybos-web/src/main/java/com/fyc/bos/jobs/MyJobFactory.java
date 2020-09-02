package com.fyc.bos.jobs;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
/*
 * 自定义JobFactory对象，重写createJobInstance方法
 */

@Component(value="jobFactory")
public class MyJobFactory extends AdaptableJobFactory {

    @Resource
    private AutowireCapableBeanFactory factory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //调用父类的原有的方法创建JobDetail对象
        Object jobInstance = super.createJobInstance(bundle);
        //把jobInstance放入SpringIOC容器中管理
        factory.autowireBean(jobInstance);

        return jobInstance;
    }
}

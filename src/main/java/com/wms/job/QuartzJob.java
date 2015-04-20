package com.wms.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.wms.task.GrabInfoTask;

@Component
public class QuartzJob extends QuartzJobBean{
    
    private GrabInfoTask grabInfoTask;
    
    public void setGrabInfoTask(GrabInfoTask grabInfoTask) {
        this.grabInfoTask = grabInfoTask;
    }

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        // TODO Auto-generated method stub
        grabInfoTask.sayHello();
    }
    

}

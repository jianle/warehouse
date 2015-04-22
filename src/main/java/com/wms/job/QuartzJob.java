package com.wms.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wms.task.GrabInfoTask;

public class QuartzJob extends QuartzJobBean {
    
    private Logger logger = LoggerFactory.getLogger(QuartzJob.class);
    
    private GrabInfoTask grabInfoTask;
    
    public void setGrabInfoTask(GrabInfoTask grabInfoTask) {
        this.grabInfoTask = grabInfoTask;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        logger.info("------ running ------");
        grabInfoTask.execute();
    }
    

}
package com.wms.job;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.wms.dao.DeliveryDao;
import com.wms.dao.DeliveryDetailDao;
import com.wms.task.GrabTask;

@Component
public class QuartzJob extends QuartzJobBean {
    
    private Logger logger = LoggerFactory.getLogger(QuartzJob.class);
    
    private Integer maxThreadNum;
    
    public void setMaxThreadNum(Integer maxThreadNum) {
        this.maxThreadNum = maxThreadNum;
    }
    
    private DeliveryDao deliveryDao;
    public void setDeliveryDao(DeliveryDao deliveryDao) {
        this.deliveryDao = deliveryDao;
    }
    
    private DeliveryDetailDao deliveryDetailDao;
    
    public void setDeliveryDetailDao(DeliveryDetailDao deliveryDetailDao) {
        this.deliveryDetailDao = deliveryDetailDao;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        logger.info("------ running ------maxThreadNum:" + this.maxThreadNum);
        runJob();
    }
    
    private void runJob() {
        ExecutorService pool = Executors.newFixedThreadPool(this.maxThreadNum);
        if (deliveryDao == null) {
            logger.debug("deliveryDao is null");
            return;
        }
        List<Map<String, Object>> list = deliveryDao.getAllContent();
        if (list == null) {
            logger.debug("Not find content.");
            return ;
        }
        for (int i = 0; i < list.size(); i++) {
            pool.execute(new GrabTask(deliveryDetailDao, String.valueOf(list.get(i).get("content"))));
        }
        pool.shutdown();
    }
    
}

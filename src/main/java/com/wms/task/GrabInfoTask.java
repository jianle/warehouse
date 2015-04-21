package com.wms.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wms.dao.OrderinfoDao;

@Component
@Scope("prototype")
public class GrabInfoTask {
    
    private Logger logger = LoggerFactory.getLogger(GrabInfoTask.class);
    
    public void sayHello() {
        logger.info("Hello !!!");
    }
    
    @Autowired
    private OrderinfoDao orderinfoDao;
    
    public void execute() {
        //System.out.println(orderinfoDao.get((long) 1).toString());
        System.out.println("-------- Quartz running the job. --------");
    }

}

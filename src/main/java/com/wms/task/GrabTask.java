package com.wms.task;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wms.dao.DeliveryDetailDao;

@Component
public class GrabTask implements Runnable {
    
    private Logger logger = LoggerFactory.getLogger(GrabTask.class);
    private static final GrabZTTask GRAB_ZT_TASK = new GrabZTTask();
    
    private DeliveryDetailDao deliveryDetailDao;
    
    private String content;
    
    public GrabTask() {
        // TODO Auto-generated constructor stub
    }
    
    public GrabTask(DeliveryDetailDao deliveryDetailDao, String content) {
        // TODO Auto-generated constructor stub
        this.deliveryDetailDao = deliveryDetailDao;
        this.content = content;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        this.execute(this.content);
    }
    
    public void execute(String content) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonObject = GRAB_ZT_TASK.doGetZT(content);
            Object object = jsonObject.get("traces");
            if (object != null) {
                jsonArray = JSONArray.fromObject(object);
            } else {
                logger.info("Not find the content:" + content);
            }
            
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (jsonArray.size()>0) {
            logger.info("Has find the content:" + content);
            deliveryDetailDao.deleteByContent(content);
            deliveryDetailDao.saveBatch(content, jsonArray);
        };
    }
    
}

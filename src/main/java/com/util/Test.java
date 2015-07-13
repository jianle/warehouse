package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;


import com.wms.model.Goods;

public class Test {
    
    public static void main(String[] args) {
        Goods goods = new Goods();
        goods.setgId(new Long(123));
        goods.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println(net.sf.json.JSONObject.fromObject(goods));
    }
    
}

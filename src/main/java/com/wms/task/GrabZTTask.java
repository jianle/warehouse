package com.wms.task;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GrabZTTask {
    
    /*
     * private static final String URL_ZT = "http://www.zto.cn/GuestService/Bill?txtbill="
     * 参考：http://testpartner.zto.cn/
     */
    private static final String URL = "http://partner.zto.cn/client/interface.php?style=json&func=mail.trace";
    private static final String PARTNER = "test";
    private static final String PASS = "ZTO123";

    
    @SuppressWarnings("restriction")
    private JSONObject parseCentanetURL(String content) {
        if (content == null) {
            return null;
        }
        content = new sun.misc.BASE64Encoder().encode(content.getBytes());
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String verify = MD5(PARTNER + datetime + content + PASS);
        
        String url = URL + "&partner=" + PARTNER + "&content=" + content + "&datetime=" + datetime + "&verify=" + verify;
        
        Document doc = null;
        JSONObject jsonObject = new JSONObject();
        try {
            doc = Jsoup.connect(url).timeout(5000).get();
            jsonObject = JSONObject.fromObject(doc.body().text().trim());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

    

    public static void main(String[] args) {
        GrabZTTask grabTask = new GrabZTTask();
        System.out.println(grabTask.parseCentanetURL("689003409239"));
        try {
            new CallApiTask().doPostZT("689003409239");
            new CallApiTask().doGetZT("689003409239");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static String MD5(String key){
        if(key == null){
            return null;
        }
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            byte b[] = md.digest();
            
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++)
            {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

           return buf.toString();
            
        }catch(NoSuchAlgorithmException e){
            return null;
        }
    }
}

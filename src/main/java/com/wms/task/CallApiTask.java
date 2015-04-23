package com.wms.task;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class CallApiTask {
    
    private Logger logger = LoggerFactory.getLogger(CallApiTask.class);
    
    private static final String URL = "http://partner.zto.cn/client/interface.php";
    private static final String PARTNER = "test";
    private static final String PASS = "ZTO123";
    private static final String STYLE = "json";
    private static final String FUNC = "mail.trace";
    
    @SuppressWarnings("restriction")
    public JSONObject doPostZT(String content) throws ParseException, IOException {
        JSONObject jsonTuple = new JSONObject();
        if (content == null) {
            return null;
        }
        content = new sun.misc.BASE64Encoder().encode(content.getBytes());
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String verify = GrabZTTask.MD5(PARTNER + datetime + content + PASS);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(URL);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            
            nvps.add(new BasicNameValuePair("style"   , STYLE   ));
            nvps.add(new BasicNameValuePair("func"    , FUNC    ));
            nvps.add(new BasicNameValuePair("partner" , PARTNER ));
            nvps.add(new BasicNameValuePair("content" , content ));
            nvps.add(new BasicNameValuePair("datetime", datetime));
            nvps.add(new BasicNameValuePair("verify"  , verify  ));
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            //logger.info(String.format("Request json, url: %s, params: %s", URL, nvps));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                jsonTuple = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
                logger.info("Result: " + jsonTuple.toString());
                return jsonTuple;
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }
    
    
    @SuppressWarnings("restriction")
    public JSONObject doGetZT(String content) throws ClientProtocolException, IOException {
        JSONObject jsonTuple = new JSONObject();
        if (content == null) {
            return null;
        }
        content = new sun.misc.BASE64Encoder().encode(content.getBytes());
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String verify = GrabZTTask.MD5(PARTNER + datetime + content + PASS);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            
            StringBuffer url = new StringBuffer(URL);
            url.append("?style=").append(STYLE)
              .append("&func=").append(FUNC)
              .append("&partner=").append(PARTNER)
              .append("&content=").append(URLEncoder.encode(content))
              .append("&datetime=").append(URLEncoder.encode(datetime))
              .append("&verify=").append(verify);
            
            HttpGet httpGet = new HttpGet(url.toString());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            
            //logger.info(String.format("Request json, url: %s ", url));
            
            try {
                jsonTuple = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
                logger.info("Result: " + jsonTuple.toString());
                return jsonTuple;
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

}

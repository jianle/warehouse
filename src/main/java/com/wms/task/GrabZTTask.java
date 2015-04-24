package com.wms.task;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GrabZTTask {
    
    //private Logger logger = LoggerFactory.getLogger(GrabZTTask.class);
    
    /*
     * private static final String URL_ZT = "http://www.zto.cn/GuestService/Bill?txtbill="
     * 参考：http://testpartner.zto.cn/
     */
    private static final String URL = "http://partner.zto.cn/client/interface.php";
    private static final String PARTNER = "test";
    private static final String PASS = "ZTO123";
    private static final String STYLE = "json";
    private static final String FUNC = "mail.trace";

    //测试方法
    public static void main(String[] args) {
        GrabZTTask grabTask = new GrabZTTask();
        System.out.println(grabTask.parseCentanetURL("689003409239"));
        try {
            grabTask.doPostZT("234234234212");
            grabTask.doGetZT("689003409239");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings({ "restriction", "deprecation" })
    public JSONObject parseCentanetURL(String content) {
        if (content == null) {
            return null;
        }
        content = new sun.misc.BASE64Encoder().encode(content.getBytes());
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String verify = MD5(PARTNER + datetime + content + PASS);
        
        StringBuffer url = new StringBuffer(URL);
        url.append("?style=").append(STYLE)
          .append("&func=").append(FUNC)
          .append("&partner=").append(PARTNER)
          .append("&content=").append(URLEncoder.encode(content))
          .append("&datetime=").append(URLEncoder.encode(datetime))
          .append("&verify=").append(verify);
        
        Document doc = null;
        JSONObject jsonObject = new JSONObject();
        try {
            doc = Jsoup.connect(url.toString()).timeout(5000).get();
            jsonObject = JSONObject.fromObject(doc.body().text().trim());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
    
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
                return jsonTuple;
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }
    
    
    @SuppressWarnings({ "restriction", "deprecation" })
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
                return jsonTuple;
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
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

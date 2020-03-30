package com.zsc.flower.tag;


//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.junit.Test;
//
//import java.io.IOException;
//
//
//public class HttpTest {
//
//    public static String getHttpResult(String url) {
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet httpget = new HttpGet(url);
//        String json = null;
//        try {
//            HttpResponse response = httpclient.execute(httpget);
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                json = EntityUtils.toString(entity, "UTF-8").trim();
//
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            httpget.abort();
//        }
//        return json;
//    }
//
//    @Test
//    public void test() {
//        String rs = getHttpResult(
//                "http://120.24.221.15:5000/common/pay/query/?tradeNo=1563096298815");
//        System.out.println(rs);
//    }
//
//}
//
public class HttpTest{}
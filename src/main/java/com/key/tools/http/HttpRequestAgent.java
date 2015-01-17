package com.key.tools.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.key.tools.common.RestResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpRequestAgent {

    static Logger LOG = Logger.getLogger(HttpRequestAgent.class);

    private String url = null;

    private String encoding = "UTF-8";

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    // 制定url中是否有参数
    boolean urlWithParam = false;
    // url是否有效
    boolean urlValid = false;

    private CloseableHttpClient httpClient;

    public static final int MAX_TIMEOUT = 120;
    // 连接超时的秒数
    private int timeoutSeconds = 60;

    public static final int MAX_POOLSIZE = 40;
    // 缓存的HTTP连接的个数
    private int poolSize = 20;

    // 重试次数
    public static final int MAX_RETRYTIMES = 10;
    private int retryTimes = 5;

    // 重试间隔
    public static final int MAX_RETRYINTERVAL = 1000;
    private int retryInterval = 100;

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        if (retryTimes <= 0 || retryTimes >= MAX_RETRYTIMES) {
            return;
        }
        this.retryTimes = retryTimes;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        if (retryInterval <= 0 || retryInterval >= MAX_RETRYINTERVAL) {
            return;
        }

        this.retryInterval = retryInterval;
    }

    public void setTimeoutSeconds(int seconds) {
        if (seconds <= 0 || seconds >= MAX_TIMEOUT) {
            return;
        }
        this.timeoutSeconds = seconds;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setPoolSize(int poolSize) {
        if (poolSize <= 0 || poolSize >= MAX_POOLSIZE) {
            return;
        }

        this.poolSize = poolSize;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void init() {
        initApacheHttpClient();
    }

    public void destroy() {
        destroyApacheHttpClient();
    }

    private void initApacheHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeoutSeconds * 1000)
                .setConnectTimeout(timeoutSeconds * 1000)
                .setConnectionRequestTimeout(timeoutSeconds * 1000)
                .setStaleConnectionCheckEnabled(true).build();

        httpClient = HttpClientBuilder.create().setMaxConnTotal(poolSize)
                .setMaxConnPerRoute(poolSize)
                .setDefaultRequestConfig(requestConfig).build();
    }

    private void destroyApacheHttpClient() {
        try {
            if (httpClient != null) {
                httpClient.close();
                httpClient = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpGet getGetRequest() {
        return new HttpGet();
    }

    public static HttpPost getPostRequest() {
        return new HttpPost();
    }

    public static HttpPut getPutRequest() {
        return new HttpPut();
    }

    public static HttpDelete getDeleteRequest() {
        return new HttpDelete();
    }

    public String executeAndGet(HttpRequestBase httpRequestBase)
            throws Exception {
        HttpResponse response;
        String entiStr = "";
        try {
            response = httpClient.execute(httpRequestBase);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOG.error("请求地址：" + httpRequestBase.getURI() + ", 请求方法："
                        + httpRequestBase.getMethod() + ",STATUS CODE = "
                        + response.getStatusLine().getStatusCode());

                throw new Exception("Response Status Code : "
                        + response.getStatusLine().getStatusCode());
            } else {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entiStr = EntityUtils.toString(entity, Consts.UTF_8);
                } else {
                    throw new Exception("Response Entity Is Null");
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return entiStr;
    }

    public String executeAndGetWithRetry(HttpRequestBase httpRequestBase)
            throws Exception {
        try {
            return executeAndGet(httpRequestBase);
        } catch (Exception e) {
            LOG.info("开始重试http请求");
        }

        Exception finalException = null;
        int count = 0;
        while (count < retryTimes) {
            try {
                return executeAndGet(httpRequestBase);
            } catch (Exception e) {
                count++;
                if (count >= retryTimes) {
                    finalException = e;
                }
                sleepAndSkipInterrupt(retryInterval);
                LOG.info("重试第" + count + "次...");
            }
        }
        throw finalException;
    }

    /**
     * 休眠，发生中断时自动捕获且忽略
     *
     * @param timeout
     */
    public static void sleepAndSkipInterrupt(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            LOG.error("sleep end, message: " + e.getMessage());
        }
    }

    public void setUrl(String url) {
        this.url = url;
        if (url == null) {
            return;
        }

        urlWithParam = url.indexOf("?") > 0;
        urlValid = true;
        // 修正网址为路径
        if (!urlWithParam && !url.endsWith("/")) {
            this.url = url + "/";
        }
    }

    public boolean appendParams(StringBuilder buf, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return true;
        }

        try {
            for (String key : params.keySet()) {
                buf.append(URLEncoder.encode(key, encoding));
                buf.append("=");
                buf.append(URLEncoder.encode(params.get(key), encoding));
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            LOG.error(url + " input params:");
            for (String key : params.keySet()) {
                LOG.error(key + " = " + params.get(key));
            }
            return false;
        }
        return true;
    }

    public String getUrl() {
        return url;
    }

    public boolean isUrlValid() {
        return urlValid;
    }

    public RestResult request(HttpRequestBase request, String url,
                              RestResult result) {
        LOG.info("Request:" + url);
        long startTime = System.currentTimeMillis();
        try {
            request.setURI(new URI(url));

            String json = executeAndGetWithRetry(request);
            LOG.info("edit appconfig result from server : {}", json);
            result.setJson(json);
            result.setStatus(RestResult.OK);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            result.setReason(ex.toString());
        }

        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        LOG.info("Spend Time:" + spendTime + " url:" + url);
        return result;
    }
    
    public RestResult post(String url, RestResult result, Map<String, Object> params)
            throws UnsupportedCharsetException {
    	
        HttpPost post = getPostRequest();
     
    	try {
    		List<NameValuePair> paramsValues = getParams(params);
			
			post.setHeader("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.toString());
			post.setEntity(new UrlEncodedFormEntity(paramsValues, "UTF-8"));
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("edit appconfig error.{}", e.getMessage());
		}
        
        return request(post, url, result);
    }
    
    private List<NameValuePair> getParams(Map<String, Object> params) {
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (params != null) {
           Iterator<String> keys = params.keySet().iterator();
           while (keys.hasNext()) {
              String key = (String) keys.next();
              NameValuePair param = new BasicNameValuePair(key,
                    String.valueOf(params.get(key)));
              nameValuePairs.add(param);
           }
           
        }
        return nameValuePairs;
     }
    
    /*
     * post form data
     */
    public RestResult postForm(String url, RestResult result, String body)
            throws UnsupportedCharsetException {
        HttpPost post = getPostRequest();
        
    	try {
    		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
			post.setEntity(new StringEntity(body, ContentType.APPLICATION_FORM_URLENCODED));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("post form error.{}", e.getMessage());
		}
    	
        return request(post, url, result);
    }    

    public RestResult post(String url, RestResult result, String body)
            throws UnsupportedCharsetException {
    	
        HttpPost post = getPostRequest();
     
    	try {
    		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
			post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("edit appconfig error.{}", e.getMessage());
		}
        
//        HttpEntity entity = new StringEntity(body, encoding);
//        post.setEntity(entity);
        return request(post, url, result);
    }

    public RestResult put(String url, RestResult result, String body)
            throws UnsupportedCharsetException {
        HttpPut put = getPutRequest();
        HttpEntity entity = new StringEntity(body, encoding);
        put.setEntity(entity);
        return request(put, url, result);
    }

    public RestResult get(String url, RestResult result) {
        HttpGet get = getGetRequest();
        return request(get, url, result);
    }
}

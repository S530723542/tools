package com.key.tools.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

public class HttpAgent
{

	private Logger logger = Logger.getLogger(HttpAgent.class);

	private String encode = "UTF-8";

	public String getEncode()
	{
		return this.encode;
	}

	public void setEncode(String encode)
	{
		this.encode = encode;
	}

	private CloseableHttpClient client;

	public void setClient(CloseableHttpClient client)
	{
		this.client = client;
	}

	// 连接超时的秒数
	private int timeoutSeconds = 60;

	public void setTimeoutSeconds(int seconds)
	{
		if (seconds <= 0)
		{
			return;
		}
		this.timeoutSeconds = seconds;
	}

	public int getTimeoutSeconds()
	{
		return this.timeoutSeconds;
	}

	// 缓存的HTTP连接的个数
	private int poolSize = 20;

	public void setPoolSize(int poolSize)
	{
		if (poolSize <= 0)
		{
			return;
		}
		this.poolSize = poolSize;
	}

	public int getPoolSize()
	{
		return this.poolSize;
	}

	// 重试次数
	private int retryTimes = 5;

	public void setRetryTimes(int retryTimes)
	{
		if (retryTimes < 0)
		{
			return;
		}
		this.retryTimes = retryTimes;
	}

	public int getRetryTimes()
	{
		return this.retryTimes;
	}

	// 重试间隔
	private int retryInterval = 100;

	public void setRetryInterval(int retryInterval)
	{
		if (retryInterval < 0)
		{
			return;
		}
		this.retryInterval = retryInterval;
	}

	public int getRetryInterval()
	{
		return this.retryInterval;
	}

	public void init()
	{
		initApacheHttpClient();
	}

	public void destroy()
	{
		destroyApacheHttpClient();
	}

	private void initApacheHttpClient()
	{
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(timeoutSeconds * 1000)
				.setConnectTimeout(timeoutSeconds * 1000)
				.setConnectionRequestTimeout(timeoutSeconds * 1000)
				.setStaleConnectionCheckEnabled(true).build();

		client = HttpClientBuilder.create().setMaxConnTotal(poolSize)
				.setMaxConnPerRoute(poolSize)
				.setDefaultRequestConfig(requestConfig).build();
	}

	private void destroyApacheHttpClient()
	{
		try
		{
			if (client != null)
			{
				client.close();
				client = null;
			}
		} catch (IOException e)
		{
			logger.error("destroyApacheHttpClient:", e);
		}
	}

	public HttpRequestBase getHttpGet(String url, Map<String, Object> params)
			throws UnsupportedEncodingException
	{
		HttpGet httpGet = new HttpGet(createGetUrl(url, params));
		return httpGet;
	}

	public HttpRequestBase getHttpPost(String url, Map<String, Object> params)
			throws UnsupportedEncodingException
	{
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> list = getParams(params);
		httpPost.setEntity(new UrlEncodedFormEntity(list, this.encode));
		return httpPost;
	}
	
	public String postAndRetry(HttpGet httpGet)
	{
		
		return null;
	}

	private List<NameValuePair> getParams(Map<String, Object> params)
	{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params != null)
		{
			Iterator<String> keys = params.keySet().iterator();
			while (keys.hasNext())
			{
				String key = (String) keys.next();
				NameValuePair param = new BasicNameValuePair(key,
						String.valueOf(params.get(key)));
				nameValuePairs.add(param);
			}

		}
		return nameValuePairs;
	}

	private String createGetUrl(String url, Map<String, Object> params)
			throws UnsupportedEncodingException
	{
		if (url == null || url.trim().length() == 0)
		{
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append("?");

		Iterator<String> iterator = params.keySet().iterator();
		String key = iterator.next();
		sb.append(URLEncoder.encode(key, this.encode));
		sb.append("=");
		sb.append(URLEncoder.encode(String.valueOf(params.get(key)),
				this.encode));

		while (iterator.hasNext())
		{
			key = iterator.next();
			sb.append("&");
			sb.append(URLEncoder.encode(key, this.encode));
			sb.append("=");
			sb.append(URLEncoder.encode(String.valueOf(params.get(key)),
					this.encode));
		}
		return sb.toString();
	}
}

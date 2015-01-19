package com.key.tools;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Target;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.key.tools.common.RestResult;
import com.key.tools.http.HttpAgent;
import com.key.tools.member.service.QQLoginService;
import com.key.tools.stock.pojo.BaiduRetData;
import com.key.tools.stock.pojo.BaiduStockJson;
import com.key.tools.test.TestService;

@RunWith(SpringJUnit4ClassRunner.class)
// 使用junit4进行测试
@ContextConfiguration("classpath:applicationContext.xml")
public class AppTest
{

	@Autowired
	TestService	testService;

	@Autowired
	@Qualifier(value = "httpAgent")
	HttpAgent	httpAgent;

	@Test
	public void test() throws UnsupportedEncodingException
	{

		String url = "http://apistore.baidu.com/microservice/stock";

		String code = "sz002230";

		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("stockid", code);
		try
		{
			RestResult<String> result = httpAgent.getAndRetry(url, params);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		//
		// if (result.getErrCode() < 0)
		// {
		// System.out.println("******************************************");
		// System.out.println(result.getErrCode());
		// System.out.println(result.getErrMsg());
		// System.out.println("******************************************");
		// }
		// Gson gson = new Gson();
		// BaiduStockJson stockJson = gson.fromJson(result.getData(),
		// BaiduStockJson.class);
		// System.out.println("******************************************");
		// System.out.println(stockJson.getErrNum());
		// System.out.println(stockJson.getErrMsg());
		// System.out.println(stockJson.getRetData().getKlinegraph().getDayurl());
		// System.out.println(stockJson.getRetData().getMarket().getShanghai().getName());
		// System.out.println(stockJson.getRetData().getStockinfo().getName());
		// System.out.println("******************************************");
	}

}

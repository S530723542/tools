package com.key.tools;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.key.tools.member.service.QQLoginService;
import com.key.tools.test.TestService;


@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration("classpath:applicationContext.xml")
public class AppTest
{

	@Autowired
	TestService testService;
	
	@Test
	public void updateTest()
	{
		try
		{
			testService.testForUpdate(1);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}


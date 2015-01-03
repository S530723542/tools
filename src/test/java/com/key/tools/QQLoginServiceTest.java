package com.key.tools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.key.tools.member.service.QQLoginService;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration("classpath:applicationContext.xml")
public class QQLoginServiceTest
{

	@Autowired
	QQLoginService qqLoginService;
	
	@Test
	public void loginByQQTest()
	{
		long id=qqLoginService.loginByQQ(232, "test");
		System.out.println(id);
	}
	
	@Test
	public void loginByQQ1Test()
	{
		long id=qqLoginService.updateQQ(6L, 530);
		System.out.println(id);
	}
}

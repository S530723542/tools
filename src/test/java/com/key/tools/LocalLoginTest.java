package com.key.tools;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.key.tools.member.db.dao.UserMapper;
import com.key.tools.member.db.model.User;
import com.key.tools.member.service.LocalLoginService;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration("classpath:applicationContext.xml")
public class LocalLoginTest
{

	@Autowired
	LocalLoginService localLoginService;
	
	@Test
	public void testAdd()
	{
		localLoginService.addLocalLogin("test", null, null, "hehe");
	}
}

package com.key.tools.member.service;

import com.key.tools.member.db.model.User;

public interface UserService
{

	public long registerByName(String name,String password);
	
	public long registerByPhone(Integer phone,String password);
	
	public long registerByEmail(String email,String password);
	
	public long registerByQQ(Integer qq);
	
	public int delteUser(long id);
	
	public User getUser(long id);
}

package com.key.tools.member.service;

import com.key.tools.member.db.model.User;



public interface UserService
{
	public long addUser(String name,Integer phone,String email,String password);
	
	public User getUserByName(String name);
	
	public User getUserByPhone(Integer phone);
	
	public User getUserByEmail(String email);
	
	public int deleteUser(Long id);
	
	/**
	 * 更新数据库中用户信息，有检查
	 * @param id
	 * @param name
	 * @param phone
	 * @param email
	 * @return
	 */
	public int updateUserMsg(Long id,String name,Integer phone,String email);
	
	public int motifyUserPassword(Long id,String password,String newPassword);
	
	public int verifyPasswordByName(String name,String password);
	
	public int verifyPasswordByPhone(Integer phone,String password);
	
	public int verifyPasswordByEmail(String email,String password);
	
}

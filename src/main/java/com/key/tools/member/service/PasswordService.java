package com.key.tools.member.service;

import com.key.tools.member.db.model.Password;

public interface PasswordService
{
	/**
	 * 添加会员并返回会员ID
	 * @param name
	 * @param password
	 * @return
	 */
	public long addMember(String password);
	
	/**
	 * 删除会员
	 * @param id
	 * @return
	 */
	public boolean deleteMember(Long id);
	
	/**
	 * 验证并修改密码
	 * @param id
	 * @param password
	 * @param newPassword
	 * @return
	 */
	public int updatePassword(Long id,String password,String newPassword);
	
	/**
	 * 验证密码
	 * @param id
	 * @param password
	 * @return
	 */
	public int verifyPassword(Long id,String password);
	
	/**
	 * 获取会员信息（名字和密码）
	 * @param id
	 * @return
	 */
	public Password getPassword(Long id);
	
}

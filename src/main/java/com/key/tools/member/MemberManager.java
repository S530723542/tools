package com.key.tools.member;

import com.key.tools.member.db.model.Member;

public interface MemberManager
{
	/**
	 * 添加会员并返回会员ID
	 * @param name
	 * @param password
	 * @return
	 */
	public int addMember(String name,String password);
	
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
	public boolean updatePassword(Long id,String password,String newPassword);
	
	/**
	 * 验证密码
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean verifyPassword(Long id,String password);
	
	/**
	 * 获取会员信息（名字和密码）
	 * @param id
	 * @return
	 */
	public Member getMember(Long id);
	
}

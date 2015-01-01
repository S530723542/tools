package com.key.tools.member.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.tools.common.Constant;
import com.key.tools.member.db.dao.PasswordMapper;
import com.key.tools.member.db.model.Password;
import com.key.tools.member.service.PasswordService;

@Service
public class PasswordServiceImpl implements PasswordService
{
	@Autowired
	PasswordMapper	passwordMapper;

	@Override
	public long addMember(String password)
	{
		Password record = new Password();
		record.setPassword(password);
		Date now = new Date(System.currentTimeMillis());
		record.setCreateTime(now);
		record.setMotifyTime(now);
		record.setIsDelete(Constant.IS_AVAILABLE);
		passwordMapper.insertAndReturnId(record);
		return record.getId();
	}

	@Override
	@Transactional
	public boolean deleteMember(Long id)
	{
		Password record = passwordMapper.selectByPrimaryKey(id);
		if (record == null || Constant.IS_DELETE.equals(record.getIsDelete()))
		{
			return false;
		}
		record = new Password();
		record.setId(id);
		Date now = new Date(System.currentTimeMillis());
		record.setMotifyTime(now);
		record.setIsDelete(Constant.IS_DELETE);
		passwordMapper.updateByPrimaryKeySelective(record);
		return true;
	}

	@Override
	@Transactional
	public int updatePassword(Long id, String password, String newPassword)
	{
		Password record = passwordMapper.selectByPrimaryKey(id);
		if (record == null || Constant.IS_DELETE.equals(record.getIsDelete()))
		{
			return Constant.NOT_EXIST;
		}
		if (!record.getPassword().equals(password))
		{
			return Constant.NOT_MATCH;
		}
		record = new Password();
		record.setId(id);
		Date now = new Date(System.currentTimeMillis());
		record.setMotifyTime(now);
		record.setPassword(newPassword);
		passwordMapper.updateByPrimaryKeySelective(record);
		return Constant.SUCCESS;
	}

	@Override
	public int verifyPassword(Long id, String password)
	{
		Password record = passwordMapper.selectByPrimaryKey(id);
		if (record == null || Constant.IS_DELETE.equals(record.getIsDelete()))
		{
			return Constant.NOT_EXIST;
		}
		if (!record.getPassword().equals(password))
		{
			return Constant.NOT_MATCH;
		} else
		{
			return Constant.SUCCESS;
		}

	}

	@Override
	public Password getPassword(Long id)
	{
		Password record = passwordMapper.selectByPrimaryKey(id);
		return record;
	}

}

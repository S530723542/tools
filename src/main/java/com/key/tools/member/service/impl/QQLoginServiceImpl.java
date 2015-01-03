package com.key.tools.member.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.key.tools.common.Constant;
import com.key.tools.member.db.dao.QQLoginMapper;
import com.key.tools.member.db.model.QQLogin;
import com.key.tools.member.service.LocalLoginService;
import com.key.tools.member.service.QQLoginService;

public class QQLoginServiceImpl implements QQLoginService
{
	Logger				logger	= Logger.getLogger(QQLoginServiceImpl.class);

	@Autowired
	QQLoginMapper		qqLoginMapper;

	@Autowired
	LocalLoginService	localLoginService;

	@Override
	public long getUserIdByQQ(Integer qq)
	{
		QQLogin record = new QQLogin();
		long userId = 0;
		record.setQq(qq);
		record.setIsDelete(Constant.IS_AVAILABLE);
		List<QQLogin> list = qqLoginMapper.selectBySelectiveForUpdate(record);
		if (list.size() == 0)
		{
			logger.info("[getUserIdByQQ] : [qq:" + qq
					+ "] this is a new qq. create!");
			userId = localLoginService.addUser();
			QQLogin qqLogin = new QQLogin();
			qqLogin.setUserId(userId);
			qqLogin.setIsDelete(Constant.IS_AVAILABLE);
			qqLogin.setQq(qq);
			Date now = new Date(System.currentTimeMillis());
			qqLogin.setCreateTime(now);
			qqLogin.setMotifyTime(now);
			qqLoginMapper.insertAndReturnId(qqLogin);
		} else
		{
			if (list.size() > 1)
			{
				logger.error("[getUserIdByQQ(] : [qq:" + qq + "] [size:"
						+ list.size() + "] is more than one!");
				return Constant.MORE_THAN_ONE;
			}
			userId = list.get(0).getUserId();
		}
		logger.info("[getUserIdByQQ] : [qq:" + qq + "] [userId:" + userId + "]");
		return userId;
	}

	@Override
	public int vertifyQQLogin(Integer qq, String password)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public int updateQQ(Long userId, Integer qq)
	{
		QQLogin record = new QQLogin();
		record.setQq(qq);
		record.setIsDelete(Constant.IS_AVAILABLE);
		List<QQLogin> list = qqLoginMapper.selectBySelectiveForUpdate(record);
		if (list.size() != 0)
		{
			logger.warn("[updateQQ] : [QQ:" + qq + "] is already exist!");
			if (list.size() > 1)
			{
				logger.error("[updateQQ] : [QQ:" + qq + "] [size:"
						+ list.size() + "] is more than one!");
			}
			return Constant.QQ_EXIST;
		}

		record = new QQLogin();
		record.setUserId(userId);
		record.setIsDelete(Constant.IS_AVAILABLE);
		list = qqLoginMapper.selectBySelective(record);
		if (list.size() == 0)
		{
			return Constant.NOT_EXIST;
		} else if (list.size() > 1)
		{
			logger.error("[updateQQ] : [userId:" + userId + "] [size:"
					+ list.size() + "] is more than one!");
			return Constant.MORE_THAN_ONE;
		}

		QQLogin qqLogin = new QQLogin();
		qqLogin.setId(list.get(0).getId());
		qqLogin.setQq(qq);
		Date now = new Date(System.currentTimeMillis());
		record.setMotifyTime(now);
		qqLoginMapper.updateByPrimaryKeySelective(record);
		logger.info("[updateQQ] : [userId:" + userId + "] [qq:" + qq
				+ "] SUCCESS!");
		return Constant.SUCCESS;
	}

	@Override
	public int deleteQQ(Long userId)
	{
		QQLogin record = new QQLogin();
		record.setUserId(userId);
		record.setIsDelete(Constant.IS_AVAILABLE);
		List<QQLogin> list = qqLoginMapper.selectBySelective(record);
		if (list.size() == 0)
		{
			return Constant.NOT_EXIST;
		} else if (list.size() > 1)
		{
			logger.error("[deleteQQ] : [userId:" + userId + "] [size:"
					+ list.size() + "] is more than one!");
			return Constant.MORE_THAN_ONE;
		}
		QQLogin qqLogin = new QQLogin();
		qqLogin.setId(list.get(0).getId());
		qqLogin.setIsDelete(Constant.IS_DELETE);
		Date now = new Date(System.currentTimeMillis());
		qqLogin.setMotifyTime(now);
		qqLoginMapper.updateByPrimaryKeySelective(qqLogin);
		logger.info("[deleteQQ] : [userId:" + userId + "] SUCCESS!");
		return Constant.SUCCESS;
	}

}

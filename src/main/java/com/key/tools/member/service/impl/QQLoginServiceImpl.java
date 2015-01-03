package com.key.tools.member.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.tools.common.Constant;
import com.key.tools.member.db.dao.QQLoginMapper;
import com.key.tools.member.db.model.QQLogin;
import com.key.tools.member.db.model.QQLoginExt;
import com.key.tools.member.service.UserService;
import com.key.tools.member.service.QQLoginService;

@Service
public class QQLoginServiceImpl implements QQLoginService
{
	Logger				logger	= Logger.getLogger(QQLoginServiceImpl.class);

	@Autowired
	QQLoginMapper		qqLoginMapper;

	@Autowired
	UserService	localLoginService;

	@Override
	public long getUserIdByQQ(Integer qq)
	{
		QQLoginExt record = new QQLoginExt();
		long userId = 0;
		record.setQq(qq);
		record.setIsDelete(Constant.IS_AVAILABLE);
		List<QQLogin> list = qqLoginMapper.selectByExtSelectiveForUpdate(record);
		if (list.size() == 0)
		{
			logger.info("[getUserIdByQQ] : [qq:" + qq
					+ "] this is a new qq. create!");
			userId = localLoginService.addUser();
			QQLogin qqLogin = new QQLogin();
			qqLogin.setUserId(userId);
			qqLogin.setQq(qq);
			Date now = new Date(System.currentTimeMillis());
			qqLogin.setCreateTime(now);
			qqLogin.setMotifyTime(now);
			qqLoginMapper.insert(qqLogin);
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
		return Constant.SUCCESS;
	}

	@Override
	@Transactional
	public int updateQQ(Long userId, Integer qq)
	{
		QQLoginExt record = new QQLoginExt();
		record.setQq(qq);
		record.setIsDelete(Constant.IS_AVAILABLE);
		List<QQLogin> list = qqLoginMapper.selectByExtSelectiveForUpdate(record);
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

		QQLogin qqLogin = new QQLogin();
		qqLogin.setUserId(userId);
		list = qqLoginMapper.selectBySelective(qqLogin);
		if (list.size() == 0)
		{
			return Constant.NOT_EXIST;
		} else if (list.size() > 1)
		{
			logger.error("[updateQQ] : [userId:" + userId + "] [size:"
					+ list.size() + "] is more than one!");
			return Constant.MORE_THAN_ONE;
		}

		qqLogin = new QQLogin();
		qqLogin.setId(list.get(0).getId());
		qqLogin.setQq(qq);
		Date now = new Date(System.currentTimeMillis());
		qqLogin.setMotifyTime(now);
		qqLoginMapper.updateByPrimaryKeySelective(qqLogin);
		logger.info("[updateQQ] : [userId:" + userId + "] [qq:" + qq
				+ "] SUCCESS!");
		return Constant.SUCCESS;
	}


	@Override
	public long loginByQQ(Integer qq, String password)
	{
		int qqLoginReturnIdId = vertifyQQLogin(qq, password);
		switch (qqLoginReturnIdId)
		{
		case Constant.QQ_NOT_EXIT:
			logger.info("[loginByQQ] : [qq:" + qq + "] is not exist!");
			return Constant.QQ_NOT_EXIT;
		case Constant.QQ_PASSWORD_WRONG:
			logger.info("[loginByQQ] : [qq:" + qq + "] password is wrong!");
			return Constant.QQ_PASSWORD_WRONG;
		case Constant.SUCCESS:
			logger.info("[loginByQQ] : [qq:" + qq + "] login success!");
			break;
		default:
			logger.error("[loginByQQ] : [qqLoginReturnIdId:"
					+ qqLoginReturnIdId + "] wrong code!");
			return Constant.SYSTEM_ERROR;
		}
		return getUserIdByQQ(qq);
	}

}

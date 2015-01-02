package com.key.tools.member.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.key.tools.common.Constant;
import com.key.tools.member.db.dao.UserMapper;
import com.key.tools.member.db.model.User;
import com.key.tools.member.service.UserService;

public class UserServiceImpl implements UserService
{
	Logger		logger	= Logger.getLogger(UserServiceImpl.class);

	@Autowired
	UserMapper	userMapper;

	@Override
	public long addUser(String name, Integer phone, String email,
			String password)
	{
		User user = new User();
		// userName检查
		if (name != null)
		{
			User record = new User();
			record.setIsDelete(Constant.IS_AVAILABLE);
			record.setUserName(name);
			List<User> list = userMapper.selectBySelective(record);
			if (list.size() == 0)
			{
				user.setUserName(name);

			} else
			{
				logger.warn("[addUser] : [name:" + name
						+ "] name is already exist!");
				if (list.size() > 1)
				{
					logger.error("[addUser] : [name:" + name + "] [size:"
							+ list.size()
							+ "] name is already exist! and more than one!");
				}
				return Constant.NAME_EXIST;
			}
		}

		// phone检查
		if (phone != null)
		{
			User record = new User();
			record.setIsDelete(Constant.IS_AVAILABLE);
			record.setPhone(phone);
			List<User> list = userMapper.selectBySelective(record);
			if (list.size() == 0)
			{
				user.setPhone(phone);

			} else
			{
				logger.warn("[addUser] : [phone:" + phone
						+ "] phone is already exist!");
				if (list.size() > 1)
				{
					logger.error("[addUser] : [phone:" + phone + "] [size:"
							+ list.size()
							+ "] phone is already exist! and more than one!");
				}
				return Constant.PHONE_EXIST;
			}
		}

		// email检查
		if (email != null)
		{
			User record = new User();
			record.setIsDelete(Constant.IS_AVAILABLE);
			record.setEmail(email);
			List<User> list = userMapper.selectBySelective(record);
			if (list.size() == 0)
			{
				user.setEmail(email);

			} else
			{
				logger.warn("[addUser] : [email:" + email
						+ "] email is already exist!");
				if (list.size() > 1)
				{
					logger.error("[addUser] : [email:" + email + "] [size:"
							+ list.size()
							+ "] email is already exist! and more than one!");
				}
				return Constant.EMAIL_EXIST;
			}
		}

		user.setIsDelete(Constant.IS_AVAILABLE);
		user.setPassword(password);
		Date now = new Date(System.currentTimeMillis());
		user.setCreateTime(now);
		user.setMotifyTime(now);
		userMapper.insertSelective(user);
		return Constant.SUCCESS;
	}

	@Override
	public User getUserByName(String name)
	{
		User record = new User();
		record.setIsDelete(Constant.IS_AVAILABLE);
		record.setUserName(name);
		List<User> list = userMapper.selectBySelective(record);

		if (list.size() == 0)
		{
			return null;
		} else if (list.size() == 1)
		{
			return list.get(0);
		} else
		{
			logger.error("[getUserByName] : [name:" + name + "] [size:"
					+ list.size() + "] name is exist and more than one!");
			return list.get(0);
		}

	}

	@Override
	public User getUserByPhone(Integer phone)
	{
		User record = new User();
		record.setIsDelete(Constant.IS_AVAILABLE);
		record.setPhone(phone);
		List<User> list = userMapper.selectBySelective(record);

		if (list.size() == 0)
		{
			return null;
		} else if (list.size() == 1)
		{
			return list.get(0);
		} else
		{
			logger.error("[getUserByPhone] : [phone:" + phone + "] [size:"
					+ list.size() + "] phone is exist and more than one!");
			return list.get(0);
		}
	}

	@Override
	public User getUserByEmail(String email)
	{
		User record = new User();
		record.setIsDelete(Constant.IS_AVAILABLE);
		record.setEmail(email);
		List<User> list = userMapper.selectBySelective(record);

		if (list.size() == 0)
		{
			return null;
		} else if (list.size() == 1)
		{
			return list.get(0);
		} else
		{
			logger.error("[getUserByEmail] : [email:" + email + "] [size:"
					+ list.size() + "] email is exist and more than one!");
			return list.get(0);
		}
	}

	@Override
	@Transactional
	public int deleteUser(Long id)
	{

		User user = userMapper.selectByPrimaryKey(id);
		if (user == null || Constant.IS_DELETE.equals(user.getIsDelete()))
		{
			return Constant.NOT_EXIST;
		}
		User record = new User();
		user.setId(id);
		user.setIsDelete(Constant.IS_DELETE);
		Date now = new Date(System.currentTimeMillis());
		user.setMotifyTime(now);
		userMapper.updateByPrimaryKeySelective(record);
		return Constant.SUCCESS;
	}

	@Override
	@Transactional
	public int updateUserMsg(Long id, String name, Integer phone, String email)
	{
		User user = new User();
		// userName检查
		if (name != null)
		{
			User record = new User();
			record.setIsDelete(Constant.IS_AVAILABLE);
			record.setUserName(name);
			List<User> list = userMapper.selectBySelective(record);
			if (list.size() == 0)
			{
				user.setUserName(name);

			} else
			{
				logger.warn("[updateUserMsg] : [name:" + name
						+ "] name is already exist!");
				if (list.size() > 1)
				{
					logger.error("[updateUserMsg] : [name:" + name + "] [size:"
							+ list.size()
							+ "] name is already exist! and more than one!");
				}
				return Constant.NAME_EXIST;
			}
		}

		// phone检查
		if (phone != null)
		{
			User record = new User();
			record.setIsDelete(Constant.IS_AVAILABLE);
			record.setPhone(phone);
			List<User> list = userMapper.selectBySelective(record);
			if (list.size() == 0)
			{
				user.setPhone(phone);

			} else
			{
				logger.warn("[updateUserMsg] : [phone:" + phone
						+ "] phone is already exist!");
				if (list.size() > 1)
				{
					logger.error("[addUser] : [phone:" + phone + "] [size:"
							+ list.size()
							+ "] phone is already exist! and more than one!");
				}
				return Constant.PHONE_EXIST;
			}
		}

		// email检查
		if (email != null)
		{
			User record = new User();
			record.setIsDelete(Constant.IS_AVAILABLE);
			record.setEmail(email);
			List<User> list = userMapper.selectBySelective(record);
			if (list.size() == 0)
			{
				user.setEmail(email);

			} else
			{
				logger.warn("[updateUserMsg] : [email:" + email
						+ "] email is already exist!");
				if (list.size() > 1)
				{
					logger.error("[updateUserMsg] : [email:" + email
							+ "] [size:" + list.size()
							+ "] email is already exist! and more than one!");
				}
				return Constant.EMAIL_EXIST;
			}
		}
		user.setId(id);
		Date now = new Date(System.currentTimeMillis());
		user.setMotifyTime(now);
		userMapper.updateByPrimaryKeySelective(user);
		return Constant.SUCCESS;
	}

	@Override
	@Transactional
	public int motifyUserPassword(Long id, String password, String newPassword)
	{
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null || Constant.IS_DELETE.equals(user.getIsDelete()))
		{
			return Constant.NOT_EXIST;
		}
		if (!user.getPassword().equals(password))
		{
			return Constant.NOT_MATCH;
		}
		User record = new User();
		user.setId(id);
		Date now = new Date(System.currentTimeMillis());
		user.setMotifyTime(now);
		user.setPassword(newPassword);
		userMapper.updateByPrimaryKeySelective(record);
		return Constant.SUCCESS;
	}

	@Override
	public int verifyPasswordByName(String name, String password)
	{
		User record = new User();
		record.setUserName(name);
		record.setIsDelete(Constant.IS_AVAILABLE);
		List<User> list = userMapper.selectBySelective(record);

		if (list.size() == 1)
		{
			return Constant.NOT_EXIST;
		} else if (list.size() > 1)
		{
			logger.error("[getUserByName] : [name:" + name + "] [size:"
					+ list.size() + "] name is exist and more than one!");
		}
		User user = list.get(0);
		if (!user.getPassword().equals(password))
		{
			return Constant.NOT_MATCH;
		} else
		{
			return Constant.SUCCESS;
		}

	}

	@Override
	public int verifyPasswordByPhone(Integer phone, String password)
	{
		User record = new User();
		record.setPhone(phone);
		record.setIsDelete(Constant.IS_AVAILABLE);
		List<User> list = userMapper.selectBySelective(record);

		if (list.size() == 1)
		{
			return Constant.NOT_EXIST;
		} else if (list.size() > 1)
		{
			logger.error("[getUserByName] : [phone:" + phone + "] [size:"
					+ list.size() + "] name is exist and more than one!");
		}
		User user = list.get(0);
		if (!user.getPassword().equals(password))
		{
			return Constant.NOT_MATCH;
		} else
		{
			return Constant.SUCCESS;
		}
	}

	@Override
	public int verifyPasswordByEmail(String email, String password)
	{
		User record = new User();
		record.setEmail(email);
		record.setIsDelete(Constant.IS_AVAILABLE);
		List<User> list = userMapper.selectBySelective(record);

		if (list.size() == 1)
		{
			return Constant.NOT_EXIST;
		} else if (list.size() > 1)
		{
			logger.error("[getUserByName] : [nemail:" + email + "] [size:"
					+ list.size() + "] name is exist and more than one!");
		}
		User user = list.get(0);
		if (!user.getPassword().equals(password))
		{
			return Constant.NOT_MATCH;
		} else
		{
			return Constant.SUCCESS;
		}
	}

}

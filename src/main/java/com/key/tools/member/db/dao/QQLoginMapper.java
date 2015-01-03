package com.key.tools.member.db.dao;

import java.util.List;

import com.key.tools.member.db.model.QQLogin;

public interface QQLoginMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qq_login
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qq_login
     *
     * @mbggenerated
     */
    int insert(QQLogin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qq_login
     *
     * @mbggenerated
     */
    int insertSelective(QQLogin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qq_login
     *
     * @mbggenerated
     */
    QQLogin selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qq_login
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(QQLogin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qq_login
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(QQLogin record);
    
    List<QQLogin> selectBySelective(QQLogin record);
    
    List<QQLogin> selectBySelectiveForUpdate(QQLogin record);
    
    int insertAndReturnId(QQLogin record);
}
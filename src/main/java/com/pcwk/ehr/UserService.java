package com.pcwk.ehr;

import java.sql.SQLException;

public interface UserService {

	/**
	 * 등업
	 * @throws SQLException
	 */
	public void upgradeLevels() throws SQLException;
	
	/**
	 * 최초 회원 가입시 등급: BASIC
	 * @param inVO
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	public int add(UserVO inVO) throws SQLException;
	
	/**
	 * 등업 여부 확인 
	 * @param user
	 * @return Boolean
	 * @throws IllegalAccessException 
	 */
	public Boolean canUpgradeLevel(UserVO user) throws IllegalAccessException;
	
    
	/**
	 * 다음 등급으로 등업
	 * @param inVO
	 * @throws SQLException
	 */
	public void upgradeLevel(UserVO inVO) throws SQLException;	
}

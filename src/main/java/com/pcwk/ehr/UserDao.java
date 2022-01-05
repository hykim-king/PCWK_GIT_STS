package com.pcwk.ehr;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

	List<UserVO> getAll();

	/**
	 * 등록건수 
	 * @return
	 * @throws SQLException
	 */
	int getCount() throws SQLException;

	/**
	 * 전체 삭제 
	 * @throws SQLException
	 */
	void deleteAll() throws SQLException;

	/**
	 * 사용자 등록 
	 * @param inVO
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	int doInsert(UserVO inVO) throws SQLException;

	/**
	 * 사용자 조회
	 * @param inVO
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	UserVO doSelectOne(UserVO inVO) throws SQLException;
	
	/**
	 * 단건 삭제
	 * @param inVO
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doDelete(UserVO inVO)throws SQLException;
	
	/**
	 * 수정
	 * @param inVO
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int doUpdate(UserVO inVO)throws SQLException;
	
	/**
	 * 목록 조회 
	 * @param inVO
	 * @return List<UserVO>
	 * @throws SQLException
	 */
    List<UserVO>  doRetrieve(Object inVO)throws SQLException;
    
    
}
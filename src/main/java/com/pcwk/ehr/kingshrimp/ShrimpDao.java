package com.pcwk.ehr.kingshrimp;

import java.sql.SQLException;

public interface ShrimpDao {
	
	/**
	 * 새우 조회 
	 * @param inVO
	 * @return ShrimpVO
	 * @throws SQLException
	 */
    ShrimpVO doRetrieve(ShrimpVO shrimpVO)throws SQLException;
}

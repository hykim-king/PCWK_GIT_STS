package com.pcwk.ehr.kingshrimp;

import java.sql.SQLException;

public interface ShrimpDao {
	
	/**
	 * ์์ฐ ์กฐํ 
	 * @param inVO
	 * @return ShrimpVO
	 * @throws SQLException
	 */
    ShrimpVO doRetrieve(ShrimpVO shrimpVO)throws SQLException;
}

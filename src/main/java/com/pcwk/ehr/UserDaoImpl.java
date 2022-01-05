package com.pcwk.ehr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


public class UserDaoImpl implements UserDao {
	final static Logger LOG = LogManager.getLogger(UserDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	//interface
	private DataSource dataSource;
	
	RowMapper<UserVO>  rowMapper = new RowMapper<UserVO>() {

		public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserVO  user =new UserVO();
			user.setuId(rs.getString("u_id"));
			user.setName(rs.getString("name"));
			user.setPasswd(rs.getString("passwd")); 
			//1 -> BASIC
			user.setLevel(Level.valueOf(rs.getInt("u_level")));
			user.setLogin(  rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend") );
			user.setEmail(rs.getString("email")); 
			user.setRegDt(rs.getString("reg_dt"));
			
			return user;
		}
		
	};
	
	public UserDaoImpl() {}
	
	//DataSource를 구현한  SimpleDriverDataSource를 주입
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	

    //OK
	@SuppressWarnings("deprecation")
	public List<UserVO>  getAll(){
		List<UserVO>  list = new ArrayList<UserVO>();
		
		StringBuilder sb=new StringBuilder(100);
		sb.append(" SELECT                    \n");
		sb.append("     u_id,                 \n");
		sb.append("     name,                 \n");
		sb.append("     passwd,               \n");
		sb.append("     u_level,              \n");
		sb.append("     login,                \n");
		sb.append("     recommend,            \n");
		sb.append("     email,                \n");
		sb.append("     TO_CHAR(reg_dt,'yyyy/mm/dd hh24:mi:ss')  reg_dt              \n");
		sb.append(" FROM hr_member \n");
		sb.append(" ORDER BY u_id  \n");
		
		LOG.debug("sql=\n"+sb.toString());
		Object[] args = {};
		list = jdbcTemplate.query(sb.toString(), args, rowMapper);
		
		for(UserVO vo  :list) {
			LOG.debug("vo:"+vo);
		}
		
		
		
		return list;
	}

	/**
	 * OK
	 * 등록건수 
	 * @return
	 * @throws SQLException
	 */
	public int getCount() throws SQLException{
		int count = 0;
		//1.DB연결
		//2.SQL Statement,PreparedStatment
		//3.PreparedStatment수행
		//4.조회 ResultSet으로 정보를 받아와 처리
		//5.자원반납	
		
		StringBuilder sb=new StringBuilder(100);
		sb.append(" SELECT COUNT(*) cnt    \n");
		sb.append(" FROM hr_member         \n");
		LOG.debug("==============================");
		LOG.debug("=sql=\n"+sb.toString());
		LOG.debug("==============================");			
		
		count = this.jdbcTemplate.queryForObject(sb.toString(), Integer.class);
		LOG.debug("==============================");
		LOG.debug("=count="+count);
		LOG.debug("==============================");			
		
		return count;
	}

	
    /**OK
     * 전체 삭제 
     * @throws SQLException
     */
    public void deleteAll() throws SQLException{
    	StringBuilder sb=new StringBuilder(100);
		sb.append(" DELETE FROM hr_member \n");
		LOG.debug("==============================");
		LOG.debug("=sql=\n"+sb.toString());
		LOG.debug("==============================");	
		
		jdbcTemplate.update(sb.toString());
    }

	/**OK
	 * 사용자 등록 
	 * @param inVO
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int doInsert(final UserVO inVO) throws SQLException{
		int flag = 0;
		//BASIC(1),SILVER(2),GOLD(3);
		Object[] args = {inVO.getuId(),inVO.getName(),inVO.getPasswd()
		                ,inVO.getLevel().getValue(),inVO.getLogin(),inVO.getRecommend(),inVO.getEmail()		
		};
		
		StringBuilder sb=new StringBuilder(100);
		sb.append(" INSERT INTO hr_member (  \n");
		sb.append("     u_id,                \n");
		sb.append("     name,                \n");
		sb.append("     passwd,              \n");
		sb.append("     u_level,             \n");
		sb.append("     login,               \n");
		sb.append("     recommend,           \n");
		sb.append("     email,               \n");
		sb.append("     reg_dt               \n");
		sb.append(" ) VALUES (               \n");
		sb.append("     ?,                   \n");
		sb.append("     ?,                   \n");
		sb.append("     ?,                   \n");
		sb.append("     ?,                   \n");
		sb.append("     ?,                   \n");
		sb.append("     ?,                   \n");
		sb.append("     ?,                   \n");
		sb.append("     sysdate              \n");
		sb.append(" )                        \n");
		
		
		LOG.debug("==============================");
		LOG.debug("=sql=\n"+sb.toString());	
		LOG.debug("=args="+args);
		LOG.debug("==============================");	
		
		flag = jdbcTemplate.update(sb.toString(), args);
	    LOG.debug("flag:"+flag);
		return flag;
	}
	
	/**OK
	 * 사용자 조회
	 * @param inVO
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@SuppressWarnings("deprecation")
	public UserVO doSelectOne(UserVO inVO) throws SQLException{
		UserVO outVO = null;
		//1.DB연결
		//2.SQL Statement,PreparedStatment
		//3.PreparedStatment수행
		//4.조회 ResultSet으로 정보를 받아와 처리
		//5.자원반납		
		
		StringBuilder sb=new StringBuilder(100);
		sb.append(" SELECT                    \n");
		sb.append("     u_id,                 \n");
		sb.append("     name,                 \n");
		sb.append("     passwd,               \n");
		sb.append("     u_level,              \n");
		sb.append("     login,                \n");
		sb.append("     recommend,            \n");
		sb.append("     email,                \n");
		sb.append("     TO_CHAR(reg_dt,'yyyy/mm/dd hh24:mi:ss')  reg_dt              \n");
		sb.append(" FROM hr_member            \n");
		sb.append(" WHERE u_id = ?            \n");
		LOG.debug("==============================");
		LOG.debug("=sql=\n"+sb.toString());
		LOG.debug("=param="+inVO.toString());
		LOG.debug("==============================");
		Object[] args = {inVO.getuId()};
		outVO = this.jdbcTemplate.queryForObject(sb.toString(), args, rowMapper);
		
		LOG.debug("==============================");
		LOG.debug("=outVO="+outVO.toString());
		LOG.debug("==============================");	
		return outVO;
	}

	public int doDelete(UserVO inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int doUpdate(UserVO inVO) throws SQLException {
		int flag = 0;
		StringBuilder sb=new StringBuilder(300);
		sb.append(" UPDATE hr_member         \n");
		sb.append(" SET name       = ?       \n");
		sb.append("     ,passwd    = ?       \n");
		sb.append("     ,u_level   = ?       \n");
		sb.append("     ,login     = ?       \n");
		sb.append("     ,recommend = ?       \n");
		sb.append("     ,email     = ?       \n");
		sb.append("     ,reg_dt    = SYSDATE \n");
		sb.append(" WHERE u_id     = ?       \n");
		
		LOG.debug("==============================");
		LOG.debug("=sql=\n"+sb.toString());
		LOG.debug("=param="+inVO.toString());
		LOG.debug("==============================");		
		Object[] args = {inVO.getName(),inVO.getPasswd(),inVO.getLevel().getValue()
				        ,inVO.getLogin(),inVO.getRecommend(),inVO.getEmail(),inVO.getuId() };
		flag = jdbcTemplate.update(sb.toString(), args);
		LOG.debug("flag="+flag);
		return flag;
	}

	public List<UserVO> doRetrieve(Object inVO) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}

























package com.pcwk.ehr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//메소드 수행 순선:asc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)//JUnit기능 스프링 프레임으로 확장
@ContextConfiguration(locations = "/applicationContext.xml" ) //applicationContext.xml loading
public class JUserDaoTest {
    final Logger  LOG = LogManager.getLogger(getClass());
	
    //spring 컨텍스트 프레임워크는 변수 타입과 일치하는 컨텍스트 내의 빈을 찾고, 변수에 주입 한다.
    @Autowired
    ApplicationContext  context;
    
    @Autowired
    UserDao dao;
    
    UserVO  user01;
    UserVO  user02;
    UserVO  user03;
    
	@Before
	public void setUp() throws Exception {
		//public UserVO(String uId, String name, String passwd, Level level, int login, int recommend, String email) {
//		사용자 레벨은 : BASIC,SILVER,GOLD
//		사용자가 처음 가입 하면 : BASIC
//		가입이후 50회 이상 로그인 하면 : SILVER
//		SILVER 레벨이면서 30번 이상 추천을 받으면 GOLD로 레벨 UP.

		user01 = new UserVO("PCWK01","이상무01","1234",Level.BASIC,1,0,"jamesol01@paran.com");
		user02 = new UserVO("PCWK02","이상무02","1234",Level.SILVER,51,2,"jamesol02@paran.com");
		user03 = new UserVO("PCWK03","이상무03","1234",Level.GOLD,100,31,"jamesol03@paran.com");
		
		
		LOG.debug("1====================");
		LOG.debug("1=context="+context);
		LOG.debug("1=dao="+dao);
		LOG.debug("1====================");
		assertNotNull(context);
		assertNotNull(dao);
	}    

	@Test
	public void doUpdate() throws SQLException {
	  //1. 전체삭제
	  //2. 신규등록: user01
	  //3. user01수정
	  //4. user01데이터 update
	  //5. 1건조회후 서로 비교 user01
		
		//1. 
		dao.deleteAll();
		
		//2.
		int flag = dao.doInsert(user01);
		assertEquals(1, flag);
		
		//3.
		String updateStr = "_U";
		int    upInt     = 10;
		
		
		user01.setName(user01.getName()+updateStr);
		user01.setPasswd(user01.getPasswd()+updateStr);
		user01.setEmail(user01.getEmail()+updateStr);
		
		user01.setLogin(user01.getLogin()+upInt);
		user01.setRecommend(user01.getRecommend()+upInt);
		user01.setLevel(Level.SILVER);
		
		//4.
		flag = dao.doUpdate(user01);
		assertEquals(1, flag);
		
		
		//5.
		UserVO vsVO = dao.doSelectOne(user01);
		isSameUser(user01, vsVO);
		
		
		
		
		
	}
	
	
	
	
	@Test
	@Ignore
	public void  getAll() throws SQLException{
		//1. 전체 삭제
		//2. 데이터 입력 3건
		//3. 전체건수 조회 = 3건
		
		//1.
		dao.deleteAll();
		
		//2.
		int flag = dao.doInsert(user01);
		assertEquals(flag, 1);
		
		flag += dao.doInsert(user02);
		assertEquals(flag, 2);
		
		flag += dao.doInsert(user03);
		assertEquals(flag, 3);	
		
		//3. 전체건수 조회 = 3건
		List<UserVO> list = dao.getAll();
		assertEquals(list.size(), 3);
		
	}
	
	
	
	
	
	//EmptyResultDataAccessException 예외가 발생하면 : 성공
	@Test(expected = EmptyResultDataAccessException.class)
	@Ignore
	public void  getFailure() throws SQLException {
		LOG.debug("====================");
		LOG.debug("=getFailure()=");
		LOG.debug("====================");	
		
		dao.deleteAll();
		
		dao.doSelectOne(user01);

	} 
	
	
	//org.junit.runners.model.TestTimedOutException: test timed out after 1 milliseconds
	@Test(timeout = 10000)
	@Ignore
	public void addAndGet() {
		LOG.debug("====================");
		LOG.debug("=addAndGet()=");
		LOG.debug("====================");		
		
		//1. 전체삭제
		//2. 1건 입력
		//3. 건수 check
		//4. 데이터 1건 조회
		//5. 입력데이터와 비교
		try {
			//1.
			dao.deleteAll();
            //2.
		    int count = dao.doInsert(user01);
		    //assertThat(count, is(1));
		    //3.
		    int addNum = dao.getCount();
		    assertEquals(addNum, 1);
		    
		    //2건 추가
		    dao.doInsert(user02);
		    assertEquals(2,dao.getCount());
		    
		    //3건 추가
		    dao.doInsert(user03);
		    assertEquals(3,dao.getCount());
		    
		    //4.
		    UserVO outVO01 = dao.doSelectOne(user01);
		    isSameUser(outVO01, user01);
		    
		    UserVO outVO02= dao.doSelectOne(user02);
		    isSameUser(outVO02,user02);
		    
		    UserVO outVO03= dao.doSelectOne(user03);
		    isSameUser(outVO03,user03);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	private void isSameUser(UserVO outVO, UserVO userVO) {
	    assertEquals(outVO.getuId(), userVO.getuId());
	    assertEquals(outVO.getName(),userVO.getName());
	    assertEquals(outVO.getPasswd(),userVO.getPasswd());		
	    
	    //로그인, 추천,이메일,레벨
	    assertEquals(outVO.getLogin(),userVO.getLogin());
	    assertEquals(outVO.getRecommend(),userVO.getRecommend());
	    assertEquals(outVO.getEmail(),userVO.getEmail());
	    assertEquals(outVO.getLevel(),userVO.getLevel());
	}
	


	@After
	public void tearDown() throws Exception {
		LOG.debug("0====================");
		LOG.debug("0=tearDown=");
		LOG.debug("0====================");		
	}

}

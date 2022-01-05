package com.pcwk.ehr;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Arrays;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)//JUnit기능 스프링 프레임으로 확장
@ContextConfiguration(locations = "/applicationContext.xml")
public class JUserServiceTest {
    final Logger LOG = LogManager.getLogger(getClass());
    
    @Autowired
    ApplicationContext context;
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserDao    userDao;
    
    List<UserVO>   list;
    
	@Before
	public void setUp() throws Exception {
		LOG.debug("===============");
		LOG.debug("setUp()");
		LOG.debug("context:"+context);
		LOG.debug("userService:"+userService);
		LOG.debug("userDao:"+userDao);
		LOG.debug("===============");
		//public UserVO(String uId, String name, String passwd, Level level, int login, int recommend, String email) {
		list = Arrays.asList(
				   new UserVO("PCWK01","이상무01","1234_1",Level.BASIC,49,0,  "jamesol01@paran.com") //BASIC(1)
				  ,new UserVO("PCWK02","이상무02","1234_2",Level.BASIC,50,10, "jamesol02@paran.com") //BASIC -> SILVER(2)
				  ,new UserVO("PCWK03","이상무03","1234_3",Level.SILVER,51,29,"jamesol03@paran.com") //SILVER(2)
				  ,new UserVO("PCWK04","이상무04","1234_4",Level.SILVER,51,30,"jamesol04@paran.com") //SILVER -> GOLD(3)
				  ,new UserVO("PCWK05","이상무05","1234_5",Level.GOLD,52,31,  "jamesol05@paran.com") //GOLD(3)
				);
		assertNotNull(userDao);
		assertNotNull(userService);
		assertNotNull(context);
	}
	
	@Test
	public void add() throws SQLException{
		//1. 전체 데이터 삭제
		//2. Level있는 데이터, Level이 Null데이터를 만들어
		//3. add
		//4. 데이터 조회
		//5. Level있는 데이터는 동일한 Level, Level이 Null인 경우는 basic
		
		//1. 
		userDao.deleteAll();
		
		//2.
		UserVO userWithLevel = list.get(4);//GOLD
		UserVO userWithOutLevel = list.get(0);//BASIC
		userWithOutLevel.setLevel(null);//BASIC -> NULL
		
		//3.
		userService.add(userWithLevel);
		assertEquals(1, userDao.getCount());
		
		userService.add(userWithOutLevel);
		assertEquals(2, userDao.getCount());
		
		//4.
		UserVO  userWithLevelRead =userDao.doSelectOne(userWithLevel);
		UserVO  userWithOutLevelRead =userDao.doSelectOne(userWithOutLevel);
		
		assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
		assertEquals(userWithOutLevelRead.getLevel(),Level.BASIC);
	}
	
	@Test
	//@Ignore
	public void upgradeLevels() throws SQLException {
		LOG.debug("===============");
		LOG.debug("upgradeLevels()");
		LOG.debug("===============");	
		
		//1. 전체 데이터 삭제
		//2. list데이터 입력
		//3. 등업
		//4. 등업데이터 비교
		
		//1. 
		userDao.deleteAll();
		
		//2.
		for(UserVO vo  :list) {
			userDao.doInsert(vo);
		}
		
		assertEquals(5, userDao.getCount());
		
		//3.
		userService.upgradeLevels();
		
		//4.
		checkLevel(list.get(0), false);
		checkLevel(list.get(1), true);//BASIC 등업
		checkLevel(list.get(2), false);
		checkLevel(list.get(3), true);//SILVER 등업
		checkLevel(list.get(4), false);
		
	}
	
	private void checkLevel(UserVO user, boolean upgraded) throws SQLException {
		UserVO upUser = userDao.doSelectOne(user);
		//LOG.debug(upUser.getLevel()+"="+expectedLevel);
		if(upgraded==true) {
			LOG.debug(upUser.getLevel()+"="+user.getLevel().nextLevel());
			assertEquals(upUser.getLevel(), user.getLevel().nextLevel());
		}else {
			assertEquals(upUser.getLevel(), upUser.getLevel());
		}
	}
	
	
	@After
	public void tearDown() throws Exception {
		LOG.debug("===============");
		LOG.debug("tearDown()");
		LOG.debug("===============");		
	}



}

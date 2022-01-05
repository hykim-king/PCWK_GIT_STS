package com.pcwk.ehr;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    final Logger LOG = LogManager.getLogger(getClass());
    
    UserDao   userDao;
    
	public UserServiceImpl() {
		
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}



	public void upgradeLevels() throws SQLException {
//		사용자 레벨은 : BASIC,SILVER,GOLD
//		사용자가 처음 가입 하면 : BASIC
//		가입이후 50회 이상 로그인 하면 : SILVER
//		SILVER 레벨이면서 30번 이상 추천을 받으면 GOLD로 레벨 UP.
        
		//모든 사용자 정보를 가지고 온다.
		//BASIC 이고 로그인 50이상이면 -> SILVER
		//SILVER 추천 30번 이상이면    -> GOLD
		//GOLD 는 대상 아님
		List<UserVO>  list = userDao.getAll();
		for(UserVO user   :list) {
			boolean  changeLevel = false;
			
			try {
				if(canUpgradeLevel(user)) {
					upgradeLevel(user);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			
		}//--for

	}//--upgradeLevels() 

	public int add(UserVO inVO) throws SQLException {
        //level이 null이면 Level.BASIC
		if(null == inVO.getLevel()) {
			inVO.setLevel(Level.BASIC);
		}
		
		return userDao.doInsert(inVO);
	}

	public Boolean canUpgradeLevel(UserVO user) throws IllegalAccessException {
		
		Level currentLevel = user.getLevel();
		
		switch(currentLevel) {
			case BASIC:
				return (user.getLogin() >= 50);
			case SILVER:
				return (user.getRecommend() >=30);
			case GOLD:
				return false;
			default:
			    throw new IllegalAccessException("Unknown Level:"+currentLevel);
		}
		
	}

	public void upgradeLevel(UserVO user) throws SQLException {
		user.upgradeLevel();		
		userDao.doUpdate(user);
	}

}


















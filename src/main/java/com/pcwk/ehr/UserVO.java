package com.pcwk.ehr;

public class UserVO extends DTO {
	/** 사용자 ID */
	private String uId      ;
	
	/** 이름 */
	private String name     ;
	
	/** 비밀번호 */
	private String passwd   ;      
	
	/**등급 */
	private Level level ;
	
	/**로그인 */
	private int   login;
	
	/**추천 */
	private int   recommend;
	
	/**이메일 */
	private String email;
	
	/**등록일  */
	private String regDt;
	

	/** default생성자 */
	public UserVO() {}



	public UserVO(String uId, String name, String passwd, Level level, int login, int recommend, String email) {
		super();
		this.uId = uId;
		this.name = name;
		this.passwd = passwd;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
		this.email = email;
	}



	public String getuId() {
		return uId;
	}



	public void setuId(String uId) {
		this.uId = uId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPasswd() {
		return passwd;
	}



	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}



	public Level getLevel() {
		return level;
	}



	public void setLevel(Level level) {
		this.level = level;
	}



	public int getLogin() {
		return login;
	}



	public void setLogin(int login) {
		this.login = login;
	}



	public int getRecommend() {
		return recommend;
	}



	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getRegDt() {
		return regDt;
	}



	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}



	@Override
	public String toString() {
		return "UserVO [uId=" + uId + ", name=" + name + ", passwd=" + passwd + ", level=" + level + ", login=" + login
				+ ", recommend=" + recommend + ", email=" + email + ", regDt=" + regDt + ", toString()="
				+ super.toString() + "]";
	}

	
	public void upgradeLevel() {
		Level nextLevel = this.level.nextLevel();
		
		if(null == nextLevel ) {
			throw new IllegalArgumentException(this.level+"더이상 업그레이드가 불가능 합니다.");
		}else {
			this.level = nextLevel;
		}
		
	}

	
	
}

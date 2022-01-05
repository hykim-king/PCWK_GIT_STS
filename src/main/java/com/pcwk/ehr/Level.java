package com.pcwk.ehr;

public enum Level {

	//BASIC(1),SILVER(2),GOLD(3);
	GOLD(3,null),SILVER(2,GOLD),BASIC(1,SILVER);
	
	private final int value;
	/**다음 단계의 레벨 정보 */
	private final Level next;
	
	
	Level(int value,Level next){
		this.value = value;
		this.next  = next;
	}
	
	/**
	 * 다음 Level 가져오기 
	 * @return
	 */
	public Level nextLevel() {
		return this.next;
	}
	
	/**
	 * 값을 가져 오는 메서드
	 * @return
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * 값으로 부터 Level가져오기
	 * BASIC -> 1
	 * SILVER-> 2
	 * GOLD  -> 3
	 * @param value
	 * @return
	 */
	public static Level valueOf(int value) {
		switch(value) {
		   case 1: return BASIC;
		   case 2: return SILVER;
		   case 3: return GOLD;
		   default: throw new AssertionError("Unknown value:"+value);
			   
		}
	}
}

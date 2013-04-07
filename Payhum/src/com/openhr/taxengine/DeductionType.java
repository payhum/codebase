package com.openhr.taxengine;

public enum DeductionType {
	SELF_LIFE_INSURANCE(0),
	
	SPOUSE_LIFE_INSURANCE(1),
	
	LIFE_INSURANCE(2),
	
	DONATION(3),
	
	EMPLOYEE_SOCIAL_SECURITY(4);
	
	private int value;
	
	DeductionType(int e) {
		this.value = e;
	}
	
	public int getValue() {
		return this.value;
	}
}

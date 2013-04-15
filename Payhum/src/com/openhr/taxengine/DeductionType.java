package com.openhr.taxengine;

public enum DeductionType {
	SELF_LIFE_INSURANCE(1),
	
	SPOUSE_LIFE_INSURANCE(2),
	
	LIFE_INSURANCE(3),
	
	DONATION(4),
	
	EMPLOYEE_SOCIAL_SECURITY(5);
	
	private int value;
	
	DeductionType(int e) {
		this.value = e;
	}
	
	public int getValue() {
		return this.value;
	}
}

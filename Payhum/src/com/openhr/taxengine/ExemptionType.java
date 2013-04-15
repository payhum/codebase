package com.openhr.taxengine;

public enum ExemptionType {
	SUPPORTING_SPOUSE(1),
	
	CHILDREN(2),
	
	BASIC_ALLOWANCE(3), 
	
	BASIC_ALLOWANCE_PERCENTAGE(4),
	
	BASIC_ALLOWANCE_LIMIT(5);
	
	private int value;
	
	ExemptionType(int e) {
		this.value = e;
	}
	
	public int getValue() {
		return this.value;
	}
}

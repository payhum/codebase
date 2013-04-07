package com.openhr.taxengine;

public enum ExemptionType {
	SUPPORTING_SPOUSE(0),
	
	CHILDREN(1),
	
	BASIC_ALLOWANCE(2), 
	
	BASIC_ALLOWANCE_PERCENTAGE(3),
	
	BASIC_ALLOWANCE_LIMIT(4);
	
	private int value;
	
	ExemptionType(int e) {
		this.value = e;
	}
	
	public int getValue() {
		return this.value;
	}
}

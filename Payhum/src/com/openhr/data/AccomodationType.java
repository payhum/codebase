package com.openhr.data;

public enum AccomodationType {
	FREE_ACCOM_FROM_EMPLOYER_FULLY_FURNISHED (0),
	
	FREE_ACCOM_FROM_EMPLOYER_NOT_FURNISHED (1);
	
	private int value;
	
	AccomodationType(int e) {
		this.value = e;
	}
	
	public int getValue() {
		return this.value;
	}
}


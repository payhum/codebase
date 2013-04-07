package com.openhr.data;

public enum ResidentType {
	LOCAL(0),
	
	RESIDENT_FOREIGNER(1),
	
	NON_RESIDENT_FOREIGNER(2);
	
	private int value;
	
	ResidentType (int e) {
		this.value = e;
	}
	
	public int getValue() {
		return this.value;
	}
}

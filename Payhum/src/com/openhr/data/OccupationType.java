package com.openhr.data;

public enum OccupationType {
	STUDENT (0),
	
	NONE (1);
	
	private int value;
	
	OccupationType(int e) {
		this.value = e;
	}
	
	public int getValue() {
		return this.value;
	}
}

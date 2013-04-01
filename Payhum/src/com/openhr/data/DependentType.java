package com.openhr.data;

public enum DependentType {
	SPOUSE(0),
	
	CHILD(1),
	
	PARENT(2);
	
	private int value;
		
	DependentType(int e) {
		this.value = e;
	}
	
	public int getValue() {
		return this.value;
	}
}

package com.openhr.employee.form;

import org.apache.struts.action.ActionForm;

public class EmpDependentsFrom extends ActionForm {
	 private Integer id;
	 
	 private Integer age;
	 
	 private String name;
	 
	 private Integer occupationType;
	 
	 private Integer depType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(Integer occupationType) {
		this.occupationType = occupationType;
	}

	public Integer getDepType() {
		return depType;
	}

	public void setDepType(Integer depType) {
		this.depType = depType;
	}
}

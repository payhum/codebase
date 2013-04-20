package com.openhr.deductiondec.form;

import org.apache.struts.action.ActionForm;

import com.openhr.data.DeductionsType;
import com.openhr.data.EmployeePayroll;

public class DeductionForm extends ActionForm {

	private Integer id;

	private Integer type;
	private Double amount;
	private String name; 
	private String description;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

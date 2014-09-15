package com.openhr.glreports.form;

import org.apache.struts.action.ActionForm;

public class GlReportForm extends ActionForm{
	
	private Double credt;
	
    private String empName; 
    
    private String empId;
    
    public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	private  String ename;
    
    private Double debit;

	public Double getCredt() {
		return credt;
	}

	public void setCredt(Double credt) {
		this.credt = credt;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}
    
    
    
    
}

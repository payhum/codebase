package com.openhr.mleave;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class LeaveRequestForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
 	private String employeeId;
	private Integer leaveTypeId;
	private Date leaveDate = new Date();
	private Integer noOfDays;
	private Date returnDate = new Date();
	private String description;
 	
	public String getEmployeeId() {
 		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(Integer leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public long getLeaveDate() {
		return leaveDate.getTime();
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public Integer getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
	public long getReturnDate() {
		return returnDate.getTime();
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
 
}

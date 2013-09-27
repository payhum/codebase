package com.openhr.company;

import java.util.Date;

public class ProcessBranchForm {

	private Integer branchId;
	private String usd;
	private String euro;
	private String pound;
	private long salPayDate;
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public String getUsd() {
		return usd;
	}
	public void setUsd(String usd) {
		this.usd = usd;
	}
	public String getEuro() {
		return euro;
	}
	public void setEuro(String euro) {
		this.euro = euro;
	}
	public String getPound() {
		return pound;
	}
	public void setPound(String pound) {
		this.pound = pound;
	}
	public long getSalPayDate() {
		return salPayDate;
	}
	public void setSalPayDate(long salPayDate) {
		this.salPayDate = salPayDate;
	}
}

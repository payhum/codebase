package com.openhr.glreports.form;

import org.apache.struts.action.ActionForm;

public class GlReportForm extends ActionForm{
	
	private Integer id;
	 private String accno;
	 
	 private String accname;
	 
	 private Double debit;
	 
	 private Double credit;
	 
	 private Double sumcredit;
	 private Double sumdebit;
	 
	 private long date;
	 
	 
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccno() {
		return accno;
	}
	public void setAccno(String accno) {
		this.accno = accno;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public Double getDebit() {
		return debit;
	}
	public void setDebit(Double debit) {
		this.debit = debit;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public Double getSumcredit() {
		return sumcredit;
	}
	public void setSumcredit(Double sumcredit) {
		this.sumcredit = sumcredit;
	}
	public Double getSumdebit() {
		return sumdebit;
	}
	public void setSumdebit(Double sumdebit) {
		this.sumdebit = sumdebit;
	}

}

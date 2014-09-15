package com.openhr.data;

import java.util.Date;

public class GLReportEmpData {
	
	private String employeeID;
	private String employeeName;
	private String deptName;
	private Date hireDate;
	private Date lastDate;
	private double monthlyGross;
	private double annualGross;
	private double baseSalary;
	private double paidBaseSalary;
	private double bonus;
	private double paidBonus;
	private double ot;
	private double paidOT;
	private double otherIncome;
	private double paidOtherIncome;
	private double retroSal;
	private double paidRetroSal;
	private double allowance;
	private double paidAllowance;
	private double accom;
	private double paidAccom;
	private double taxableOSAmt;
	private double paidTaxableOSAmt;
	private double emprSS;
	private double paidEmprSS;
	private double leaveLoss;
	private double paidLeaveLoss;
	private Double paidTaxAmtInMMK;
	private Double taxAmtInMMK;
	private double paidNetPay;
	private double netPay;
	private double paidTaxAmt;
	private double taxAmt;
	private double paidTaxableIncome;
	private double taxableIncome;
	private double exemDonation;
	private double paidExemDonation;
	private double paidDeducEmpeSS;
	private double deducEmpeSS;
	private double paidDeducLifeInsurance;
	private double deducLifeInsurance;
	private double paidExemSpouse;
	private double exemSpouse;
	private double paidExemBasicAllow;
	private double exemBasicAllow;
	private double paidExemChildren;
	private double exemChildren;
	private boolean retro;
	private String payPeriod;
	private String payDate;

	public void setEmployeeID(String employeeId) {
		this.employeeID = employeeId;
	}

	public void setEmployeeName(String fullName) {
		this.employeeName = fullName;
	}

	public void setDeptName(String deptname) {
		this.deptName = deptname;
	}

	public void setHireDate(Date hiredate) {
		this.hireDate = hiredate;
	}

	public void setLastDate(Date inactiveDate) {
		this.lastDate = inactiveDate;
	}

	public void setMonthlyGross(double d) {
		this.monthlyGross = d;
	}

	public void setAnnualGross(double d) {
		this.annualGross = d;
	}

	public void setBaseSalary(double d) {
		this.baseSalary = d;
	}

	public void setPaidBaseSalary(double d) {
		this.paidBaseSalary = d;
	}

	public void setPaidBonus(double d) {
		this.paidBonus = d;
	}

	public void setBonus(double d) {
		this.bonus = d;
	}

	public void setOT(double d) {
		this.ot = d;
	}

	public void setPaidOT(double d) {
		this.paidOT = d;
	}

	public void setOtherIncome(double d) {
		this.otherIncome = d;
	}

	public void setPaidOtherIncome(double d) {
		this.paidOtherIncome = d;
	}

	public void setRetroSal(double d) {
		this.retroSal = d;
	}

	public void setPaidRetroSal(double d) {
		this.paidRetroSal = d;
	}

	public void setRetro(boolean b) {
		this.retro = b;
	}

	public void setAllowance(double d) {
		this.allowance = d;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getDeptName() {
		return deptName;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public double getMonthlyGross() {
		return monthlyGross;
	}

	public double getAnnualGross() {
		return annualGross;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public double getPaidBaseSalary() {
		return paidBaseSalary;
	}

	public double getBonus() {
		return bonus;
	}

	public double getPaidBonus() {
		return paidBonus;
	}

	public double getPaidOT() {
		return paidOT;
	}

	public double getOtherIncome() {
		return otherIncome;
	}

	public double getPaidOtherIncome() {
		return paidOtherIncome;
	}

	public double getRetroSal() {
		return retroSal;
	}

	public double getPaidRetroSal() {
		return paidRetroSal;
	}

	public double getAllowance() {
		return allowance;
	}

	public double getPaidAllowance() {
		return paidAllowance;
	}

	public double getAccom() {
		return accom;
	}

	public double getPaidAccom() {
		return paidAccom;
	}

	public double getTaxableOSAmt() {
		return taxableOSAmt;
	}

	public double getPaidTaxableOSAmt() {
		return paidTaxableOSAmt;
	}

	public double getEmprSS() {
		return emprSS;
	}

	public double getPaidEmprSS() {
		return paidEmprSS;
	}

	public double getLeaveLoss() {
		return leaveLoss;
	}

	public double getPaidLeaveLoss() {
		return paidLeaveLoss;
	}

	public Double getPaidTaxAmtInMMK() {
		return paidTaxAmtInMMK;
	}

	public Double getTaxAmtInMMK() {
		return taxAmtInMMK;
	}

	public double getPaidNetPay() {
		return paidNetPay;
	}

	public double getNetPay() {
		return netPay;
	}

	public double getPaidTaxAmt() {
		return paidTaxAmt;
	}

	public double getTaxAmt() {
		return taxAmt;
	}

	public double getPaidTaxableIncome() {
		return paidTaxableIncome;
	}

	public double getTaxableIncome() {
		return taxableIncome;
	}

	public double getExemDonation() {
		return exemDonation;
	}

	public double getPaidExemDonation() {
		return paidExemDonation;
	}

	public double getPaidDeducEmpeSS() {
		return paidDeducEmpeSS;
	}

	public double getDeducEmpeSS() {
		return deducEmpeSS;
	}

	public double getPaidDeducLifeInsurance() {
		return paidDeducLifeInsurance;
	}

	public double getDeducLifeInsurance() {
		return deducLifeInsurance;
	}

	public double getPaidExemSpouse() {
		return paidExemSpouse;
	}

	public double getExemSpouse() {
		return exemSpouse;
	}

	public double getPaidExemBasicAllow() {
		return paidExemBasicAllow;
	}

	public double getExemBasicAllow() {
		return exemBasicAllow;
	}

	public double getPaidExemChildren() {
		return paidExemChildren;
	}

	public double getExemChildren() {
		return exemChildren;
	}

	public boolean isRetro() {
		return retro;
	}

	public void setOt(double ot) {
		this.ot = ot;
	}

	public void setPaidAllowance(double d) {
		this.paidAllowance = d;
	}

	public void setAccom(double d) {
		this.accom = d;
	}

	public void setPaidAccom(double d) {
		this.paidAccom = d;
	}

	public void setTaxableOSAmt(double d) {
		this.taxableOSAmt = d;
	}

	public void setPaidTaxableOSAmt(double d) {
		this.paidTaxableOSAmt = d;
	}

	public void setEmprSS(double d) {
		this.emprSS = d;
	}

	public void setPaidEmprSS(double d) {
		this.paidEmprSS = d;
	}

	public void setLeaveLoss(double d) {
		this.leaveLoss = d;	
	}

	public void setPaidLeaveLoss(double d) {
		this.paidLeaveLoss = d;
	}

	public void setExemChildren(double d) {
		this.exemChildren = d;
	}

	public void setPaidExemChildren(double d) {
		this.paidExemChildren = d;
	}

	public void setExemBasicAllow(double d) {
		this.exemBasicAllow = d;
	}

	public void setPaidExemBasicAllow(double d) {
		this.paidExemBasicAllow = d;
	}

	public void setExemSpouse(double d) {
		this.exemSpouse = d;
	}

	public void setPaidExemSpouse(double d) {
		this.paidExemSpouse = d;
	}

	public void setDeducLifeInsurance(double d) {
		this.deducLifeInsurance = d;
	}

	public void setPaidDeducLifeInsurance(double d) {
		this.paidDeducLifeInsurance = d;
	}

	public void setDeducEmpeSS(double d) {
		this.deducEmpeSS = d;
	}

	public void setPaidDeducEmpeSS(double d) {
		this.paidDeducEmpeSS = d;
	}

	public void setPaidExemDonation(double d) {
		this.paidExemDonation = d;
	}

	public void setExemDonation(double d) {
		this.exemDonation = d;
	}

	public void setTaxableIncome(double d) {
		this.taxableIncome = d;
	}

	public void setPaidTaxableIncome(double d) {
		this.paidTaxableIncome = d;
	}

	public void setTaxAmt(double d) {
		this.taxAmt = d;
	}

	public void setPaidTaxAmt(double d) {
		this.paidTaxAmt = d;
	}

	public void setNetPay(double d) {
		this.netPay = d;
	}

	public void setPaidNetPay(double d) {
		this.paidNetPay = d;
	}

	public void setTaxAmtInMMK(Double taxAmount) {
		this.taxAmtInMMK = taxAmount;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public double getOt() {
		return ot;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPaidTaxAmtInMMK(Double paidTaxAmt) {
		this.paidTaxAmtInMMK = paidTaxAmt;
	}

	public void setPayPeriod(String string) {
		this.payPeriod = string;
	}

	public void setPayDate(String dateFormatString) {
		this.payDate = dateFormatString;
	}


}

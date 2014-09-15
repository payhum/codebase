package com.openhr.data;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.openhr.common.PayhumConstants;

/**
*
* @author Vijay
*/
@Entity
@Table(name = "emp_payroll_map", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "EmpPayrollMap.findAll", query = "SELECT p FROM EmpPayrollMap p"),
   @NamedQuery(name = "EmpPayrollMap.findBypayrollId",query ="select emap from  EmpPayrollMap emap  where emap.payrollId= ?"),
   @NamedQuery(name = "EmpPayrollMap.findByPayDateId",query ="select emap from  EmpPayrollMap emap, Payroll pr where pr.id=emap.payrollId and pr.payDateId= ?"),
   @NamedQuery(name = "EmpPayrollMap.findByEmpPayrollId", query = "SELECT p FROM EmpPayrollMap p WHERE p.emppayId = ?"),
   @NamedQuery(name = "EmpPayrollMap.findByEmpPayrollIdAndPayrollId", query = "SELECT p FROM EmpPayrollMap p WHERE p.emppayId = ? AND p.payrollId = ?")})
public class EmpPayrollMap implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "id", nullable = false)
   private Integer id;
   
   @JoinColumn(name = "emppayId", referencedColumnName = "id", nullable = false)
   @ManyToOne(optional = false)
   private EmployeePayroll emppayId;
   
   @JoinColumn(name = "payrollId", referencedColumnName = "id", nullable = false)
   @ManyToOne(optional = false)
   private Payroll payrollId;
   
   @Basic(optional = false)
   @Column(name = "taxAmount", nullable = false)
   private Double taxAmount;
   
   @Basic(optional = false)
   @Column(name = "netPay", nullable = false)
   private Double netPay;
   
   @Basic(optional = false)
   @Column(name = "empeSocialSec", nullable = false)
   private Double empeSocialSec;
   
   @Basic(optional = false)
   @Column(name = "emprSocialSec", nullable = false)
   private Double emprSocialSec;
   
   @Basic(optional = false)
   @Column(name = "overtimeAmt", nullable = false)
   private Double overtimeAmt;

   @Basic(optional = false)
   @Column(name = "otherIncome", nullable = false)
   private Double otherIncome;

   @Basic(optional = false)
   @Column(name = "mode", nullable = false)
   private Integer mode;
   
   @Basic(optional = false)
   @Column(name = "baseSalary", nullable = false)
   private Double baseSalary;
   
   @Basic(optional = false)
   @Column(name = "bonus", nullable = false)
   private Double bonus;
   
   @Basic(optional = false)
   @Column(name = "retroBaseSal", nullable = false)
   private Double retroBaseSal;
   
   @Basic(optional = false)
   @Column(name = "currencyConverRate", nullable = false)
   private Double currencyConverRate;
   
   @Basic(optional = false)
   @Column(name = "salPayDate", nullable = false)
   private Integer salPayDate;
   
   @Basic(optional = false)
   @Column(name = "taxableAmt", nullable = false)
   private Double taxableAmt;

   @Basic(optional = false)
   @Column(name = "accomAmt", nullable = false)
   private Double accomAmt;

   @Basic(optional = false)
   @Column(name = "basicAllow", nullable = false)
   private Double basicAllow;
   
   @Basic(optional = false)
   @Column(name = "leaveLoss", nullable = false)
   private Double leaveLoss;

   @Basic(optional = false)
   @Column(name = "allowance", nullable = false)
   private Double allowance;
   
   public Double getLeaveLoss() {
		return leaveLoss;
	}
	
	public void setLeaveLoss(Double leaveLoss) {
		this.leaveLoss = leaveLoss;
	}

	public Double getBasicAllow() {
		return basicAllow;
	}
	
	public void setBasicAllow(Double basicAllow) {
		this.basicAllow = basicAllow;
	}
	
	public Double getAccomAmt() {
	   return accomAmt;
	}
	
	public void setAccomAmt(Double accomAmt) {
		this.accomAmt = accomAmt;
	}
	
	public Double getTaxableAmt() {
		return taxableAmt;
	}
	
	public void setTaxableAmt(Double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public Double getOvertimeAmt() {
	   return overtimeAmt;
	}
	
	public void setOvertimeAmt(Double overtimeAmt) {
		this.overtimeAmt = overtimeAmt;
	}
	
	public Double getOtherIncome() {
		return otherIncome;
	}
	
	public void setOtherIncome(Double otherIncome) {
		this.otherIncome = otherIncome;
	}
	
   public EmpPayrollMap() {
	   this.leaveLoss = 0D;
	   this.allowance = 0D;
   }
	
	public Double getAllowance() {
		return allowance;
	}
	
	public void setAllowance(Double allowance) {
		this.allowance = allowance;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public EmployeePayroll getEmppayId() {
		return emppayId;
	}
	
	public void setEmppayId(EmployeePayroll emppayId) {
		this.emppayId = emppayId;
	}
	
	public Payroll getPayrollId() {
		return payrollId;
	}
	
	public void setPayrollId(Payroll payrollId) {
		this.payrollId = payrollId;
	}
		
	public Double getTaxAmount() {
		return taxAmount;
	}
	
	public void setTaxAmount(Double taxAmt) {
		this.taxAmount = taxAmt;
	}
	
	public Double getNetPay() {
		return netPay;
	}
	
	public void setNetPay(Double netPay) {
		this.netPay = netPay;
	}
	
	public Double getEmpeSocialSec() {
		return empeSocialSec;
	}
	
	public void setEmpeSocialSec(Double socialSec) {
		this.empeSocialSec = socialSec;
	}

	public Double getEmprSocialSec() {
		return emprSocialSec;
	}
	
	public void setEmprSocialSec(Double socialSec) {
		this.emprSocialSec = socialSec;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public Double getRetroBaseSal() {
		return retroBaseSal;
	}

	public void setRetroBaseSal(Double retroBaseSal) {
		this.retroBaseSal = retroBaseSal;
	}

	public Double getCurrencyConverRate() {
		return currencyConverRate;
	}

	public void setCurrencyConverRate(Double currencyConverRate) {
		this.currencyConverRate = currencyConverRate;
	}

	public Integer getSalPayDate() {
		return salPayDate;
	}

	public void setSalPayDate(Integer salPayDate) {
		this.salPayDate = salPayDate;
	}
	
}

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
   @Column(name = "socialSec", nullable = false)
   private Double socialSec;
   
   @Basic(optional = false)
   @Column(name = "overtimeAmt", nullable = false)
   private Double overtimeAmt;

   @Basic(optional = false)
   @Column(name = "otherIncome", nullable = false)
   private Double otherIncome;

   @Basic(optional = false)
   @Column(name = "mode", nullable = false)
   private Integer mode;
   
   
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
	
	public Double getSocialSec() {
		return socialSec;
	}
	
	public void setSocialSec(Double socialSec) {
		this.socialSec = socialSec;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}
}

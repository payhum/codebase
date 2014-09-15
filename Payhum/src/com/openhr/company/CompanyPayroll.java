package com.openhr.company;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.openhr.common.PayhumConstants;
import com.openhr.data.Branch;

/**
 * @author Vijay
 *
 */
@Entity
@Table(name = "company_payroll", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "CompanyPayroll.findAll", query = "SELECT e FROM CompanyPayroll e"),
    @NamedQuery(name = "CompanyPayroll.findById", query = "SELECT e FROM CompanyPayroll e WHERE e.id = ?"),
    @NamedQuery(name = "CompanyPayroll.findByCompAndProcessedDate", query = "SELECT e FROM CompanyPayroll e WHERE e.branchId = ? " + 
    		" AND MONTH(e.processedDate) = MONTH(?) AND YEAR(e.processedDate) = YEAR(?)")})
public class CompanyPayroll implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "branchId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Branch branchId;
    
    @Basic(optional = false)
    @Column(name = "processedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedDate;
    
    @Basic(optional = false)
    @Column(name = "employeeId", nullable = false, length=45)
    private String employeeId;
    
    @Basic(optional = false)
    @Column(name = "taxAmount", nullable = false)
    private Double taxAmount;
    
    @Basic(optional = false)
    @Column(name = "netPay", nullable = false)
    private Double netPay;
    
    @Basic(optional = false)
    @Column(name = "emprSocialSec", nullable = false)
    private Double emprSocialSec;
    
    @Basic(optional = false)
    @Column(name = "empeSocialSec", nullable = false)
    private Double empeSocialSec;
    
    @Basic(optional = false)
    @Column(name = "empFullName", nullable = false, length=60)
    private String empFullName;

    @Basic(optional = false)
    @Column(name = "empNationalID", nullable = false, length=45)
    private String empNationalID;
    
	@Basic(optional = false)
    @Column(name = "bankName", nullable = false, length=45)
    private String bankName;
    
    @Basic(optional = false)
    @Column(name = "bankBranch", nullable = false, length=45)
    private String bankBranch;

    @Basic(optional = false)
    @Column(name = "accountNo", nullable = false, length=45)
    private String accountNo;

    @Basic(optional = false)
    @Column(name = "routingNo", nullable = false, length=45)
    private String routingNo;
    
    @Basic(optional = false)
    @Column(name = "currencySym", nullable = false, length=10)
    private String currencySym;
    
    @Basic(optional = false)
    @Column(name = "deptName", nullable = false, length=45)
    private String deptName;
    
    @Basic(optional = false)
    @Column(name = "baseSalary", nullable = false)
    private Double baseSalary;
    
    @Basic(optional = false)
    @Column(name = "residentType", nullable = false, length=45)
    private String residentType;
    
    public String getDeptName() {
		return deptName;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRoutingNo() {
		return routingNo;
	}

	public String getResidentType() {
		return residentType;
	}

	public void setResidentType(String residentType) {
		this.residentType = residentType;
	}

	public void setRoutingNo(String routingNo) {
		this.routingNo = routingNo;
	}

	public String getEmpFullName() {
		return empFullName;
	}

	public void setEmpFullName(String empFullName) {
		this.empFullName = empFullName;
	}

	public String getBankName() {
		return bankName;
	}

	public Double getEmprSocialSec() {
		return emprSocialSec;
	}

	public void setEmprSocialSec(Double socialSec) {
		this.emprSocialSec = socialSec;
	}

	public Double getEmpeSocialSec() {
		return empeSocialSec;
	}

	public void setEmpeSocialSec(Double socialSec) {
		this.empeSocialSec = socialSec;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public CompanyPayroll () {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Branch getBranchId() {
		return branchId;
	}

	public void setBranchId(Branch branchId) {
		this.branchId = branchId;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getNetPay() {
		return netPay;
	}

	public void setNetPay(Double netPay) {
		this.netPay = netPay;
	}
	
    public String getEmpNationalID() {
		return empNationalID;
	}

	public void setEmpNationalID(String empNationalID) {
		this.empNationalID = empNationalID;
	}

	public String getCurrencySym() {
		return currencySym;
	}

	public void setCurrencySym(String currencySym) {
		this.currencySym = currencySym;
	}
}

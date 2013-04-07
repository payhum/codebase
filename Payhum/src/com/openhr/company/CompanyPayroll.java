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

@Entity
@Table(name = "company_payroll", catalog = "payhumrepo", schema = "")
@NamedQueries({
    @NamedQuery(name = "CompanyPayroll.findAll", query = "SELECT e FROM CompanyPayroll e"),
    @NamedQuery(name = "CompanyPayroll.findById", query = "SELECT e FROM CompanyPayroll e WHERE e.id = ?"),
    @NamedQuery(name = "CompanyPayroll.findByProcessedDate", query = "SELECT e FROM CompanyPayroll e WHERE e.processedDate = ?"),
    @NamedQuery(name = "CompanyPayroll.findByCompIdMonthYear", query = "SELECT e FROM CompanyPayroll e WHERE e.companyId= ? AND e.month=? AND e.year=?"),
    @NamedQuery(name = "CompanyPayroll.findByCompanyId", query = "SELECT e FROM CompanyPayroll e WHERE e.companyId = ?")})
public class CompanyPayroll implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "companyId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Company companyId;
    
    @Basic(optional = false)
    @Column(name = "processedDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date processedDate;
    
    @Basic(optional = false)
    @Column(name = "employeeId", nullable = false)
    private Integer employeeId;
    
    @Basic(optional = false)
    @Column(name = "taxAmount", nullable = false)
    private Double taxAmount;
    
    @Basic(optional = false)
    @Column(name = "netPay", nullable = false)
    private Double netPay;
    
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
    @Column(name = "month", nullable = false)
    private Integer month;

    @Basic(optional = false)
    @Column(name = "year", nullable = false)
    private Integer year;

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
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

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
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
}

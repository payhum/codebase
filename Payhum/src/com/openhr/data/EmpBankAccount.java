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

@Entity
@Table(name = "employee_account", catalog = "payhumrepo", schema = "")
@NamedQueries({
    @NamedQuery(name = "EmpBankAccount.findAll", query = "SELECT e FROM EmpBankAccount e"),
    @NamedQuery(name = "EmpBankAccount.findById", query = "SELECT e FROM EmpBankAccount e WHERE e.id = ?"),
    @NamedQuery(name = "EmpBankAccount.findByEmployeeId", query = "SELECT e FROM EmpBankAccount e WHERE e.employeeId = ?")})
public class EmpBankAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "employeeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Employee employeeId;
    
    @Basic(optional = false)
    @Column(name = "bankName", nullable = false, length = 45)
    private String bankName;
    
    @Basic(optional = false)
    @Column(name = "bankBranch", nullable = false, length = 90)
    private String bankBranch;

    @Basic(optional = false)
    @Column(name = "accountNo", nullable = false, length = 90)
    private String accountNo;
    
	public EmpBankAccount () {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
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
}

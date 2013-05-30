package com.openhr.taxengine;

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

import com.openhr.common.PayhumConstants;
import com.openhr.data.DeductionsType;
import com.openhr.data.EmployeePayroll;

@Entity
@Table(name = "deduction_decl", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "DeductionsDeclared.findAll", query = "SELECT e FROM  DeductionsDeclared e"),
    @NamedQuery(name = "DeductionsDeclared.findType", query = "SELECT e FROM  DeductionsDeclared e where e.type=?"),
    @NamedQuery(name = "DeductionsDeclared.find", query = "SELECT e FROM  DeductionsDeclared e where e.payrollId=?")}
		)
public class DeductionsDeclared implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(targetEntity=DeductionsType.class)
    @JoinColumn(name = "type", referencedColumnName="id")
	private DeductionsType type;
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
	private Double amount;
    @ManyToOne(targetEntity=EmployeePayroll.class)
    @JoinColumn(name = "payrollId", referencedColumnName="id")
    private EmployeePayroll payrollId;
    
    @Basic(optional = false)
    @Column(name = "description")
	private String description;
    
    
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DeductionsDeclared() {
		
	}

	public DeductionsDeclared(EmployeePayroll eid, DeductionsType type, Double amt) {
		this.type = type;
		this.amount = amt;
		this.payrollId = eid;
	}

	public DeductionsType getType() {
		return type;
	}

	public void setType(DeductionsType type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EmployeePayroll getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(EmployeePayroll pId) {
		this.payrollId = pId;
	}

}

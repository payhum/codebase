package com.openhr.taxengine;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

import org.hibernate.engine.Cascade;

import com.openhr.common.PayhumConstants;
import com.openhr.data.DeductionsType;
import com.openhr.data.EmployeePayroll;

@Entity
@Table(name = "deduction_done", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "DeductionsDone.findAll", query = "SELECT e FROM  DeductionsDone e"),
    @NamedQuery(name = "DeductionsDone.findByEmpPayrollId", query = "SELECT e FROM  DeductionsDone e where e.payrollId = ?")})
public class DeductionsDone implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @ManyToOne(targetEntity=DeductionsType.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "type", referencedColumnName="id")
	private DeductionsType type;

    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
	private Double amount;
    
    @ManyToOne(targetEntity=EmployeePayroll.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "payrollId", referencedColumnName="id")
    private EmployeePayroll payrollId;

	public DeductionsDone() {
		
	}
	
	public DeductionsDone(EmployeePayroll epid, DeductionsType type, Double amt) {
		this.type = type;
		this.amount = amt;
		this.payrollId = epid;
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

	public EmployeePayroll getEmployeePayrollId() {
		return payrollId;
	}

	public void setEmployeePayrollId(EmployeePayroll payrollId) {
		this.payrollId = payrollId;
	}

}

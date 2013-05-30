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
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Exemptionstype;

@Entity
@Table(name = "exemptions_done", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "ExemptionsDone.findAll", query = "SELECT e FROM  ExemptionsDone e"),
    @NamedQuery(name = "ExemptionsDone.findByEmpPayrollId", query = "SELECT e FROM  ExemptionsDone e where e.payrollId = ?")})
public class ExemptionsDone implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(targetEntity=Exemptionstype.class)
    @JoinColumn(name = "type", referencedColumnName="id")
	private Exemptionstype type;
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
	private Double amount;
    @ManyToOne(targetEntity=EmployeePayroll.class)
    @JoinColumn(name = "payrollId", referencedColumnName="id")
    private EmployeePayroll payrollId;
    
	public ExemptionsDone() {
		
	}
	
	public ExemptionsDone(EmployeePayroll epid, Exemptionstype type, Double amt) {
		this.type = type;
		this.amount = amt;
		this.payrollId = epid;
	}

	public Exemptionstype getType() {
		return type;
	}

	public void setType(Exemptionstype type) {
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

	public void setEmployeePayrollId(EmployeePayroll epId) {
		this.payrollId = epId;
	}

}

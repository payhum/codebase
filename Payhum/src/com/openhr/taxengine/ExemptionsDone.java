package com.openhr.taxengine;


import java.io.Serializable;
import javax.persistence.FetchType;
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

import com.openhr.data.DeductionType;
import com.openhr.data.Employee;
import com.openhr.data.Exemptionstype;

@Entity
@Table(name = "exemptions_done", catalog = "payhumrepo", schema = "")
@NamedQueries({
    @NamedQuery(name = "ExemptionsDone.findAll", query = "SELECT e FROM  ExemptionsDone e"),
    @NamedQuery(name = "ExemptionsDone.find", query = "SELECT e FROM  ExemptionsDone e where e.employeeId=?")})
public class ExemptionsDone implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(targetEntity=Exemptionstype.class, cascade=CascadeType.ALL)
    @JoinColumn(name = "type", referencedColumnName="id")
	private Exemptionstype type;
    
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
	private Double amount;
    @ManyToOne(targetEntity=Employee.class, cascade=CascadeType.ALL)
    @JoinColumn(name = "employeeId", referencedColumnName="id")
    private Employee employeeId;
    
	public ExemptionsDone() {
		
	}
	
	public ExemptionsDone(Employee eid, Exemptionstype type, Double amt) {
		this.type = type;
		this.amount = amt;
		this.employeeId = eid;
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

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}

}

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

<<<<<<< HEAD
import com.openhr.data.DeductionType;
import com.openhr.data.Employee;

@Entity
@Table(name = "deduction_done", catalog = "payhumrepo", schema = "")
@NamedQueries({
    @NamedQuery(name = "DeductionsDone.findAll", query = "SELECT e FROM  DeductionsDone e"),
    @NamedQuery(name = "DeductionsDone.find", query = "SELECT e FROM  DeductionsDone e where e.employeeId=?")})

public class DeductionsDone implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    
    private Integer id;
    
   
    @ManyToOne(targetEntity=DeductionType.class, cascade=CascadeType.ALL)
    @JoinColumn(name = "type", referencedColumnName="id")
	private DeductionType type;
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
	private Double amount;
    @ManyToOne(targetEntity=Employee.class, cascade=CascadeType.ALL)
    @JoinColumn(name = "employeeId", referencedColumnName="id")
    private Employee employeeId;

	public DeductionsDone() {
		
	}
	
	public DeductionsDone(Employee eid, DeductionType type, Double amt) {
		this.type = type;
		this.amount = amt;
		this.employeeId = eid;
	}



	public DeductionType getType() {
		return type;
	}

	public void setType(DeductionType type) {
=======
import com.openhr.data.Employee;

@Entity
@Table(name = "deductions_done", catalog = "payhumrepo", schema = "")
@NamedQueries({
    @NamedQuery(name = "DeductionsDone.findAll", query = "SELECT e FROM  DeductionsDone e")})
public class DeductionsDone implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "type", nullable = false)
	private Integer type;
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
	private Double amount;
    @ManyToOne(targetEntity=Employee.class, cascade=CascadeType.ALL)
    @JoinColumn(name = "employeeId", referencedColumnName="id")
    private Employee employeeId;

	public DeductionsDone() {
		
	}
	
	public DeductionsDone(Employee eid, Integer type, Double amt) {
		this.type = type;
		this.amount = amt;
		this.employeeId = eid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
>>>>>>> branch 'master' of https://github.com/payhum/codebase.git
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

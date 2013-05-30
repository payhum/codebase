package com.openhr.data;

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
import javax.xml.bind.annotation.XmlRootElement;

import com.openhr.common.PayhumConstants;

@Entity
@Table(name = "emp_bonus", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeBonus.findAll", query = "SELECT tr FROM EmployeeBonus tr"),
    @NamedQuery(name = "EmployeeBonus.findById", query = "SELECT tr FROM EmployeeBonus tr WHERE tr.id = ?"),
    @NamedQuery(name = "EmployeeBonus.findByEmpId", query = "SELECT tr FROM EmployeeBonus tr WHERE tr.employeeId = ?")
})
public class EmployeeBonus implements Serializable{
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
    @Column(name = "givendate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date givendate;
	
	@Basic(optional = false)
    @Column(name = "amount", nullable = false)
    private Double amount;

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

	public Date getGivendate() {
		return givendate;
	}

	public void setGivendate(Date givendate) {
		this.givendate = givendate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}

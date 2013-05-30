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
import com.openhr.factories.TypesDataFactory;

@Entity
@Table(name = "emp_salary", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeSalary.findAll", query = "SELECT tr FROM EmployeeSalary tr"),
    @NamedQuery(name = "EmployeeSalary.findSal", query = "SELECT tr FROM EmployeeSalary tr where tr.fromdate=tr.todate and tr.employeeId=?"),
    @NamedQuery(name = "EmployeeSalary.findById", query = "SELECT tr FROM EmployeeSalary tr WHERE tr.id = ?"),
    @NamedQuery(name = "EmployeeSalary.findByEmpId", query = "SELECT tr FROM EmployeeSalary tr WHERE tr.employeeId = ?")
})
public class EmployeeSalary implements Serializable{
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
    @Column(name = "fromdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromdate;
	
	@Basic(optional = false)
    @Column(name = "todate")
    private Date todate;
	
	@Basic(optional = false)
    @Column(name = "basesalary", nullable = false)
    private Double basesalary;
	
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

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public Double getBasesalary() {
		return basesalary;
	}

	public void setBasesalary(Double basesalary) {
		this.basesalary = basesalary;
	}
}

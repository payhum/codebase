package com.openhr.data;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.openhr.common.PayhumConstants;
 
@Entity
@Table(name="dtest",catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "Dtest.findAll", query = "SELECT e FROM Dtest e")})
public class Dtest {
 
    @Id
    @GeneratedValue
    @Column(name="DEPARTMENT_ID")
    private Long departmentId;
     
    @Column(name="DEPT_NAME")
    private String departmentName;
     
    @OneToMany(mappedBy="department")
    private Set<Etest> employees;

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Set<Etest> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Etest> employees) {
		this.employees = employees;
	}
 
    // Getter and Setter methods
}
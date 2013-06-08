package com.openhr.data;

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

import com.openhr.common.PayhumConstants;

@Entity
@Table(name = "emp_dependents", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "EmpDependents.findAll", query = "SELECT e FROM  EmpDependents e"),
    @NamedQuery(name = "EmpDependents.findEmpWithDepType", query = "SELECT e FROM  EmpDependents e where e.employeeId=? and  e.depType=?"),
    @NamedQuery(name = "EmpDependents.findIndual", query = "SELECT e FROM  EmpDependents e WHERE e.employeeId=?")}
		)
public class EmpDependents  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(targetEntity=Employee.class, cascade=CascadeType.ALL)
    @JoinColumn(name = "employeeId", referencedColumnName="id")
    private Employee employeeId;
    @Basic(optional = false)
    @Column(name = "age", nullable = false, length = 20)
	private int age;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
	private String name;
    @JoinColumn(name = "occupationType", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TypesData occupationType;
    @JoinColumn(name = "depType", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TypesData depType;
    
    public EmpDependents() {
    	
    }

	public EmpDependents(Integer id, Employee employeeId, int age, String name, TypesData occType, TypesData dType) {
		this.id = id;
		this.employeeId = employeeId;
		this.age = age;
		this.name = name;
		this.occupationType = occType;
		this.depType = dType;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypesData getDepType() {
		return depType;
	}

	public void setDepType(TypesData depType) {
		this.depType = depType;
	}

	public TypesData getOccupationType() {
		return this.occupationType;
	}

	public void setOccupationType(TypesData occupationType) {
		this.occupationType = occupationType;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}
}

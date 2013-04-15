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

@Entity
@Table(name = "emp_dependents", catalog = "payhumrepo", schema = "")
@NamedQueries({
    @NamedQuery(name = "EmpDependents.findAll", query = "SELECT e FROM  EmpDependents e")})
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
    @Basic(optional = false)
    @Column(name = "occupationType", nullable = false, length = 20)
	private Integer occupationType;
    @Basic(optional = false)
    @Column(name = "depType", nullable = false, length = 20)
	private Integer depType;
    
    public EmpDependents() {
    	
    }

	public EmpDependents(Integer id, Employee employeeId, int age, String name, Integer occType, Integer dType) {
		this.id = id;
		this.employeeId = employeeId;
		this.age = age;
		this.name = name;
		this.occupationType = occType;
		this.depType = dType;
	}
	
	public OccupationType getOccupationType() {
		if(this.occupationType != null) {
			if(occupationType == OccupationType.STUDENT.getValue()) {
				return OccupationType.STUDENT;
			} else if(occupationType == OccupationType.NONE.getValue()) {
				return OccupationType.NONE;
			}
		} 
		
		return null;
	}

	public void setOccupationType(Integer occupationType) {
		this.occupationType = occupationType;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setType(Integer depType) {
		this.depType = depType;
	}

	public DependentType getType() {
		if(this.depType != null) {
			if(depType == DependentType.CHILD.getValue()) {
				return DependentType.CHILD;
			} else if(depType == DependentType.PARENT.getValue()) {
				return DependentType.PARENT;
			} else if(depType == DependentType.SPOUSE.getValue()) {
				return DependentType.SPOUSE;
			} 
		} 
		
		return null;
	}

	public int getAge() {
		return age;
	}
}

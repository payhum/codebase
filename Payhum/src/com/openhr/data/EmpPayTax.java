package com.openhr.data;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.openhr.common.PayhumConstants;



@Entity
@Table(name = "emppaytax", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "EmpPayTax.findAll", query = "SELECT e FROM EmpPayTax e")

   })

public class EmpPayTax {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "emp_taxid", nullable = false)	
private Integer emptaId;

   @ManyToOne(targetEntity=Employee.class, cascade=CascadeType.ALL)
  @JoinColumn(name = "employeeId", referencedColumnName="id")
    private Employee  employeeId;
    
    @Column(name = "tax_des", length=10)
    private String taxdes;

    
    @Column(name = "tax_amt", length=10)
    private Double taxamt;


	public Integer getEmptaId() {
		return emptaId;
	}


	public void setEmptaId(Integer emptaId) {
		this.emptaId = emptaId;
	}


	public Employee getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}


	public String getTaxdes() {
		return taxdes;
	}


	public void setTaxdes(String taxdes) {
		this.taxdes = taxdes;
	}


	public Double getTaxamt() {
		return taxamt;
	}


	public void setTaxamt(Double taxamt) {
		this.taxamt = taxamt;
	}


	
    
    
}

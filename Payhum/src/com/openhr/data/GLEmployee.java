package com.openhr.data;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "glemployee", catalog = "payhumrepo", schema = "")
@NamedQueries({ @NamedQuery(name = "GLEmployee.findAll", query = "SELECT e FROM GLEmployee  e")})
public class GLEmployee {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "gl_id", nullable = false)
	    private Integer id;
	 
	 @OneToOne(targetEntity=Employee.class,cascade=CascadeType.ALL)
		@JoinColumn(name="employeeId",referencedColumnName="id")
	    private Employee employeeId;
	  
	 
	  @Basic(optional = false)
	    @Column(name = "acc_no", nullable = false, length = 45)
	  private Integer accno;
	  
	  @Basic(optional = false)
	    @Column(name = "accnt_name", nullable = false, length = 45)
	    private String accname;
	  
	  @Basic(optional = false)
	    @Column(name = "debit", nullable = false, length = 45)
	    private Double debit;
	  
	  
	  @Basic(optional = false)
	    @Column(name = "credit", nullable = false, length = 45)
	    private Double credit;
	  
	  
	 
	  
	  @Basic(optional = false)
	    @Column(name = "date", nullable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date date;
	  
	  
	
	  
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getAccno() {
		return accno;
	}

	public void setAccno(Integer accno) {
		this.accno = accno;
	}

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}



	public long getDate() {
		return date.getTime();
	}

	public void setDate1(Date date) {
		this.date = date;
	}

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}
	  
	  
	  
	  
}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.openhr.common.PayhumConstants;

/**
 * 
 * @author xmen
 */
@Entity
@Table(name = "overtime", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement     
@NamedQueries({
		@NamedQuery(name = "OverTime.findAll", query = "SELECT l FROM OverTime l"),
		@NamedQuery(name = "OverTime.findByStatus", query = "SELECT l FROM OverTime l WHERE l.status = ?"),
		@NamedQuery(name = "OverTime.findByEmployeeId", query = "SELECT l FROM OverTime l WHERE l.employeeId = ?"),
		@NamedQuery(name = "OverTime.findById", query = "SELECT l FROM OverTime l WHERE l.id = ?")
		 })
public class OverTime implements Serializable {
	/*
	 * @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestId") private
	 * List<LeaveApproval> leaveApprovalList;
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Basic(optional = false)
	@Column(name = "overtimedate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date overTimeDate;
	
	@Basic(optional = false)
	@Column(name = "approveddate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date ApprovedDate;
	
	@Basic(optional = false)
	@Column(name = "noofhours", nullable = false)
 	private Double noOfHours;
	
	@Basic(optional = false)
	@Column(name = "approvedby", nullable = false)
 	private String approvedBy;
	
	@Basic(optional = false)
	@Column(name = "status", nullable = false)
	private Integer status;
	
	@JoinColumn(name = "employeeId", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private Employee employeeId;

	 

	public OverTime(){
 
	} 

	public OverTime(Integer id) {
		this.id = id;
	}

	public OverTime(Integer id, Date overTimeDate, Double noOfHours, int status) {
		this.id = id;
		this.overTimeDate = overTimeDate;
		this.noOfHours = noOfHours;
		this.status = status;
 	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getNoOfHours() {
		return noOfHours;
	}

	public void setNoOfHours(Double noOfHours) {
		this.noOfHours = noOfHours;
	}
	 

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof OverTime)) {
			return false;
		}
		OverTime other = (OverTime) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.openhr.data.OverTime[ id=" + id + " ]";
	}

	public long getOverTimeDate() {
		return overTimeDate.getTime();
	}

	public void setOverTimeDate(Date overTimeDate) {
		this.overTimeDate = overTimeDate;
	}
	
	public long getApprovedDate() {
		return ApprovedDate.getTime();
	}

	public void setApprovedDate(Date approvedDate) {
		this.ApprovedDate = approvedDate;
	}
	
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}
  
 }

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author xmen
 */
@Entity
@Table(name = "holidays", catalog = "payhumrepo", schema = "")
@XmlRootElement     
@NamedQueries({
		@NamedQuery(name = "Holidays.findAll", query = "SELECT l FROM Holidays l"),
		@NamedQuery(name = "Holidays.findByDate", query = "SELECT l FROM Holidays l WHERE l.date = ?"),
})
public class Holidays implements Serializable {
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
	@Column(name = "date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Basic(optional = false)
	@Column(name = "name", nullable = false)
 	private String name;
	
	 

	public Holidays(){
 
	} 

	public Holidays(Integer id) {
		this.id = id;
	}

	public Holidays(Integer id, Date date, String name) {
		this.id = id;
		this.date = date;
		this.name = name;
  	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		if (!(object instanceof Holidays)) {
			return false;
		}
		Holidays other = (Holidays) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.openhr.data.Holidays[ id=" + id + " ]";
	}

	public long getDate() {
		return date.getTime();
	}

	public void setDate(Date date) {
		this.date = date;
	}
   
 }

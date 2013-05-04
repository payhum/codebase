package com.openhr.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
*
* @author Vijay
*/
@Entity
@Table(name = "payroll_date", catalog = "payhumrepo", schema = "")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "PayrollDate.findAll", query = "SELECT p FROM PayrollDate p")})
public class PayrollDate implements Serializable {
   private static final long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "id", nullable = false)
   private Integer id;
   
   @Basic(optional = false)
   @Column(name = "runDate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date runDate;
	
   public PayrollDate() {
	   
   }
   
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getRunDate() {
		return runDate;
	}
	
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payroll)) {
            return false;
        }
        PayrollDate other = (PayrollDate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.PayrollDate[ id=" + id + " ]";
    }
}
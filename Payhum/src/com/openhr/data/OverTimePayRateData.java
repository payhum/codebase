package com.openhr.data;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.openhr.common.PayhumConstants;
@Entity
@Table(name = "overtime_payrate", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OverTimePayRateData.findAll", query = "SELECT ov FROM OverTimePayRateData ov"),
    @NamedQuery(name = "OverTimePayRateData.findById", query = "SELECT ov FROM OverTimePayRateData ov WHERE ov.id = ?"),
    @NamedQuery(name = "OverTimePayRateData.findByName", query = "SELECT ov FROM OverTimePayRateData ov WHERE ov.dayGroupName = ?")})
public class OverTimePayRateData implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "day_group", nullable = false, length = 45)
    private String dayGroupName;
    @Basic(optional = false)
   
    @Column(name = "grosspay_percent", nullable = false, length = 65535)
    private Double grossPercent;
    
    
    public OverTimePayRateData()
    {}
    
    public OverTimePayRateData(Integer id,String dayGroupName,Double grossPercent)
    {
    	this.id=id;
    	this.dayGroupName=dayGroupName;
    			this.grossPercent=grossPercent;
    	
    }
    
     public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDayGroupName() {
		return this.dayGroupName;
	}

	public void setDayGroupName(String dayGroupName) {
		this.dayGroupName = dayGroupName;
	}

	public Double getGrossPercent() {
		return this.grossPercent;
	}

	public void setGrossPercent(Double grossPercent) {
		this.grossPercent = grossPercent;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

	
	
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OverTimePayRateData)) {
            return false;
        }
        OverTimePayRateData other = (OverTimePayRateData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.OverTimePayRateData[ id=" + id + " ]";
    }

  
}

package com.openhr.data;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "taxrates", catalog = "payhumrepo", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TaxRatesData.findAll", query = "SELECT tr FROM TaxRatesData tr"),
    @NamedQuery(name = "TaxRatesData.findByIncomeTo", query = "SELECT tr FROM TaxRatesData tr WHERE tr.incomeTo = ?"),
    @NamedQuery(name = "TaxRatesData.findByIncomeFrom", query = "SELECT tr FROM TaxRatesData tr WHERE tr.incomeFrom = ?")
})
public class TaxRatesData implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "income_from", nullable = false, length = 45)
    private Double incomeFrom;
    
    @Basic(optional = false)
    @Column(name = "income_to", nullable = false, length = 65535)
    private Double incomeTo;
    
    @Basic(optional = false)
    @Column(name = "income_percnt", nullable = false, length = 65535)
    private Double incomePersent;

    
    
    public TaxRatesData()
    {}
    
    public TaxRatesData(Integer id,Double incomeFrom,Double incomeTo,Double incomePersent)
    {
    	this.id=id;
    	this.incomeFrom=incomeFrom;
    			this.incomeTo=incomeTo;
    			this.incomePersent=incomePersent;
    	
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
        if (!(object instanceof TaxRatesData)) {
            return false;
        }
        TaxRatesData other = (TaxRatesData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.TaxRatesData[ id=" + id + " ]";
    }

    
    
    
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getIncomeFrom() {
		return this.incomeFrom;
	}

	public void setIncomeFrom(Double incomeFrom) {
		this.incomeFrom = incomeFrom;
	}

	public Double getIncomeTo() {
		return this.incomeTo;
	}

	public void setIncomeTo(Double incomeTo) {
		this.incomeTo = incomeTo;
	}

	public Double getIncomePersent() {
		return this.incomePersent;
	}

	public void setIncomePersent(Double incomePersent) {
		this.incomePersent = incomePersent;
	}
    
    
    
  
    
    
    
    
    
}

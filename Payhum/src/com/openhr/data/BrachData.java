package com.openhr.data;

import java.io.Serializable;

import javax.persistence.Basic;
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

import com.openhr.company.Company;
@Entity
@Table(name = "branch", catalog = "payhumrepo", schema = "")
@NamedQueries({
    @NamedQuery(name = "BrachData.findAll", query = "FROM BrachData br"),
    @NamedQuery(name = "BrachData.findById", query = "FROM BrachData br WHERE id = ?"),
    @NamedQuery(name = "BrachData.findByName", query = "SELECT br FROM BrachData br WHERE br.name = ?")})

public class BrachData implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic(optional = false)
    @Column(name = "address", nullable = false)
    private String brachAddrs;
    @JoinColumn(name = "companyId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Company companyId;
    
    

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

	public String getBrachAddrs() {
		return brachAddrs;
	}

	public void setBrachAddrs(String brachAddrs) {
		this.brachAddrs = brachAddrs;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
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
        if (!(object instanceof BrachData)) {
            return false;
        }
        BrachData other = (BrachData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.BrachData[id=" + id + "]";
    }

}

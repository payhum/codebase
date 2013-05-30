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
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
@Entity
@Table(name = "branch", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "Branch.findAll", query = "FROM Branch br where name != 'MMain'"),
    @NamedQuery(name = "Branch.findById", query = "FROM Branch br WHERE id = ?"),
    @NamedQuery(name = "Branch.findByName", query = "SELECT br FROM Branch br WHERE br.name = ?"),
    @NamedQuery(name = "Branch.findByCompanyId", query = "SELECT br FROM Branch br WHERE br.companyId = ?"),})
	@NamedNativeQuery(name = "Branch.findLastId", query = "SELECT * FROM Branch WHERE Branch.id = (SELECT max(Branch.id) FROM Branch)",
	resultClass=Branch.class)

public class Branch implements Serializable {
	
    private static final long serialVersionUID = 1L;
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
    private String address;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String brachAddrs) {
		this.address = brachAddrs;
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
        if (!(object instanceof Branch)) {
            return false;
        }
        Branch other = (Branch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.Branch[id=" + id + "]";
    }

}

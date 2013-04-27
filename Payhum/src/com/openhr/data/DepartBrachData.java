package com.openhr.data;

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
@Table(name = "department", catalog = "payhumrepo", schema = "")
@NamedQueries({
    @NamedQuery(name = "DepartBrachData.findAll", query = "FROM DepartBrachData db"),
    @NamedQuery(name = "DepartBrachData.findByBrachId", query = "FROM DepartBrachData db WHERE brchId = ?"),
    @NamedQuery(name = "DepartBrachData.findById", query = "FROM DepartBrachData db WHERE id = ?"),
    @NamedQuery(name = "DepartBrachData.findByName", query = "SELECT db FROM DepartBrachData db WHERE db.name = ?")})
public class DepartBrachData {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "deptname", nullable = false, length = 45)
    private String name;
  

    @JoinColumn(name = "branchId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private BrachData brchId;
    
    
    
    
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

	public BrachData getBrchId() {
		return brchId;
	}

	public void setBrchId(BrachData brchId) {
		this.brchId = brchId;
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
        if (!(object instanceof DepartBrachData)) {
            return false;
        }
        DepartBrachData other = (DepartBrachData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.DepartBrachData[id=" + id + "]";
    }
}

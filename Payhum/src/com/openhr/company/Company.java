package com.openhr.company;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.openhr.common.PayhumConstants;

@Entity
@Table(name = "company", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "Company.findAll", query = "SELECT e FROM Company e where e.name != 'MasterComp'"),
    @NamedQuery(name = "Company.findById", query = "SELECT e FROM Company e WHERE e.id = ?"),
    @NamedQuery(name = "Company.findByCompanyId", query = "SELECT e FROM Company e WHERE e.companyId = ?"),
    @NamedQuery(name = "Company.findByName", query = "SELECT e FROM Company e WHERE e.name = ?")})
	@NamedNativeQuery(name = "Company.findLastId", query = "SELECT * FROM Company WHERE Company.id = (SELECT max(Company.id) FROM Company)",
	resultClass=Company.class)

public class Company implements Serializable {

	
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "companyId", nullable = false, length = 45)
    private String companyId;
    
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    
    @Basic(optional = false)
    @Column(name = "fystart", nullable = false)
    private Integer fystart;
    
	public Company () {
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFystart() {
		return fystart;
	}

	public void setFystart(Integer fystart) {
		this.fystart = fystart;
	}

	public int getFinStartMonth() {
		return getFystart();
	}
	 
}

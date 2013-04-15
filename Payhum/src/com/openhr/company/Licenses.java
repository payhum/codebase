package com.openhr.company;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "licenses", catalog = "payhumrepo", schema = "")
@NamedQueries({
    @NamedQuery(name = "Licenses.findAll", query = "SELECT e FROM Licenses e"),
    @NamedQuery(name = "Licenses.findById", query = "SELECT e FROM Licenses e WHERE e.id = ?"),
    @NamedQuery(name = "Licenses.findByCompanyId", query = "SELECT e FROM Licenses e WHERE e.companyId = ?"),
    @NamedQuery(name = "Licenses.findByActive", query = "SELECT e FROM Licenses e WHERE e.active = ?")})
public class Licenses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JoinColumn(name = "companyId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Company companyId;
    
    @Basic(optional = false)
    @Column(name = "fromdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromdate;
    
    @Basic(optional = false)
    @Column(name = "todate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date todate;
    
    @Basic(optional = false)
    @Column(name = "active", nullable = false)
    private Integer active;
    
    @Basic(optional = false)
    @Column(name = "licensekey", nullable = false)
    private String licensekey;
    
	public Licenses () {
	}
	
	public String getLicensekey() {
		return licensekey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}
	
	public void formLicenseKey() {
		String compName = companyId.getName();
		String strToBeEncrypted = LicenseValidator.formStringToEncrypt(compName, this.todate);
		licensekey = LicenseValidator.encrypt(strToBeEncrypted);
	}

}

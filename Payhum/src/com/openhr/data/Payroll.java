/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.data;

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
import javax.xml.bind.annotation.XmlRootElement;

import com.openhr.common.PayhumConstants;

/**
 *
 * @author Vijay
 */
@Entity
@Table(name = "payroll", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payroll.findAll", query = "SELECT p FROM Payroll p"),
    @NamedQuery(name = "Payroll.findcountRunOnDate", query = "SELECT count(distinct p.runOnDate) FROM Payroll p"),
    @NamedQuery(name = "Payroll.findById", query = "SELECT p FROM Payroll p WHERE p.id = ?"),
    @NamedQuery(name = "Payroll.findByPayDateAndBranch", query = "SELECT p FROM Payroll p WHERE p.payDateId= ? AND p.branchId = ?"),
    @NamedQuery(name = "Payroll.findByBranch", query = "SELECT p FROM Payroll p WHERE p.branchId = ?"),
    @NamedQuery(name = "Payroll.findByRunOnDate", query = "SELECT p FROM Payroll p WHERE p.runOnDate = ?")})
public class Payroll implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "runOnDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date runOnDate;
    
    @JoinColumn(name = "runBy", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Users runBy;

    @JoinColumn(name = "payDateId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private PayrollDate payDateId;

    @JoinColumn(name = "branchId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Branch branchId;

    public Branch getBranchId() {
		return branchId;
	}

	public void setBranchId(Branch branchId) {
		this.branchId = branchId;
	}

	public Payroll() {
    }

    public Payroll(Integer id) {
        this.id = id;
    }

    public Payroll(Integer id, Date runOnDate) {
        this.id = id;
        this.runOnDate = runOnDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRunOnDate() {
        return runOnDate;
    }

    public void setRunOnDate(Date runOnDate) {
        this.runOnDate = runOnDate;
    }
    
    public Users getRunBy() {
        return runBy;
    }

    public void setRunBy(Users runBy) {
        this.runBy = runBy;
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
        if (!(object instanceof Payroll)) {
            return false;
        }
        Payroll other = (Payroll) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.Payroll[ id=" + id + " ]";
    }

	public PayrollDate getPayDateId() {
		return payDateId;
	}

	public void setPayDateId(PayrollDate payDateId) {
		this.payDateId = payDateId;
	}
    
}

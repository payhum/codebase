/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.data;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.openhr.common.PayhumConstants;

/**
 *
 * @author xmen
 */
@Entity
@Table(name = "deduction", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deduction.findAll", query = "SELECT d FROM Deduction d"),
    @NamedQuery(name = "Deduction.findById", query = "SELECT d FROM Deduction d WHERE d.id = :id"),
    @NamedQuery(name = "Deduction.findByDoneDate", query = "SELECT d FROM Deduction d WHERE d.doneDate = :doneDate")})
public class Deduction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "doneDate", nullable = false, length = 45)
    private String doneDate;
    @JoinColumn(name = "deductionType", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private DeductionsType deductionType;
    @JoinColumn(name = "employeeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Employee employeeId;

    public Deduction() {
    }

    public Deduction(Integer id) {
        this.id = id;
    }

    public Deduction(Integer id, String doneDate) {
        this.id = id;
        this.doneDate = doneDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    public DeductionsType getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(DeductionsType deductionType) {
        this.deductionType = deductionType;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
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
        if (!(object instanceof Deduction)) {
            return false;
        }
        Deduction other = (Deduction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.Deduction[ id=" + id + " ]";
    }
    
}

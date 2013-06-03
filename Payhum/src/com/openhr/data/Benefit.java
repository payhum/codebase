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
 * @author Vijay
 */
@Entity
@Table(name = "benefit", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Benefit.findAll", query = "SELECT b FROM Benefit b"),
    @NamedQuery(name = "Benefit.findById", query = "SELECT b FROM Benefit b WHERE b.id = ?"),
    @NamedQuery(name = "Benefit.findByEmpId", query = "SELECT b FROM Benefit b WHERE b.employeeId = ?"),
    @NamedQuery(name = "Benefit.findByTypeId", query = "SELECT b FROM Benefit b WHERE b.typeId = ?"),
    @NamedQuery(name = "Benefit.findByAmount", query = "SELECT b FROM Benefit b WHERE b.amount = ?")})
public class Benefit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
    private double amount;
    @JoinColumn(name = "typeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private BenefitType typeId;
    @JoinColumn(name = "employeeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Employee employeeId;

    public Benefit() {
    }

    public Benefit(Integer id) {
        this.id = id;
    }

    public Benefit(Integer id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BenefitType getTypeId() {
        return typeId;
    }

    public void setTypeId(BenefitType typeId) {
        this.typeId = typeId;
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
        if (!(object instanceof Benefit)) {
            return false;
        }
        Benefit other = (Benefit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.Benefit[ id=" + id + " ]";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

import com.openhr.common.PayhumConstants;

/**
 *
 * @author Vijay
 */
@Entity
@Table(name = "position", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@NamedQueries({
    @NamedQuery(name = "Position.findAll", query = "FROM Position p"),
    @NamedQuery(name = "Position.findById", query = "FROM Position p WHERE id = ?"),
    @NamedQuery(name = "Position.findByName", query = "SELECT p FROM Position p WHERE p.name = ?"),
    @NamedQuery(name = "Position.findByLowSal", query = "SELECT p FROM Position p WHERE p.lowSal= ?"),
    @NamedQuery(name = "Position.findByHighSal", query = "SELECT p FROM Position p WHERE p.highSal = ?")})
public class Position implements Serializable {
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
    @Column(name = "lowsal", nullable = false)
    private Double lowSal;
    @Basic(optional = false)
    @Column(name = "highsal", nullable = false)
    private Double highSal;

    public Position() {
    }

    public Position(Integer id) {
        this.id = id;
    }

    public Position(Integer id, String name, Double ls, Double hs) {
        this.id = id;
        this.name = name;
        this.lowSal = ls;
        this.highSal = hs;
    }

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

    public Double getLowSal() {
        return lowSal;
    }

    public void setLowSal(Double salary) {
        this.lowSal = salary;
    }

    public Double getHighSal() {
        return highSal;
    }

    public void setHighSal(Double sal) {
        this.highSal = sal;
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
        if (!(object instanceof Position)) {
            return false;
        }
        Position other = (Position) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openhr.data.Position[id=" + id + "]";
    }

}

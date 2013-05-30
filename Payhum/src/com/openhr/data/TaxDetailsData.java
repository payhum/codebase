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
import javax.xml.bind.annotation.XmlRootElement;

import com.openhr.common.PayhumConstants;

@Entity
@Table(name = "taxdetails", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TaxDetailsData.findAll", query = "SELECT tr FROM TaxDetailsData tr"),
    @NamedQuery(name = "TaxDetailsData.findById", query = "SELECT tr FROM TaxDetailsData tr WHERE tr.id = ?"),
    @NamedQuery(name = "TaxDetailsData.findByTypeId", query = "SELECT tr FROM TaxDetailsData tr WHERE tr.typeId = ?")
})
public class TaxDetailsData implements Serializable{
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
	@JoinColumn(name = "typeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TypesData typeId;
    
    @Basic(optional = false)
    @Column(name = "amount", nullable = false)
    private Double amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypesData getTypeId() {
		return typeId;
	}

	public void setTypeId(TypesData typeId) {
		this.typeId = typeId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
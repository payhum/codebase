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
import javax.xml.bind.annotation.XmlRootElement;

import com.openhr.common.PayhumConstants;

@Entity
@Table(name = "types", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypesData.findAll", query = "SELECT tr FROM TypesData tr"),
    @NamedQuery(name = "TypesData.findById", query = "SELECT tr FROM TypesData tr WHERE tr.id = ?"),
    @NamedQuery(name = "TypesData.findByName", query = "SELECT tr FROM TypesData tr WHERE tr.name = ?"),
    @NamedQuery(name = "TypesData.findByType", query = "SELECT tr FROM TypesData tr WHERE tr.type = ?")
})
public class TypesData implements Serializable{
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length=45)
    private String name;

    @Basic(optional = false)
    @Column(name = "desc", nullable = false, length=256)
    private String desc;

	@Basic(optional = false)
    @Column(name = "type", nullable = false, length=45)
    private String type;
	
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
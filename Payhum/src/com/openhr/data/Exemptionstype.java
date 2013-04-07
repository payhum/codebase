package com.openhr.data;



import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
*
* @author xmen
*/
@Entity
@Table(name = "exemptionstype", catalog = "payhumrepo", schema = "")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Exemptionstype.findAll", query = "SELECT d FROM Exemptionstype d"),
   @NamedQuery(name = "Exemptionstype.findById", query = "SELECT d FROM Exemptionstype d WHERE d.id = :id"),
   @NamedQuery(name = "Exemptionstype.findByName", query = "SELECT d FROM Exemptionstype d WHERE d.name = :name")})
public class Exemptionstype implements Serializable{

	
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
    @Lob
    @Column(name = "description", nullable = false, length = 65535)
    private String description;

    public Exemptionstype() {
    }

    public Exemptionstype(Integer id) {
        this.id = id;
    }

    public Exemptionstype(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	        if (!(object instanceof Exemptionstype)) {
	            return false;
	        }
	        Exemptionstype other = (Exemptionstype) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.openhr.data.Exemptionstype[ id=" + id + " ]";
	    }

}

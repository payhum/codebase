package com.openhr.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import com.openhr.company.Company;

/**
 * 
 * @author Vijay
 */
@Entity
@Table(name = "payroll_date", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "PayrollDate.findAll", query = "SELECT p FROM PayrollDate p"),
	 @NamedQuery(name = "PayrollDate.findById", query = "SELECT p FROM PayrollDate p WHERE p.id = ?"),
	@NamedQuery(name = "PayrollDate.findByBranchId", query = "SELECT br FROM PayrollDate br WHERE br.branchId = ?")})
public class PayrollDate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Basic(optional = false)
	@Column(name = "runDate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date runDate;

	@JoinColumn(name = "branchId", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false)
	private Branch branchId;

	public Branch getBranchId() {
		return branchId;
	}

	public void setBranchId(Branch branchId) {
		this.branchId = branchId;
	}

	public PayrollDate() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRunDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String d = sdf.format(runDate);
		return d;
	}

	public Date getRunDateofDateObject() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Payroll)) {
			return false;
		}
		PayrollDate other = (PayrollDate) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.openhr.data.PayrollDate[ id=" + id + " ]";
	}
}

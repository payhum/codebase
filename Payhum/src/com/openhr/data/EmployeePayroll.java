package com.openhr.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.openhr.taxengine.DeductionType;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionType;
import com.openhr.taxengine.ExemptionsDone;

/**
 *
 * @author xmen
 */
@Entity
@Table(name = "emp_payroll_view", catalog = "payhumrepo", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeePayroll.findAll", query = "SELECT e FROM EmployeePayroll e"),
    @NamedQuery(name = "EmployeePayroll.findByFullName", query = "SELECT e FROM EmployeePayroll e WHERE e.fullName = :fullName"),
    @NamedQuery(name = "EmployeePayroll.findByGrossSalary", query = "SELECT e FROM EmployeePayroll e WHERE e.grossSalary = :grossSalary")})
public class EmployeePayroll implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "employeeId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Employee employeeId;
    @Basic(optional = false)
    @Column(name = "FULL_NAME", nullable = false, length = 137)
    private String fullName;
    @Basic(optional = false)
    @Column(name = "GROSS_SALARY", nullable = false)
    private double grossSalary;
    /*@Column(name = "BENEFIT_TYPE", length = 45)
    private String benefitType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BENEFIT_AMNT", precision = 22)
    private Double benefitAmnt;*/

    @Basic(optional = false)
    @Column(name = "taxableIncome", nullable = false)
    private Double taxableIncome;
    
    @Basic(optional = false)
    @Column(name = "taxAmount", nullable = false)
    private Double taxAmount;
    
    @Basic(optional = false)
    @Column(name = "totalIncome", nullable = false)
    private Double totalIncome;
    
    @Basic(optional = false)
    @Column(name = "taxableOverseasIncome", nullable = false)
    private Double taxableOverseasIncome;
    
    @Basic(optional = false)
    @Column(name = "allowances", nullable = false)
    private Double allowances;
    
    @Basic(optional = false)
    @Column(name = "baseSalary", nullable = false)
    private Double baseSalary;
    
    @Basic(optional = false)
    @Column(name = "bonus", nullable = false)
    private Double bonus;
    
    @Basic(optional = false)
    @Column(name = "accomodationAmount", nullable = false)
    private Double accomodationAmount;
    
    @Basic(optional = false)
    @Column(name = "employerSS", nullable = false)
    private Double employerSS;
    
    @Basic(optional = false)
    @Column(name = "accomodationType", nullable = false)
    private Integer accomodationType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private List<DeductionsDeclared> deductionsDeclared;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private List<DeductionsDone> deductionsDone;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId")
    private List<ExemptionsDone> exemptionsDone;
    
    public EmployeePayroll() {
    	this.taxableIncome = 0D;
        this.taxAmount = 0D;
        this.exemptionsDone = new ArrayList<ExemptionsDone>();
        this.totalIncome= 0D;
        this.taxableOverseasIncome = 0D;
        this.allowances = 0D;
        this.deductionsDone = new ArrayList<DeductionsDone>();
        this.baseSalary = 0D;
        this.deductionsDeclared = null;
        this.bonus= 0D ;
        this.accomodationType= null ;
        this.accomodationAmount = 0D;
        this.employerSS = 0D;
        this.deductionsDeclared = new ArrayList<DeductionsDeclared>();
        this.grossSalary = 0D;
    }
    
    public Double getAccomodationAmount() {
		return accomodationAmount;
	}

	public Double getEmployerSS() {
		return employerSS;
	}

    public String getEmployeeid() {
        return employeeId.getEmployeeId();
    }

	public Employee getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Employee employeeId) {
		this.employeeId = employeeId;
	}
	
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }
/*
    public String getBenefitType() {
        return benefitType;
    }

    public void setBenefitType(String benefitType) {
        this.benefitType = benefitType;
    }

    public Double getBenefitAmnt() {
        return benefitAmnt;
    }

    public void setBenefitAmnt(Double benefitAmnt) {
        this.benefitAmnt = benefitAmnt;
    }
*/
	public void save() {
		// TODO Auto-generated method stub
		
	}

	public Double getTaxableIncome() {
		return this.taxableIncome;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public void addExemption(ExemptionType eType, Double exemption) {
		exemptionsDone.add(new ExemptionsDone(this.employeeId, eType.getValue(), exemption));
		
		if(taxableIncome > exemption) {
			taxableIncome -= exemption;
		} else {
			taxableIncome = 0D;
		}
	}

	public void addExemption(ExemptionType eType, Double exemption,
			int multiplier) {
		Double exemptionAmt = exemption * multiplier;
		
		exemptionsDone.add(new ExemptionsDone(this.employeeId, eType.getValue(), exemptionAmt));
		
		if(taxableIncome > exemptionAmt) {
			taxableIncome -= exemptionAmt;
		} else {
			taxableIncome = 0D;
		}
	}

	public Double getBaseSalary() {
		return this.baseSalary;
	}
	
	public void addDeduction(DeductionType entity, Double amount) {
		deductionsDone.add(new DeductionsDone(this.employeeId, entity.getValue(), amount));
		
		if(taxableIncome > amount) {
			taxableIncome -= amount;
		} else {
			taxableIncome = 0D;
		}
	}

	public static EmployeePayroll loadEmpPayroll(Employee emp) {
		if(System.getProperty("DRYRUN") != null 
		&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			return populateTestData();
		}
		
		// TODO - Load from repos
		
		return null;		
	}

	public Double getAllowancesAmount() {
		return this.allowances;
	}

	public Double getBonus() {
		return this.bonus;
	}

	public AccomodationType getAccomodationType() {
		if(this.accomodationType != null) {
			if(accomodationType == AccomodationType.FREE_ACCOM_FROM_EMPLOYER_FULLY_FURNISHED.getValue()) {
				return AccomodationType.FREE_ACCOM_FROM_EMPLOYER_FULLY_FURNISHED;
			} else if(accomodationType == AccomodationType.FREE_ACCOM_FROM_EMPLOYER_NOT_FURNISHED.getValue()) {
				return AccomodationType.FREE_ACCOM_FROM_EMPLOYER_NOT_FURNISHED;
			}
		} 
		
		return null;
	}

	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
		
		if(taxableIncome != null & taxableIncome == 0D) {
			this.taxableIncome = totalIncome;
		}
	}
    
	public Double getTotalIncome() {
		return this.totalIncome;
	}

	public Double getTaxableOverseasIncome() {
		return this.taxableOverseasIncome;
	}
	
	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxableOverseasIncome(Double taxableOverseasIncome) {
		this.taxableOverseasIncome = taxableOverseasIncome;
	}

	public void setAllowances(Double allowances) {
		this.allowances = allowances;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public void setAccomodationType(Integer accomodationType) {
		this.accomodationType = accomodationType;
	}
	
	//Testing method
	private static EmployeePayroll populateTestData() {
		EmployeePayroll ePayroll = new EmployeePayroll();
		ePayroll.setAccomodationType(AccomodationType.FREE_ACCOM_FROM_EMPLOYER_FULLY_FURNISHED.getValue());
		ePayroll.setAllowances((double) 100000);
		ePayroll.setBaseSalary((double) 20000000);
		ePayroll.setBonus((double) 2000000);
		
		Employee emp = new Employee(0, "E1", "John", "", "Lui", "male", new Date(), new Date());
		emp.setResidentType(ResidentType.NON_RESIDENT_FOREIGNER.getValue());
		emp.setMarried("true");
		
		ePayroll.setEmployeeId(emp);
		
		return ePayroll;
	}

	public void setAccomodationAmount(Double accomAmt) {
		this.accomodationAmount = accomAmt;
	}
	
	public void setEmployerSS(Double employerSS) {
		this.employerSS = employerSS;
	}
	
    public Map<ExemptionType, Double> getExemptionsDone() {
    	Map<ExemptionType, Double> exempMap = new HashMap<ExemptionType, Double>();
    	
    	for(ExemptionsDone ed : exemptionsDone) {
    		if(ed.getType() == ExemptionType.BASIC_ALLOWANCE.getValue()) {
    			exempMap.put(ExemptionType.BASIC_ALLOWANCE, ed.getAmount());
    		} else if(ed.getType() == ExemptionType.CHILDREN.getValue()) {
    			exempMap.put(ExemptionType.CHILDREN, ed.getAmount());
    		} else if(ed.getType() == ExemptionType.SUPPORTING_SPOUSE.getValue()) {
    			exempMap.put(ExemptionType.SUPPORTING_SPOUSE, ed.getAmount());
    		}
    	}
    	
		return exempMap;
	}

	public Map<DeductionType, Double> getDeductionsDone() {
		Map<DeductionType, Double> deducMap = new HashMap<DeductionType, Double>();
    	
    	for(DeductionsDone dd : deductionsDone) {
    		if(dd.getType() == DeductionType.DONATION.getValue()) {
    			deducMap.put(DeductionType.DONATION, dd.getAmount());
    		} else if(dd.getType() == DeductionType.EMPLOYEE_SOCIAL_SECURITY.getValue()) {
    			deducMap.put(DeductionType.EMPLOYEE_SOCIAL_SECURITY, dd.getAmount());
    		} else if(dd.getType() == DeductionType.LIFE_INSURANCE.getValue()) {
    			deducMap.put(DeductionType.LIFE_INSURANCE, dd.getAmount());
    		} 
    	}
    	
		return deducMap;
	}
	
	public Map<DeductionType, Double> getDeductionsDeclared() {
		Map<DeductionType, Double> deducMap = new HashMap<DeductionType, Double>();
    	
    	for(DeductionsDeclared dd : deductionsDeclared) {
    		if(dd.getType() == DeductionType.DONATION.getValue()) {
    			deducMap.put(DeductionType.DONATION, dd.getAmount());
    		} else if(dd.getType()  == DeductionType.EMPLOYEE_SOCIAL_SECURITY.getValue()) {
    			deducMap.put(DeductionType.EMPLOYEE_SOCIAL_SECURITY, dd.getAmount());
    		} else if(dd.getType()  == DeductionType.LIFE_INSURANCE.getValue()) {
    			deducMap.put(DeductionType.LIFE_INSURANCE, dd.getAmount());
    		} else if(dd.getType()  == DeductionType.SELF_LIFE_INSURANCE.getValue()) {
    			deducMap.put(DeductionType.SELF_LIFE_INSURANCE, dd.getAmount());
    		} else if(dd.getType()  == DeductionType.SPOUSE_LIFE_INSURANCE.getValue()) {
    			deducMap.put(DeductionType.SPOUSE_LIFE_INSURANCE, dd.getAmount());
    		} 
    	}
    	
		return deducMap;
	}
}
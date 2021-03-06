package com.openhr.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.openhr.common.PayhumConstants;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionsDone;

/**
 *
 * @author xmen
 */
@Entity
@Table(name = "emp_payroll", catalog = PayhumConstants.DATABASE_NAME, schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "EmployeePayroll.findByEmployeeId", query = "SELECT e FROM EmployeePayroll e WHERE e.employeeId = ?"),
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
    @Column(name = "GROSS_SALARY")
    private Double grossSalary;

    @Basic(optional = false)
    @Column(name = "taxableIncome")
    private Double taxableIncome;
    
    @Basic(optional = false)
    @Column(name = "taxAmount")
    private Double taxAmount;
    
    @Basic(optional = false)
    @Column(name = "totalIncome")
    private Double totalIncome;
    
    @Basic(optional = false)
    @Column(name = "taxableOverseasIncome")
    private Double taxableOverseasIncome;
    
    @Basic(optional = false)
    @Column(name = "allowances")
    private Double allowances;
    
    @Basic(optional = false)
    @Column(name = "baseSalary", nullable = false)
    private Double baseSalary;
    
    @Basic(optional = false)
    @Column(name = "bonus")
    private Double bonus;
    
    @Basic(optional = false)
    @Column(name = "accomodationAmount")
    private Double accomodationAmount;
    
    @Basic(optional = false)
    @Column(name = "employerSS")
    private Double employerSS;
    
    @JoinColumn(name = "accomodationType", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TypesData accomodationType;
    
    @OneToMany(mappedBy = "payrollId", fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<DeductionsDeclared> deductionsDeclared;
    
    @OneToMany(mappedBy = "payrollId", fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<DeductionsDone> deductionsDone;
    
    @OneToMany(mappedBy = "payrollId", fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<ExemptionsDone> exemptionsDone;

    @Basic(optional = false)
    @Column(name = "totalDeductions")
    private Double totalDeductions;

    @Basic(optional = false)
    @Column(name = "netPay")
    private Double netPay;

    @Basic(optional = false)
    @Column(name = "overtimeamt")
    private Double overtimeamt;
    
    @Basic(optional = false)
    @Column(name = "paidTaxAmt")
    private Double paidTaxAmt;
    
    @Basic(optional = false)
    @Column(name = "paidNetPay")
    private Double paidNetPay;

    @Basic(optional = false)
    @Column(name = "paidEmpeSS")
    private Double paidEmpeSS;
    
    @Basic(optional = false)
    @Column(name = "paidEmprSS")
    private Double paidEmprSS;
    
    @Basic(optional = false)
    @Column(name = "otherIncome")
    private Double otherIncome;
    
    @Basic(optional = false)
    @Column(name = "leaveLoss")
    private Double leaveLoss;
    
    @Basic(optional = false)
    @Column(name = "paidLeaveLoss")
    private Double paidLeaveLoss;
    
	@Basic(optional = false)
    @Column(name = "taxPaidByEmployer")
    private Integer taxPaidByEmployer;
    
	@Basic(optional = false)
    @Column(name = "payAccomAmt")
    private Integer payAccomAmt;
	
    @Basic(optional = false)
    @Column(name = "withholdTax")
    private Integer withholdTax;
    
    @Basic(optional = false)
    @Column(name = "withholdSS")
    private Integer withholdSS;
    
    @Basic(optional = false)
    @Column(name = "retroBaseSal")
    private Double retroBaseSal;
    
    @Basic(optional = false)
    @Column(name = "newOvertimeAmt")
    private Double newOvertimeAmt;
    
    @Basic(optional = false)
    @Column(name = "newOtherIncome")
    private Double newOtherIncome;
    
    @Basic(optional = false)
    @Column(name = "newBonus")
    private Double newBonus;
    
    @Basic(optional = false)
    @Column(name = "paidBaseSalary")
    private Double paidBaseSalary;
    
    @Basic(optional = false)
    @Column(name = "paidTaxableAmt")
    private Double paidTaxableAmt;
    
    @Basic(optional = false)
    @Column(name = "paidAccomAmt")
    private Double paidAccomAmt;
    
    @Basic(optional = false)
    @Column(name = "paidBasicAllow")
    private Double paidBasicAllow;
    
    @Basic(optional = false)
    @Column(name = "paidAllowance")
    private Double paidAllowance;
    
    public Double getPaidBasicAllow() {
		return paidBasicAllow;
	}

	public void setPaidBasicAllow(Double paidBasicAllow) {
		this.paidBasicAllow = paidBasicAllow;
	}

    public Double getPaidAllowance() {
		return paidAllowance;
	}

	public void setPaidAllowance(Double paidAllow) {
		this.paidAllowance = paidAllow;
	}
	
    public Double getPaidLeaveLoss() {
		return paidLeaveLoss;
	}

	public void setPaidLeaveLoss(Double paidLeaveLoss) {
		this.paidLeaveLoss = paidLeaveLoss;
	}

	public Double getPaidAccomAmt() {
		return paidAccomAmt;
	}

	public void setPaidAccomAmt(Double paidAccomAmt) {
		this.paidAccomAmt = paidAccomAmt;
	}

	public Double getNewBonus() {
		return newBonus;
	}

	public Integer getPayAccomAmt() {
		return payAccomAmt;
	}

	public void setPayAccomAmt(Integer payAccomAmt) {
		this.payAccomAmt = payAccomAmt;
	}

	public void setNewBonus(Double newBonus) {
		this.newBonus = newBonus;
	}

	public void clearNewBonus() {
    	this.newBonus = 0D;
    }
    
    public void clearNewOvertimeAmt() {
    	this.newOvertimeAmt = 0D;
    }
    
    public void clearNewOtherIncome() {
    	this.newOtherIncome = 0D;
    }
    
	public Double getNewOvertimeAmt() {
		return newOvertimeAmt;
	}

	public void setNewOvertimeAmt(Double newOvertimeAmt) {
		this.newOvertimeAmt = newOvertimeAmt;
	}

	public Double getNewOtherIncome() {
		return newOtherIncome;
	}

	public void setNewOtherIncome(Double newOtherIncome) {
		this.newOtherIncome = newOtherIncome;
	}

	public Double getRetroBaseSal() {
		return retroBaseSal;
	}

	public void setRetroBaseSal(Double retroBaseSal) {
		this.retroBaseSal = retroBaseSal;
	}

	public EmployeePayroll() {
    	this.taxableIncome = 0D;
        this.taxAmount = 0D;
        this.exemptionsDone = new ArrayList<ExemptionsDone>();
        this.totalIncome= 0D;
        this.taxableOverseasIncome = 0D;
        this.allowances = 0D;
        this.deductionsDone = new ArrayList<DeductionsDone>();
        this.baseSalary = 0D;
        this.bonus= 0D ;
        this.accomodationType= null ;
        this.accomodationAmount = 0D;
        this.employerSS = 0D;
        this.deductionsDeclared = new ArrayList<DeductionsDeclared>();
        this.grossSalary = 0D;
        this.netPay = 0D;
        this.totalDeductions = 0D;
        this.overtimeamt=0D;
        this.netPay = 0D;
        this.leaveLoss = 0D;
        this.paidNetPay = 0D;
        this.paidEmpeSS = 0D;
        this.paidEmprSS = 0D;
        this.paidTaxAmt = 0D;
        this.otherIncome = 0D;
        this.leaveLoss = 0D;
        this.taxPaidByEmployer = 0;
        this.withholdSS=0;
        this.withholdTax=0;
        this.newBonus = 0D;
        this.newOvertimeAmt= 0D;
        this.newOtherIncome= 0D;
        this.retroBaseSal= 0D;
        this.paidBaseSalary = 0D;
        this.paidTaxableAmt = 0D;
        this.paidAccomAmt = 0D;
        this.paidBasicAllow = 0D;
        this.paidLeaveLoss = 0D;
        this.paidAllowance = 0D;
        this.payAccomAmt = 0;
    }
    
	public Double getPaidTaxableAmt() {
		return paidTaxableAmt;
	}

	public void setPaidTaxableAmt(Double paidTaxableAmt) {
		this.paidTaxableAmt = paidTaxableAmt;
	}

	public void addPaidBaseSalary(Double sal) {
		this.paidBaseSalary += sal;
	}
	
    public Double getPaidBaseSalary() {
		return paidBaseSalary;
	}

	public void setPaidBaseSalary(Double paidBaseSalary) {
		this.paidBaseSalary = paidBaseSalary;
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

    public Double getGrossSalary() {
        return grossSalary == null ? 0D : grossSalary;
    }

    public void setGrossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
    }

	public Double getTaxableIncome() {
		return this.taxableIncome;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public void addExemption(Exemptionstype eType, Double exemption) {
		boolean found = false;
		for(ExemptionsDone ed: exemptionsDone) {
		    if(ed.getType().getId().compareTo(eType.getId()) == 0D) {
				ed.setAmount(exemption);
				found = true;
				
				EmpPayTaxFactroy.updateExemptionsDone(ed);
				break;
			}
		}
		
		if(!found) {
			ExemptionsDone ed = new ExemptionsDone(this, eType, exemption); 
			exemptionsDone.add(ed);
			EmpPayTaxFactroy.insertExemptionsDone(ed);
		}
		
		totalDeductions += exemption;
		
		if(taxableIncome > exemption) {
			taxableIncome -= exemption;
		} else {
			taxableIncome = 0D;
		}
	}

	public void addExemption(Exemptionstype eType, Double exemption,
			int multiplier) {
		Double exemptionAmt = exemption * multiplier;
		
		this.addExemption(eType, exemptionAmt);
	}

	public Double getBaseSalary() {
		return this.baseSalary;
	}
	
	public void addDeduction(DeductionsType entity, Double amount) {
		boolean found = false;
		for(DeductionsDone dd: deductionsDone) {
			if(dd.getType().getId().compareTo(entity.getId()) == 0) {
				dd.setAmount(amount);
				found = true;
				
				EmpPayTaxFactroy.updateDeductionsDone(dd);
				break;
			}
		}
		
		if(!found) {
			DeductionsDone dd = new DeductionsDone(this, entity, amount); 
			deductionsDone.add(dd);
			EmpPayTaxFactroy.insertDeductionsDone(dd);
		}
		
		totalDeductions += amount;
		
		if(taxableIncome > amount) {
			taxableIncome -= amount;
		} else {
			taxableIncome = 0D;
		}
	}

	public static EmployeePayroll loadEmpPayroll(Employee empId) {
		if(System.getProperty("DRYRUN") != null 
		&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			return populateTestData();
		}
		return null;		
	}

	public Double getAllowancesAmount() {
		return this.getAllowances();
	}

	public Double getBonus() {
		return this.bonus;
	}

	public TypesData getAccomodationType() {
		return accomodationType;
	}

	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
		this.taxableIncome = totalIncome;
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

	public void setAccomodationType(TypesData accomodationType) {
		this.accomodationType = accomodationType;
	}
	
	//Testing method
	private static EmployeePayroll populateTestData() {
		TypesData accomType = new TypesData();
		accomType.setDesc(PayhumConstants.ACCOM_FULLY_FURNISHED);
		accomType.setName(PayhumConstants.ACCOM_FULLY_FURNISHED);
		accomType.setType(PayhumConstants.TYPE_ACCOMODATIONTYPE);
		
		EmployeePayroll ePayroll = new EmployeePayroll();
		ePayroll.setAccomodationType(accomType);
		//ePayroll.setAllowances((double) 50000);
		//ePayroll.setBaseSalary((double) 30000000);
		//ePayroll.setBonus((double) 4000000);
		
		TypesData residentType = new TypesData();
		residentType.setDesc(PayhumConstants.LOCAL);
		residentType.setName(PayhumConstants.LOCAL);
		residentType.setType(PayhumConstants.TYPE_RESIDENTTYPE);
		
		Employee emp = new Employee(2, "E1", "John", "", "Lui", "male", new Date(), new Date());
		emp.setResidentType(residentType);
		emp.setMarried("true");
		
		ePayroll.setEmployeeId(emp);
		
		DeductionsType dType = new DeductionsType(4, "Employee Social Security", "");
		DeductionsDeclared dd = new DeductionsDeclared(ePayroll, dType, 0D);
		ePayroll.deductionsDeclared.add(dd);
		
		return ePayroll;
	}

	public Double getTotalDeductions() {
		return totalDeductions;
	}

	public void setTotalDeductions(Double totalDeductions) {
		this.totalDeductions = totalDeductions;
	}

	public Double getNetPay() {
		return netPay;
	}

	public void setNetPay(Double netPay) {
		this.netPay = netPay;
	}

	public Double getAllowances() {
		return allowances;
	}

	public void setTaxableIncome(Double taxableIncome) {
		this.taxableIncome = taxableIncome;
	}

	public void setAccomodationAmount(Double accomAmt) {
		this.accomodationAmount = accomAmt;
	}
	
	public void setEmployerSS(Double employerSS) {
		this.employerSS = employerSS;
	}
	
    public List<ExemptionsDone> getExemptionsDone() {
    	return exemptionsDone;
	}

	public List<DeductionsDone> getDeductionsDone() {
		return deductionsDone;
	}
	
	public List<DeductionsDeclared> getDeductionsDeclared() {
		return deductionsDeclared;
	}
	
	public Integer getId() {
		return id;
	}

	public void setDeductionsDone(List<DeductionsDone> deductionsDone) {
		this.deductionsDone = deductionsDone;
	}

	public void setExemptionsDone(List<ExemptionsDone> exemptionsDone) {
		this.exemptionsDone = exemptionsDone;
	}
	
    public void setId(Integer id) {
		this.id = id;
	}

    public Double getOvertimeamt() {
		return overtimeamt;
	}

	public void addOvertimeamt(Double overtimeamt) {
		this.overtimeamt += overtimeamt;
	}

	public Double getPaidTaxAmt() {
		return paidTaxAmt;
	}

	public void setPaidTaxAmt(Double paidTaxAmt) {
		this.paidTaxAmt = paidTaxAmt;
	}

	public Double getPaidNetPay() {
		return paidNetPay;
	}

	public void setPaidNetPay(Double paidNetPay) {
		this.paidNetPay = paidNetPay;
	}

	public Double getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(Double otherIncome) {
		this.otherIncome = otherIncome;
	}

	public Double getLeaveLoss() {
		return leaveLoss;
	}

	public void setLeaveLoss(Double leaveLoss) {
		this.leaveLoss = leaveLoss;
	}

	public Double getPaidEmpeSS() {
		return paidEmpeSS;
	}

	public void setPaidEmpeSS(Double paidSS) {
		this.paidEmpeSS = paidSS;
	}

	public Double getPaidEmprSS() {
		return paidEmprSS;
	}

	public void setPaidEmprSS(Double paidSS) {
		this.paidEmprSS = paidSS;
	}

	public Integer getTaxPaidByEmployer() {
		return taxPaidByEmployer;
	}

	public void setTaxPaidByEmployer(Integer taxPaidByEmployer) {
		this.taxPaidByEmployer = taxPaidByEmployer;
	}

	public Integer getWithholdTax() {
		return withholdTax;
	}

	public void setWithholdTax(Integer withholdTax) {
		this.withholdTax = withholdTax;
	}

	public Integer getWithholdSS() {
		return withholdSS;
	}

	public void setWithholdSS(Integer withholdSS) {
		this.withholdSS = withholdSS;
	}	
}

package com.openhr.taxengine;

import java.util.HashMap;
import java.util.Map;

import com.openhr.data.AccomodationType;

public class TaxDetails {
	
	private Map<ExemptionType, Double> exemptionList;
	private Double limitForNoTax;
	private Map<DeductionType, Double> deductionList;
	private Map<AccomodationType, Float> accomationPercentage;
	private Float employerSSPercentage;
	private Boolean employerSSDone;
	
	private TaxDetails() {
		exemptionList = new HashMap<ExemptionType, Double>();
		deductionList = new HashMap<DeductionType, Double>();
		accomationPercentage = new HashMap<AccomodationType, Float>();
		employerSSDone = true;
	}

	public Boolean getEmployerSSDone() {
		return employerSSDone;
	}

	public void setEmployerSSDone(Boolean employerSSDone) {
		this.employerSSDone = employerSSDone;
	}

	public static TaxDetails getTaxDetailsForCountry() {
		if(System.getProperty("DRYRUN") != null 
		&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			return populateTestData();
		}
		
		// TODO Load from repos.
		return populateTestData();
	}

	public Double getExemption(ExemptionType type) {
		if(exemptionList.containsKey(type)) {
			return exemptionList.get(type);
		}
		
		return 0D;
	}

	public Double getDeduction(DeductionType type) {
		if(deductionList.containsKey(type)) {
			return deductionList.get(type);
		}
		
		return 0D;
	}

	public Double getLimitForNoTax() {
		return limitForNoTax;
	}

	public Float getAccomodationPercentage(AccomodationType accomodationType) {
		if(accomationPercentage.containsKey(accomodationType)) {
			return accomationPercentage.get(accomodationType);
		}
		
		return 0F;
	}

	public Float getEmployerSocialSecurityPercentage() {
		return employerSSPercentage;
	}


	public void setLimitForNoTax(Double limitForNoTax) {
		this.limitForNoTax = limitForNoTax;
	}

	public void setEmployerSocialSecurityPercentage(Float employerSSPercentage) {
		this.employerSSPercentage = employerSSPercentage;
	}
	
	public void addExemption(ExemptionType type, Double amount) {
		exemptionList.put(type,  amount);
	}
	
	public void addDeduction(DeductionType type, Double amount) {
		deductionList.put(type,  amount);
	}
	
	public void addAccomodationPercentage(AccomodationType type, Float amount) {
		accomationPercentage.put(type,  amount);
	}

	private static TaxDetails populateTestData() {
		TaxDetails td = new TaxDetails();
		
		td.addDeduction(DeductionType.DONATION, 25D);
		td.addDeduction(DeductionType.EMPLOYEE_SOCIAL_SECURITY, 1.5D);
		
		td.addExemption(ExemptionType.BASIC_ALLOWANCE_LIMIT, 10000000D);
		td.addExemption(ExemptionType.BASIC_ALLOWANCE_PERCENTAGE, 20D);
		
		td.setLimitForNoTax(1440000D);
		
		td.addExemption(ExemptionType.CHILDREN, 200000D);
		td.addExemption(ExemptionType.SUPPORTING_SPOUSE, 300000D);
		
		td.addAccomodationPercentage(AccomodationType.FREE_ACCOM_FROM_EMPLOYER_FULLY_FURNISHED, 12.5F);
		td.addAccomodationPercentage(AccomodationType.FREE_ACCOM_FROM_EMPLOYER_NOT_FURNISHED, 10F);
		
		td.setEmployerSocialSecurityPercentage(2.5F);
		
		return td;
	}
}

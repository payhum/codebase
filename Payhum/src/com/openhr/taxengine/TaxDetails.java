package com.openhr.taxengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openhr.common.PayhumConstants;
import com.openhr.data.TaxDetailsData;
import com.openhr.factories.TaxFactory;

public class TaxDetails {
		
	private Map<String, Double> exemptionList;
	private Double limitForNoTax;
	private Map<String, Double> deductionList;
	private Map<String, Double> accomationPercentage;
	private Double employerSSPercentage;
	private Boolean employerSSDone;
	
	private static TaxDetails taxDetailsObj;
	
	private TaxDetails() {
		exemptionList = new HashMap<String, Double>();
		deductionList = new HashMap<String, Double>();
		accomationPercentage = new HashMap<String, Double>();
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
		
		// Check if its already loaded and available.
		if(taxDetailsObj != null) {
			return taxDetailsObj;
		}
		
		// Else load from repos.
		List<TaxDetailsData> taxDetailsData = TaxFactory.findAllTaxDetails();
		
		TaxDetails td = new TaxDetails();
		for(TaxDetailsData tdd : taxDetailsData) {
			if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.DONATION)) {
				td.addDeduction(PayhumConstants.DONATION, tdd.getAmount());
			}else if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)) {
				td.addDeduction(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY, tdd.getAmount());
			}else if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.BASIC_ALLOWANCE_LIMIT)) {
				td.addExemption(PayhumConstants.BASIC_ALLOWANCE_LIMIT, tdd.getAmount());
			}else if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.BASIC_ALLOWANCE_PERCENTAGE)) {
				td.addExemption(PayhumConstants.BASIC_ALLOWANCE_PERCENTAGE, tdd.getAmount());
			}else if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.INCOME_LIMIT_FOR_NO_TAX)) {
				td.setLimitForNoTax(tdd.getAmount());
			}else if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.CHILDREN)) {
				td.addExemption(PayhumConstants.CHILDREN, tdd.getAmount());
			}else if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.SUPPORTING_SPOUSE)) {
				td.addExemption(PayhumConstants.SUPPORTING_SPOUSE, tdd.getAmount());
			}else if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.ACCOM_FULLY_FURNISHED)) {
				td.addAccomodationPercentage(PayhumConstants.ACCOM_FULLY_FURNISHED, tdd.getAmount());
			}else if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.ACCOM_NOT_FURNISHED)) {
				td.addAccomodationPercentage(PayhumConstants.ACCOM_NOT_FURNISHED, tdd.getAmount());
			}else if(tdd.getTypeId().getName().equalsIgnoreCase(PayhumConstants.EMPLOYER_SOCIAL_SECURITY)) {
				td.setEmployerSocialSecurityPercentage(tdd.getAmount());
			}
		}
		
		taxDetailsObj = td;
		return taxDetailsObj;
	}

	public Double getExemption(String typeStr) {
		if(exemptionList.containsKey(typeStr)) {
			return exemptionList.get(typeStr);
		}
		
		return 0D;
	}

	public Double getDeduction(String type) {
		if(deductionList.containsKey(type)) {
			return deductionList.get(type);
		}
		
		return 0D;
	}

	public Double getLimitForNoTax() {
		return limitForNoTax;
	}

	public Double getAccomodationPercentage(String accomodationType) {
		if(accomationPercentage.containsKey(accomodationType)) {
			return accomationPercentage.get(accomodationType);
		}
		
		return 0D;
	}

	public Double getEmployerSocialSecurityPercentage() {
		return employerSSPercentage;
	}


	public void setLimitForNoTax(Double limitForNoTax) {
		this.limitForNoTax = limitForNoTax;
	}

	public void setEmployerSocialSecurityPercentage(Double employerSSPercentage) {
		this.employerSSPercentage = employerSSPercentage;
	}
	
	public void addExemption(String type, Double amount) {
		exemptionList.put(type,  amount);
	}
	
	public void addDeduction(String type, Double amount) {
		deductionList.put(type,  amount);
	}
	
	public void addAccomodationPercentage(String type, Double amount) {
		accomationPercentage.put(type,  amount);
	}

	private static TaxDetails populateTestData() {
		TaxDetails td = new TaxDetails();
		
		td.addDeduction(PayhumConstants.DONATION, 25D);
		td.addDeduction(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY, 1.5D);
		
		td.addExemption(PayhumConstants.BASIC_ALLOWANCE_LIMIT, 10000000D);
		td.addExemption(PayhumConstants.BASIC_ALLOWANCE_PERCENTAGE, 20D);
		
		td.setLimitForNoTax(1440000D);
		
		td.addExemption(PayhumConstants.CHILDREN, 200000D);
		td.addExemption(PayhumConstants.SUPPORTING_SPOUSE, 300000D);
		
		td.addAccomodationPercentage(PayhumConstants.ACCOM_FULLY_FURNISHED, 12.5D);
		td.addAccomodationPercentage(PayhumConstants.ACCOM_NOT_FURNISHED, 10D);
		
		td.setEmployerSocialSecurityPercentage(2.5D);
		
		return td;
	}
}

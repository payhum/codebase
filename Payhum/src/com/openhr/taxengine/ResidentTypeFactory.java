package com.openhr.taxengine;

import com.openhr.data.Employee;
import com.openhr.data.ResidentType;
import com.openhr.taxengine.IncomeCalculator;
import com.openhr.taxengine.impl.LocalDC;
import com.openhr.taxengine.impl.LocalEC;
import com.openhr.taxengine.impl.LocalIC;
import com.openhr.taxengine.impl.LocalTC;
import com.openhr.taxengine.impl.NonResidentForeignerDC;
import com.openhr.taxengine.impl.NonResidentForeignerEC;
import com.openhr.taxengine.impl.NonResidentForeignerIC;
import com.openhr.taxengine.impl.NonResidentForeignerTC;
import com.openhr.taxengine.impl.ResidentForeignerDC;
import com.openhr.taxengine.impl.ResidentForeignerEC;
import com.openhr.taxengine.impl.ResidentForeignerIC;
import com.openhr.taxengine.impl.ResidentForeignerTC;

public class ResidentTypeFactory {

	public static IncomeCalculator getIncomeCalculator(Employee emp) {
		if(emp.getResidentType() == ResidentType.LOCAL) {
			LocalIC localIncomeCalc = new LocalIC();
			return localIncomeCalc;
		} else if(emp.getResidentType() == ResidentType.RESIDENT_FOREIGNER) {
			ResidentForeignerIC rfIncomeCalc = new ResidentForeignerIC ();
			return rfIncomeCalc;
		} else if(emp.getResidentType() == ResidentType.NON_RESIDENT_FOREIGNER) {
			NonResidentForeignerIC nrfIncomeCalc = new NonResidentForeignerIC();
			return nrfIncomeCalc;
		}
		return null;
	}

	public static ExemptionCalculator getExemptionCalculator(Employee emp) {

		if(emp.getResidentType() == ResidentType.LOCAL) {
			LocalEC localExmpCalc = new LocalEC();
			return localExmpCalc;
		} else if(emp.getResidentType() == ResidentType.RESIDENT_FOREIGNER) {
			ResidentForeignerEC rfExmpCalc = new ResidentForeignerEC();
			return rfExmpCalc;
		} else if(emp.getResidentType() == ResidentType.NON_RESIDENT_FOREIGNER) {
			NonResidentForeignerEC nrfExmpCalc = new NonResidentForeignerEC();
			return nrfExmpCalc;
		}
		return null;
	}

	public static DeductionCalculator getDeductionCalculator(Employee emp) {

		if(emp.getResidentType() == ResidentType.LOCAL) {
			LocalDC localDeducCalc = new LocalDC();
			return localDeducCalc;
		} else if(emp.getResidentType() == ResidentType.RESIDENT_FOREIGNER) {
			ResidentForeignerDC rfDeducCalc = new ResidentForeignerDC();
			return rfDeducCalc;
		} else if(emp.getResidentType() == ResidentType.NON_RESIDENT_FOREIGNER) {
			NonResidentForeignerDC nrfDeducCalc = new NonResidentForeignerDC();
			return nrfDeducCalc;
		}
		return null;
	}

	public static TaxCalculator getTaxCalculator(Employee emp) {

		if(emp.getResidentType() == ResidentType.LOCAL) {
			LocalTC localTaxCalc = new LocalTC();
			return localTaxCalc;
		} else if(emp.getResidentType() == ResidentType.RESIDENT_FOREIGNER) {
			ResidentForeignerTC rfTaxCalc = new ResidentForeignerTC ();
			return rfTaxCalc;
		} else if(emp.getResidentType() == ResidentType.NON_RESIDENT_FOREIGNER) {
			NonResidentForeignerTC nrfTaxCalc = new NonResidentForeignerTC();
			return nrfTaxCalc;
		}
		return null;
	}
}

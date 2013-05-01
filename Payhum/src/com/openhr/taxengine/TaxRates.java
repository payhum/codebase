package com.openhr.taxengine;

import java.util.ArrayList;
import java.util.List;

import com.openhr.common.PayhumConstants;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.TaxRatesData;
import com.openhr.factories.TaxFactory;

public class TaxRates {

	private List<Slabs> regularSlabs;
	private List<Slabs> nonResidentForeignerSlabs;

	private static TaxRates taxRatesObj;
	
	private TaxRates() {
		this.regularSlabs = new ArrayList<Slabs>();
		this.nonResidentForeignerSlabs = new ArrayList<Slabs>();
	}
	
	public static TaxRates getTaxRatesForCountry() {
		if(System.getProperty("DRYRUN") != null 
		&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			return populateTestData();
		}
		
		// Check if its already loaded and available.
		if(taxRatesObj != null) {
			return taxRatesObj;
		}
		
		// Else load from repos.
		List<TaxRatesData> taxRatesData = TaxFactory.findAll();
		
		TaxRates tr = new TaxRates();
		for(TaxRatesData trd : taxRatesData) {
			if(trd.getResidentTypeId().getName().equalsIgnoreCase(PayhumConstants.LOCAL)) {
				if(trd.getIncomeFrom().compareTo(trd.getIncomeTo()) == 0) {
					Slabs slab = tr.new Slabs(trd.getIncomeFrom(), trd.getIncomeTo(), trd.getIncomePercentage(), true);
					tr.addSlab(slab);	
				} else {
					Slabs slab = tr.new Slabs(trd.getIncomeFrom(), trd.getIncomeTo(), trd.getIncomePercentage(), false);
					tr.addSlab(slab);
				}
			} else if(trd.getResidentTypeId().getName().equalsIgnoreCase(PayhumConstants.NON_RESIDENT_FOREIGNER)) {
				Slabs slab = tr.new Slabs(trd.getIncomeFrom(), trd.getIncomeTo(), trd.getIncomePercentage(), true);
				tr.addNonResidentForeignerSlabs(slab);
			} 	
		}
		
		taxRatesObj = tr;
		return taxRatesObj;
	}

	public boolean shouldTax(EmployeePayroll empPayroll) {
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		
		if(empPayroll.getTaxableIncome() <= taxDetails.getLimitForNoTax()) {
			return false;
		}
		
		return true;
	}

	public List<Slabs> getSlabs() {
		return regularSlabs;
	}

	public List<Slabs> getNonResidentForeignerSlabs() {
		return nonResidentForeignerSlabs;
	}
	
	public void addSlab(Slabs slab) {
		regularSlabs.add(slab);
	}
	
	public void addNonResidentForeignerSlabs(Slabs slab) {
		nonResidentForeignerSlabs.add(slab);
	}
	
	private static TaxRates populateTestData() {
		TaxRates tr = new TaxRates();
		
		TaxRates.Slabs slab1 = tr.new Slabs(1D, 500000D, 1D, false);
		Slabs slab2 = tr.new Slabs(500001D, 1000000D, 2D, false);
		Slabs slab3 = tr.new Slabs(1000001D, 1500000D, 3D, false);
		Slabs slab4 = tr.new Slabs(1500001D, 2000000D, 4D, false);
		Slabs slab5 = tr.new Slabs(2000001D, 3000000D, 5D, false);
		Slabs slab6 = tr.new Slabs(3000001D, 4000000D, 6D, false);
		Slabs slab7 = tr.new Slabs(4000001D, 6000000D, 7D, false);
		Slabs slab8 = tr.new Slabs(6000001D, 8000000D, 9D, false);
		Slabs slab9 = tr.new Slabs(8000001D, 10000000D, 11D, false);
		Slabs slab10 = tr.new Slabs(10000001D, 15000000D, 13D, false);
		Slabs slab11 = tr.new Slabs(15000001D, 20000000D, 15D, false);
		Slabs slab12 = tr.new Slabs(20000001D, 20000001D, 20D, true);
		
		tr.addSlab(slab1);
		tr.addSlab(slab2);
		tr.addSlab(slab3);
		tr.addSlab(slab4);
		tr.addSlab(slab5);
		tr.addSlab(slab6);
		tr.addSlab(slab7);
		tr.addSlab(slab8);
		tr.addSlab(slab9);
		tr.addSlab(slab10);
		tr.addSlab(slab11);
		tr.addSlab(slab12);

		TaxRates.Slabs nfSlab = tr.new Slabs(1D, 1D, 35D, true);
		tr.addNonResidentForeignerSlabs(nfSlab);
		
		return tr;
	}

	public class Slabs {
		
		private Double toValue;
		private Double fromValue;
		private Double ratePercentage;
		private boolean lastSlab;

		public Slabs(Double from, Double to, Double rate, boolean last) {
			this.toValue = to;
			this.fromValue = from;
			this.ratePercentage = rate;
			this.lastSlab = last;
		}
		
		public Double getTo() {
			return toValue;
		}
		
		public Double getFrom() {
			return fromValue;
		}
		
		public Double getRatePercentage() {
			return ratePercentage;
		}

		public boolean isLast() {
			return lastSlab;
		}
	}
}

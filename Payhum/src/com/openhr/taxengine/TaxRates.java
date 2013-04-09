package com.openhr.taxengine;

import java.util.ArrayList;
import java.util.List;

import com.openhr.data.EmployeePayroll;

public class TaxRates {

	private List<Slabs> regularSlabs;
	private List<Slabs> nonResidentForeignerSlabs;

	public TaxRates() {
		this.regularSlabs = new ArrayList<Slabs>();
		this.nonResidentForeignerSlabs = new ArrayList<Slabs>();
	}
	
	public static TaxRates getTaxRatesForCountry() {
		if(System.getProperty("DRYRUN") != null 
		&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			return populateTestData();
		}
		
		// TODO Load from repos.
		return populateTestData();
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
		
		TaxRates.Slabs slab1 = tr.new Slabs(1D, 500000D, 1F, false);
		Slabs slab2 = tr.new Slabs(500001D, 1000000D, 2F, false);
		Slabs slab3 = tr.new Slabs(1000001D, 1500000D, 3F, false);
		Slabs slab4 = tr.new Slabs(1500001D, 2000000D, 4F, false);
		Slabs slab5 = tr.new Slabs(2000001D, 3000000D, 5F, false);
		Slabs slab6 = tr.new Slabs(3000001D, 4000000D, 6F, false);
		Slabs slab7 = tr.new Slabs(4000001D, 6000000D, 7F, false);
		Slabs slab8 = tr.new Slabs(6000001D, 8000000D, 9F, false);
		Slabs slab9 = tr.new Slabs(8000001D, 10000000D, 11F, false);
		Slabs slab10 = tr.new Slabs(10000001D, 15000000D, 13F, false);
		Slabs slab11 = tr.new Slabs(15000001D, 20000000D, 15F, false);
		Slabs slab12 = tr.new Slabs(20000001D, 20000001D, 20F, true);
		
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

		TaxRates.Slabs nfSlab = tr.new Slabs(1D, 1D, 35F, true);
		tr.addNonResidentForeignerSlabs(nfSlab);
		
		return tr;
	}

	public class Slabs {
		
		private Double toValue;
		private Double fromValue;
		private Float ratePercentage;
		private boolean lastSlab;

		public Slabs(Double from, Double to, Float rate, boolean last) {
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
		
		public Float getRatePercentage() {
			return ratePercentage;
		}

		public boolean isLast() {
			return lastSlab;
		}
	}
}

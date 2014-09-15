package com.openhr.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GLReportData {
	
	private String companyName;
	private String branchName;
	private Date runOnDate;
	private List<GLReportEmpData> glredList = new ArrayList<GLReportEmpData>();

	public void setCompanyName(String name) {
		this.companyName = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getBranchName() {
		return branchName;
	}

	public Date getRunOnDate() {
		return runOnDate;
	}

	public List<GLReportEmpData> getGlredList() {
		return glredList;
	}

	public void setBranchName(String name) {
		this.branchName = name;
	}

	public void setRunOnDate(Date runOnDate) {
		this.runOnDate = runOnDate;
	}

	public void addEmpData(GLReportEmpData glred) {
		this.glredList.add(glred);
	}

}

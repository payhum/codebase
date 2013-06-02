/**
 * 
 */
/**
 * @author Admin
 *
 */
package com.openhr.tax.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.openhr.data.EmployeePayroll;
import com.openhr.data.Position;

public class TaxAnnualForm  extends ActionForm {
	
	
    private Integer id;
    private String employeeId;
    private String firstname;
	private String middlename;
    private String lastname;
    private String sex;
    private Position positionId;
    private double freeacom;
    private double disburse;
    
    private double sumsalary;
    
    private Integer sumGPF;
    
    private String lifeinsurance;
    private double svaingsGovt;
    private double sumGovt;
    
    private double wifetax;
    private String childeren;
    private double incometaxdec;
    private boolean logic;
    
    private EmployeePayroll epay;
    
    private String spouse;
    
    
 private List lit;
 
 
 
 
    public List getLit() {
	return lit;
}
    
    
    
    
public String getSpouse() {
		return spouse;
	}




	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}




public void setLit(List lit) {
	this.lit = lit;
}
	public boolean isLogic() {
		return logic;
	}
	public void setLogic(boolean logic) {
		this.logic = logic;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Position getPositionId() {
		return positionId;
	}
	public void setPositionId(Position positionId) {
		this.positionId = positionId;
	}
	public double getSumsalary() {
		return sumsalary;
	}
	public void setSumsalary(double sumsalary) {
		this.sumsalary = sumsalary;
	}
	public Integer getSumGPF() {
		return sumGPF;
	}
	public void setSumGPF(Integer sumGPF) {
		this.sumGPF = sumGPF;
	}
	public String getLifeinsurance() {
		return lifeinsurance;
	}
	public void setLifeinsurance(String lifeinsurance) {
		this.lifeinsurance = lifeinsurance;
	}
	public double getSvaingsGovt() {
		return svaingsGovt;
	}
	public void setSvaingsGovt(double svaingsGovt) {
		this.svaingsGovt = svaingsGovt;
	}
	public double getSumGovt() {
		return sumGovt;
	}
	public void setSumGovt(double sumGovt) {
		this.sumGovt = sumGovt;
	}
	public double getWifetax() {
		return wifetax;
	}
	public void setWifetax(double wifetax) {
		this.wifetax = wifetax;
	}
	public String getChilderen() {
		return childeren;
	}
	public void setChilderen(String childeren) {
		this.childeren = childeren;
	}
	public double getIncometaxdec() {
		return incometaxdec;
	}
	public void setIncometaxdec(double incometaxdec) {
		this.incometaxdec = incometaxdec;
	}
	public double getFreeacom() {
		return freeacom;
	}
	public void setFreeacom(double freeacom) {
		this.freeacom = freeacom;
	}
	public double getDisburse() {
		return disburse;
	}
	public void setDisburse(double disburse) {
		this.disburse = disburse;
	}
	public EmployeePayroll getEpay() {
		return epay;
	}
	public void setEpay(EmployeePayroll epay) {
		this.epay = epay;
	}

	
}
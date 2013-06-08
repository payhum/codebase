package com.openhr.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.struts.action.ActionForm;

public class EmployeeForm extends ActionForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String employeeId;
	private String firstname;
	private String middlename;
	private String lastname;
	private String sex;
	private Date birthdate;
	private Date hiredate;
	private String photo;
	private String famly;
	private Integer positionId;
	private String status;
	private Integer deptIdVal;

	private Integer paidTax;
	public Integer getPaidTax() {
		return paidTax;
	}

	public void setPaidTax(Integer paidTax) {
		this.paidTax = paidTax;
	}

	private Date passExpDate;
	
	private String empAddrss;
	
	private String passNo;
	
	private String phNo;
	
	private String passPlace;
	
	private String empNation;
	
	private String contName;

	private String contNumber;

	private Integer residentVal;
	
	private Integer curnsy;

	private String nationID;

	private Integer accommodationVal;

	private Double baseSalry;

	private String emerContactName;

	private String emerContactNo;

	private Date inactiveDate;

	private Position positionIds;

	private boolean married;

	private EmployeeSalary empsal;

	private EmployeePayroll payrol;

	private TypesData residentType;

	private Department deptId;

	private String empNationalID;

	
	
	private Long count;
	public Long getCount() {
	return count;
}

public void setCount(Long count) {
	this.count = count;
}

	public String getEmerContactName() {
		return emerContactName;
	}

	public void setEmerContactName(String emerContactName) {
		this.emerContactName = emerContactName;
	}

	public String getEmerContactNo() {
		return emerContactNo;
	}

	public void setEmerContactNo(String emerContactNo) {
		this.emerContactNo = emerContactNo;
	}

	public long getInactiveDate() {
		if(this.inactiveDate!=null)
		{
			return inactiveDate.getTime();
			
		}
		else
		{return 0;}
		
	}

	public Integer getDeptIdVal() {
		return deptIdVal;
	}

	public void setDeptIdVal(Integer deptIdVal) {
		this.deptIdVal = deptIdVal;
	}

	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}


	public void setInactiveDate(long inactiveDate) {
		this.inactiveDate =new Date(inactiveDate);
	}
	
	
	public Position getPositionIds() {
		return positionIds;
	}

	public void setPositionIds(Position positionIds) {
		this.positionIds = positionIds;
	}

	public  boolean getMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}

	public EmployeeSalary getEmpsal() {
		return empsal;
	}

	public void setEmpsal(EmployeeSalary empsal) {
		this.empsal = empsal;
	}

	public EmployeePayroll getPayrol() {
		return payrol;
	}

	public void setPayrol(EmployeePayroll payrol) {
		this.payrol = payrol;
	}

	public TypesData getResidentType() {
		return residentType;
	}

	public void setResidentType(TypesData residentType) {
		this.residentType = residentType;
	}

	public Department getDeptId() {
		return deptId;
	}

	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}

	public String getEmpNationalID() {
		return empNationalID;
	}

	public void setEmpNationalID(String empNationalID) {
		this.empNationalID = empNationalID;
	}

	public Double getBaseSalry() {
		return baseSalry;
	}

	public void setBaseSalry(Double baseSalry) {
		this.baseSalry = baseSalry;
	}

	public Integer getAccommodationVal() {
		return accommodationVal;
	}

	public long getBirthdate() {
		return birthdate.getTime();
	}

	public String getContName() {
		return contName;
	}

	public String getContNumber() {
		return contNumber;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getFamly() {
		return famly;
	}

	public String getFirstname() {
		return firstname;
	}

	public long getHiredate() {
		return hiredate.getTime();
	}
	
	public long getPassExpDate() {
		if(passExpDate==null)
		{
			return new Date().getTime();
		}
		else{return passExpDate.getTime();}
		
	}
	

	public Integer getId() {
		return id;
	}

	public String getLastname() {
		return lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public String getNationID() {
		return nationID;
	}

	public String getPhoto() {
		return photo;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public Integer getResidentVal() {
		return residentVal;
	}

	public String getSex() {
		return sex;
	}

	public String getStatus() {
		return status;
	}

	public void setAccommodationVal(Integer accommodationVal) {
		this.accommodationVal = accommodationVal;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setBirthdate(long birthdate) {
		this.birthdate = new Date(birthdate);
	}

	public void setContName(String contName) {
		this.contName = contName;
	}

	public void setContNumber(String contNumber) {
		this.contNumber = contNumber;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setFamly(String famly) {
		this.famly = famly;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public void setHiredate(long hiredate) {
		this.hiredate = new Date(hiredate);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public void setNationID(String nationID) {
		this.nationID = nationID;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public void setResidentVal(Integer residentVal) {
		this.residentVal = residentVal;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	public void setPassExpDate(long passExpDate) {
		this.passExpDate = new Date(passExpDate);
	}
	public void setPassExpDate(Date passExpDate) {
		this.passExpDate = passExpDate;
	}

	public String getEmpAddrss() {
		return empAddrss;
	}

	public void setEmpAddrss(String empAddrss) {
		this.empAddrss = empAddrss;
	}

	public String getPassNo() {
		return passNo;
	}

	public void setPassNo(String passNo) {
		this.passNo = passNo;
	}

	public String getPhNo() {
		return phNo;
	}

	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}

	public String getPassPlace() {
		return passPlace;
	}

	public void setPassPlace(String passPlace) {
		this.passPlace = passPlace;
	}

	public String getEmpNation() {
		return empNation;
	}

	public void setEmpNation(String empNation) {
		this.empNation = empNation;
	}

	public Integer getCurnsy() {
		return curnsy;
	}

	public void setCurnsy(Integer curnsy) {
		this.curnsy = curnsy;
	}
	
	}

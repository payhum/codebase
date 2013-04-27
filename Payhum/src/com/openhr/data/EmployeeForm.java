package com.openhr.data;

import java.io.Serializable;
import java.util.Date;

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

	private String contName;

	private Integer contNumber;

	private Integer residentVal;

	private String nationID;

	private Integer accommodationVal;

	private Integer departId;

	public Integer getAccommodationVal() {
		return accommodationVal;
	}

	public long getBirthdate() {
		return birthdate.getTime();
	}

	public String getContName() {
		return contName;
	}

	public Integer getContNumber() {
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

	public void setContNumber(Integer contNumber) {
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

	public Integer getDepartId() {
		return departId;
	}

	public void setDepartId(Integer departId) {
		this.departId = departId;
	}

}

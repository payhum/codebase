/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openhr.employee.action;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Department;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.Position;
import com.openhr.data.TypesData;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.PositionFactory;

/**
 * 
 * @author Mekbib
 */
public class UpdateEmployeeAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			 throws Exception{
		//EmployeeForm EmployeeForm = (EmployeeForm) form;
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		boolean flag=false;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONArray json = JSONArray.fromObject(sb.toString());
		Collection<EmployeeForm> aCollection = JSONArray.toCollection(json, EmployeeForm.class);
		
		System.out.println("UPDATE Employee JSON " + json.toString());
		String empID=null;
		Integer acomId=null;
		String lastName=null;
		
		
		Date hireDate=null;
		Employee e = new Employee();
		for (EmployeeForm eFromJSON : aCollection) {
			empID=eFromJSON.getEmployeeId();
			acomId=eFromJSON.getAccommodationVal();
			lastName=eFromJSON.getLastname();
			hireDate=new Date(eFromJSON.getHiredate());
			
			e.setEmployeeId(empID);
			e.setFirstname(eFromJSON.getFirstname());
			e.setMiddlename(eFromJSON.getMiddlename());
			e.setLastname(eFromJSON.getLastname());
			e.setSex(eFromJSON.getSex());
			e.setBirthdate(new Date(eFromJSON.getBirthdate()));
			System.out.println("BIRTHDATE "+ eFromJSON.getBirthdate());
			e.setHiredate(hireDate);
			e.setInactiveDate(hireDate);
			
			e.setEmpNationalID(eFromJSON.getNationID());
			e.setMarried(eFromJSON.getFamly());
			
			e.setStatus(eFromJSON.getStatus());
			e.setEmerContactName(eFromJSON.getContName());
			
			e.setEmerContactNo(eFromJSON.getContNumber());
			
			TypesData tyd=EmployeeFactory.findTypesById(eFromJSON.getResidentVal());
			e.setResidentType(tyd);
			
			Position p = null;
			if(PositionFactory.findById(eFromJSON.getPositionId())!=null)
			p = PositionFactory.findById(eFromJSON.getPositionId()).get(0);
			e.setPositionId(p);
			if(request.getAttribute("photo-name")!=null){
				e.setPhoto(request.getAttribute("photo-name").toString());
			}else{
				e.setPhoto(eFromJSON.getPhoto());
			}
			e.setStatus(eFromJSON.getStatus());
			Department  db = EmployeeFactory.findDepartById(eFromJSON.getDeptIdVal());
			
			if(db!=null)
			{
				e.setDeptId(db);
			}
			
			e.setId(eFromJSON.getId());
				//EmployeePayroll epl=new EmployeePayroll();
				
				
				EmployeePayroll epl=EmpPayTaxFactroy.findEmpPayrollbyEmpID(e);
				TypesData acomTyds=EmployeeFactory.findTypesById(acomId);
				
				epl.setAccomodationType(acomTyds);
				epl.setFullName(lastName);
				epl.setEmployeeId(e);
				//flag=EmpPayTaxFactroy.save(epl);
				
				EmployeeSalary empsal=EmployeeFactory.getEmpsalry(e);
				empsal.setBasesalary(eFromJSON.getBaseSalry());
				empsal.setEmployeeId(e);
				empsal.setFromdate(hireDate);
				empsal.setTodate(hireDate);
			flag=EmpPayTaxFactroy.updateEmpPaytax(e,epl,empsal);
			
		}

		return map.findForward(""); 
	}
}

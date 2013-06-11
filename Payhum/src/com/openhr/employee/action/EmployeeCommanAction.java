package com.openhr.employee.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.DeductionsType;
import com.openhr.data.Department;
import com.openhr.data.Dtest;
import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmpDependents;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeBonus;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.Etest;
import com.openhr.data.Roles;
import com.openhr.data.TypesData;
import com.openhr.data.Users;
import com.openhr.employee.form.EmpDependentsFrom;
import com.openhr.employee.form.EmployeeDepartFrom;
import com.openhr.employee.form.EmployeeForm;
import com.openhr.employee.form.EmployeeSalaryForm;
import com.openhr.factories.DeductionFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.UsersFactory;

public class EmployeeCommanAction extends DispatchAction
{

	
	 public ActionForward create(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	     
			BufferedReader bf = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			 JSONArray result = null;
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
			try
			
			{
				
			JSONArray json = JSONArray.fromObject(sb.toString());
			Collection<EmployeeDepartFrom> aCollection = JSONArray.toCollection(json, EmployeeDepartFrom.class);
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			for (EmployeeDepartFrom eFromJSON : aCollection) {
				System.out.println(eFromJSON.getId());
				
				Department d=new Department();
				d.setId(Integer.valueOf(eFromJSON.getId()));
				Employee e=new Employee();
				
				e.setDeptId(d);
				//List<Users> eptx=UsersFactory.findByRoleId(e);
				 
				List<Employee> lis=EmployeeFactory.findAllEmpPerDepart(d);
				
				result = JSONArray.fromObject(lis,config);
				
				
			}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			 response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		        out.print(result.toString());
		        out.flush();
	        return mapping.findForward("success");
	    }
	 
	 public ActionForward getBankDetails(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	     
			BufferedReader bf = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			JSONObject result = null;
			
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
			try
			
			{
				
				JsonConfig config = new JsonConfig();
				config.setIgnoreDefaultExcludes(false);
				config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
				JSONObject json = JSONObject.fromObject(sb.toString());
				
				Integer id=json.getInt("id");
			//Collection<EmployeeDepartFrom> aCollection = JSONArray.toCollection(json, EmployeeDepartFrom.class);
				Employee  emp=EmployeeFactory.findById(id).get(0);
				
				EmpBankAccount empsal=EmployeeFactory.getBankDetails(emp);
				
				result=JSONObject.fromObject(empsal,config);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			 response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		        out.print(result.toString());
		        out.flush();
	        return mapping.findForward("success");
	    }
	 
	 
	 
	 
	 public ActionForward getEmpPayMentDetails(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	     
			
			JSONObject result = null;
			
			 JSONObject result1 = null;
			try
			
			{
				Integer id=(Integer) request.getSession().getAttribute("employeeId");
				
				Integer empID=(Integer) request.getSession().getAttribute("empPay");
				
			
				long start=0,end=0,diff=0;
				BufferedReader bf = request.getReader();
			    StringBuffer sb = new StringBuffer();
			    String line = null;
			    while ((line = bf.readLine()) != null) {
			        sb.append(line);
			    }
			    
			    JSONObject json = JSONObject.fromObject(sb.toString());
				//JsonConfig config = new JsonConfig();
				//config.setIgnoreDefaultExcludes(false);
				//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			    Integer a= json.getInt("id");
				
					
					Employee e=new Employee();
					
					e.setId(id);
					
					EmployeePayroll emp=EmpPayTaxFactroy.findEmpPayrollbyEmpID(e);
					emp.setEmployeeId(e);
					EmpPayrollMap employees = EmpPayTaxFactroy.findTaxMonthlyForEmployee(a,emp);
					start=System.currentTimeMillis();
					JsonConfig config = new JsonConfig();
					config.setIgnoreDefaultExcludes(false);
					config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
				   result1 = JSONObject.fromObject(employees, config);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			 response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		        out.print(result1.toString());
		        out.flush();
	        return mapping.findForward("success");
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public ActionForward getCurrentSalry(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	     
			BufferedReader bf = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			JSONObject result = null;
			
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
			try
			
			{
				
				JsonConfig config = new JsonConfig();
				config.setIgnoreDefaultExcludes(false);
				config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
				JSONObject json = JSONObject.fromObject(sb.toString());
				
				Integer id=json.getInt("id");
			//Collection<EmployeeDepartFrom> aCollection = JSONArray.toCollection(json, EmployeeDepartFrom.class);
				Employee  emp=EmployeeFactory.findById(id).get(0);
				
				EmployeeSalary empsal=EmployeeFactory.getCurrentSalry(emp);
				
				result=JSONObject.fromObject(empsal,config);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			 response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		        out.print(result.toString());
		        out.flush();
	        return mapping.findForward("success");
	    }
	 
	 public ActionForward saveBank(ActionMapping map,
	            ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse reponse) throws Exception {
			boolean flag=false;
			BufferedReader bf = request.getReader();
	        StringBuffer sb = new StringBuffer();
	        String line = null;
	        while ((line = bf.readLine()) != null) {
	            sb.append(line);
	        }
	        JSONArray json = JSONArray.fromObject(sb.toString());
	        
	        
	      //  System.out.println("THE JSON " + json.toString());
	        
	        Employee emp=null;
	        
	        Collection<EmpBankAccountFrom> aCollection = JSONArray.toCollection(json, EmpBankAccountFrom.class);
	        EmpBankAccount empban = new EmpBankAccount();
	        
	        for (EmpBankAccountFrom rFromJSON : aCollection) {
	        	
	      
	        	
	        Integer id=rFromJSON.getEmpId();
	        if(id!=null)
	        {
	        	 emp=EmployeeFactory.findById(id).get(0);
	        	 
	        	}
	       
	        empban.setEmployeeId(emp);
	        
	        empban.setAccountNo(rFromJSON.getAccNo());
	        empban.setBankBranch(rFromJSON.getBankBranch());
	        empban.setBankName(rFromJSON.getBankName());
	        empban.setRoutingNo(rFromJSON.getRoutingNo());
	        
	        empban.setId(rFromJSON.getId());
	
	        
	        flag=EmployeeFactory.saveUpdateBankDet(empban);  
	        
	        
	        }
	        PrintWriter out = reponse.getWriter();
	out.println(flag);
	        return map.findForward("");
		}
	 
	 
	 public ActionForward saveDepdents(ActionMapping map,
	            ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse reponse) throws Exception {
			boolean flag=false;
			BufferedReader bf = request.getReader();
	        StringBuffer sb = new StringBuffer();
	        String line = null;
	        while ((line = bf.readLine()) != null) {
	            sb.append(line);
	        }
	        JSONArray json = JSONArray.fromObject(sb.toString());
	        
	        
	      //  System.out.println("THE JSON " + json.toString());
	        
	        Employee emp=null;
	        TypesData depType=null;
	        TypesData tad=null;
	        TypesData occupationType=null;
	        Collection<EmpDependentsFrom> aCollection = JSONArray.toCollection(json, EmpDependentsFrom.class);
	        EmpDependents empD = new EmpDependents();
	        
	        for (EmpDependentsFrom rFromJSON : aCollection) {
	        	
	      
	        	
	        Integer id=rFromJSON.getId();
	        if(id!=null)
	        {
	        	 emp=EmployeeFactory.findById(id).get(0);
	        	 emp.setInactiveDate(new Date());
	        	 empD.setEmployeeId(emp);
	        	}
	       
	        Integer depty =rFromJSON.getDepType();
	        
	        if(depty!=null)
	        {
	    		 depType=EmployeeFactory.findTypesById(depty);
	    		 empD.setDepType(depType);
	        	}
	        Integer ocid=rFromJSON.getOccupationType();
	        if(depty!=null)
	        {
	        	occupationType=EmployeeFactory.findTypesById(ocid);
	        	empD.setOccupationType(occupationType);
	        	}
	        	//emp.setId(rFromJSON.getEmployeeId());
	        	//empD.setDepType(rFromJSON.getDepType());
	        	//empD.setEmployeeId(rFromJSON.getEmployeeId());
	        	//empD.setOccupationType(rFromJSON.getOccupationType());
	        	empD.setName(rFromJSON.getName());
	       empD.setAge(rFromJSON.getAge());
	        	
	        	 
	        flag=EmployeeFactory.saveDepdent(empD);  
	        
	        
	        }
	        PrintWriter out = reponse.getWriter();
	out.println(flag);
	        return map.findForward("");
		}
	 
	 public ActionForward getEmpDepdents(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	 
	 
			BufferedReader bf = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			 JSONArray result = null;
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
			try
			
			{
				JSONObject jb=JSONObject.fromObject(sb.toString());
				
				Integer id=jb.getInt("ID");
				JsonConfig config = new JsonConfig();
				config.setIgnoreDefaultExcludes(false);
				config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			//jb.get
				Employee emp=new Employee();
				emp.setId(id);
				List<EmpDependents> employees = EmployeeFactory.findEmpDepdentAll(emp);
				
				result = JSONArray.fromObject(employees, config);
		
				System.out.println(result.toString());
				
		
			} catch (Exception e) {
				e.printStackTrace();
			}

			 response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		        out.print(result.toString());
		        out.flush();

			return map.findForward("");
		
	 }
	 
	 
	 public ActionForward saveCurrentSalry(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	 
	 
			BufferedReader bf = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			 JSONArray result = null;
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
			try
			
			{
				 JSONArray json = JSONArray.fromObject(sb.toString());
				
				
				  Collection<EmployeeSalaryForm> aCollection = JSONArray.toCollection(json, EmployeeSalaryForm.class);
			//jb.get
				//Employee emp=new Employee();
				//emp.setId(id);
				//List<EmpDependents> employees = EmployeeFactory.findEmpDepdentAll(emp);
				
				//result = JSONArray.fromObject(employees, config);
				  EmployeeSalary es=new EmployeeSalary();
				  for(EmployeeSalaryForm rFromJSON:aCollection)
					{
					  es.setTodate(new Date(rFromJSON.getFromDate()));
					//  EmployeeFactory.findById(employeeId);
					  
					//  es.setEmployeeId(EmployeeFactory.findById(employeeId));
					  es.setBasesalary(rFromJSON.getCurSalry());
					  es.setFromdate(new Date(rFromJSON.getFromDate()));
					  
					  es.setEmployeeId((EmployeeFactory.findById(rFromJSON.getEmpId()).get(0)));
					  
					  EmployeeFactory.saveSal(es,rFromJSON.getPrvsId());
					}
				//System.out.println(result.toString());
				
		
			} catch (Exception e) {
				e.printStackTrace();
			}

			 response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		      //  out.print(result.toString());
		        out.flush();

			return map.findForward("");
		
	 }
	 
	 
	 
	 
	 
	 
	 public ActionForward saveBonus(ActionMapping map, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	 
	 
			BufferedReader bf = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			 JSONArray result = null;
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
			try
			
			{
				 JSONArray json = JSONArray.fromObject(sb.toString());
				
				
				  Collection<EmployeeBonusForm> aCollection = JSONArray.toCollection(json, EmployeeBonusForm.class);
			//jb.get
				//Employee emp=new Employee();
				//emp.setId(id);
				//List<EmpDependents> employees = EmployeeFactory.findEmpDepdentAll(emp);
				
				//result = JSONArray.fromObject(employees, config);
				  EmployeeBonus ebf=new EmployeeBonus();
				  for(EmployeeBonusForm rFromJSON:aCollection)
					{
					 
		
					  
					  ebf.setAmount(rFromJSON.getBonusAmont());
					  ebf.setEmployeeId((EmployeeFactory.findById(rFromJSON.getEmpId()).get(0)));
					  ebf.setGivendate(new Date(rFromJSON.getBonusDate()));
					  
					  
					  EmployeeFactory.saveBonus(ebf);
					}
				//System.out.println(result.toString());
				
		
			} catch (Exception e) {
				e.printStackTrace();
			}

			 response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		      //  out.print(result.toString());
		        out.flush();

			return map.findForward("");
		
	 }
	 
	 
	 
	 
	 
	 public ActionForward test(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	     String s=null;
	     JSONArray result =null;
	     
	     try
			
			{
	    	

				/*List<Dtest>  eptx=UsersFactory.findBy();
				//result = JSONArray.fromObject(eptx);
			for(Dtest d:eptx)
			{
				Set<Etest> employees=d.getEmployees();
				for(Etest e:employees)
					
				{	System.out.println(e.getFirstname());}
			
			}*/
			//JSONArray result1 = JSONArray.fromObject(eptx);
			//System.out.println(result1.toString());
	    	 
	    	 JsonConfig config = new JsonConfig();
				config.setIgnoreDefaultExcludes(false);
				config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
				List<Employee> employees = EmployeeFactory.findAll();
				//result = JSONArray.fromObject(emplist, config);
				result = JSONArray.fromObject(employees, config);

				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			
				out.print(result.toString());
			
			out.flush();

			return mapping.findForward("");
		}
	 
	 
	 
}

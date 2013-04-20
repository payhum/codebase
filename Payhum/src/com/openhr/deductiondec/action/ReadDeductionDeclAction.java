package com.openhr.deductiondec.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.DeductionsType;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.OverTimePayRateData;
import com.openhr.deductiondec.form.DeductionForm;
import com.openhr.factories.DeductionFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.taxengine.DeductionsDeclared;

public class ReadDeductionDeclAction extends DispatchAction {
	
	
	
	
	 public ActionForward checkDeduction(ActionMapping map,
	            ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse reponse) throws Exception {
			
		// Integer id=(Integer) request.getSession().getAttribute("employeeId");
			
		//	Integer empID=(Integer) request.getSession().getAttribute("empPay");
			boolean flag=false;
			BufferedReader bf = request.getReader();
	        StringBuffer sb = new StringBuffer();
	        String line = null;
	        while ((line = bf.readLine()) != null) {
	            sb.append(line);
	        }
	        try{
	        	JSONObject json = JSONObject.fromObject(sb.toString());
	        
	        Integer ids= json.getInt("ids");	
	       // Collection<DeductionForm> aCollection = JSONArray.toCollection(json, DeductionForm.class);
	   DeductionsType dtyp=new  DeductionsType();
	   dtyp.setId(ids);
	        	 flag=DeductionFactory.checkDeductionDeclare(dtyp);            
	     
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
		        PrintWriter out = reponse.getWriter();
		        out.println(flag);
		        return map.findForward("");
		}
		
	
    public ActionForward getAllDeduction(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) throws Exception {
		
		Integer id=(Integer) request.getSession().getAttribute("employeeId");
		
		Integer empID=(Integer) request.getSession().getAttribute("empPay");
	 JSONArray result = null;
	        try {
	        	//Employee e=new Employee();
	        	//e.setId(id);
	        	//EmployeePayroll dedc = EmpPayTaxFactroy.findEmpPayrollbyEmpID(e);
	        	//EmployeePayroll empl=new  EmployeePayroll();
	        	
	        	//empl.setId(empID);
	        	List<DeductionsDeclared> lded=DeductionFactory.deductionsDecl(empID);
	        	
	        	List<DeductionsDeclared>  ld=new ArrayList<DeductionsDeclared>();
	        	DeductionsDeclared dcf=null;
	        	 for (DeductionsDeclared rFromJSON : lded) {
	        		 dcf=new DeductionsDeclared();
	             	
	        		 dcf.setId(rFromJSON.getId());
	        		 dcf.setDescription(rFromJSON.getDescription());
	        		 dcf.setType(rFromJSON.getType());
	        		dcf.setAmount(rFromJSON.getAmount());
	        		 
	             	     ld.add(dcf);
	             }
	        	
	       
	            result = JSONArray.fromObject(ld);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        System.out.print(result.toString());
	        
	        reponse.setContentType("application/json; charset=utf-8");
	        PrintWriter out = reponse.getWriter();
	    out.print(result.toString());
	        out.flush();


	        return map.findForward("");
	}
    public ActionForward saveDeduction(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) throws Exception {
		
		Integer id=(Integer) request.getSession().getAttribute("employeeId");
		
		Integer empID=(Integer) request.getSession().getAttribute("empPay");
		boolean flag=false;
		BufferedReader bf = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        try{
        JSONArray json = JSONArray.fromObject(sb.toString());
        Collection<DeductionForm> aCollection = JSONArray.toCollection(json, DeductionForm.class);
        DeductionsDeclared dcd = new DeductionsDeclared();
        DeductionsType dtp=new DeductionsType();
        EmployeePayroll empl=new EmployeePayroll();
        for (DeductionForm rFromJSON : aCollection) {
        	empl.setId(empID);
        	dcd.setPayrollId(empl);
           	dcd.setAmount(rFromJSON.getAmount());
        	dcd.setDescription(rFromJSON.getDescription());
        	dtp.setId(rFromJSON.getType());
        	dcd.setType(dtp);
        	 flag=DeductionFactory.insertDeducDec(dcd);            
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	        PrintWriter out = reponse.getWriter();
	        out.println(flag);
	        return map.findForward("");
	}
    public ActionForward deleteDeduction(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) throws Exception {
		
		Integer id=(Integer) request.getSession().getAttribute("employeeId");
		
		Integer empID=(Integer) request.getSession().getAttribute("empPay");
		boolean flag=false;
		BufferedReader bf = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        try{
        	JSONObject json = JSONObject.fromObject(sb.toString());
        
        Integer ids= json.getInt("ids");	
       // Collection<DeductionForm> aCollection = JSONArray.toCollection(json, DeductionForm.class);
   
        	 flag=DeductionFactory.deleteDeducDec(ids);            
     
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	        PrintWriter out = reponse.getWriter();
	        out.println(flag);
	        return map.findForward("");
	}
}
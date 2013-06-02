package com.openhr.tax.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.Position;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.tax.form.TaxAnnualForm;
import com.openhr.tax.form.TaxMonthForm;

public class ReadTaxMonthlyAction extends Action{
	
	 @Override
	    public ActionForward execute(ActionMapping map,
	            ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 
		 JSONArray result = null;
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
			try {
				
				
				
				List<EmpPayrollMap> employees = EmpPayTaxFactroy.findTaxMonthly(a);
				start=System.currentTimeMillis();
				JsonConfig config = new JsonConfig();
				config.setIgnoreDefaultExcludes(false);
				config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			/*	Iterator<Employee> it = employees.iterator();
				while (it.hasNext()) {
					
					TaxMonthForm tm=new TaxMonthForm();
					Employee e = it.next();
					
					tm.setEmployeeId(e.getEmployeeId());
					tm.setFirstname(e.getFirstname()+"   "+e.getMiddlename());
					tm.setId(e.getId());
					tm.setPositionId(e.getPositionId());
					tm.setRemarks("NO");
					tm.setTaxdec("NO");
					employees1.add(tm);
					
				}
				*/
				
				
				result = JSONArray.fromObject(employees, config);
				end=System.currentTimeMillis();
				diff = end - start;
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("It took " + diff +" milli seconds to read the results");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(result.toString());
			out.flush();

			return map.findForward("");
		}
}

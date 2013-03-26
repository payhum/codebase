package com.openhr.miscellaneous.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Employee;
import com.openhr.data.Position;
import com.openhr.factories.EmployeeFactory;
import com.openhr.miscellaneous.form.ActiveForm;
import com.openhr.tax.form.TaxAnnualForm;

public class ReadActiveAction  extends Action{
	private static List<ActiveForm> employees1 = new ArrayList<ActiveForm>();
	 @Override
	    public ActionForward execute(ActionMapping map,
	            ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
		 
		 JSONArray result = null;
			long start=0,end=0,diff=0;
			try {
				
				List<Employee> employees = EmployeeFactory.findAll();
				start=System.currentTimeMillis();
				
				//Employee e = null;
				Iterator<Employee> it = employees.iterator();
				StringBuilder sb = new StringBuilder();
				while (it.hasNext()) {
					//Object o1 = it.next();
					ActiveForm ta=new ActiveForm();
					Employee e = it.next();

					Position p = e.getPositionId();

					String s = p.getId().toString();

					sb.append(s + ",");

					String s2[] = sb.toString().split(s);

					if (!(s2.length > 2)) {
						System.out.println(p.getId() + "---p.hashCode()---"
								+ p.hashCode());
						Double d = p.getSalary();
						p.setSalary(((d) * 12));

					}
					
					ta.setEmployeeId(e.getEmployeeId());
					
					
					ta.setFirstname(e.getFirstname()+"-"+e.getMiddlename());
					
					ta.setId(e.getId());
					String ss=e.getStatus();
					boolean b=ss.equals("ACTIVE");
					
				//b? ta.setActive("YES") : ta.setActive("NO");
				if(b)
				{
					ta.setActive("YES");
					ta.setTerminated("NO");
				}
				if(!b)
				{
					ta.setTerminated("YES");
					ta.setActive("NO");	
				}
		
					
					employees1.add(ta);
					

				}
				result = JSONArray.fromObject(employees1);
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

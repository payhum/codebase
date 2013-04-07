package com.openhr.tax.action;

import org.apache.struts.action.Action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Employee;
import com.openhr.data.Position;
import com.openhr.factories.EmployeeFactory;
import com.openhr.tax.form.TaxAnnualForm;

public class ReadTaxAnnualAction extends Action {
	private static List<TaxAnnualForm> employees1 = new ArrayList<TaxAnnualForm>();

	// private static Position p=null;
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONArray result = null;
		long start = 0, end = 0, diff = 0;
		try {

			List<Employee> employees = EmployeeFactory.findAll();
			
			//Employee e = null;
			Iterator<Employee> it = employees.iterator();
			StringBuilder sb = new StringBuilder();
			while (it.hasNext()) {
				//Object o1 = it.next();
				TaxAnnualForm ta=new TaxAnnualForm();
				Employee e = it.next();

				Position p = e.getPositionId();

				String s = p.getId().toString();

				sb.append(s + "  ");

				String s2[] = sb.toString().split(s);

				if (!(s2.length > 2)) {
					System.out.println(p.getId() + "---p.hashCode()---"
							+ p.hashCode());
					Double d = p.getSalary();
					p.setSalary(((d) * 12));

				}
				
				ta.setEmployeeId(e.getEmployeeId());
				ta.setChilderen(0);
				
				ta.setFirstname(e.getFirstname()+"."+e.getMiddlename());
				
				ta.setId(e.getId());
				ta.setIncometaxdec(0);
				
				ta.setLastname(e.getLastname());
				
				ta.setPositionId(p);
				
				ta.setFreeacom(0);
				ta.setDisburse(0);
				ta.setSumsalary(e.getPositionId().getSalary()+0+0);
				ta.setLifeinsurance(0);
				ta.setSumGPF(0);
				ta.setSvaingsGovt(0);
				ta.setWifetax(0);
				employees1.add(ta);
				

			}

			start = System.currentTimeMillis();

			result = JSONArray.fromObject(employees1);
			end = System.currentTimeMillis();
			diff = end - start;
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("It took " + diff
				+ " milli seconds to read the results"+result.toString());
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}
	
	public static void main(String s[])
	{
		
		
		Map<String, Long> map = new HashMap<String, Long>();
        map.put("A", 10L);
        map.put("B", 20L);
        map.put("C", 30L);
         
        JSONObject json = new JSONObject();
        json.accumulateAll(map);
         
        System.out.println(json.toString());
 
         
        List<String> list = new ArrayList<String>();
        list.add("Sunday");
        list.add("Monday");
        list.add("Tuesday");
         
        json.accumulate("weekdays", map);
        System.out.println(json.toString());
		
		
	}
}

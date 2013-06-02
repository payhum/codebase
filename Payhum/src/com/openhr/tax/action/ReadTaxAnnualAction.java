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
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.DeductionsType;
import com.openhr.data.EmpDependents;
import com.openhr.data.EmpPayTax;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Position;
import com.openhr.data.TypesData;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.tax.form.TaxAnnualForm;
import com.openhr.taxengine.DeductionsDone;

public class ReadTaxAnnualAction extends Action {


	// private static Position p=null;
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONArray result = null;
		long start = 0, end = 0, diff = 0;
		try {
			
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			 List<TaxAnnualForm> employees1 = new ArrayList<TaxAnnualForm>();
			List<EmployeePayroll> employees =EmpPayTaxFactroy.findAllEmpPayroll();
			TaxAnnualForm ta=null;
		
for(EmployeePayroll emp:employees)
{
	 ta=new TaxAnnualForm();
	 ta.setEpay(emp);
	// int type=3;
	 DeductionsType type=new DeductionsType();
	 
	 type.setId(3);
	 DeductionsDone decDone=EmpPayTaxFactroy.findDeductionDone(type,emp);
	 if(decDone==null)
	 { ta.setLifeinsurance("0");
		 
	 }
	
	 else
	 {
		 ta.setLifeinsurance(decDone.getAmount().toString());
	 }
	 int typeDepdent=21;
	 
	 
	 
	 TypesData  typeData=new TypesData();
	 typeData.setId(21);
	 Long empDep= EmpPayTaxFactroy.findEmpDepdentsWithType(typeData,emp.getEmployeeId());
	 Long ob=0l;
	 if(empDep==ob)
	 { ta.setChilderen("NA");
		 
	 }
	
	 else
	 {
		 ta.setChilderen(empDep.toString());
	 }
	 
	 
	 TypesData  typeData1=new TypesData();
	 typeData1.setId(20);
	 EmpDependents  empDep1= EmpPayTaxFactroy.findEmpDepdentsWithType2(typeData1,emp.getEmployeeId());
	 
	 if(empDep1==null)
	 { ta.setSpouse("NA");
		 
	 }
	
	 else
	 {
		 ta.setSpouse(empDep1.getOccupationType().getName());
	 }
	 
	 employees1.add(ta);
}
			start = System.currentTimeMillis();

			result = JSONArray.fromObject(employees1,config);
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

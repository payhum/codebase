package com.openhr.employee.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.Branch;

import com.openhr.data.PayrollDate;
import com.openhr.data.TypesData;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.data.Department;

public class CommantypesAction extends DispatchAction {

	public ActionForward getBranch(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
		try {
			
			//JsonConfig config = new JsonConfig();
			//config.setIgnoreDefaultExcludes(false);
			//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			List<Branch> eptx = EmployeeFactory.findBrachAll();
			result = JSONArray.fromObject(eptx);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}
	
	
	
	public ActionForward getResident(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
		try {
			
			//JsonConfig config = new JsonConfig();
			//config.setIgnoreDefaultExcludes(false);
			//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			List<TypesData> eptx = EmployeeFactory.findTypes("RESIDENTTYPE");
			result = JSONArray.fromObject(eptx);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}
	
	
	
	public ActionForward getCurrensy(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
		try {
			
			//JsonConfig config = new JsonConfig();
			//config.setIgnoreDefaultExcludes(false);
			//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			List<TypesData> eptx = EmployeeFactory.findTypes("CURRENCY");
			result = JSONArray.fromObject(eptx);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}
	
	
	
	
	
	
	
	
	
	
	
	public ActionForward getPayRollDates(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
		try {
			
			//JsonConfig config = new JsonConfig();
			//config.setIgnoreDefaultExcludes(false);
			//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);


			List<PayrollDate> eptx = EmpPayTaxFactroy.findPayrollDates();
			result = JSONArray.fromObject(eptx);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}	
	
	
	
	public ActionForward getAccommodation(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
		try {
			
			//JsonConfig config = new JsonConfig();
			//config.setIgnoreDefaultExcludes(false);
			//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			List<TypesData> eptx = EmployeeFactory.findTypes("ACCOMODATIONTYPE");
			result = JSONArray.fromObject(eptx);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}
	
	
	public ActionForward getdepdentType(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
		try {
			
			//JsonConfig config = new JsonConfig();
			//config.setIgnoreDefaultExcludes(false);
			//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			List<TypesData> eptx = EmployeeFactory.findTypes("DEPENDENTTYPE");
			result = JSONArray.fromObject(eptx);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}
	
	public ActionForward getOccupationType(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
		try {
			
			//JsonConfig config = new JsonConfig();
			//config.setIgnoreDefaultExcludes(false);
			//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			List<TypesData> eptx = EmployeeFactory.findTypes("OCCUPATIONTYPE");
			result = JSONArray.fromObject(eptx);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}
	
	
	
	
	
	
	public ActionForward getAllBrachDepart(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
	
	BufferedReader bf = request.getReader();
    StringBuffer sb = new StringBuffer();
    String line = null;
    while ((line = bf.readLine()) != null) {
        sb.append(line);
    }
    
    
		try {
			JSONObject json = JSONObject.fromObject(sb.toString());
			//JsonConfig config = new JsonConfig();
			//config.setIgnoreDefaultExcludes(false);
			//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			Integer ids= json.getInt("Id");	
			List<Department> dpList = EmployeeFactory.findBrachDepart(ids);
			result = JSONArray.fromObject(dpList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}


	public ActionForward getAllBranchesOfComp(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
		try {
			
			//JsonConfig config = new JsonConfig();
			//config.setIgnoreDefaultExcludes(false);
			//config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			BufferedReader bf = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
			JSONObject json = JSONObject.fromObject(sb.toString());
			String compId   		= json.getString("compId");
			
			List<Branch> eptx = EmployeeFactory.findBranchByCompId(Integer.parseInt(compId));
			result = JSONArray.fromObject(eptx);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}
	

}

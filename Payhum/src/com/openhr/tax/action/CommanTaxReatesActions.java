package com.openhr.tax.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.Employee;
import com.openhr.data.TaxRatesData;
import com.openhr.employee.form.EmployeeForm;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.TaxratesFactory;

public class CommanTaxReatesActions extends DispatchAction{

	
	
	public ActionForward getTaxRate(ActionMapping map, ActionForm form,
	
			HttpServletRequest request, HttpServletResponse response)
	
			throws Exception {
	
		 JSONArray result = null;
		 try {
			 TaxRatesData  txd=TaxratesFactory.findFromIncome();
			 
			 List<TaxRatesData> txl=new ArrayList<TaxRatesData>();
			 if(txd==null)
			 {
				 txd=new TaxRatesData();
				 txd.setIncomeFrom(1.0);
				 
			 }
			 
			 txl.add(txd);
			 
			 
			 result = JSONArray.fromObject(txl);
			 
		 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		 
		 response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(result.toString());
			out.flush();

			return map.findForward("");
		
			}
	
	
	public ActionForward getAllTaxRates(ActionMapping map, ActionForm form,
			
			HttpServletRequest request, HttpServletResponse response)
	
			throws Exception {
	
		 JSONArray result = null;
		 try {
			
			 
			 List<TaxRatesData> txl=TaxratesFactory.findAll();
		
			 
			 
			 result = JSONArray.fromObject(txl);
			 
		 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		 
		 response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(result.toString());
			out.flush();

			return map.findForward("");
		
			}
	
	
	
	public ActionForward upDateTaxRate(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //EmployeeForm EmployeeForm = (EmployeeForm) form;
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		JSONArray result = null;
		boolean flag=false;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONArray json = JSONArray.fromObject(sb.toString());
		Collection<TaxRatesData> aCollection = JSONArray.toCollection(json, TaxRatesData.class);
		
		System.out.println("Employee JSON "+json.toString());
		
		//Employee e = new Employee();
		for (TaxRatesData eFromJSON : aCollection) {
			flag=TaxratesFactory.updatePercent(eFromJSON);
			
			// result = JSONArray.fromObject(txl);
		}
	//	response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();

		return map.findForward("");
	   
	}
	
	
	
	public ActionForward deleteTaxRate(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //EmployeeForm EmployeeForm = (EmployeeForm) form;
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		JSONArray result = null;
		
		Double useIncomeFrom=null;
		Double useIncomeTo=null;
		boolean flag=false;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONArray json = JSONArray.fromObject(sb.toString());
		Collection<TaxRatesData> aCollection = JSONArray.toCollection(json, TaxRatesData.class);
		
		System.out.println("Employee JSON "+json.toString());
		
		//Employee e = new Employee();
		for (TaxRatesData eFromJSON : aCollection) {
			useIncomeFrom=eFromJSON.getIncomeFrom();
			useIncomeTo=eFromJSON.getIncomeTo()+1;
			
			if(TaxratesFactory.delete(eFromJSON))
			{
				
				flag=TaxratesFactory.upDateDelete(useIncomeTo,useIncomeFrom);
			}
			
			// result = JSONArray.fromObject(txl);
		}
	//	response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();

		return map.findForward("");
	   
	}
}

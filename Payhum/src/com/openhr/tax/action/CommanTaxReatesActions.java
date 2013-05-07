package com.openhr.tax.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.TaxRatesData;
import com.openhr.factories.TaxFactory;

public class CommanTaxReatesActions extends DispatchAction {
 	public ActionForward getTaxRate(ActionMapping map, ActionForm form,
 	HttpServletRequest request, HttpServletResponse response) throws Exception {
 		JSONArray result = null;
 		try {
			
			List<TaxRatesData> txdList = TaxFactory.findAllTaxByType(11);
			TaxRatesData txd = null;
			if(txdList.size() != 0){
				txd = txdList.get(txdList.size()-1);
			}
			System.out.println("dadaf...."+txd.getIncomeFrom());
			 
 			List<TaxRatesData> txl = new ArrayList<TaxRatesData>();
			if (txd == null) {
				txd = new TaxRatesData();
				txd.setIncomeFrom(1.0);
 			}
 			txl.add(txd);
 			result = JSONArray.fromObject(txl);
 		}
		catch (Exception e) {
			e.printStackTrace();
		}
 		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();
 		return map.findForward("");
  	}
 	
 	public ActionForward getTaxRateForForiegn(ActionMapping map, ActionForm form,
 		 	HttpServletRequest request, HttpServletResponse response) throws Exception {
 		 		JSONArray result = null;
 		 		try {
 					
 					List<TaxRatesData> txdList = TaxFactory.findAllTaxByType(13);
 					TaxRatesData txd = null;
 					if(txdList.size() != 0){
 						txd = txdList.get(txdList.size()-1);
 					}
  					 
 		 			List<TaxRatesData> txl = new ArrayList<TaxRatesData>();
 					if (txd == null) {
 						txd = new TaxRatesData();
 						txd.setIncomeFrom(1.0);
 		 			}
 		 			txl.add(txd);
 		 			result = JSONArray.fromObject(txl);
 		 		}
 				catch (Exception e) {
 					e.printStackTrace();
 				}
 		 		response.setContentType("application/json; charset=utf-8");
 				PrintWriter out = response.getWriter();
 				out.print(result.toString());
 				out.flush();
 		 		return map.findForward("");
 		  	}
 	
 	public ActionForward getTaxRateForForiegnTax(ActionMapping map, ActionForm form,
 		 	HttpServletRequest request, HttpServletResponse response) throws Exception {
 		 		JSONArray result = null;
 		 		try {
 					
 					List<TaxRatesData> txdList = TaxFactory.findAllTaxByType(13);
 					TaxRatesData txd = null;
 					double a[] = {0,1};
 					if(txdList.size() != 0){
 						txd = txdList.get(txdList.size()-1);
 						a[0] = txd.getIncomePercentage();
 					}
  					 
 					
 					
 		 			result = JSONArray.fromObject(a);
 		 		}
 				catch (Exception e) {
 					e.printStackTrace();
 				}
 		 		response.setContentType("application/json; charset=utf-8");
 				PrintWriter out = response.getWriter();
 				out.print(result.toString());
 				out.flush();
 		 		return map.findForward("");
 		  	}

	public ActionForward getAllTaxRates(ActionMapping map, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
 		JSONArray result = null;
		try {
  			List<TaxRatesData> txl = TaxFactory.findAllTaxByType(11);
  			result = JSONArray.fromObject(txl);
 		} catch (Exception e) {
			e.printStackTrace();
		}
 		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();
 		return map.findForward("");
 	}
	
	public ActionForward getAllTaxRatesForiegn(ActionMapping map, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
 		JSONArray result = null;
		try {
  			List<TaxRatesData> txl = TaxFactory.findAllTaxByType(13);
   			result = JSONArray.fromObject(txl);
 		} catch (Exception e) {
			e.printStackTrace();
		}
		
 		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();
 		return map.findForward("");
 	}

	public ActionForward upDateTaxRate(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
 		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		JSONArray result = null;
		boolean flag = false;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONArray json = JSONArray.fromObject(sb.toString());
		Double percentage = 0.0;
		Collection<TaxRatesData> aCollection = JSONArray.toCollection(json,TaxRatesData.class);
		if(json.size() != 0){
			JSONObject json1 = (JSONObject) json.get(0);
			String percent = json1.getInt("incomePersent")+"";
			percentage = Double.parseDouble(percent);
		}
 		
 		
 		
		for (TaxRatesData eFromJSON : aCollection) {
			eFromJSON.setIncomePercentage(percentage);
 			flag = TaxFactory.updatePercent(eFromJSON);
 
		}
 		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();

		return map.findForward("");

	}

	public ActionForward deleteTaxRate(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
 		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		JSONArray result = null;

		Double useIncomeFrom = null;
		Double useIncomeTo = null;
		boolean flag = false;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONArray json = JSONArray.fromObject(sb.toString());
		Collection<TaxRatesData> aCollection = JSONArray.toCollection(json,
				TaxRatesData.class);

		System.out.println("Employee JSON " + json.toString());
  		for (TaxRatesData eFromJSON : aCollection) {
			useIncomeFrom = eFromJSON.getIncomeFrom();
			useIncomeTo = eFromJSON.getIncomeTo() + 1;

			if (eFromJSON.getIncomeTo() != -1) {

				if (TaxFactory.delete(eFromJSON)) {
					flag = TaxFactory.upDateDelete(useIncomeTo, useIncomeFrom);
				}

			}

 		}
 		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();

		return map.findForward("");

	}
}

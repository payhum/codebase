package com.openhr.tax.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.TaxRatesData;
import com.openhr.data.TypesData;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.TaxFactory;
import com.openhr.factories.TypesDataFactory;

public class ForiegnTaxRatesAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		boolean flag = false;
 		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONArray json = JSONArray.fromObject(sb.toString());
		
		JSONObject json1 = (JSONObject) json.get(0);
		
 
			
 			Double incomeFrom = json1.getDouble("incomeFrom");
			Double incomeTo = json1.getDouble("incomeTo");
			Double incomePercnt = json1.getDouble("incomePersent");
			TypesData residentType = TypesDataFactory.findById(13);
			Integer id = json1.getInt("id");
			System.out.println("venkat....."+incomeFrom+"------"+incomeTo+"----"+incomePercnt+"---"+residentType.getId());
			TaxRatesData txr;
			if(TaxFactory.findAllTaxByType(13).size() != 0){
			    txr = TaxFactory.findAllTaxByType(13).get(0);
				txr.setIncomeFrom(1.0);
 				txr.setIncomeTo(1.0);
				txr.setIncomePercentage(incomePercnt);
				TaxFactory.update(txr);
 			}
			else{
				txr = new TaxRatesData();
				txr.setIncomeFrom(1.0);
 				txr.setIncomeTo(1.0);
				txr.setIncomePercentage(incomePercnt);
				txr.setResidentTypeId(residentType);
				EmpPayTaxFactroy.insertTaxRates(txr);
			}
			
			 
			PrintWriter out = reponse.getWriter();
			out.print(flag);
			out.flush();
		return map.findForward("");
	}
}

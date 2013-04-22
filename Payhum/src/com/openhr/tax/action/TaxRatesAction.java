package com.openhr.tax.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.TaxRatesData;
import com.openhr.data.TypesData;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.TaxFactory;

public class TaxRatesAction extends Action {
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

		Collection<TaxRatesData> aCollection = JSONArray.toCollection(json,
				TaxRatesData.class);
		TaxRatesData txr = new TaxRatesData();
		for (TaxRatesData rFromJSON : aCollection) {

			// ovt.setId(rFromJSON.getId());
			Double incomeFrom = rFromJSON.getIncomeFrom();
			Double incomeTo = rFromJSON.getIncomeTo();
			Double incomePercnt = rFromJSON.getIncomePercentage();
			TypesData residentType = rFromJSON.getResidentTypeId();
			Integer id = rFromJSON.getId();

			txr.setId(id);
			txr.setIncomeFrom(incomeFrom);
			txr.setIncomeTo(incomeTo);
			txr.setIncomePercentage(incomePercnt);
			txr.setResidentTypeId(residentType);
			if (id == 0) {
				flag = EmpPayTaxFactroy.insertTaxRates(txr);
				if (flag) {

					txr.setIncomeFrom(incomeTo + 1);
					txr.setIncomeTo(-1.0);
					txr.setIncomePercentage(incomePercnt);
					flag = EmpPayTaxFactroy.insertTaxRates(txr);

				}

			} else {
				if (TaxFactory.update(txr)) {
					txr.setIncomeFrom(incomeTo + 1);
					txr.setIncomeTo(-1.0);
					txr.setIncomePercentage(incomePercnt);
					flag = EmpPayTaxFactroy.insertTaxRates(txr);
				}
			}
		}
		PrintWriter out = reponse.getWriter();
		out.print(flag);
		out.flush();
		return map.findForward("");
	}
}

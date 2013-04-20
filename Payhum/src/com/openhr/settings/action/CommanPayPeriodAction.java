package com.openhr.settings.action;

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

import com.openhr.data.PayPeriodData;
import com.openhr.data.TaxRatesData;
import com.openhr.factories.PayPeriodFactory;
import com.openhr.factories.TaxratesFactory;

public class CommanPayPeriodAction extends DispatchAction {

	public ActionForward getPayPeriod(ActionMapping map, ActionForm form,

	HttpServletRequest request, HttpServletResponse response)

	throws Exception {

		JSONArray result = null;
		try {
			List<PayPeriodData> payprd = PayPeriodFactory.findAll();

			result = JSONArray.fromObject(payprd);

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");

	}

	public ActionForward upDatePayPeriod(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// EmployeeForm EmployeeForm = (EmployeeForm) form;
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		JSONArray result = null;
		boolean flag = false;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONArray json = JSONArray.fromObject(sb.toString());
		Collection<PayPeriodData> aCollection = JSONArray.toCollection(json,
				PayPeriodData.class);

		System.out.println("Employee JSON " + json.toString());

		// Employee e = new Employee();
		for (PayPeriodData eFromJSON : aCollection) {
			flag = PayPeriodFactory.update(eFromJSON);

			// result = JSONArray.fromObject(txl);
		}
		// response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();

		return map.findForward("");

	}

}

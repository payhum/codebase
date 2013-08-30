package com.openhr.company;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.common.PayhumConstants;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.ConfigDataFactory;
 
public class ReadBranchAction extends Action {

	@SuppressWarnings("unused")
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray result = null;
		long start = 0, end = 0, diff = 0;
		try {
			ConfigData userComp = ConfigDataFactory.findByName(PayhumConstants.LOGGED_USER_COMP); 
			Integer compId = Integer.parseInt(userComp.getConfigValue());
			
			List<Company> comps = CompanyFactory.findById(compId);
			Company comp = null;
			if(comps != null && !comps.isEmpty()) {
				comp = comps.get(0);
			}
			
			if(comp!=null) {
				List<Branch> companies = BranchFactory.findByCompanyId(comp.getId());
				start = System.currentTimeMillis();
				result = JSONArray.fromObject(companies);
				end = System.currentTimeMillis();
				diff = end - start;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

 		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (result == null) {
			out.print("");
		} else {
			out.print(result.toString());
		}
		out.flush();

		return map.findForward("");
	}
}
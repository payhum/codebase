package com.openhr.company;


import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.factories.CompanyFactory;

public class UpdateCompanyAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
		String companyId   = json.getString("companyId");
		String companyName = json.getString("name");
		String address     = json.getString("address");
		 
		Company company = CompanyFactory.findByCompanyId(companyId).get(0);
 		company.setName(companyName);
		CompanyFactory.update(company);
	 
		return null;
	}
}

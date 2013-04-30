package com.openhr.company;


import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Branch;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.LicenseFactory;

public class DeleteCompanyAction extends Action {
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
		String companyId   =  json.getString("compId");
  		Company company = CompanyFactory.findByCompanyId(companyId).get(0);
  		Branch branch = BranchFactory.findByCompanyId(company.getId()).get(0);
 		if(branch != null){
 			BranchFactory.delete(branch);
 		}
  		Licenses license = LicenseFactory.findByCompanyId(company.getId()).get(0);
   		System.out.println("license size...."+license.getCompanyId());
		LicenseFactory.delete(license);
		CompanyFactory.delete(company);
  		return null;
	}
}

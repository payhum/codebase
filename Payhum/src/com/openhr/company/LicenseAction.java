package com.openhr.company;


import java.io.BufferedReader;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.factories.CompanyFactory;
import com.openhr.factories.LicenseFactory;

public class LicenseAction extends Action {
	@SuppressWarnings("deprecation")
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
		String from   = json.getString("fromdate");
		String to     = json.getString("todate");
		
		
		Company company = CompanyFactory.findByCompanyId(companyId).get(0);
		
		List<Licenses> licenses = LicenseFactory.findByCompanyId(company.getId());
		
		for(Licenses lic: licenses) {
			if(lic.getActive().compareTo(1) == 0) {
				lic.setActive(0);
				LicenseFactory.update(lic);
			}
		}
		
		Licenses newLicense = new Licenses();
		newLicense.setActive(1);
		newLicense.setCompanyId(company);
		newLicense.setFromdate(new Date(from));
		newLicense.setTodate(new Date(to));
		newLicense.formLicenseKey();
 		LicenseFactory.insert(newLicense);
 		return null;
	}
}

package com.openhr.company;


import java.io.BufferedReader;
import java.util.Calendar;
import java.util.Date;

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

public class CreateCompanyAction extends Action {
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
		String companyName = json.getString("name");
		String address     = json.getString("address");
		String fromDate    = json.getString("fromDate");
		String toDate      = json.getString("toDate");
		String fystart	   = json.getString("fystart");

		Date fydate = new Date(fystart);
		
		Calendar effDtCal = Calendar.getInstance();
	    effDtCal.setTime(fydate);
		int startmonth = effDtCal.get(Calendar.MONTH) + 1;
	    
		Company company = new Company();
		company.setCompanyId(companyId);
 		company.setName(companyName);
 		company.setFystart(new Integer(startmonth));
		CompanyFactory.insert(company);
		
		Branch branch = new Branch();
		branch.setAddress(address);
		branch.setCompanyId(company);
		branch.setName("Main");
		BranchFactory.insert(branch);
				
		Licenses license = new Licenses();
		license.setActive(1);
		license.setCompanyId(company);
		license.setFromdate(new Date(fromDate));
		license.setTodate(new Date(toDate));
 		license.formLicenseKey();
 		LicenseFactory.insert(license);
		   
		return null;
	}
}

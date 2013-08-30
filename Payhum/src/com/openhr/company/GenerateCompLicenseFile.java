package com.openhr.company;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Branch;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.LicenseFactory;

public class GenerateCompLicenseFile extends Action {
	private static final String COMMA = ",";
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String compIdStr = request.getParameter("cId");
		System.out.println("CompId is - " + compIdStr);
		
		List<Company> comps = CompanyFactory.findByCompanyId(compIdStr);
		Company comp = null;
		
		if(comps != null && !comps.isEmpty()) {
			comp = comps.get(0);
		} else {
			throw new Exception("Unable to get the company details");
		}
		
		String fileName = comp.getName() + "_Details.csv";
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/force-download");
		
		
		List<Branch> branches = BranchFactory.findByCompanyId(comp.getId());
		Branch branch = null;
		
		if(branches != null && !branches.isEmpty()) {
			branch = branches.get(0);
		} else {
			throw new Exception("Unable to get Company's branch details");
		}
		
		List<Licenses> licenses = LicenseFactory.findByCompanyId(comp.getId());
		Licenses license = null;
		
		if(licenses != null && !licenses.isEmpty()) {
			for(Licenses lic: licenses) {
				if(lic.getActive().compareTo(1) == 0) {
					license = lic;
					break;
				}
			}
		} else {
			throw new Exception("Unable to get Company's licenses details");
		}
		
		String processedAddr = branch.getAddress();
		processedAddr = processedAddr.replace(",", ";");
		
		StringBuilder compDetailsStr = new StringBuilder();
		compDetailsStr.append(comp.getCompanyId());
		compDetailsStr.append(COMMA);
		compDetailsStr.append(comp.getName());
		compDetailsStr.append(COMMA);
		compDetailsStr.append(branch.getName());
		compDetailsStr.append(COMMA);
		compDetailsStr.append(processedAddr);
		compDetailsStr.append(COMMA);
		compDetailsStr.append(license.getFromdate());
		compDetailsStr.append(COMMA);
		compDetailsStr.append(license.getTodate());
		compDetailsStr.append(COMMA);
		compDetailsStr.append(license.getLicensekey());
		compDetailsStr.append(COMMA);
		compDetailsStr.append(comp.getFinStartMonth());
		
		OutputStream os = response.getOutputStream();
		os.write(compDetailsStr.toString().getBytes());
		os.close();
		
		return map.findForward("masteradmin.form");
	
	}
}

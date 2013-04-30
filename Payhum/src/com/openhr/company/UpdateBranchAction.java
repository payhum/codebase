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

public class UpdateBranchAction extends Action {
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
		String branchId   = json.getString("companyId");
		String branchName = json.getString("name");
		String address     = json.getString("address");
		int bid  = Integer.parseInt(branchId.split("-")[1]);
		Branch branch = BranchFactory.findById(bid).get(0);
 		branch.setAddress(address);
 		branch.setName(branchName);
 		BranchFactory.update(branch);
	 
		return null;
	}
}

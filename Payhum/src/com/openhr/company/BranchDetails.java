package com.openhr.company;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Branch;
import com.openhr.factories.BranchFactory;

public class BranchDetails extends Action {
	@SuppressWarnings("unused")
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		BufferedReader bf = request.getReader();
  		JSONArray result = null;
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
 		int companyId = json.getInt("id");
  		List<Branch> branch = BranchFactory.findByCompanyId(companyId);
   		
  		 result = JSONArray.fromObject(branch);
  		 response.setContentType("application/json; charset=utf-8");
         if(result != null) {
         	PrintWriter out = response.getWriter();
         	out.print(result.toString());
         	out.flush();
         }
         
  		
  		
  		
  		
		return null;
	}
}

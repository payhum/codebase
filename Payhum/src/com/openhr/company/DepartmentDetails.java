package com.openhr.company;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.openhr.data.Department;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.DepartmentFactory;

public class DepartmentDetails extends Action {
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
  		List<Department> department = new ArrayList<Department>();
  		for(int i = 0;i < branch.size(); i++){
  			department.addAll(DepartmentFactory.findByBranchId(branch.get(i).getId()));
   		}
 
  		for(int j=0;j<department.size();j++){
   			department.get(j).getBranchId().setAddress(department.get(j).getDeptname());
   		}
  		 
  		 result = JSONArray.fromObject(department);
  		 response.setContentType("application/json; charset=utf-8");
         if(result != null) {
         	PrintWriter out = response.getWriter();
         	out.print(result.toString());
         	out.flush();
         }
         
  		
  		
  		
  		
		return null;
	}
}

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
import com.openhr.data.Department;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.DepartmentFactory;

public class CreateDepartmentAction extends Action {
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
		int branchId   		= json.getInt("branchId");
		String deptName 	= json.getString("name");
 		Branch branch       = BranchFactory.findById(branchId).get(0);
 		
		Department department = new Department();
		department.setDeptname(deptName);
		department.setBranchId(branch);
		DepartmentFactory.insert(department); 
 
		return null;
	}
}

package com.openhr.company;


import java.io.BufferedReader;
import java.util.List;

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

public class DeleteDepartmentAction extends Action {
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
		int did = Integer.parseInt(companyId.split("-")[1]);
   		Department dept = DepartmentFactory.findById(did).get(0);
   		if(dept != null){
   			DepartmentFactory.delete(dept);
   		}
  		return null;
	}
}

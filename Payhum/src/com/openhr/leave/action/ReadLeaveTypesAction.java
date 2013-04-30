package com.openhr.leave.action;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.LeaveType;
import com.openhr.factories.LeaveTypeFactory;
 
public class ReadLeaveTypesAction extends Action {

	@SuppressWarnings("unused")
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray result = null;
		long start = 0, end = 0, diff = 0;
		try {

			List<LeaveType> companies = LeaveTypeFactory.findAll();
			 
			result = JSONArray.fromObject(companies);
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

 		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (result == null) {
			out.print("");
		} else {
			out.print(result.toString());
		}
		out.flush();

		return null;
	}
}
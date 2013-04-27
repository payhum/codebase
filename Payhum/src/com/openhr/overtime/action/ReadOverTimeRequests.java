package com.openhr.overtime.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.factories.OverTimeFactory;

public class ReadOverTimeRequests extends Action {
	@SuppressWarnings("rawtypes")
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
		
		JSONArray result = null;
        try {
        	
        	 List applicationList = OverTimeFactory.findByStatus(0);
             result = JSONArray.fromObject(applicationList);
        } catch (Exception e) {
            e.printStackTrace();
        }         
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result.toString());
        out.flush();
		
		return null;
	}
}

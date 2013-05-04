package com.openhr.leave.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.factories.LeaveRequestFactory;

public class ReadLeaveAction extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception { 
		
		int empId = (Integer) request.getSession().getAttribute("employeeId");
		JSONArray result = null;
        try {
        	List applicationList = LeaveRequestFactory.findByEmployeeId(empId);
            
            JsonConfig config = new JsonConfig();
            config.setIgnoreDefaultExcludes(false);
            config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            result = JSONArray.fromObject(applicationList, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(result != null) {
	        response.setContentType("application/json; charset=utf-8");
	        PrintWriter out = response.getWriter();
	        out.print(result.toString());
	        out.flush();
        }
		
		return null;
	}
}

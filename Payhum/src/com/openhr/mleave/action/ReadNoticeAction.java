package com.openhr.mleave.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.OverTime;
import com.openhr.factories.OverTimeFactory;

public class ReadNoticeAction extends Action {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception { 
 
 		int empId = (Integer) request.getSession().getAttribute("employeeId");
   		JSONArray result = null;
   		List<OverTime> list = new ArrayList<OverTime>();
        try {
        	List<OverTime> applicationList =  OverTimeFactory.findByEmployeeId(empId);
        	
        	for(OverTime l : applicationList){
        		if(l.getStatus() == 1){
        			list.add(l);
        		}
        	}
        	
        	
            result = JSONArray.fromObject(list);
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

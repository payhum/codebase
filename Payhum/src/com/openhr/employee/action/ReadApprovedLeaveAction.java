package com.openhr.employee.action;

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

import com.openhr.data.LeaveApproval;
import com.openhr.data.LeaveRequest;
import com.openhr.factories.LeaveRequestFactory;

public class ReadApprovedLeaveAction extends Action {
	@SuppressWarnings("rawtypes")
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception { 
 
 		int empId = (Integer) request.getSession().getAttribute("employeeId");
		 
		JSONArray result = null;
        try {
        	 List<LeaveRequest> applicationList = LeaveRequestFactory.findByEmployeeId(empId);
        	 List<LeaveApproval> list = new ArrayList<LeaveApproval>();
        	 
        	 for(int i=0;i<applicationList.size();i++){
        		 List<LeaveApproval> appLeaveList = LeaveRequestFactory.findByLeaveId(applicationList.get(i).getId());
        		 if(appLeaveList != null && !appLeaveList.isEmpty()) {
	        		 LeaveApproval l = appLeaveList.get(0);
	        		 if(l != null){
	        			 l.getRequestId().setReturnDate(l.getApprovedbydate());
	        			 list.add(l);
	        		 }
        		 }
        	 }
        	 result = JSONArray.fromObject(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.setContentType("application/json; charset=utf-8");
        if(result != null) {
        	PrintWriter out = response.getWriter();
        	out.print(result.toString());
        	out.flush();
        }
        
 		return null;
	}
}

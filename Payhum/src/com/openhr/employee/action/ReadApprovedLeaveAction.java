package com.openhr.employee.action;

import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.openhr.data.LeaveApproval;
import com.openhr.data.LeaveRequest;
import com.openhr.data.OverTime;
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
        	 System.out.println("Size is - " + applicationList.size());	
        	 
        	 if(applicationList.size() > 10){
         		for(int i=10; i<applicationList.size(); i++){
         			applicationList.remove(i);
         		}
         	}
        	 
        	   List<LeaveRequest> invertedList = new ArrayList<LeaveRequest>();
         	    for (int i = applicationList.size() - 1; i >= 0; i--) {
            	    invertedList.add(applicationList.get(i));
            	}
        	   
        	   List<LeaveRequest> topTen = new ArrayList<LeaveRequest>();

        	   JsonConfig config = new JsonConfig();
	   			config.setIgnoreDefaultExcludes(false);
	   			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
  			
        	    if(invertedList.size() >= 10){
        	    	for(int i=0; i<10; i++){
        	    		topTen.add(invertedList.get(i));
        	    	}
                   result = JSONArray.fromObject(topTen, config);
         	    }
        	    else{
                   result = JSONArray.fromObject(invertedList, config);
        	    }
       	   
        	
         	
        	 
        	 
//        	 for(int i=0;i<applicationList.size();i++){
//        		 List<LeaveApproval> appLeaveList = LeaveRequestFactory.findByLeaveId(applicationList.get(i).getId());
//        		 if(appLeaveList != null && !appLeaveList.isEmpty()) {
//	        		 LeaveApproval l = appLeaveList.get(0);
//	        		 applicationList.get(i).setReturnDate(l.getApprovedbydate());
// 	        		 if(l != null){
//	        			 l.getRequestId().setReturnDate(l.getApprovedbydate());
//	        			 list.add(l);
//	        		 }
//        		 }
//         	 }
        	 
        	 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(result);
        response.setContentType("application/json; charset=utf-8");
        if(result != null) {
        	PrintWriter out = response.getWriter();
        	out.print(result.toString());
        	out.flush();
        }
        
 		return null;
	}
}

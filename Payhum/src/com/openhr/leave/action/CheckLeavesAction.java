package com.openhr.leave.action;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.LeaveTypeFactory;
 
public class CheckLeavesAction extends Action {

	@SuppressWarnings("unused")
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
			JSONArray result = null;
 		    BufferedReader bf = request.getReader();
	        StringBuffer sb = new StringBuffer();
	        String line = null;
	        while ((line = bf.readLine()) != null) {
	            sb.append(line);
	        }
	        JSONObject json = JSONObject.fromObject(sb.toString());
	        
	        int ltypeId = json.getInt("leaveTypeId");
	        int empId   = json.getInt("employeeId");
	        System.out.println(ltypeId+"helooooooooooo"+empId);
	        LeaveType type = LeaveTypeFactory.findById(ltypeId).get(0);
	        List<LeaveRequest> allLeaves = LeaveRequestFactory.findByEmployeeId(empId);
	        List<LeaveRequest> approvedLeaves = new ArrayList<LeaveRequest>();
	        List<LeaveRequest> leaveKind = new ArrayList<LeaveRequest>();
	        if(allLeaves.size() != 0){
	        	for(LeaveRequest leaves : allLeaves){
	        		if(leaves.getStatus() == 1){
	        			approvedLeaves.add(leaves);
	        		}
	        	}
	        }
	        
	        if(approvedLeaves.size() != 0){
	        	for(LeaveRequest leav : approvedLeaves){
	        		if(leav.getLeaveTypeId().getId() == ltypeId){
	        			leaveKind.add(leav);
	        		}
	        	}
	        }
	        
	        double remain = 0;
	        int gap = 0;
	        double totalApp = 0;
	        
	        if(leaveKind.size() != 0){
	        	for(LeaveRequest lea : leaveKind){
	        		totalApp = totalApp + lea.getNoOfDays();
	        		
	        	}
		        remain = (double)leaveKind.get(0).getLeaveTypeId().getDayCap() - totalApp;
  	        }
        	 
 
	        if(remain == 0 ){
	        	remain = (int)type.getDayCap();
	        }
	        
        	int a[] = {10,20};
         	a[0] = (int)remain;
        	a[1] = (int)type.getDayCap();
			result = JSONArray.fromObject(a);

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
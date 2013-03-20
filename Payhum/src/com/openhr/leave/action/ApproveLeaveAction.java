package com.openhr.leave.action;

import java.io.BufferedReader;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Leave;
import com.openhr.data.LeaveApproval;
import com.openhr.factories.LeaveFactory;

public class ApproveLeaveAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) throws Exception {
		BufferedReader bf = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        JSONArray json = JSONArray.fromObject(sb.toString());
        Collection<LeaveApproval> aCollection = JSONArray.toCollection(json, LeaveApproval.class);
        
        
        
       // LeaveApproval lApproval = new LeaveApproval();
        for (LeaveApproval leaveApproval : aCollection) {
        	System.out.println("Approved date " + leaveApproval.getApprovedbydate());
        	LeaveFactory.insert(leaveApproval);
        }
		return null;
	}
}

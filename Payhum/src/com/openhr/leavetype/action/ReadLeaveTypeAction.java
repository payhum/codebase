package com.openhr.leavetype.action;

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

public class ReadLeaveTypeAction extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //LeaveTypeForm leaveTypeForm = (LeaveTypeForm) form;


        JSONArray result = null;
        try {
            List<LeaveType> leaveTypes = LeaveTypeFactory.findAll();
            result = JSONArray.fromObject(leaveTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.print(result.toString());
        
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result.toString());
        out.flush();


        return map.findForward("");
    }
}

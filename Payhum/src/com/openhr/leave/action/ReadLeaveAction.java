package com.openhr.leave.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Leave; 
import com.openhr.factories.LeaveFactory; 

public class ReadLeaveAction extends Action{
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception { 


        JSONArray result = null;
        try {
            List<Leave> leaves = LeaveFactory.findAll();
            System.out.print("Fecthed from db -- " + leaves.size());
            /*String[] excludes = {"employeeId","leaveType"};
            JsonConfig jsonCONFIG =  new JsonConfig();
            jsonCONFIG.setExcludes(excludes);*/
            result = JSONArray.fromObject(leaves);//, jsonCONFIG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.print(result);
        
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result.toString());
        out.flush();


        return map.findForward("");
    }
}

package com.openhr.leavetype.action;

import java.io.BufferedReader;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.LeaveType;
import com.openhr.factories.LeaveTypeFactory;



public class LeaveTypeAction extends Action {
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
        Collection<LeaveType> aCollection = JSONArray.toCollection(json, LeaveType.class);
        LeaveType lt = new LeaveType();
        for (LeaveType ltFromJSON : aCollection) {
            lt.setName(ltFromJSON.getName());
            lt.setDayCap(ltFromJSON.getDayCap()); 
            LeaveTypeFactory.insert(lt);
        }

        return map.findForward("");
    }
}

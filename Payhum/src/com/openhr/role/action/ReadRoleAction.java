package com.openhr.role.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Roles; 
import com.openhr.factories.RolesFactory; 

public class ReadRoleAction extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
//        UserForm userForm = (UserForm) form;


        JSONArray result = null;
        try {
            List<Roles> roles = RolesFactory.findAll();
            System.out.println("Size of the list that contains employees " + roles.size());
            result = JSONArray.fromObject(roles);            
        } catch (Exception e) {
            e.printStackTrace();
        }     
        
        
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result.toString());
        out.flush();

        return map.findForward("");
    }
}

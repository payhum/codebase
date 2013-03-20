package com.openhr.role.action;

import java.io.BufferedReader;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Roles; 
import com.openhr.factories.RolesFactory; 

public class RoleAction extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
       // UserForm userForm = (UserForm) form;


		BufferedReader bf = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        JSONArray json = JSONArray.fromObject(sb.toString());
        
        
        System.out.println("THE JSON " + json.toString());
        
        
        Collection<Roles> aCollection = JSONArray.toCollection(json, Roles.class);
        Roles r = new Roles();
        for (Roles rFromJSON : aCollection) {
            r.setName(rFromJSON.getName());  
            RolesFactory.insert(r);            
        }

        return map.findForward("");
    }
}

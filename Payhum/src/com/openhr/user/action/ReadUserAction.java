package com.openhr.user.action;

import java.io.PrintWriter;
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


import com.openhr.data.Users; 
import com.openhr.factories.UsersFactory;

public class ReadUserAction extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONArray result = null;
        try {
            List<Users> users = UsersFactory.findAll();
            System.out.println("Size of the list that contains employees " + users.size());
            
            JsonConfig config = new JsonConfig();
            config.setIgnoreDefaultExcludes(false);
            config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            
            result = JSONArray.fromObject(users, config);            
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

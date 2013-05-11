/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.position.action;

import com.openhr.data.Position;
import com.openhr.factories.PositionFactory;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Mekbib
 */
public class DeletePositionAction extends Action {

    @Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        
BufferedReader bf = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }

        List<JSONObject> json = JSONArray.fromObject(sb.toString());
        JSONArray n = new JSONArray();
        for (JSONObject obj : json) {
            obj.remove("employeeCollection");
            n.add(obj);
        }

        Collection<Position> aCollection = JSONArray.toCollection(n, Position.class);

        System.out.println(" Size of List for Update " + aCollection.size());


        Position p = new Position();
        for (Position pFromJSON : aCollection) {
            p.setId(pFromJSON.getId());
            try{
            	
            PositionFactory.delete(p);
            
            
            }catch(Exception e){
            	e.printStackTrace();
            	response.setStatus(500);
            	System.out.println("CAUGHT AN EXCEPTION...");
            	PrintWriter out = response.getWriter();
            	response.setContentType("text");
            	out.write("Cannot be deleted!");
            	out.flush();            	
            }
        }
        response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
			out.print("Sravabnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		
		out.flush();
        return map.findForward(null);
    }
}

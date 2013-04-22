/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.position.action;

import com.openhr.data.Position;
import com.openhr.factories.PositionFactory;
import com.openhr.position.form.PositionForm;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Mekbib
 */
public class ReadPositionAction extends Action {

    @Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray result = null;
        try {
            List<Position> positions = PositionFactory.findAll();
            result = JSONArray.fromObject(positions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(result != null) {
	        response.setContentType("application/json; charset=utf-8");
	        PrintWriter out = response.getWriter();
	        out.print(result.toString());
	        out.flush();
        }

        return map.findForward("");
    }
}

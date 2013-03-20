/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.position.action;

import com.openhr.data.Position;
import com.openhr.factories.PositionFactory;
import java.io.BufferedReader;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Mekbib
 */
public class PositionAction extends Action {

    @Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) throws Exception {
        //PositionForm positionForm = (PositionForm) form;
        //JSONSerializer.toJava(json);
        BufferedReader bf = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        JSONArray json = JSONArray.fromObject(sb.toString());
        Collection<Position> aCollection = JSONArray.toCollection(json, Position.class);
        Position p = new Position();
        for (Position pFromJSON : aCollection) {
            p.setName(pFromJSON.getName());
            p.setSalary(pFromJSON.getSalary());
            p.setRaisePerYear(pFromJSON.getRaisePerYear());
            PositionFactory.insert(p);
        }

        return map.findForward("");
    }
}

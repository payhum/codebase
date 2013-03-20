package com.openhr.benefit.action;

import java.io.BufferedReader;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Benefit;
import com.openhr.factories.BenefitFactory;

public class DeleteBenefitAction extends Action {
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
        Collection<Benefit> aCollection = JSONArray.toCollection(json, Benefit.class);
        Benefit bt = new Benefit();
        for (Benefit btFromJSON : aCollection) {
            bt.setId(btFromJSON.getId()); 
            BenefitFactory.delete(bt);
        }

        return map.findForward("");
    }
}

package com.openhr.deduction.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.DeductionsType;
import com.openhr.data.Position;
import com.openhr.factories.DeductionFactory;
import com.openhr.factories.PositionFactory;

public class ReadDeductionAction extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) throws Exception {
		
		
		 JSONArray result = null;
	        try {
	        	List<DeductionsType> dedc = DeductionFactory.findAllToDisplay();
	            result = JSONArray.fromObject(dedc);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        System.out.print(result.toString());
	        
	        reponse.setContentType("application/json; charset=utf-8");
	        PrintWriter out = reponse.getWriter();
	        out.print(result.toString());
	        out.flush();


	        return map.findForward("");
	}
}

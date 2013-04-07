package com.openhr.payroll.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.EmpPayTax;
import com.openhr.data.Position;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.PositionFactory;


public class ReadPayDetailsAction extends Action{
	
	

	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
       


        JSONArray result = null;
        try {
            List<EmpPayTax> eptx= EmpPayTaxFactroy.findAll();
            result = JSONArray.fromObject(eptx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.print(result.toString());
        
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result.toString());
        out.flush();


        return map.findForward("");
    }
}
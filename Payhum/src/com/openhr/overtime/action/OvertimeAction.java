package com.openhr.overtime.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.DeductionsType;
import com.openhr.data.OverTimePayRateData;
import com.openhr.factories.DeductionFactory;
import com.openhr.factories.EmpPayTaxFactroy;

public class OvertimeAction extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) throws Exception {
		boolean flag=false;
		JSONArray result = null;
		BufferedReader bf = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        JSONArray json = JSONArray.fromObject(sb.toString());
        
        
      //  System.out.println("THE JSON " + json.toString());
        
        
        Collection<OverTimePayRateData> aCollection = JSONArray.toCollection(json, OverTimePayRateData.class);
        OverTimePayRateData ovt = new OverTimePayRateData();
        for (OverTimePayRateData rFromJSON : aCollection) {
        	 
        	ovt.setId(rFromJSON.getId());
        	List<OverTimePayRateData> ovr=EmpPayTaxFactroy.getOverTimeRate(ovt);   
        	 result = JSONArray.fromObject(ovr);
        }
        System.out.print(result.toString());
        
        reponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = reponse.getWriter();
        out.print(result.toString());
        out.flush();
        return map.findForward("");
	}
}

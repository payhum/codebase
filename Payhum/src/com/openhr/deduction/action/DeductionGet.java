package com.openhr.deduction.action;

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
import com.openhr.factories.DeductionFactory;

public class DeductionGet extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) throws Exception {
		JSONArray result = null;
		List<DeductionsType> dedList=null;
		BufferedReader bf = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        JSONArray json = JSONArray.fromObject(sb.toString());
        
        
        System.out.println("THE JSON " + json.toString());
        
        StringBuilder s=new StringBuilder("hello");
        Collection<DeductionsType> aCollection = JSONArray.toCollection(json, DeductionsType.class);
        DeductionsType dtyp = new DeductionsType();
        for (DeductionsType rFromJSON : aCollection) {
        	dtyp.setName(rFromJSON.getName());  
        	dtyp.setDescription(rFromJSON.getDescription());  
        	 dedList =DeductionFactory.findById(rFromJSON.getId());     
        	 result = JSONArray.fromObject(dedList);
        
        }
        PrintWriter out = reponse.getWriter();
out.println(result.toString());
        return map.findForward("");
        
	}
}

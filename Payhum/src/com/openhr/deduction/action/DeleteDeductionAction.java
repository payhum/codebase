package com.openhr.deduction.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.DeductionsType;
import com.openhr.employee.form.EmployeeForm;
import com.openhr.factories.DeductionFactory;

public class DeleteDeductionAction extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse reponse) throws Exception {
		Boolean flag=false;
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		
		JSONArray json = JSONArray.fromObject(sb.toString());
		Collection<DeductionsType> aCollection = JSONArray.toCollection(json, DeductionsType.class);
		  DeductionsType dtyp = new DeductionsType();
		  for (DeductionsType rFromJSON : aCollection) {
	        	dtyp.setName(rFromJSON.getName());  
	        	dtyp.setDescription(rFromJSON.getDescription()); 
	        	
	        	dtyp.setId(rFromJSON.getId());
	        	 flag =DeductionFactory.delete(dtyp);     
	        	// result = JSONArray.fromObject(dedList);
	        
	        }
		  PrintWriter out = reponse.getWriter();
		  out.println(flag);
	        return map.findForward("");
	}
}

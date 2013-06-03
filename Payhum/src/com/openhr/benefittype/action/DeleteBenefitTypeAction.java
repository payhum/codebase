package com.openhr.benefittype.action;

import java.io.BufferedReader;
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

import com.openhr.data.Benefit;
import com.openhr.data.BenefitType;
import com.openhr.factories.BenefitFactory;
import com.openhr.factories.BenefitTypeFactory;

public class DeleteBenefitTypeAction extends Action {
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
        Collection<BenefitType> aCollection = JSONArray.toCollection(json, BenefitType.class);
        BenefitType bt = new BenefitType();
        for (BenefitType btFromJSON : aCollection) {
            bt.setId(btFromJSON.getId()); 
            
            List<Benefit> bList = BenefitFactory.findByTypeId(bt);
            
            if(bList != null && !bList.isEmpty()) {
            	JSONObject jsonObject = new JSONObject();

    			jsonObject.put("sss", "dddd");

    			// response.setContentType("text");
    			reponse.setContentType("application/json; charset=utf-8");
    			PrintWriter out = reponse.getWriter();

    			out.write(jsonObject.toString());
    			out.flush();
            } else {
            	BenefitTypeFactory.delete(bt);
            	
            	PrintWriter out = reponse.getWriter();
				reponse.setContentType("text");
				out.write("Deleted!");
				out.flush();
            }
        }

        return map.findForward("");
    }
}

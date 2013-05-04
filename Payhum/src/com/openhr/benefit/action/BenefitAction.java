package com.openhr.benefit.action;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Benefit;
import com.openhr.data.BenefitType;
import com.openhr.data.Employee;
import com.openhr.factories.BenefitFactory;
import com.openhr.factories.BenefitTypeFactory;
import com.openhr.factories.EmployeeFactory;

public class BenefitAction extends Action{
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
        JSONObject json = JSONObject.fromObject(sb.toString());
        Employee emp = EmployeeFactory.findByEmployeeId(json.getString("empId")).get(0);
        BenefitType benfitType = BenefitTypeFactory.findById(json.getInt("benfitType")).get(0);
        Benefit bt = new Benefit();
        bt.setAmount(json.getDouble("amount"));
        bt.setTypeId(benfitType); 
        bt.setEmployeeId(emp); 
        BenefitFactory.insert(bt);
 
        return map.findForward("");
    }
}

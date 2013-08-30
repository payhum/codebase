package com.openhr.company;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.common.PayhumConstants;
import com.openhr.data.ConfigData;
import com.openhr.factories.ConfigDataFactory;

public class ProcessCompanyAction extends Action  {
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
		int branchId   		= json.getInt("branchId");
		
		ConfigData config1 = ConfigDataFactory.findByName(PayhumConstants.PROCESS_BRANCH);
		config1.setConfigValue(Integer.toString(branchId));
 		ConfigDataFactory.update(config1);
 		
		return null;
	}
}

package com.openhr.overtime.action;

import java.io.BufferedReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.OverTime;
import com.openhr.factories.OverTimeFactory;

public class DeleteOvertimeAction extends Action {
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
 		
		int employeeId   	 = json.getInt("employeeId");
		String requestOnDate = json.getString("requestOnDate");
  		List<OverTime> overTimeList = OverTimeFactory.findByEmployeeId(employeeId);
  		OverTime overTimes = null;
  		for(OverTime overTime : overTimeList){
 			Date date = new Date(overTime.getOverTimeDate());
		    Format format = new SimpleDateFormat("MMM, dd yyyy");
		    String d = format.format(date).toString();
 			if(d.equals(requestOnDate)){
 				overTimes = overTime;
				break;
			}
 		}
   		
   		OverTimeFactory.delete(overTimes);
		return null;
		
		
		
 	}
}

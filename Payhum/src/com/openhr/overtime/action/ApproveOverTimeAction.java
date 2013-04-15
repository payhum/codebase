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

import com.openhr.data.Employee;
import com.openhr.data.OverTime;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.OverTimeFactory;

public class ApproveOverTimeAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		
		
 		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
		String approverId = json.getString("approverId");
		
		List<OverTime> list = OverTimeFactory.findByEmployeeId(
				json.getInt("employeeId"));
		OverTime overTime = null;
 		String str_date =  json.getString("requestedDate");
 		for(OverTime l : list){
			Date date = new Date(l.getOverTimeDate());
		    Format format = new SimpleDateFormat("MMM, dd yyyy");
		    String d = format.format(date).toString();
 			if(d.equals(str_date)){
 				overTime = l;
				break;
			}
		}
 		
  		Employee approveEmployee = EmployeeFactory.findById(Integer.parseInt(approverId)).get(0);
 		overTime.setStatus(1);
 		overTime.setApprovedBy(approveEmployee.getEmployeeId());
 		overTime.setApprovedDate(new Date());
		OverTimeFactory.update(overTime);
 		return null;
	}
}

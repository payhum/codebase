package com.openhr;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.icu.util.Holiday;
import com.openhr.data.Holidays;
import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.factories.HolidaysFactory;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.LeaveTypeFactory;
 
public class CheckHolidayAction extends Action {

	@SuppressWarnings("unused")
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
			JSONArray result = null;
 		    BufferedReader bf = request.getReader();
	        StringBuffer sb = new StringBuffer();
	        String line = null;
	        while ((line = bf.readLine()) != null) {
	            sb.append(line);
	        }
	        JSONObject json = JSONObject.fromObject(sb.toString());
	        
	        String holidayDate = json.getString("holidayDate");
	        int a[] = {10,20};
	        List<Holidays> holidays = HolidaysFactory.findByDate(new Date(holidayDate));
	        if(holidays.size() != 0){
	        	a[0] = 0;
	        }
	        
	        
			result = JSONArray.fromObject(a);

        	response.setContentType("application/json; charset=utf-8");
    		PrintWriter out = response.getWriter();
    		if (result == null) {
    			out.print("");
    		} else {
    			out.print(result.toString());
    		}
    		out.flush();
        	
        	
        	
		return null;
	}
	
	 
	
}
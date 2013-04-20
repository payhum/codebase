package com.openhr;

import java.io.BufferedReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Holidays;
import com.openhr.factories.HolidaysFactory;

public class AddHolidayAction extends Action {
	@SuppressWarnings("deprecation")
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
 		String holidayDate    = json.getString("holidayDate");
		String holidayName    = json.getString("holidayName");	
  		
		Holidays holiday = new Holidays();
		holiday.setDate(new Date(holidayDate));
		holiday.setName(holidayName);
		HolidaysFactory.insert(holiday);	 
		return null;
	}
}

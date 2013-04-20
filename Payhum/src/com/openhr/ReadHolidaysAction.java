package com.openhr;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.factories.HolidaysFactory;

public class ReadHolidaysAction extends Action {
	@SuppressWarnings("deprecation")
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
 		JSONArray result = null;
        try {
        	@SuppressWarnings("rawtypes")
			List holidayList = HolidaysFactory.findAll();
            result = JSONArray.fromObject(holidayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result.toString());
        out.flush();
 		return null;
	}
}

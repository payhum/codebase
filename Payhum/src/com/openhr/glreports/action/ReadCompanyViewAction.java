package com.openhr.glreports.action;

import java.io.PrintWriter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.GLEmployee;
import com.openhr.factories.GLEmployeeFactory;
import com.openhr.glreports.form.GlReportForm;

public class ReadCompanyViewAction extends Action{
	static Double sumc=0.0;
	static Double sumd=0.0;
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// EmployeeForm EmployeeForm = (EmployeeForm) form;
		List<GlReportForm> glemployees1=new LinkedList<GlReportForm>();
		
		JSONArray result = null;
		long start=0,end=0,diff=0;
		try {
			
			List<GLEmployee> glemployees = GLEmployeeFactory. findCompanyView();
			start=System.currentTimeMillis();
		
			Iterator iterator=glemployees.iterator();
			System.out.println("Designation\tTotal Salary");
			while(iterator.hasNext()){
				GlReportForm gl=new GlReportForm();
				Object []obj = (Object[])iterator.next();
				//Integer it=(Integer) obj[1];
				
			 sumc=sumd+(Double)obj[5];
				 sumd=sumd+(Double)obj[4];
				 
				gl.setAccno((Integer) obj[1]);
				gl.setAccname((String)obj[2]);
				gl.setSumcredit((Double)obj[5]);
				gl.setSumdebit((Double)obj[4]);
				java.sql.Timestamp d=(java.sql.Timestamp)obj[3];
				gl.setDate((Long)d.getTime());
				glemployees1.add(gl);
			
			}
			GlReportForm gl=new GlReportForm();
			gl.setAccname("TOTAL");
			gl.setSumcredit(sumc);
			gl.setSumdebit(sumd);
			glemployees1.add(gl);
			result = JSONArray.fromObject(glemployees1);
			end=System.currentTimeMillis();
			diff = end - start;
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("It took " + diff +" milli seconds to read the results");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}


}

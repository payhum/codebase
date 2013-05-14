package com.openhr.mleave.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.LeaveRequest;
import com.openhr.data.OverTime;
import com.openhr.factories.OverTimeFactory;

public class ReadNoticeAction extends Action {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception { 
 
 		int empId = (Integer) request.getSession().getAttribute("employeeId");
   		JSONArray result = null;
   		List<OverTime> list = new ArrayList<OverTime>();
        try {
        	List<OverTime> applicationList =  OverTimeFactory.findByEmployeeId(empId);
        	
        	/*for(OverTime l : applicationList){
        		if(l.getStatus() == 1){
        			list.add(l);
        		}
        	}*/
        	
        	System.out.println("size......."+applicationList.size());
        	
        	if(applicationList.size() > 10){
        		for(int i=10; i<applicationList.size(); i++){
        			applicationList.remove(i);
        		}
        	}
        	
        	
         	    List<OverTime> invertedList = new ArrayList<OverTime>();
          	    for (int i = applicationList.size() - 1; i >= 0; i--) {
             	    invertedList.add(applicationList.get(i));
             	}
         	   
         	   List<OverTime> topTen = new ArrayList<OverTime>();

         	   JsonConfig config = new JsonConfig();
	   			config.setIgnoreDefaultExcludes(false);
	   			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
   			
         	    if(invertedList.size() >= 10){
         	    	for(int i=0; i<10; i++){
         	    		topTen.add(invertedList.get(i));
         	    	}
                    result = JSONArray.fromObject(topTen, config);
          	    }
         	    else{
                    result = JSONArray.fromObject(invertedList, config);
         	    }
        	   
         	
        	
        	
        	System.out.println("size......."+applicationList.size());
        	
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(result != null) {
	        response.setContentType("application/json; charset=utf-8");
	        PrintWriter out = response.getWriter();
	        out.print(result.toString());
	        out.flush();
        }
 		return null;
	}
}

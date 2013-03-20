package com.openhr.employee.action;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping; 

import com.openhr.employee.PhotoCropDimension;
import com.openhr.employee.PhotoDimension;
import com.openhr.employee.form.PhotoCropForm;

public class PhotoCropAction extends Action{
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		
		BufferedReader bf = null;
		bf = request.getReader();   
		 
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		
		JSONObject json = JSONObject.fromObject(sb.toString());
		
		System.out.println("The Photo Crop JSON : "+json);
		PhotoDimension pcd = (PhotoDimension)JSONObject.toBean(json, PhotoDimension.class);
		PhotoCropDimension.x = (int)pcd.getX();
		PhotoCropDimension.y = (int)pcd.getY();
		PhotoCropDimension.width = pcd.getWidth();
		PhotoCropDimension.height= pcd.getHeight();
		PhotoCropDimension.employeeId = pcd.getEmployeeId();
	    return map.findForward("");
	}
}

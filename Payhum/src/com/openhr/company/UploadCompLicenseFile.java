package com.openhr.company;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Branch;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.LicenseFactory;

public class UploadCompLicenseFile extends Action {
	private static final String UPLOAD_DIRECTORY = "upload";
    private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	private static final String COMMA = ",";
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		// checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return map.findForward("HRHome");
        }
        
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
         
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(MAX_REQUEST_SIZE);
         
        // constructs the directory path to store upload file
        String uploadPath = UPLOAD_DIRECTORY;
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
         
        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
             
            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    
                    // saves the file on disk
                    item.write(storeFile);
                    
                    // Read the file object contents and parse it and store it in the repos
                    FileInputStream fstream = new FileInputStream(storeFile);
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    
                    //Read File Line By Line
                    while ((strLine = br.readLine()) != null)   {
                    	System.out.print("Processing line - " + strLine);
                    	
                    	String[] lineColumns = strLine.split(COMMA);
                    	
                    	if(lineColumns.length < 7) {
                    		br.close();
                    		in.close();
                    		fstream.close();
                    		throw new Exception("The required columns are missing in the line - " + strLine);
                    	}
                    	
                    	// Format is - CompID,CompName,Branch,Address,From,To,LicenseKey
                    	String companyId   = lineColumns[0];
                		String companyName = lineColumns[1];
                		String branchName  = lineColumns[2];
                		String address     = lineColumns[3];
                		String fromDateStr = lineColumns[4];
                		String toDateStr   = lineColumns[5];
                		String licenseKey  = lineColumns[6];
                		
                		Date fromDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH).parse(fromDateStr);
                		Date toDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH).parse(toDateStr);
                		
                		List<Company> eComp = CompanyFactory.findByName(companyName);
                		if(eComp == null || eComp.isEmpty()) {                		
	                		Company company = new Company();
	                		company.setCompanyId(companyId);
	                 		company.setName(companyName);
	                		
	                		Branch branch = new Branch();
	                		branch.setAddress(address);
	                		branch.setCompanyId(company);
	                		branch.setName(branchName);
	                		
	                		Licenses license = new Licenses();
	                		license.setActive(1);
	                		license.setCompanyId(company);
	                		license.setFromdate(fromDate);
	                		license.setTodate(toDate);
	                 		license.formLicenseKey();
	                 		
	                 		if(license.getLicensekey().equalsIgnoreCase(licenseKey)) {
	                 			CompanyFactory.insert(company);
	                 			BranchFactory.insert(branch);
		                		LicenseFactory.insert(license);	
	                 		} else {
	                 			br.close();
	                 			in.close();
	                     		fstream.close();
	                     		
	                 			throw new Exception("License is tampared. Contact Support.");
	                 		}
                		} else {
                			// Company is present, so update it.
                			Company company = eComp.get(0);
                			List<Licenses> licenses = LicenseFactory.findByCompanyId(company.getId());
                			
                			Licenses newLicense = new Licenses();
                			newLicense.setActive(1);
                			newLicense.setCompanyId(company);
                			newLicense.setFromdate(fromDate);
                			newLicense.setTodate(toDate);
                			newLicense.formLicenseKey();
                			
                			if(newLicense.getLicensekey().equalsIgnoreCase(licenseKey)) {
                				for(Licenses lic: licenses) {
                    				if(lic.getActive().compareTo(1) == 0) {
                    					lic.setActive(0);
                    					LicenseFactory.update(lic);
                    				}
                    			}
                    			
	                 			LicenseFactory.insert(newLicense);	
	                 		} else {
	                 			br.close();
	                 			in.close();
	                     		fstream.close();
	                     		
	                 			throw new Exception("License is tampared. Contact Support.");
	                 		}
                		}
                    }
                    
                    //Close the input stream
                    br.close();
        			in.close();
            		fstream.close();
                }
            }
            System.out.println("Upload has been done successfully!");
        } catch (Exception ex) {
        	System.out.println("There was an error: " + ex.getMessage());
        	ex.printStackTrace();
        }
        
		return map.findForward("CompLicHome");
	}
}

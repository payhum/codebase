package com.openhr;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.company.Company;
import com.openhr.company.CompanyPayroll;
import com.openhr.company.LicenseValidator;
import com.openhr.company.Licenses;
import com.openhr.data.Branch;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.CompanyPayrollFactory;
import com.openhr.factories.LicenseFactory;

public class UploadFile extends Action {
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
            return map.findForward("masteradmin");
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
                    Company comp = null;
                    Branch branch = null;
                    Calendar currDtCal = Calendar.getInstance();

            	    // Zero out the hour, minute, second, and millisecond
            	    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
            	    currDtCal.set(Calendar.MINUTE, 0);
            	    currDtCal.set(Calendar.SECOND, 0);
            	    currDtCal.set(Calendar.MILLISECOND, 0);

            	    Date now = currDtCal.getTime();
            	    
                    //Read File Line By Line
                    while ((strLine = br.readLine()) != null)   {
                    	System.out.print("Processing line - " + strLine);
                    	
                    	String[] lineColumns = strLine.split(COMMA);
                    	
                    	if(lineColumns.length < 13) {
                    		br.close();
                    		in.close();
                    		fstream.close();
                    		throw new Exception("The required columns are missing in the line - " + strLine);
                    	}
                    	
                    	// Format is - 
                    	// CompID,BranchName,EmpID,EmpFullName,EmpNationalID,BankName,BankBranch,RoutingNo,AccountNo,NetPay,Currency,
                    	// TaxAmount,EmployerSS,EmployeeSS
                    	if(comp == null || comp.getId() != Integer.parseInt(lineColumns[0])) {
                    		List<Company> comps = CompanyFactory.findById(Integer.parseInt(lineColumns[0]));
                    		
                    		if(comps != null && comps.size() > 0) {
                    			comp = comps.get(0);
                    		}
                    		else {
                    			br.close();
                    			in.close();
                        		fstream.close();
                        		
                    			throw new Exception("Unable to get the details of the company");
                    		}
                    		
                    		// Check for licenses
                    		List<Licenses> compLicenses = LicenseFactory.findByCompanyId(comp.getId());
                    		for(Licenses lis : compLicenses) {
                    			if(lis.getActive() == 1) {
                    				Date endDate = lis.getTodate();
                    				if( ! isLicenseActive(now, endDate)) {
                    					br.close();
                            			in.close();
                                		fstream.close();
                                		
                    					// License has expired and throw an error
                    					throw new Exception("License has expired");
                    					
                    					//TODO remove the below code and enable above
                    					/*List<Branch> branches = BranchFactory.findByCompanyId(comp.getId());
                						String branchName = lineColumns[1];
                						if(branches != null && !branches.isEmpty()) {
                							for(Branch bb: branches) {
                								if(branchName.equalsIgnoreCase(bb.getName())) {
                									branch = bb;
                									break;
                								}
                							}
                							
                							if(branch == null) {
                								Branch bb = new Branch();
                                				bb.setName(branchName);
                                				bb.setAddress("NA");
                                				bb.setCompanyId(comp);
                                				
                                				BranchFactory.insert(bb);
                                				
                                				List<Branch> lbranches = BranchFactory.findByName(branchName);
                                				branch = lbranches.get(0);
                							}
                						}*/
                    					//TODO
                    				} else {
                    					// License enddate is valid, so lets check the key.
                    					String compName = comp.getName();
                    					String licenseKeyStr = LicenseValidator.formStringToEncrypt(compName, endDate);
                    					if(LicenseValidator.encryptAndCompare(licenseKeyStr, lis.getLicensekey())) {
                    						// License key is valid, so proceed.
                    						List<Branch> branches = BranchFactory.findByCompanyId(comp.getId());
                    						String branchName = lineColumns[1];
                    						if(branches != null && !branches.isEmpty()) {
                    							for(Branch bb: branches) {
                    								if(branchName.equalsIgnoreCase(bb.getName())) {
                    									branch = bb;
                    									break;
                    								}
                    							}
                    							
                    							if(branch == null) {
                    								Branch bb = new Branch();
                                    				bb.setName(branchName);
                                    				bb.setAddress("NA");
                                    				bb.setCompanyId(comp);
                                    				
                                    				BranchFactory.insert(bb);
                                    				
                                    				List<Branch> lbranches = BranchFactory.findByName(branchName);
                                    				branch = lbranches.get(0);
                    							}
                    						}
                    						break;
                    					} else {
                    						br.close();
                                			in.close();
                                    		fstream.close();
                                    		
                    						throw new Exception("License is tampered. Contact Support.");
                    					}
                    				}
                    			}
                    		}
                    	}

                    	// CompID,BranchName,EmpID,EmpFullName,EmpNationalID,DeptName,BankName,BankBranch,RoutingNo,AccountNo,NetPay,currency,TaxAmt,emprSS,empess                    	
                    	CompanyPayroll compPayroll = new CompanyPayroll();
                    	compPayroll.setBranchId(branch);
                    	compPayroll.setEmployeeId(lineColumns[2]);
                    	compPayroll.setEmpFullName(lineColumns[3]);
                    	compPayroll.setEmpNationalID(lineColumns[4]);
                    	compPayroll.setDeptName(lineColumns[5]);
                    	compPayroll.setBankName(lineColumns[6]);
                    	compPayroll.setBankBranch(lineColumns[7]);
                    	compPayroll.setRoutingNo(lineColumns[8]);
                    	compPayroll.setAccountNo(lineColumns[9]);
                    	compPayroll.setNetPay(Double.parseDouble(lineColumns[10]));
                    	compPayroll.setCurrencySym(lineColumns[11]);
                    	compPayroll.setTaxAmount(Double.parseDouble(lineColumns[12]));
                    	compPayroll.setEmprSocialSec(Double.parseDouble(lineColumns[13]));
                    	compPayroll.setEmpeSocialSec(Double.parseDouble(lineColumns[14]));
                    	compPayroll.setProcessedDate(now);
                    	CompanyPayrollFactory.insert(compPayroll);
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
        
		return map.findForward("MasterHome");
	}
	

	private boolean isLicenseActive(Date currentDate, Date endDate) {
	    if (currentDate.after(endDate)) {
	    	// License has expired
	    	return false;
	    }
	    
	    return true;
	}
}
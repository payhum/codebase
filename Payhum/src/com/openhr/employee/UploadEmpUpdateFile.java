package com.openhr.employee;

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

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.data.Benefit;
import com.openhr.data.BenefitType;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.data.Department;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeBonus;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.LeaveApproval;
import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.factories.BenefitFactory;
import com.openhr.factories.BenefitTypeFactory;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.DepartmentFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.LeaveTypeFactory;
import com.util.payhumpackages.PayhumUtil;

public class UploadEmpUpdateFile extends Action {
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
        
        int remainingMonths = 12;
        Date currDt = new Date();
        Calendar currDate = Calendar.getInstance();
        currDate.setTime(currDt);
        
        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            
            List<LeaveType> leaveTypes = LeaveTypeFactory.findByName(PayhumConstants.PAID_LEAVE);
            List<LeaveRequest> allLeaveRequests = LeaveRequestFactory.findAll();
            int leaveRequestID = allLeaveRequests.size() + 1;
            
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

                    boolean firstLineIgnored = false;
                    
                    Company comp = null;
                    List<Employee> compEmps = null;
                    
                    //Read File Line By Line
                    while ((strLine = br.readLine()) != null)   {
                    	System.out.println("Processing line - " + strLine);
                    	if(!firstLineIgnored) {
                    		firstLineIgnored = true;
                    		continue;
                    	}
                    	
                    	String[] lineColumns = strLine.split(COMMA);
                    	
                    	if(lineColumns.length != 11) {
                    		br.close();
                    		in.close();
                    		fstream.close();
                    		throw new Exception("The number of columns in the line is not 11 - " + strLine);
                    	}
                    	
                    	// Format is - EmployeeID, Allowance, BaseSalary, OT, Commission, Bonus, Branch, Dept,unpaidleave,prepaidAccom
                    	String employeeId = lineColumns[0];
                		String allowance = lineColumns[1];
                		String baseSalary = lineColumns[2];
                		String overtimeAmt = lineColumns[3];
                		String otherIncome = lineColumns[4];
                		String bonusAmt = lineColumns[5];
                		String company = lineColumns[6];
                		String branchName = lineColumns[7];
                		String deptName = lineColumns[8];
                		String unpaidLeave = lineColumns[9];
                		String prepaidAccom = lineColumns[10];
                		
                		//Validate the Emp existence
                		if(comp == null || !comp.getName().equalsIgnoreCase(company)) {
	                		comp = getComp(company);
	                		compEmps = EmployeeFactory.findAllEmpByComp(comp.getId());
	                		remainingMonths = PayhumUtil.remainingMonths(currDate, comp.getFinStartMonth());
                		}
                		
                		boolean empPresent = false;
                		Employee employee = null;
                		for(Employee emp : compEmps) {
                			if(emp.getEmployeeId().equalsIgnoreCase(employeeId)) {
                				empPresent = true;
                				employee = emp; 
                				break;
                			} 
                		}
                		
                		if(!empPresent) {
                			br.close();
                    		in.close();
                    		fstream.close();
                			throw new RuntimeException("Employee with ID - " + employeeId + " is not present in the system.");
                		}
                		
                		Department dept = getDept(comp, branchName, deptName);
                		if(dept != null) {
                			employee.setDeptId(dept);
                			EmployeeFactory.update(employee);
                		}
                		
                		// Process allowance
                		if(!allowance.equalsIgnoreCase("NC")) {
                			List<Benefit> benefits = BenefitFactory.findByEmpId(employee);
                			if(benefits != null && !benefits.isEmpty()) {
                				Benefit ben = benefits.get(0);
                				Double existingAmt = ben.getAmount();
                				Double existingAmtPerMonth = ben.getPerMonthAmt();
                				
                				Double newPerMonth = Double.parseDouble(allowance);
                				Double diffPerMonth = 0D;
                				if(newPerMonth > existingAmtPerMonth) {
                					diffPerMonth = newPerMonth - existingAmtPerMonth;
                					ben.setAmount(existingAmt + diffPerMonth * remainingMonths);
                					ben.setPerMonthAmt(newPerMonth);
                				} else {
                					diffPerMonth = existingAmtPerMonth - newPerMonth;
                					ben.setAmount(existingAmt - diffPerMonth * remainingMonths);
                					ben.setPerMonthAmt(newPerMonth);
                				}
                				
                				BenefitFactory.update(ben);
                			} else {
                				List<BenefitType> benefitTypes = BenefitTypeFactory.findAll();
                        		BenefitType bt = benefitTypes.get(0);
                        		Benefit ben = new Benefit();
                        		ben.setAmount(Double.parseDouble(allowance) * remainingMonths);
                        		ben.setPerMonthAmt(Double.parseDouble(allowance));
                        		ben.setEmployeeId(employee);
                        		ben.setTypeId(bt);
                        		
                        		BenefitFactory.insert(ben);
                			}
                		}
                		
                		// Process Basesalary
                		if(!baseSalary.equalsIgnoreCase("NC")) {
                			List<EmployeeSalary> empSals = EmpPayTaxFactroy.findEmpSalary(employee);
                			EmployeeSalary latestSal = null;
                			for(EmployeeSalary empS : empSals) {
                				Date effectiveDate = empS.getTodate();
                				Date fromDate = empS.getFromdate();
                				
                				if(effectiveDate.compareTo(fromDate) == 0) {
                					// this is latest one
                					latestSal = empS;
                					break;
                				}
                			}
                			
                			if(latestSal != null) {
                				latestSal.setTodate(currDt);
                				EmployeeFactory.empsalUpdate(latestSal);
                				
                				EmployeeSalary empSal = new EmployeeSalary();
                        		empSal.setBasesalary(Double.parseDouble(baseSalary) * 12);
                        		empSal.setEmployeeId(employee);
                        		empSal.setFromdate(currDt);
                        		empSal.setTodate(currDt);
                        		EmpPayTaxFactroy.insertEmpSal(empSal);
                			}
                		}
                		
                		//process bonus
                		if(! bonusAmt.equalsIgnoreCase("NC")) {
	                		EmployeeBonus empB = new EmployeeBonus();
	                		empB.setAmount(Double.parseDouble(bonusAmt));
	                		empB.setEmployeeId(employee);
	                		empB.setGivendate(currDt);
	                		EmpPayTaxFactroy.insertBonus(empB);
                		}
                		
                		EmployeePayroll empPayroll = EmpPayTaxFactroy.findEmpPayrollbyEmpID(employee);
                		if(!overtimeAmt.equalsIgnoreCase("NC")) {
                			empPayroll.setNewOvertimeAmt(Double.parseDouble(overtimeAmt));
                		}
                		
                		if(!otherIncome.equalsIgnoreCase("NC")) {
                			empPayroll.setNewOtherIncome(Double.parseDouble(otherIncome));
                		}

                		// Prepaid accom
                		if(!prepaidAccom.equalsIgnoreCase("NC")) {
                			if(prepaidAccom.equalsIgnoreCase("true")
                			|| prepaidAccom.equalsIgnoreCase("yes")) {
                				empPayroll.setPayAccomAmt(0);
                			} else {
                				empPayroll.setPayAccomAmt(1);
                			}
                		}
                		
                		EmpPayTaxFactroy.update(empPayroll);
                		
                		// Update Leave details
                		if(unpaidLeave != null && !unpaidLeave.isEmpty()) {
                			double unpaidLeaveInt = Double.parseDouble(unpaidLeave);
                			if(unpaidLeaveInt > 0) {
	                			LeaveRequest lrq = new LeaveRequest();
	                			lrq.setId(leaveRequestID++);
	                			lrq.setEmployeeId(employee);
	                			lrq.setDescription("For month/year - " + currDate.get(Calendar.MONTH) + 1 + "/" + currDate.get(Calendar.YEAR));
	                			lrq.setLeaveDate(currDt);
	                			lrq.setLeaveTypeId(leaveTypes.get(0));
	                			lrq.setNoOfDays(unpaidLeaveInt);
	                			lrq.setReturnDate(currDt);
	                			lrq.setStatus(1);
	                			
	                			LeaveRequestFactory.insert(lrq);
	                			
	                			LeaveApproval lar = new LeaveApproval();
	                			lar.setApprovedbydate(currDt);
	                			lar.setApproverId(employee);
	                			lar.setRequestId(lrq);
	                			LeaveRequestFactory.insert(lar);
                			}
                		}
                    }
                }
            }
        }
        catch (Exception ex) {
        	System.out.println("There was an error: " + ex.getMessage());
        	ex.printStackTrace();
        }
        
		return map.findForward("HRHome");
	}
	
	private Company getComp(String compName) 
		throws Exception {
		Company comp = null;
		
		if(compName.isEmpty() || compName.equalsIgnoreCase("NA")
				|| compName.equalsIgnoreCase("NC")) {
			ConfigData logConfig = ConfigDataFactory.findByName(PayhumConstants.LOGGED_USER_COMP);
            Integer compId = Integer.parseInt(logConfig.getConfigValue());
            
            List<Company> comps = CompanyFactory.findById(compId);
            if(comps.size() > 0 ) {
            	comp = comps.get(0);
            }
		} else {
			List<Company> comps = CompanyFactory.findByName(compName);
            if(comps.size() > 0 ) {
            	comp = comps.get(0);
            }
		}
		
		return comp;
	}
	
	private Department getDept(Company comp, String branchName, String deptName) 
			throws Exception {
		Branch branch = null;
		Department dept = null;
		
		//Branch
		if(branchName.isEmpty() || branchName.equalsIgnoreCase("NA")
				|| branchName.equalsIgnoreCase("NC")) {
			List<Branch> branches = BranchFactory.findByCompanyId(comp.getId());
			branch = branches.get(0);
		} else {
			List<Branch> brs = BranchFactory.findByName(branchName);
			List<Branch> lbranches = null;
			
			if(brs == null || brs.isEmpty()) {
				// There is no branch by that name, so add it
				Branch bb = new Branch();
				bb.setName(branchName);
				bb.setAddress("NA");
				bb.setCompanyId(comp);
				
				BranchFactory.insert(bb);
				
				lbranches = BranchFactory.findByName(branchName);
			} else {
				lbranches = brs;
			}
			
			branch = lbranches.get(0);
		}
		
		//Dept
		List<Department> ldepts = DepartmentFactory.findByBranchId(branch.getId());
        
        if(ldepts != null && !ldepts.isEmpty()) {
        	for(Department dd : ldepts) {
        		if(dd.getDeptname().equalsIgnoreCase(deptName)) {
        			dept = dd;
        			break;
        		}
        	}
        } 
        
        if(dept == null && deptName != null &&
        		!deptName.equalsIgnoreCase("NC") && !deptName.equalsIgnoreCase("NA")) {
        	Department d = new Department();
        	d.setBranchId(branch);
        	d.setDeptname(deptName);
        	
        	DepartmentFactory.insert(d);
        	dept = d;
        }
		
		return dept;
	}
}
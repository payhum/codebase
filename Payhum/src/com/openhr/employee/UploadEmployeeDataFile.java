package com.openhr.employee;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.data.Benefit;
import com.openhr.data.BenefitType;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.data.Department;
import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmpDependents;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.Position;
import com.openhr.data.Roles;
import com.openhr.data.TypesData;
import com.openhr.data.Users;
import com.openhr.factories.BenefitTypeFactory;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.DepartmentFactory;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.PositionFactory;
import com.openhr.factories.RolesFactory;
import com.openhr.factories.TypesDataFactory;
import com.openhr.factories.UsersFactory;
import com.util.payhumpackages.PayhumUtil;

public class UploadEmployeeDataFile extends Action {
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
        
        boolean needUsers = false;
        boolean checkEmps = true;
        
        Date currDt = new Date();
        Calendar currDate = Calendar.getInstance();
        currDate.setTime(currDt);
        
        int remainingMonths = 12;
        try {
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            Employee empForUser = null;
            String compNameUser = "";
            
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
                    
                    //Read File Line By Line
                    while ((strLine = br.readLine()) != null)   {
                    	System.out.println("Processing line - " + strLine);
                    	if(!firstLineIgnored) {
                    		firstLineIgnored = true;
                    		continue;
                    	}
                    	
                    	String[] lineColumns = strLine.split(COMMA);
                    	
                    	if(lineColumns.length != 28) {
                    		br.close();
                    		in.close();
                    		fstream.close();
                    		throw new Exception("The number of columns in the line is not 28 - " + strLine);
                    	}
                    	
                    	// Format is - EmployeeID,FirstName,MiddleName,LastName,Sex,Birthdate,Hiredate,
                    	// Position,Married,ResidentType,NationalID,PassportNo,AccomodationType,Allowances,
                    	// BaseSalary,SpouseWorking,NoOfChildren,BankAccountNo,BankName,BankBranch,BankRoutingNo,
                    	// Currency,AmountType,Company,Branch,Department,OT,OtherIncome
                    	String employeeId = lineColumns[0];
                		String firstName = lineColumns[1];
                		String middleName = lineColumns[2];
                		String lastName = lineColumns[3];
                		String sex = lineColumns[4];
                		String birthDateStr = lineColumns[5];
                		String hireDateStr = lineColumns[6];
                		String position = lineColumns[7];
                		String married = lineColumns[8];
                		String residentType = lineColumns[9];
                		String nationalID = lineColumns[10];
                		String passportNo = lineColumns[11];
                		String accomType = lineColumns[12];
                		String allowances = lineColumns[13];
                		String baseSalary = lineColumns[14];
                		String spouseWorking = lineColumns[15];
                		String noOfChildren = lineColumns[16];
                		String bankAccountNo = lineColumns[17];
                		String bankName = lineColumns[18];
                		String bankBranch = lineColumns[19];
                		String bankRoutingNo = lineColumns[20];
                		String currency = lineColumns[21];
                		String amountType = lineColumns[22];
                		String compName = lineColumns[23];
                		String branchName = lineColumns[24];
                		String deptName = lineColumns[25];
                		String otAmount = lineColumns[26];
                		String otherIncome = lineColumns[27];
                		
                		if(checkEmps) {
                			needUsers = checkIfCompNeedsNewUsers(compName);
                			checkEmps = false;
                			compNameUser = compName;
                			remainingMonths = getRemainingMonths(currDate, compName);
                		}
                		
                		Date birthDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(birthDateStr);
                		Date hireDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(hireDateStr);
                		
                		Employee emp = new Employee();
                		emp.setEmployeeId(employeeId);
                		emp.setFirstname(firstName);
                		emp.setMiddlename(middleName);
                		emp.setLastname(lastName);
                		emp.setSex(sex);
                		emp.setBirthdate(birthDate);
                		emp.setHiredate(hireDate);
                		emp.setEmpNationalID(nationalID);
                		emp.setPpNumber(passportNo);
                		
                		//Get position, if not present insert it and use it
                		List<Position> positions = PositionFactory.findByName(position);
                		if(positions != null && !positions.isEmpty()) {
                			emp.setPositionId(positions.get(0));
                		} else {
                			Position pos = new Position();
                			pos.setHighSal(1D);
                			pos.setLowSal(0D);
                			pos.setName(position);
                			PositionFactory.insert(pos);
                			
                			emp.setPositionId(pos);
                		}
                		
                		// If married is "yes", then set true, else false
                		if("yes".equalsIgnoreCase(married)) {
                			emp.setMarried("true");
                		} else{
                			emp.setMarried("false");
                		}
                		
                		// set resident type
                		if(residentType.equalsIgnoreCase("local")){
                			TypesData resType = TypesDataFactory.findByName(PayhumConstants.LOCAL);
                			emp.setResidentType(resType);
                		} else if (residentType.equalsIgnoreCase("resident foreigner")
                				|| residentType.equalsIgnoreCase("resident_foreigner")
                				|| residentType.equalsIgnoreCase("residentforeigner")){
                			TypesData resType = TypesDataFactory.findByName(PayhumConstants.RESIDENT_FOREIGNER);
                			emp.setResidentType(resType);
                		} else if (residentType.equalsIgnoreCase("non resident foreigner")
                				|| residentType.equalsIgnoreCase("non_resident_foreigner")
                				|| residentType.equalsIgnoreCase("nonresidentforeigner")){
                			TypesData resType = TypesDataFactory.findByName(PayhumConstants.NON_RESIDENT_FOREIGNER);
                			emp.setResidentType(resType);
                		}
                		
                		// Set currency
                		if(currency.equalsIgnoreCase("usd")) {
                			TypesData currType = TypesDataFactory.findByName(PayhumConstants.CURRENCY_USD);
                			emp.setCurrency(currType);
                		} else if(currency.equalsIgnoreCase("euro")) {
                			TypesData currType = TypesDataFactory.findByName(PayhumConstants.CURRENCY_EURO);
                			emp.setCurrency(currType);
                		} else if(currency.equalsIgnoreCase("pound")) {
                			TypesData currType = TypesDataFactory.findByName(PayhumConstants.CURRENCY_POUND);
                			emp.setCurrency(currType);
                		} else {
                			TypesData currType = TypesDataFactory.findByName(PayhumConstants.CURRENCY_MMK);
                			emp.setCurrency(currType);
                		}
                		
                		// Misc
                		emp.setPhoto("NA");
                		emp.setStatus("ACTIVE");
                		emp.setEmerContactName("NA");
                		emp.setEmerContactNo("NA");
                		emp.setAddress("NA");
                		emp.setPhoneNo("NA");
                		emp.setNationality("NA");
                		emp.setId(EmployeeFactory.findLastId() + 1);
                		
                		//Branch, Dept Updates
                		emp.setDeptId(getDept(compName, branchName, deptName));
                		
                		// Data into emp_payroll.
                		EmployeePayroll empPayroll = new EmployeePayroll();
                		empPayroll.setEmployeeId(emp);
                		empPayroll.setFullName(firstName + " " + middleName + " " + lastName);
                		empPayroll.setTaxPaidByEmployer(0);
                		empPayroll.setWithholdSS(1);
                		empPayroll.setWithholdTax(1);
                		
                		//Other Income
                		empPayroll.setOtherIncome(Double.parseDouble(otherIncome));
                		
                		//Initial Overtime
                		empPayroll.addOvertimeamt(Double.parseDouble(otAmount));
                		
                		if(accomType.equalsIgnoreCase("free fully furnished accomodation")
                			|| accomType.equalsIgnoreCase("fully furnished accomodation")
                			|| accomType.equalsIgnoreCase("free fully furnished")
                			|| accomType.equalsIgnoreCase("fully furnished")) {
                			TypesData accomTypeData = TypesDataFactory.findByName(PayhumConstants.ACCOM_FULLY_FURNISHED);
                			empPayroll.setAccomodationType(accomTypeData);
                		} else if(accomType.equalsIgnoreCase("free not furnished accomodation")
                    			|| accomType.equalsIgnoreCase("not furnished accomodation")
                    			|| accomType.equalsIgnoreCase("free not furnished")
                    			|| accomType.equalsIgnoreCase("not furnished")) {
                    			TypesData accomTypeData = TypesDataFactory.findByName(PayhumConstants.ACCOM_NOT_FURNISHED);
                    			empPayroll.setAccomodationType(accomTypeData);
                    	} else {
                			TypesData accomTypeData = TypesDataFactory.findByName(PayhumConstants.ACCOM_NO);
                			empPayroll.setAccomodationType(accomTypeData);
                		}                		
                		
                		// Data into emp_salary
                		EmployeeSalary empSal = new EmployeeSalary();
                		if(amountType.equalsIgnoreCase("annual") || amountType.equalsIgnoreCase("annually")
                        || amountType.equalsIgnoreCase("year") || amountType.equalsIgnoreCase("yearly")) {
                			empSal.setBasesalary(Double.parseDouble(baseSalary));
                		} else {
                			empSal.setBasesalary(Double.parseDouble(baseSalary) * 12);
                		}
                		empSal.setEmployeeId(emp);
                		empSal.setFromdate(currDt);
                		empSal.setTodate(currDt);
                		
                		// Data into Benefit
                		List<BenefitType> benefits = BenefitTypeFactory.findAll();
                		BenefitType bt = benefits.get(0);
                		Benefit ben = new Benefit();
                		if(amountType.equalsIgnoreCase("annual") || amountType.equalsIgnoreCase("annually")
                		|| amountType.equalsIgnoreCase("year") || amountType.equalsIgnoreCase("yearly")) {
                			ben.setAmount(Double.parseDouble(allowances));	
                		} else {
                			ben.setAmount(Double.parseDouble(allowances) * remainingMonths);
                		}
                		
                		ben.setEmployeeId(emp);
                		ben.setTypeId(bt);
                		
                		List<EmpDependents> dependents = new ArrayList<EmpDependents>();
                		
                		//Data into emp_dependents
                		if(emp.isMarried()) {
                			// Insert spouse info
                			EmpDependents empSpouse = new EmpDependents();
                			empSpouse.setAge(0);
                			empSpouse.setEmployeeId(emp);
                			empSpouse.setName("NA");
                			
                			if("yes".equalsIgnoreCase(spouseWorking)) {
                				TypesData occupType = TypesDataFactory.findByName(PayhumConstants.OCCUP_WORKING);
                				empSpouse.setOccupationType(occupType);
                			} else {
                				TypesData occupType = TypesDataFactory.findByName(PayhumConstants.OCCUP_NONE);
                				empSpouse.setOccupationType(occupType);
                			}
                			
                			TypesData depType = TypesDataFactory.findByName(PayhumConstants.DEP_SPOUSE);
                			empSpouse.setDepType(depType);
                			dependents.add(empSpouse);
                		}
                		
                		// Add children if any
                		if(noOfChildren != null && !noOfChildren.isEmpty()) {
                			int noChild = Integer.parseInt(noOfChildren);
                			
                			TypesData depType = TypesDataFactory.findByName(PayhumConstants.DEP_CHILD);
                			TypesData occupType = TypesDataFactory.findByName(PayhumConstants.OCCUP_STUDENT);
                			
                			for(int i = 0; i < noChild; i ++) {
                				EmpDependents child = new EmpDependents();
                				child.setAge(0);
                				child.setName("NA");
                				child.setEmployeeId(emp);
                				child.setDepType(depType);
                				child.setOccupationType(occupType);
                				
                				dependents.add(child);
                			}
                		}
                		
                		// Add Bank accounts
                		EmpBankAccount empBank = null;
                		if(!"NA".equalsIgnoreCase(bankName)) {
                			empBank = new EmpBankAccount();
                			empBank.setAccountNo(bankAccountNo);
                			empBank.setBankBranch(bankBranch);
                			empBank.setBankName(bankName);
                			empBank.setEmployeeId(emp);
                			empBank.setRoutingNo(bankRoutingNo);
                		}
                		
                		EmployeeFactory.insertAll(emp, empPayroll, empSal, ben, empBank, dependents);
                		
                		if(needUsers) {
                			empForUser = emp;
                		}
                    }
                    
                    //Close the input stream
                    br.close();
        			in.close();
            		fstream.close();
                }
            }
            System.out.println("Upload has been done successfully!");
            
            // Create default users as required
            if(needUsers) {
            	compNameUser = compNameUser.replaceAll(" ", "_");
            	
            	List<Roles> hrRole = RolesFactory.findByName("HumanResource");
            	Users user = new Users();
            	user.setEmployeeId(empForUser);
            	user.setPassword("welcome");
            	user.setUsername(compNameUser + "_" + "hr");
            	user.setRoleId(hrRole.get(0));
            	UsersFactory.insert(user);
            	
            	List<Roles> finRole = RolesFactory.findByName("Accountant");
            	Users user1 = new Users();
            	user1.setEmployeeId(empForUser);
            	user1.setPassword("welcome");
            	user1.setUsername(compNameUser + "_" + "fin");
            	user1.setRoleId(finRole.get(0));
            	UsersFactory.insert(user1);
            	
            	List<Roles> adminRole = RolesFactory.findByName("PageAdmin");
            	Users user2 = new Users();
            	user2.setEmployeeId(empForUser);
            	user2.setPassword("welcome");
            	user2.setUsername(compNameUser + "_" + "admin");
            	user2.setRoleId(adminRole.get(0));
            	UsersFactory.insert(user2);
            	
            	List<Roles> empRole = RolesFactory.findByName("Employee");
            	Users user3 = new Users();
            	user3.setEmployeeId(empForUser);
            	user3.setPassword("welcome");
            	user3.setUsername(compNameUser + "_" + "emp");
            	user3.setRoleId(empRole.get(0));
            	UsersFactory.insert(user3);
            }
        } catch (Exception ex) {
        	System.out.println("There was an error: " + ex.getMessage());
        	ex.printStackTrace();
        }
        
		return map.findForward("HRHome");
	}

	private boolean checkIfCompNeedsNewUsers(String compName) throws Exception {
		if(compName.isEmpty() || compName.equalsIgnoreCase("NA")) {
			return false;
		} else {
			List<Company> comps = CompanyFactory.findByName(compName);
			List<Employee> emps = EmployeeFactory.findAllActive(comps.get(0).getId());
			if(emps == null || emps.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	private int getRemainingMonths(Calendar currDate, String compName) throws Exception {
		List<Company> comps = CompanyFactory.findByName(compName);
		return PayhumUtil.remainingMonths(currDate, comps.get(0).getFinStartMonth());
	}

	private Department getDept(String compName, String branchName, String deptName) 
			throws Exception {
		Company comp = null;
		Branch branch = null;
		Department dept = null;
		
		//Company
		if(compName.isEmpty() || compName.equalsIgnoreCase("NA")) {
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
		
		//Branch
		if(branchName.isEmpty() || branchName.equalsIgnoreCase("NA")) {
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
        
        if(dept == null) {
        	Department d = new Department();
        	d.setBranchId(branch);
        	d.setDeptname(deptName);
        	
        	DepartmentFactory.insert(d);
        	dept = d;
        }
		
		return dept;
	}
}

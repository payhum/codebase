package com.openhr.employee;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.openhr.data.TypesData;
import com.openhr.factories.BenefitFactory;
import com.openhr.factories.BenefitTypeFactory;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.DepartmentFactory;
import com.openhr.factories.EmpBankAccountFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.PositionFactory;
import com.openhr.factories.TypesDataFactory;

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
                    
                    // Get the default company and the dept to insert data
                    ConfigData logConfig = ConfigDataFactory.findByName(PayhumConstants.LOGGED_USER_COMP);
                    Integer compId = Integer.parseInt(logConfig.getConfigValue());
                    
                    List<Branch> branches = BranchFactory.findByCompanyId(compId);
                    List<Department> depts = DepartmentFactory.findByBranchId(branches.get(0).getId());
                    Department dept = null;
                    
                    if(depts != null && !depts.isEmpty()) {
                    	dept = depts.get(0);
                    } else {
                    	Department d = new Department();
                    	d.setBranchId(branches.get(0));
                    	d.setDeptname("NewDept");
                    	
                    	DepartmentFactory.insert(d);
                    	dept = d;
                    }
                    
                    Date currDate = new Date();
                    boolean firstLineIgnored = false;
                    
                    //Read File Line By Line
                    while ((strLine = br.readLine()) != null)   {
                    	System.out.println("Processing line - " + strLine);
                    	if(!firstLineIgnored) {
                    		firstLineIgnored = true;
                    		continue;
                    	}
                    	
                    	String[] lineColumns = strLine.split(COMMA);
                    	
                    	if(lineColumns.length < 22) {
                    		br.close();
                    		in.close();
                    		fstream.close();
                    		throw new Exception("The required columns are missing in the line - " + strLine);
                    	}
                    	
                    	// Format is - EmployeeID,FirstName,MiddleName,LastName,Sex,Birthdate,Hiredate,
                    	// Position,Married,ResidentType,NationalID,PassportNo,AccomodationType,Allowances,
                    	// BaseSalary,SpouseWorking,NoOfChildren,BankAccountNo,BankName,BankBranch,BankRoutingNo,Currency
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
                		
                		Date birthDate = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(birthDateStr);
                		Date hireDate = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(hireDateStr);
                		
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
                		emp.setDeptId(dept);
                		emp.setId(EmployeeFactory.findLastId() + 1);
                		
                		// Data into emp_payroll.
                		EmployeePayroll empPayroll = new EmployeePayroll();
                		empPayroll.setEmployeeId(emp);
                		empPayroll.setFullName(firstName + " " + middleName + " " + lastName);
                		empPayroll.setTaxPaidByEmployer(0);
                		empPayroll.setWithholdSS(1);
                		empPayroll.setWithholdTax(1);
                		
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
                		empSal.setBasesalary(Double.parseDouble(baseSalary));
                		empSal.setEmployeeId(emp);
                		empSal.setFromdate(currDate);
                		empSal.setTodate(currDate);
                		
                		// Data into Benefit
                		List<BenefitType> benefits = BenefitTypeFactory.findAll();
                		BenefitType bt = benefits.get(0);
                		Benefit ben = new Benefit();
                		ben.setAmount(Double.parseDouble(allowances));
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
                		if(bankAccountNo != null && !bankAccountNo.isEmpty()) {
                			empBank = new EmpBankAccount();
                			empBank.setAccountNo(bankAccountNo);
                			empBank.setBankBranch(bankBranch);
                			empBank.setBankName(bankName);
                			empBank.setEmployeeId(emp);
                			empBank.setRoutingNo(bankRoutingNo);
                		}
                		
                		EmployeeFactory.insertAll(emp, empPayroll, empSal, ben, empBank, dependents);
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
        
		return map.findForward("HRHome");
	}
}

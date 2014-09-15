package com.openhr;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.read.biff.WorkbookParser;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.data.Deduction;
import com.openhr.data.DeductionsType;
import com.openhr.data.EmpDependents;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.TypesData;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.DeductionFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.PdfFactory;
import com.openhr.taxengine.DeductionsDone;

public class YearEndTaxReport extends Action {

	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// response.setHeader("Content-Disposition", "attachment; filename=" + "newtest.xls");
		// response.setContentType("application/vnd.ms-excel");

		Date now = new Date();
        String year = new SimpleDateFormat("yyyy").format(now);
		
		ConfigData config = ConfigDataFactory.findByName(PayhumConstants.LOGGED_USER_COMP); 
		Integer compId = Integer.parseInt(config.getConfigValue());
		
		List<Company> comps = CompanyFactory.findById(compId);
		Company comp = null;
		if(comps != null && !comps.isEmpty()) {
			comp = comps.get(0);
		}
		
		String compName = comp.getName();
		compName = compName.replace(" ", "_");
		
		String fileName = "YearEndTaxReport" + compName + "_" + year + ".zip"; 
		
		fileName = fileName.replace(" ", "_");
		
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition",
				"attachment; filename=" + fileName );

		ServletOutputStream os = response.getOutputStream();

		ZipOutputStream zos = new ZipOutputStream(os);

		List<Employee> allEmps = EmployeeFactory.findAll();
		
		createReportForAllEmps(zos, allEmps, year);

		zos.flush();

		zos.close();

		os.flush();
		os.close();

		return map.findForward("Payroll");
	}

	private void createReportForAllEmps(ZipOutputStream zos,
			List<Employee> allEmps, String year) throws BiffException, IOException, WriteException {
		
		for(Employee emp: allEmps) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			Workbook workbook = Workbook.getWorkbook(new File("C:\\Program Files\\STPPayroll\\Annual Tax Filing Report.xls"));
			WritableWorkbook wb = Workbook.createWorkbook(baos, workbook);
			
			updateWorkbook(emp, wb);
			
			wb.write();
		    wb.close();
		    
			zos.putNextEntry(new ZipEntry("YearEndReport_" + emp.getEmployeeId() + "_" + emp.getFirstname() + "_" + emp.getLastname()
											+ "_" + year + ".xls"));

			zos.write(baos.toByteArray());
			zos.closeEntry();
			baos.flush();
			baos.close();
		}
	}

	private void updateWorkbook(Employee emp, WritableWorkbook wb) 
			throws RowsExceededException, WriteException {
		
		EmployeePayroll empPayroll = EmpPayTaxFactroy.findEmpPayrollbyEmpID(emp);
		
		WritableSheet sheet = wb.getSheet(0);
	    
		CellFormat wcfSerialNo = sheet.getCell(1, 6).getCellFormat();
		Label serialNo = new Label(1, 6, "1", new WritableCellFormat(wcfSerialNo)); 
		sheet.addCell(serialNo); 
		
		CellFormat wcfEmpName = sheet.getCell(2, 6).getCellFormat();
		Label empName = new Label(2, 6, empPayroll.getFullName(), new WritableCellFormat(wcfEmpName)); 
		sheet.addCell(empName); 
		
		CellFormat wcfPosTitle = sheet.getCell(3, 6).getCellFormat();
		Label posTitle = new Label(3, 6, emp.getPositionId().getName(), new WritableCellFormat(wcfPosTitle)); 
		sheet.addCell(posTitle); 
				
		CellFormat wcfBaseSal = sheet.getCell(4, 6).getCellFormat();
	    jxl.write.Number baseSal = new jxl.write.Number(4, 6, empPayroll.getPaidBaseSalary(), new WritableCellFormat(wcfBaseSal)); 
	    sheet.addCell(baseSal);
	    
	    CellFormat wcfAccom = sheet.getCell(5, 6).getCellFormat();
	    jxl.write.Number accom = new jxl.write.Number(5, 6, empPayroll.getAccomodationAmount(), new WritableCellFormat(wcfAccom)); 
	    sheet.addCell(accom);
	    
	    Double annualGross = empPayroll.getOtherIncome() + empPayroll.getOvertimeamt() + empPayroll.getBonus() + empPayroll.getRetroBaseSal()
	    					+ empPayroll.getPaidAllowance() + empPayroll.getTaxableOverseasIncome();
	    CellFormat wcfOD = sheet.getCell(6, 6).getCellFormat();
	    jxl.write.Number otherDis= new jxl.write.Number(6, 6, annualGross, new WritableCellFormat(wcfOD)); 
	    sheet.addCell(otherDis);
	    
	    CellFormat wcfTotalIncome = sheet.getCell(7, 6).getCellFormat();
	    jxl.write.Number totalIncome = new jxl.write.Number(7, 6, annualGross + empPayroll.getPaidBaseSalary()
	    		+  empPayroll.getAccomodationAmount(), new WritableCellFormat(wcfTotalIncome));
	    sheet.addCell(totalIncome);
	    
	    CellFormat wcfSS = sheet.getCell(8, 6).getCellFormat();
	    List<DeductionsType> deductionsTypes = DeductionFactory.findAll();
	    jxl.write.Number ssb = new jxl.write.Number(8, 6, empPayroll.getPaidEmpeSS(), new WritableCellFormat(wcfSS));
	    sheet.addCell(ssb);
	    
	    DeductionsDone insuranceDD = EmpPayTaxFactroy.findDeductionDone(getDeductionsType(deductionsTypes, PayhumConstants.LIFE_INSURANCE),
	    		empPayroll);
	    CellFormat wcfInsurance = sheet.getCell(9, 6).getCellFormat();
	    jxl.write.Number insurance= new jxl.write.Number(9, 6, insuranceDD.getAmount(), new WritableCellFormat(wcfInsurance));
	    sheet.addCell(insurance);
	    
	    CellFormat wcfOtherDeduc = sheet.getCell(10, 6).getCellFormat();
	    jxl.write.Number otherDeduc= new jxl.write.Number(10, 6, 0, new WritableCellFormat(wcfOtherDeduc));
	    sheet.addCell(otherDeduc);
	    
	    CellFormat wcfTotalDeduc = sheet.getCell(11, 6).getCellFormat();
	    jxl.write.Number totalDeduc= new jxl.write.Number(11, 6, empPayroll.getPaidEmpeSS() + insuranceDD.getAmount(), 
	    		new WritableCellFormat(wcfTotalDeduc));
	    sheet.addCell(totalDeduc);
	    
	    CellFormat wcfSpouse = sheet.getCell(12, 6).getCellFormat();
	    List<EmpDependents> dependents = emp.getDependents();
	    String spouseEarningStr = "Yes";
	    if(emp.isMarried()) {
			for(EmpDependents dependent : dependents) {
				if(dependent.getDepType().getName().equalsIgnoreCase(PayhumConstants.DEP_SPOUSE)
				  && dependent.getOccupationType().getName().equalsIgnoreCase(PayhumConstants.OCCUP_NONE)) {
					spouseEarningStr = "No";
					break;
				}
			}
		} 
		
		// Handle Children exemptions
		int noOfChildern = 0;
		for(EmpDependents dependent : dependents) {
			if((dependent.getDepType().getName().equalsIgnoreCase(PayhumConstants.DEP_CHILD))
			&& ( dependent.getAge() < 18 || (dependent.getOccupationType().getName().equalsIgnoreCase(PayhumConstants.OCCUP_STUDENT)))) {
				noOfChildern++;
			}
		}
		
	    Label spouseEarning= new Label(12, 6, spouseEarningStr, new WritableCellFormat(wcfSpouse)); 
		sheet.addCell(spouseEarning); 
		
		CellFormat wcfChildren = sheet.getCell(13, 6).getCellFormat();
		jxl.write.Number noOfChildren = new jxl.write.Number(13, 6, noOfChildern, new WritableCellFormat(wcfChildren)); 
		sheet.addCell(noOfChildren);
		
		CellFormat wcfTax = sheet.getCell(14, 6).getCellFormat();
		jxl.write.Number totalTax= new jxl.write.Number(14, 6, empPayroll.getPaidTaxAmt(), new WritableCellFormat(wcfTax));
	    sheet.addCell(totalTax);
	    
	    // Misc details
	    CellFormat wcfCompanyName = sheet.getCell(3, 17).getCellFormat();
	    Label compName = new Label(3, 17, emp.getDeptId().getBranchId().getCompanyId().getName(), new WritableCellFormat(wcfCompanyName)); 
		sheet.addCell(compName); 
		
		CellFormat wcfEmpID = sheet.getCell(3, 18).getCellFormat();
	    Label empID = new Label(3, 18, emp.getEmployeeId(), new WritableCellFormat(wcfEmpID)); 
		sheet.addCell(empID); 
		
		if(emp.getResidentType().getName().equalsIgnoreCase(PayhumConstants.LOCAL)) {
			CellFormat wcfEmpNationalID = sheet.getCell(3, 19).getCellFormat();
		    Label empNationalID = new Label(3, 19, emp.getEmpNationalID(), new WritableCellFormat(wcfEmpNationalID)); 
			sheet.addCell(empNationalID);	
		} else {
			CellFormat wcfEmpPassportNo = sheet.getCell(3, 20).getCellFormat();
		    Label empPassportNo = new Label(3, 20, emp.getPpNumber(), new WritableCellFormat(wcfEmpPassportNo)); 
			sheet.addCell(empPassportNo);
		}
		
		//To show the period we need to get the first payroll of this emp and the last payroll.
		List<EmpPayrollMap> empPayrollMaps = EmpPayTaxFactroy.findAllEmpPayrollMapForGivenEmpPayroll(empPayroll.getId());
		Date startDate = null;
		Date endDate = null;
		
		for(EmpPayrollMap empPayrollMap : empPayrollMaps)
		{
			Date currDate = empPayrollMap.getPayrollId().getRunOnDate();
			if(startDate == null
					|| currDate.compareTo(startDate) < 0)
				startDate = currDate;
			
			if(endDate == null
					|| currDate.compareTo(endDate) > 0)
				endDate = currDate;
		}
		
		String printPeriodStr = "";
		printPeriodStr = new SimpleDateFormat("MMM yyyy").format(startDate)
						 + " - " +
						 new SimpleDateFormat("MMM yyyy").format(endDate);
		CellFormat wcfPeriod = sheet.getCell(3, 21).getCellFormat();
	    Label period = new Label(3, 21, printPeriodStr , new WritableCellFormat(wcfPeriod)); 
		sheet.addCell(period); 
		
	}
	
	private DeductionsType getDeductionsType(
			List<DeductionsType> deductionsTypes, String typeStr) {
		for(DeductionsType dType: deductionsTypes) {
			if(dType.getName().equalsIgnoreCase(typeStr))
				return dType;
		}
		
		return null;
	}
}

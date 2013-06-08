package com.openhr.pdfreports;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.DeductionsType;
import com.openhr.data.EmpDependents;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.TypesData;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.GLEmployeeFactory;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.PdfFactory;
import com.openhr.glreports.form.GlReportForm;
import com.openhr.tax.form.TaxAnnualForm;
import com.openhr.taxengine.DeductionsDone;



public class PDFActions extends DispatchAction

{
	public ActionForward employeePDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
     
	
		try
		
		{
			
			PDDocument doc = new PDDocument();
			
			
			
		PDPage page = new PDPage();
		
		
		doc.addPage(page);
		
		PDFont font = PDType1Font.TIMES_BOLD_ITALIC;
		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		
		  String[][] content = {{"Name","EmPID", "SEX",}} ;
		  
		  
		  contentStream.beginText();
		  contentStream.setFont(font, 20);
          contentStream.moveTextPositionByAmount(250,730);

          contentStream.drawString("Helloo");
          contentStream.endText();
		  
		  float y=700;
		  float margin=100;
		    List<Employee> employees = EmployeeFactory.findAll();
		  final int rows = employees.size()+1;
		    final int cols =3;

		    final float rowHeight = 20f;
		    final float tableWidth = page.findMediaBox().getWidth()-(2*margin);
		    final float tableHeight = rowHeight * rows;
		    final float colWidth = tableWidth/(float)cols;
		    final float cellMargin=5f;
		 
		    //draw the rows
		    float nexty = y ;
		    for (int i = 0; i <= rows; i++) {
		        contentStream.drawLine(margin,nexty,margin+tableWidth,nexty);
		        nexty-= rowHeight;
		    }
		 
		    //draw the columns
		    float nextx = margin;
		    for (int i = 0; i <= cols; i++) {
		        contentStream.drawLine(nextx,y,nextx,y-tableHeight);
		        nextx += colWidth;
		    }
		 
		    //now add the text
		    contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
		 
		    float textx = margin+cellMargin;
		    float texty = y-15;
		    
		    
		    
		    for(int i = 0; i < content.length; i++){
		        for(int j = 0 ; j < content[i].length; j++){
		            String text = content[i][j];
		            contentStream.beginText();
		            contentStream.moveTextPositionByAmount(textx,texty);
		            contentStream.drawString(text);
		            contentStream.endText();
		            textx += colWidth;
		            
		        }
		        texty-=rowHeight;
		        textx = margin+cellMargin;
		    }
		    
			for (Employee emp : employees) {
				  for(int j = 0 ; j < 2; j++){
					  contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					  switch (j){  
					  case 1:
						  contentStream.drawString(emp.getEmployeeId());
					 break;
					  case 2:
						  contentStream.drawString(emp.getFirstname());
					  break;
					  case 3:
						  contentStream.drawString(emp.getFirstname());
					  break;
					  case 4:
						  contentStream.drawString(emp.getFirstname());
					  break;
					  default:
						  contentStream.drawString(emp.getFirstname());
					  }
					  
				        contentStream.endText();
			            textx += colWidth;
					  }
				  texty-=rowHeight;
			        textx = margin+cellMargin;
				  }
		    
			
		    
		contentStream.close();
		
PDPage page1 = new PDPage();
		
		
		doc.addPage(page1);
		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"file.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
            e.printStackTrace();
        }
		
		 return null;
	}
	public ActionForward employeeViewPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
     
		try
		
		{
			
			
			PDDocument doc = new PDDocument();
	
		
			List<EmployeePayroll> empPay = EmpPayTaxFactroy.findAllEmpPayroll();
			
			List<GlReportForm> empFrm = new ArrayList<GlReportForm>();

			GlReportForm glf = null;

			for (EmployeePayroll emp : empPay) {

				for (int i = 1; i < 6; i++)

				{

					glf = new GlReportForm();

					switch (i) {
					case 1:
						glf.setEmpName(emp.getFullName());
						glf.setEname("NetPay");
						glf.setCredt(emp.getNetPay());
						break;
					case 2:
						glf.setEname("TaxAmount");
						glf.setEmpName(emp.getFullName());

						glf.setCredt(emp.getTaxableIncome());
						break;
					case 3:
						glf.setEname("Deductions");

						glf.setEmpName(emp.getFullName());
						glf.setCredt(emp.getTotalDeductions());
						break;
					case 4:
						glf.setEname("Earnings");

						glf.setEmpName(emp.getFullName());
						glf.setDebit(emp.getBaseSalary());
						break;
					case 5:
						glf.setEname("Allowanse");

						glf.setEmpName(emp.getFullName());
						glf.setDebit(emp.getAllowancesAmount());
					}

					empFrm.add(glf);
				}
			

		
			}
			
	 int rows = empFrm.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =4;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                            start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
		   	 String[][] content  = {{"Name","EmpName", "Debit","Credit"}} ;
		    	PdfFactory.employeeViewPDFadd(doc,actRow,cols,i,empFrm,end, start,content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"employeeview.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
            e.printStackTrace();
        }
		
		 return null;
	}
	
	public ActionForward companyViewPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		PDDocument doc = new PDDocument();
		try
		{
		List<Object[]> empPay = GLEmployeeFactory.findCompanyView();
		
		List<GlReportForm> empFrm = new ArrayList<GlReportForm>();

		
		GlReportForm glf = null;

		for (Object[] obj : empPay) {
			for (int i = 1; i < 6; i++)

			{

				glf = new GlReportForm();

				switch (i) {
				case 1:
					glf.setEname("Total NetPay");

					glf.setCredt((Double) obj[0]);
					break;
				case 2:
					glf.setEname("Tax Amount");
					glf.setCredt((Double) obj[1]);
					break;
				case 3:
					glf.setEname("Total Deductions");

					glf.setCredt((Double) obj[2]);
					break;
				case 4:
					glf.setEname("Total Earnings");

					glf.setCredt((Double) obj[3]);
					break;
				case 5:
					glf.setEname("Total Allowanse");

					glf.setCredt((Double) obj[4]);
				}

				empFrm.add(glf);
			}
			}
		
		 int rows = empFrm.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =3;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                          start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
		    	
		    	
		    	 String[][] content  = {{"Name", "Debit","Credit"}} ;
		    	PdfFactory.employeeViewPDFadd(doc,actRow,cols,i,empFrm,end, start, content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"companyview.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
          e.printStackTrace();
      }
		
		 return null;
	}
	public ActionForward taxAnnualPdf(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		PDDocument doc = new PDDocument();
		try
		{
			 List<TaxAnnualForm> employees1 = new ArrayList<TaxAnnualForm>();
				List<EmployeePayroll> employees =EmpPayTaxFactroy.findAllEmpPayroll();
				TaxAnnualForm ta=null;
			
	for(EmployeePayroll emp:employees)
	{
		 ta=new TaxAnnualForm();
		 ta.setEpay(emp);
		// int type=3;
		 DeductionsType type=new DeductionsType();
		 
		 type.setId(3);
		 DeductionsDone decDone=EmpPayTaxFactroy.findDeductionDone(type,emp);
		 if(decDone==null)
		 { ta.setLifeinsurance("0");
			 
		 }
		
		 else
		 {
			 ta.setLifeinsurance(decDone.getAmount().toString());
		 }
		 int typeDepdent=21;
		 
		 
		 
		 TypesData  typeData=new TypesData();
		 typeData.setId(21);
		 Long empDep= EmpPayTaxFactroy.findEmpDepdentsWithType(typeData,emp.getEmployeeId());
		 Long ob=0l;
		 if(empDep==ob)
		 { ta.setChilderen("NA");
			 
		 }
		
		 else
		 {
			 ta.setChilderen(empDep.toString());
		 }
		 
		 
		 TypesData  typeData1=new TypesData();
		 typeData1.setId(20);
		 EmpDependents  empDep1= EmpPayTaxFactroy.findEmpDepdentsWithType2(typeData1,emp.getEmployeeId());
		 
		 if(empDep1==null)
		 { ta.setSpouse("NA");
			 
		 }
		
		 else
		 {
			 ta.setSpouse(empDep1.getOccupationType().getName());
		 }
		 
		 employees1.add(ta);
	}

		 int rows = employees1.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =11;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                          start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
		    	
		    	
		    	 String[][] content  = {{"Id", "Name","Designation","Salary","Acom","Others","Total","LifeIns","Supose","Children","Income"}} ;
		    	PdfFactory.taxAnnualPdf(doc,actRow,cols,i,employees1,end, start, content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"taxannual.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
          e.printStackTrace();
      }
		
		 return null;
	}
	
	
	
	
	public ActionForward taxMonthPdf(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		PDDocument doc = new PDDocument();
		try
		{
			List<EmpPayrollMap> employees = EmpPayTaxFactroy.findTaxMonthly(0);
			EmployeeForm acfm=null;

		 int rows = employees.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =5;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                          start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
	
		    	
		    	 String[][] content  = {{"Id", "Name","Designation","Salary","IncomeTaxDeducted"}} ;
		    	PdfFactory.taxMonthPdf(doc,actRow,cols,i,employees,end, start, content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"taxmonth.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
          e.printStackTrace();
      }
		
		 return null;
	}
	
	
	
	
	
	
	public ActionForward empEarnsPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		PDDocument doc = new PDDocument();
		try
		{
			List<EmployeePayroll> empPayroll = EmpPayTaxFactroy.findAllEmpPayroll();
			EmployeeForm acfm=null;

		 int rows = empPayroll.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =4;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                          start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
		    	
		    	
		    	 String[][] content  = {{"Id", "Name","Department","AmountEarned"}} ;
		    	PdfFactory.empEarns(doc,actRow,cols,i,empPayroll,end, start, content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"employeeearns.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
          e.printStackTrace();
      }
		
		 return null;
	}
	
	public ActionForward empAcumPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		PDDocument doc = new PDDocument();
		try
		{
			
				
				List <Object[]> ls=LeaveRequestFactory.findByStatusNodays(1);
				List<EmployeeForm> acl=new ArrayList<EmployeeForm>();
				EmployeeForm acfm=null;
		for(Object[] obj:ls)
		{
			acfm =new EmployeeForm();
		Employee e=(Employee)obj[0];
		acfm.setEmployeeId(e.getEmployeeId());
		acfm.setFirstname(e.getFirstname()+"."+e.getMiddlename());
			acfm.setBirthdate(e.getBirthdate());
			acfm.setHiredate(e.getBirthdate());
			acfm.setPayrol(EmpPayTaxFactroy.findEmpPayrollbyEmpID(e));
			acfm.setDeptId(e.getDeptId());
			acfm.setCount((Long)obj[1]);
			acl.add(acfm);
		}
				
			
		
		 int rows = acl.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =6;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                          start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
		    	
		    	
		    	 String[][] content  = {{"Id", "Name","LeaveDays","TillDate","Pending","Department"}} ;
		    	PdfFactory.employeeacumPdf(doc,actRow,cols,i,acl,end, start, content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"employeeacum.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
          e.printStackTrace();
      }
		
		 return null;
	}
	
	
	
	public ActionForward payRollSummaryPDF(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		PDDocument doc = new PDDocument();
		try
		{
			List<EmployeePayroll> eptx = EmpPayTaxFactroy.findAllEmpPayroll();
		
		 int rows = eptx.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =7;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                          start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
		    	
		    	
		    	 String[][] content  = {{"Id", "Name","GrossPay","TaxWith","Deductions","Netpay","BaseSalary"}} ;
		    	PdfFactory.employeePayrollSummaryPDFadd(doc,actRow,cols,i,eptx,end, start, content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"payrollsummary.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
          e.printStackTrace();
      }
		
		 return null;
	}
	
	
	public ActionForward empDepartmentPdf(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		PDDocument doc = new PDDocument();
		try
		{
		    List<Employee> employees = EmployeeFactory.findAll();
		
		 int rows = employees.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =3;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                          start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
		    	
		    	
		    	 String[][] content  = {{"Id", "Name","Department"}} ;
		    	PdfFactory.empDepartmentPdf(doc,actRow,cols,i,employees,end, start, content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"payrollsummary.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
          e.printStackTrace();
      }
		
		 return null;
	}
	
	
	
	public ActionForward empActivePdf(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		PDDocument doc = new PDDocument();
		try
		{
		    List<Employee> employees = EmployeeFactory.findAll();
		
		 int rows = employees.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =4;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                          start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
		    	
		    	
		    	 String[][] content  = {{"Id", "Name","Active","Terminated"}} ;
		    	PdfFactory.empActivePdf(doc,actRow,cols,i,employees,end, start, content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"payrollsummary.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
          e.printStackTrace();
      }
		
		 return null;
	}
	
	
	public ActionForward empAdressPdf(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		PDDocument doc = new PDDocument();
		try
		{
		    List<Employee> employees = EmployeeFactory.findAll();
		
		 int rows = employees.size(),temp=0, actRow=0, start=0, end=0;
		   int cols =3;
		    long n=(long) Math.ceil((double)rows/(double)30);
		
		    for (int i=1; i<=n; i++) {
		    	
		    	
		    	if(rows>30)
		    	{
		    		
		    		if(i==1)
		    		{
		    			temp=rows-30;
		    			
		    		}
		    		else
		    			{temp=temp-30;}
		    		
		    		
		    		
		    		if(temp>30)
			    	{
			    	  	
			    		actRow=30;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    			
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=end+30;
			    			
			    		}
			    	}
			    	else
			    	{
			    		
			    		int check=temp+30;
			    		if(check>30)
			    			
			    		{
                          start=end;
			    			
			    			end=end+30;
			    			
			    			
			    		}
			    		else
			    		{
			    		actRow=check;
			    		
			    		if(i==1)
			    		{
			    			start=0;
			    			end=30;
			    		}
			    		else
			    		{
			    			start=end;
			    			
			    			end=rows;
			    		}
			    		
			    		}
			    	}
		    	}
		    	
		  
		    	else
		    	{
		    		actRow=rows;
		    		
		    		start=0;
		    		
		    	    end=rows;
		    	}
		    	
		    	
		    	 String[][] content  = {{"Id", "Name","Address"}} ;
		    	PdfFactory.empAdressPdf(doc,actRow,cols,i,employees,end, start, content);
		    }
		    
		    

		
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.save(baos);

		doc.close();
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"payrollsummary.pdf\"");
		  
		ServletOutputStream os = response.getOutputStream();
		baos.writeTo(os);
		os.flush();
		os.close();
		
	}
		catch(Exception e){
          e.printStackTrace();
      }
		
		 return null;
	}
}
package com.openhr.factories;

import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
import com.openhr.glreports.form.GlReportForm;
import com.openhr.tax.form.TaxAnnualForm;

public class PdfFactory {

	
	
	
	public static void employeeViewPDFadd(PDDocument doc, int rows, int cols, int c, List<GlReportForm> empPay, int end, int start, String[][] content )
	{
		
		try
		{
		PDPage page = new PDPage();
		doc.addPage(page);

	
		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		contentStream.beginText();
		  contentStream.setFont(PDType1Font.HELVETICA_BOLD,20);
      contentStream.moveTextPositionByAmount(200,730);


      
      if(cols==3)
      {
    	 
    	  contentStream.drawString("CompanyView Report");
    	  
      }
      else
      {
    	  
    	  
    	  contentStream.drawString("EmployeeView Report");
      }
      contentStream.endText();
		  float y=700;
		  float margin=100;
		  int row=rows+1;
		final float rowHeight = 20f;
	    final float tableWidth = page.findMediaBox().getWidth()-(2*margin);
	    final float tableHeight = rowHeight * row;
	    final float colWidth = tableWidth/(float)cols;
	    final float cellMargin=5f;
	 
	    //draw the rows
	    float nexty = y ;
	   
	    for (int i = 0; i <= row; i++) {
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
	   
	 
	    float textx = margin+cellMargin;
	    float texty = y-15;
	    
	    
	    
	    for(int i = 0; i < content.length; i++){
	        for(int j = 0 ; j < content[i].length; j++){
	            String text = content[i][j];
	            contentStream.beginText();
	            contentStream.moveTextPositionByAmount(textx,texty);
	    	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
	            contentStream.drawString(text);
	            contentStream.endText();
	            textx += colWidth;
	            
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);
	    for(int i=start; i<end; i++){
	    	
	    GlReportForm glf = empPay.get(i);
	    	for (int k = 1; k < 5; k++)

			{
	    		
	    		
	    		
	    		
	    		switch (k) {
	    		case 1:
	    			 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
	    			contentStream.drawString(glf.getEname());
	    			 contentStream.endText();
			            textx += colWidth;
					break;
				case 2:
					if(glf.getEmpName()!=null)
					{
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					contentStream.drawString(glf.getEmpName());
					 contentStream.endText();
			            textx += colWidth;
					}
					break;
				case 3:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					if(glf.getDebit()==null)
					{
						contentStream.drawString("0.0");
					}
					else
					{
						contentStream.drawString(glf.getDebit().toString());
					}
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 4:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					if(glf.getCredt()==null)
					{
						contentStream.drawString("0.0");
					}
					else
					{
						contentStream.drawString(glf.getCredt().toString());
					}
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 5:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					if(glf.getCredt()==null)
					{
						contentStream.drawString("0");
					}
					else
					{
						contentStream.drawString(glf.getCredt().toString());
					}
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				}
	    		 
			}
	    	
	    	texty-=rowHeight;
	        textx = margin+cellMargin;
	    		}
			  
		        
		      contentStream.close();
		      
		}
		
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
	}
	
	
	public static void employeeacumPdf(PDDocument doc, int rows, int cols, int c, List<EmployeeForm> eptx, int end, int start, String[][] content )
	{
		
		try
		{
		PDPage page = new PDPage();
		doc.addPage(page);

		
		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		  
		  contentStream.beginText();
		  contentStream.setFont(PDType1Font.HELVETICA_BOLD,20);
        contentStream.moveTextPositionByAmount(200,730);

        contentStream.drawString("Employee Accumulators Report");
        contentStream.endText();
		float y=700;
		  float margin=100;
		  int row=rows+1;
		final float rowHeight = 20f;
	    final float tableWidth = page.findMediaBox().getWidth()-(150);
	    final float tableHeight = rowHeight * row;
	    final float colWidth = tableWidth/(float)cols;
	    final float cellMargin=5f;
	 
	    //draw the rows
	    float nexty = y ;
	   
	    for (int i = 0; i <= row; i++) {
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
	  
	 
	    float textx = margin+cellMargin;
	    float texty = y-15;
	    
	    
	    
	    for(int i = 0; i < content.length; i++){
	        for(int j = 0 ; j < content[i].length; j++){
	            String text = content[i][j];
	            contentStream.beginText();
	            contentStream.moveTextPositionByAmount(textx,texty);
	            contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
	            contentStream.drawString(text);
	            contentStream.endText();
	            textx += colWidth;
	            
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);
	    for(int i=start; i<end; i++){
	    	
	    	EmployeeForm glf = eptx.get(i);
	    	for (int k = 1; k < 7; k++)

			{
	    		
	    		
	    		
	    		
	    		switch (k) {
	    		case 1:
	    			 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
	    			contentStream.drawString(glf.getEmployeeId());
	    			 contentStream.endText();
			            textx += colWidth;
					break;
				case 2:
				contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					contentStream.drawString(glf.getFirstname());
					 contentStream.endText();
			            textx += colWidth;
					
					break;
				case 3:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getCount().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 4:
					 contentStream.beginText();
					 contentStream.moveTextPositionByAmount(textx,texty);
					
					    contentStream.drawString(glf.getPayrol().getPaidNetPay().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 5:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            
			            Double d=glf.getPayrol().getNetPay()-glf.getPayrol().getPaidNetPay();
			            contentStream.drawString(d.toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 6:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 7:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				case 8:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			            contentStream.drawString(glf.getDeptId().getDeptname());
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				}
	    		 
			}
	    	
	    	texty-=rowHeight;
	        textx = margin+cellMargin;
	    		}
			  
		        
		      contentStream.close();
		      
		}
		
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void empEarns(PDDocument doc, int rows, int cols, int c, List<EmployeePayroll> eptx, int end, int start, String[][] content )
	{
		
		try
		{
		PDPage page = new PDPage();
		doc.addPage(page);


		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		contentStream.beginText();
		  contentStream.setFont(PDType1Font.HELVETICA_BOLD,20);
    contentStream.moveTextPositionByAmount(200,730);

    contentStream.drawString("Employee Earns Report");
    contentStream.endText();
		float y=700;
		  float margin=100;
		  int row=rows+1;
		final float rowHeight = 20f;
	    final float tableWidth = page.findMediaBox().getWidth()-(2*margin);
	    final float tableHeight = rowHeight * row;
	    final float colWidth = tableWidth/(float)cols;
	    final float cellMargin=5f;
	 
	    //draw the rows
	    float nexty = y ;
	   
	    for (int i = 0; i <= row; i++) {
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
	            contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
	            contentStream.drawString(text);
	            contentStream.endText();
	            textx += colWidth;
	            
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);
	    for(int i=start; i<end; i++){
	  
	    	EmployeePayroll glf = eptx.get(i);
	    	for (int k = 1; k < 5; k++)

			{
	    		
	    		
	    		
	    		
	    		switch (k) {
	    		case 1:
	    			 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
	    			contentStream.drawString(glf.getEmployeeId().getEmployeeId());
	    			 contentStream.endText();
			            textx += colWidth;
					break;
				case 2:
				contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					contentStream.drawString(glf.getEmployeeId().getFirstname());
					 contentStream.endText();
			            textx += colWidth;
					
					break;
				case 3:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getEmployeeId().getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 4:
					 contentStream.beginText();
					 contentStream.moveTextPositionByAmount(textx,texty);
					
					    contentStream.drawString(glf.getPaidNetPay().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 5:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getNetPay().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 6:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getNetPay().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 7:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getNetPay().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				case 8:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			            contentStream.drawString(glf.getNetPay().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				}
	    		 
			}
	    	
	    	texty-=rowHeight;
	        textx = margin+cellMargin;
	    		}
			  
		        
		      contentStream.close();
		      
		}
		
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	
	public static void taxAnnualPdf(PDDocument doc, int rows, int cols, int c, List<TaxAnnualForm> eptx, int end, int start, String[][] content )
	{
		
		try
		{
		PDPage page = new PDPage();
		doc.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		contentStream.beginText();
		  contentStream.setFont(PDType1Font.HELVETICA_BOLD,20);
    contentStream.moveTextPositionByAmount(250,730);

    contentStream.drawString(" Tax Annual Report");
    contentStream.endText();
		  float y=700;
		  float margin=10;
		  int row=rows+1;
		final float rowHeight = 20f;
	    final float tableWidth = page.findMediaBox().getWidth();
	    final float tableHeight = rowHeight * row;
	    final float colWidth = tableWidth/(float)cols;
	    final float cellMargin=5f;
	 
	    //draw the rows
	    float nexty = y ;
	   
	    for (int i = 0; i <= row; i++) {
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
	            contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
	            contentStream.drawString(text);
	            contentStream.endText();
	            textx += colWidth;
	            
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	    
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);
	    for(int i=start; i<end; i++){
	 
	    	TaxAnnualForm glf = eptx.get(i);
	    	for (int k = 1; k <12; k++)

			{
	    		
	    		
	    		
	    		
	    		switch (k) {
	    		case 1:
	    			 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
	    			contentStream.drawString(glf.getEpay().getEmployeeId().getEmployeeId());
	    			 contentStream.endText();
			            textx += colWidth;
					break;
				case 2:
				contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			        	contentStream.drawString(glf.getEpay().getEmployeeId().getFirstname());
					 contentStream.endText();
			            textx += colWidth;
					
					break;
				case 3:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
						
						
			        	contentStream.drawString(glf.getEpay().getEmployeeId().getPositionId().getName());
						
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 4:
					 contentStream.beginText();
					 contentStream.moveTextPositionByAmount(textx,texty);
					
						contentStream.drawString(glf.getEpay().getBaseSalary().toString());
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 5:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			        	contentStream.drawString(glf.getEpay().getAccomodationAmount().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 6:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			        	contentStream.drawString(glf.getEpay().getOtherIncome().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 7:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getEpay().getTotalIncome().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				case 8:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			        	contentStream.drawString(glf.getLifeinsurance());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
					 
					 
					 
				case 9:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			        	contentStream.drawString(glf.getSpouse());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					 
					 
					 
				case 10:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			        	contentStream.drawString(glf.getChilderen());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					 
					 
				case 11:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			        	contentStream.drawString(glf.getEpay().getTotalIncome().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				}
	    		
	    		
	    		 
			}
	    	
	    	texty-=rowHeight;
	        textx = margin+cellMargin;
	    		}
			  
		        
		      contentStream.close();
		      
		}
		
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	
	public static void employeePayrollSummaryPDFadd(PDDocument doc, int rows, int cols, int c, List<EmployeePayroll> eptx, int end, int start, String[][] content )
	{
		
		try
		{
		PDPage page = new PDPage();
		doc.addPage(page);


		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		contentStream.beginText();
		  contentStream.setFont(PDType1Font.HELVETICA_BOLD,20);
    contentStream.moveTextPositionByAmount(200,730);

    contentStream.drawString("PayrollSummary");
    contentStream.endText();
		  float y=700;
		  float margin=10;
		  int row=rows+1;
		final float rowHeight = 20f;
	    final float tableWidth = page.findMediaBox().getWidth();
	    final float tableHeight = rowHeight * row;
	    final float colWidth = tableWidth/(float)cols;
	    final float cellMargin=5f;
	    
	    
	    
	    
	    
	 
	    //draw the rows
	    float nexty = y ;
	   
	    for (int i = 0; i <= row; i++) {
	        contentStream.drawLine(margin,nexty,margin+tableWidth-40,nexty);
	        nexty-= rowHeight;
	    }
	 
	    //draw the columns
	    float nextx = margin;
	    for (int i = 0; i <= cols; i++) {
	    	
	    	if(i==6)
	        {
	    		nextx=nextx-40;
	        }
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
	            if(j==6)
	            {
	            	
	            	textx=textx-40;
	            }
	            contentStream.moveTextPositionByAmount(textx,texty);
	            contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
	            contentStream.drawString(text);
	            contentStream.endText();
	            textx += colWidth;
	            
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	    
	    
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);
	    for(int i=start; i<end; i++){
	   	
	    	EmployeePayroll glf = eptx.get(i);
	    	for (int k = 1; k < 8; k++)

			{
	    		
	    		
	    		
	    		
	    		switch (k) {
	    		case 1:
	    			 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
	    			contentStream.drawString(glf.getEmployeeId().getEmployeeId());
	    			 contentStream.endText();
			            textx += colWidth;
					break;
				case 2:
				contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					contentStream.drawString(glf.getEmployeeId().getFirstname());
					 contentStream.endText();
			            textx += colWidth;
					
					break;
				case 3:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
						contentStream.drawString(glf.getGrossSalary().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 4:
					 contentStream.beginText();
					 contentStream.moveTextPositionByAmount(textx,texty);
					
						contentStream.drawString(glf.getTaxableIncome().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 5:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
						contentStream.drawString(glf.getTaxableIncome().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 6:
					
					
					 contentStream.beginText();
					 
			            contentStream.moveTextPositionByAmount(textx,texty);
			        	contentStream.drawString(glf.getNetPay().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 7:
					textx=textx-40;
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			        	contentStream.drawString(glf.getBaseSalary().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				case 8:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
						contentStream.drawString(glf.getBaseSalary().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				}
	    		 
			}
	    	
	    	texty-=rowHeight;
	        textx = margin+cellMargin;
	    		}
			  
		        
		      contentStream.close();
		      
		}
		
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	

	public static void empDepartmentPdf(PDDocument doc, int rows, int cols, int c, List<Employee> eptx, int end, int start, String[][] content )
	{
		
		try
		{
		PDPage page = new PDPage();
		doc.addPage(page);


		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		contentStream.beginText();
		  contentStream.setFont(PDType1Font.HELVETICA_BOLD,20);
    contentStream.moveTextPositionByAmount(200,730);

    contentStream.drawString("Employees By Department");
    contentStream.endText();
    float y=700;
	  float margin=100;
		  int row=rows+1;
		  final float rowHeight = 20f;
		    final float tableWidth = page.findMediaBox().getWidth()-(2*margin);
		    final float tableHeight = rowHeight * row;
		    final float colWidth = tableWidth/(float)cols;
		    final float cellMargin=5f;
	 
	    //draw the rows
	    float nexty = y ;
	   
	    for (int i = 0; i <= row; i++) {
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
	            contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
	            contentStream.drawString(text);
	            contentStream.endText();
	            textx += colWidth;
	            
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	    
	    
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);
	    for(int i=start; i<end; i++){
	   	
	    	Employee glf = eptx.get(i);
	    	for (int k = 1; k < 4; k++)

			{
	    		
	    		
	    		
	    		
	    		switch (k) {
	    		case 1:
	    			 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
	    			contentStream.drawString(glf.getEmployeeId());
	    			 contentStream.endText();
			            textx += colWidth;
					break;
				case 2:
				contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					contentStream.drawString(glf.getFirstname());
					 contentStream.endText();
			            textx += colWidth;
					
					break;
				case 3:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
						contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 4:
					 contentStream.beginText();
					 contentStream.moveTextPositionByAmount(textx,texty);
					
					 contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 5:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 6:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 7:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				case 8:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				}
	    		 
			}
	    	
	    	texty-=rowHeight;
	        textx = margin+cellMargin;
	    		}
			  
		        
		      contentStream.close();
		      
		}
		
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void empActivePdf(PDDocument doc, int rows, int cols, int c, List<Employee> eptx, int end, int start, String[][] content )
	{
		
		try
		{
		PDPage page = new PDPage();
		doc.addPage(page);


		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		contentStream.beginText();
		  contentStream.setFont(PDType1Font.HELVETICA_BOLD,20);
    contentStream.moveTextPositionByAmount(200,730);

    contentStream.drawString("Employee Status");
    contentStream.endText();
    float y=700;
	  float margin=100;
		  int row=rows+1;
		  final float rowHeight = 20f;
		    final float tableWidth = page.findMediaBox().getWidth()-(2*margin);
		    final float tableHeight = rowHeight * row;
		    final float colWidth = tableWidth/(float)cols;
		    final float cellMargin=5f;
	 
	    //draw the rows
	    float nexty = y ;
	   
	    for (int i = 0; i <= row; i++) {
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
	            contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
	            contentStream.drawString(text);
	            contentStream.endText();
	            textx += colWidth;
	            
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	    
	    
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);
	    for(int i=start; i<end; i++){
	   	
	    	Employee glf = eptx.get(i);
	    	for (int k = 1; k < 5; k++)

			{
	    		
	    		
	    		
	    		
	    		switch (k) {
	    		case 1:
	    			 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
	    			contentStream.drawString(glf.getEmployeeId());
	    			 contentStream.endText();
			            textx += colWidth;
					break;
				case 2:
				contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					contentStream.drawString(glf.getFirstname());
					 contentStream.endText();
			            textx += colWidth;
					
					break;
				case 3:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            
			    		String ss=glf.getStatus();
						boolean b=ss.equals("ACTIVE");
						
					//b? ta.setActive("YES") : ta.setActive("NO");
					if(b)
					{
						
						contentStream.drawString("YES");
					}
					if(!b)
					{
						
						contentStream.drawString("NO");
					}
			
						
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 4:
					 contentStream.beginText();
					 contentStream.moveTextPositionByAmount(textx,texty);
					
						String ss1=glf.getStatus();
						boolean b1=ss1.equals("ACTIVE");
						
					//b? ta.setActive("YES") : ta.setActive("NO");
					if(b1)
					{
						
						contentStream.drawString("NO");
					}
					if(!b1)
					{
						
						contentStream.drawString("YES");
					}
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 5:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 6:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 7:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				case 8:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				}
	    		 
			}
	    	
	    	texty-=rowHeight;
	        textx = margin+cellMargin;
	    		}
			  
		        
		      contentStream.close();
		      
		}
		
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	

	
	public static void empAdressPdf(PDDocument doc, int rows, int cols, int c, List<Employee> eptx, int end, int start, String[][] content )
	{
		
		try
		{
		PDPage page = new PDPage();
		doc.addPage(page);


		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		contentStream.beginText();
		  contentStream.setFont(PDType1Font.HELVETICA_BOLD,20);
    contentStream.moveTextPositionByAmount(200,730);

    contentStream.drawString("EmployeeAddress");
    contentStream.endText();
    float y=700;
	  float margin=100;
		  int row=rows+1;
		  final float rowHeight = 20f;
		    final float tableWidth = page.findMediaBox().getWidth()-(2*margin);
		    final float tableHeight = rowHeight * row;
		    final float colWidth = tableWidth/(float)cols;
		    final float cellMargin=5f;
	 
	    //draw the rows
	    float nexty = y ;
	   
	    for (int i = 0; i <= row; i++) {
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
	            contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
	            contentStream.drawString(text);
	            contentStream.endText();
	            textx += colWidth;
	            
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	    
	    
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);
	    for(int i=start; i<end; i++){
	   	
	    	Employee glf = eptx.get(i);
	    	for (int k = 1; k < 4; k++)

			{
	    		
	    		
	    		
	    		
	    		switch (k) {
	    		case 1:
	    			 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
	    			contentStream.drawString(glf.getEmployeeId());
	    			 contentStream.endText();
			            textx += colWidth;
					break;
				case 2:
				contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					contentStream.drawString(glf.getFirstname());
					 contentStream.endText();
			            textx += colWidth;
					
					break;
				case 3:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            
			    	
						if(glf.getAddress()==null)
						{
							 contentStream.drawString("None");
						}
						else
						{
							 contentStream.drawString(glf.getAddress());
						}
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 4:
					 contentStream.beginText();
					 contentStream.moveTextPositionByAmount(textx,texty);
					
						String ss1=glf.getStatus();
						boolean b1=ss1.equals("ACTIVE");
						
					//b? ta.setActive("YES") : ta.setActive("NO");
					if(b1)
					{
						
						contentStream.drawString("NO");
					}
					if(!b1)
					{
						
						contentStream.drawString("YES");
					}
					
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 5:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 6:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 7:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				case 8:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			            contentStream.drawString(glf.getDeptId().getDeptname());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				}
	    		 
			}
	    	
	    	texty-=rowHeight;
	        textx = margin+cellMargin;
	    		}
			  
		        
		      contentStream.close();
		      
		}
		
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	
   	
	public static void taxMonthPdf(PDDocument doc, int rows, int cols, int c, List<EmpPayrollMap> eptx, int end, int start, String[][] content )
	{
		
		try
		{
		PDPage page = new PDPage();
		doc.addPage(page);


		PDPageContentStream contentStream = new PDPageContentStream(doc,page);
		contentStream.beginText();
		  contentStream.setFont(PDType1Font.HELVETICA_BOLD,20);
    contentStream.moveTextPositionByAmount(200,730);

    contentStream.drawString("TaxMonthly");
    contentStream.endText();
    float y=700;
	  float margin=100;
		  int row=rows+1;
		  final float rowHeight = 20f;
		  final float tableWidth = page.findMediaBox().getWidth()-(2*margin);
		    final float tableHeight = rowHeight * row;
		    final float colWidth = tableWidth/(float)cols;
		    final float cellMargin=5f;
	 
	    //draw the rows
	    float nexty = y ;
	   
	    for (int i = 0; i <= row; i++) {
	        contentStream.drawLine(margin,nexty,margin+tableWidth+50,nexty);
	        nexty-= rowHeight;
	    }
	 
	    
	   
	    //draw the columns
	    float nextx = margin;
	    for (int i = 0; i <= cols; i++) {
	    	
if(i==5)
{
	nextx=nextx+50;
}
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
	            contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);
	            contentStream.drawString(text);
	            contentStream.endText();
	            textx += colWidth;
	            
	        }
	        texty-=rowHeight;
	        textx = margin+cellMargin;
	    }
	    
	    
	    contentStream.setFont(PDType1Font.HELVETICA_BOLD,8);
	    for(int i=start; i<end; i++){
	   	
	    	EmpPayrollMap glf = eptx.get(i);
	    	for (int k = 1; k < 6; k++)

			{
	    		
	    		
	    		
	    		
	    		switch (k) {
	    		case 1:
	    			 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
	    			contentStream.drawString(glf.getEmppayId().getEmployeeId().getEmployeeId());
	    			 contentStream.endText();
			            textx += colWidth;
					break;
				case 2:
				contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getEmppayId().getEmployeeId().getFirstname());
					 contentStream.endText();
			            textx += colWidth;
					
					break;
				case 3:
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			            contentStream.drawString(glf.getEmppayId().getEmployeeId().getPositionId().getName());
			    	
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 4:
					 contentStream.beginText();
					 contentStream.moveTextPositionByAmount(textx,texty);
					 contentStream.drawString(glf.getNetPay().toString());
					 contentStream.endText();
			            textx += colWidth;
					break;
				case 5:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
			   		 contentStream.drawString(glf.getTaxAmount().toString());
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 6:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			         
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
				case 7:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
			          
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				case 8:
					
					 contentStream.beginText();
			            contentStream.moveTextPositionByAmount(textx,texty);
					
					
					 contentStream.endText();
			            textx += colWidth;
					 break;
					
				}
	    		 
			}
	    	
	    	texty-=rowHeight;
	        textx = margin+cellMargin;
	    		}
			  
		        
		      contentStream.close();
		      
		}
		
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	
}

package com.openhr.pdfreports;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

import com.openhr.common.EmployeeComparer;
import com.openhr.common.EmployeePayrollComparer;
import com.openhr.data.Branch;
import com.openhr.data.DeductionsType;
import com.openhr.data.Department;
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
			PDPageContentStream contentStream = new PDPageContentStream(doc,
					page);

			String[][] content = { { "Name", "EmPID", "SEX", } };

			contentStream.beginText();
			contentStream.setFont(font, 20);
			contentStream.moveTextPositionByAmount(250, 730);

			contentStream.drawString("Helloo");
			contentStream.endText();

			float y = 700;
			float margin = 100;
			List<Employee> employees = EmployeeFactory.findAll();
			final int rows = employees.size() + 1;
			final int cols = 3;

			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth()
					- (2 * margin);
			final float tableHeight = rowHeight * rows;
			final float colWidth = tableWidth / (float) cols;
			final float cellMargin = 5f;

			// draw the rows
			float nexty = y;
			for (int i = 0; i <= rows; i++) {
				contentStream.drawLine(margin, nexty, margin + tableWidth,
						nexty);
				nexty -= rowHeight;
			}

			// draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx, y, nextx, y - tableHeight);
				nextx += colWidth;
			}

			// now add the text
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			float textx = margin + cellMargin;
			float texty = y - 15;

			for (int i = 0; i < content.length; i++) {
				for (int j = 0; j < content[i].length; j++) {
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;

				}
				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			for (Employee emp : employees) {
				for (int j = 0; j < 2; j++) {
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx, texty);
					switch (j) {
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
				texty -= rowHeight;
				textx = margin + cellMargin;
			}

			contentStream.close();

			PDPage page1 = new PDPage();

			doc.addPage(page1);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"file.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward employeeViewPDF(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try

		{

			PDDocument doc = new PDDocument();

			List<EmployeePayroll> empPayAll = EmpPayTaxFactroy.findAllEmpPayroll();

			Map<Branch, List<EmployeePayroll>> empByBranch = groupByBranchForEmpPayroll(empPayAll);
			
			for(Branch b : empByBranch.keySet()) {
				List<EmployeePayroll> empPay = empByBranch.get(b);
				
				Collections.sort(empPay, new EmployeePayrollComparer());
				
				List<GlReportForm> empFrm = new ArrayList<GlReportForm>();
	
				GlReportForm glf = null;
	
				String compName = null;
				Integer compId = null;
				
				for (EmployeePayroll emp : empPay) {
	
					for (int i = 1; i < 6; i++)
	
					{
	
						if(compName == null) 
						{
							compName = emp.getEmployeeId().getDeptId().getBranchId().getCompanyId().getName();
							compId = emp.getEmployeeId().getDeptId().getBranchId().getCompanyId().getId();
						}
						
						glf = new GlReportForm();
	
						switch (i) {
						case 1:
							glf.setEmpName(emp.getEmployeeId().getFirstname() + " " + emp.getEmployeeId().getLastname());
							glf.setEname("NetPay");
							glf.setCredt(emp.getNetPay());
							glf.setEmpId(emp.getEmployeeId().getEmployeeId());
							break;
						case 2:
							glf.setEname("TaxAmount");
							glf.setEmpName(emp.getEmployeeId().getFirstname() + " " + emp.getEmployeeId().getLastname());
							glf.setEmpId(emp.getEmployeeId().getEmployeeId());
							glf.setCredt(emp.getTaxAmount());
							break;
						case 3:
							glf.setEname("Deductions");
							glf.setEmpId(emp.getEmployeeId().getEmployeeId());
							glf.setEmpName(emp.getEmployeeId().getFirstname() + " " + emp.getEmployeeId().getLastname());
							glf.setCredt(emp.getTotalDeductions());
							break;
						case 4:
							glf.setEname("Earnings");
							glf.setEmpId(emp.getEmployeeId().getEmployeeId());
							glf.setEmpName(emp.getEmployeeId().getFirstname() + " " + emp.getEmployeeId().getLastname());
							glf.setDebit(emp.getGrossSalary());
							break;
						case 5:
							glf.setEname("Allowance");
							glf.setEmpId(emp.getEmployeeId().getEmployeeId());
							glf.setEmpName(emp.getEmployeeId().getFirstname() + " " + emp.getEmployeeId().getLastname());
							glf.setDebit(emp.getAllowances());
						}
	
						empFrm.add(glf);
					}
	
				}
	
				int rows = empFrm.size(), temp = 0, actRow = 0, start = 0, end = 0;
				int cols = 5;
				long n = (long) Math.ceil((double) rows / (double) 28);
	
				for (int i = 1; i <= n; i++) {
	
					if (rows > 28) {
	
						if (i == 1) {
							temp = rows - 28;
	
						} else {
							temp = temp - 28;
						}
	
						if (temp > 28) {
	
							actRow = 28;
	
							if (i == 1) {
								start = 0;
								end = 28;
	
							} else {
								start = end;
	
								end = end + 28;
	
							}
						} else {
	
							int check = temp + 28;
							if (check > 28)
	
							{
								start = end;
	
								end = end + 28;
	
								actRow = 28;
							} else {
								actRow = check;
	
								if (i == 1) {
									start = 0;
									end = 28;
								} else {
									start = end;
	
									end = rows;
								}
	
							}
						}
					}
	
					else {
						actRow = rows;
	
						start = 0;
	
						end = rows;
					}
					
					
					String[][] content = { { "Employee ID", "Employee Name", "Account Type", "Debit (MMK)", "Credit (MMK)" } };
					PdfFactory.employeeViewPDFadd(doc, actRow, cols, i, empFrm,
							end, start, content, compName, compId, b.getName());
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"EmployeeViewGLReport.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward companyViewPDF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PDDocument doc = new PDDocument();
		try {
			List<Object[]> empPay = GLEmployeeFactory.findCompanyView();

			List<GlReportForm> empFrm = new ArrayList<GlReportForm>();

			List<EmployeePayroll> empPayroll = EmpPayTaxFactroy.findAllEmpPayroll();
			String compName = empPayroll.get(0).getEmployeeId().getDeptId().getBranchId().getCompanyId().getName();
			Integer compId = empPayroll.get(0).getEmployeeId().getDeptId().getBranchId().getCompanyId().getId();
			
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

						glf.setDebit((Double) obj[3]);
						break;
					case 5:
						glf.setEname("Total Allowance");

						glf.setDebit((Double) obj[4]);
					}

					empFrm.add(glf);
				}
			}

			int rows = empFrm.size(), temp = 0, actRow = 0, start = 0, end = 0;
			int cols = 3;
			long n = (long) Math.ceil((double) rows / (double) 30);

			for (int i = 1; i <= n; i++) {

				if (rows > 30) {

					if (i == 1) {
						temp = rows - 30;

					} else {
						temp = temp - 30;
					}

					if (temp > 30) {

						actRow = 30;

						if (i == 1) {
							start = 0;
							end = 30;

						} else {
							start = end;

							end = end + 30;

						}
					} else {

						int check = temp + 30;
						if (check > 30)

						{
							start = end;

							end = end + 30;

						} else {
							actRow = check;

							if (i == 1) {
								start = 0;
								end = 30;
							} else {
								start = end;

								end = rows;
							}

						}
					}
				}

				else {
					actRow = rows;

					start = 0;

					end = rows;
				}

				String[][] content = { { "Account Type", "Debit (MMK)", "Credit (MMK)" } };
				PdfFactory.employeeViewPDFadd(doc, actRow, cols, i, empFrm,
						end, start, content, compName, compId, null);
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"CompanyViewGLReport.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward taxAnnualPdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PDDocument doc = new PDDocument();
		try {
			List<EmployeePayroll> empPayAll = EmpPayTaxFactroy.findAllEmpPayroll();
			TaxAnnualForm ta = null;
			
			Map<Branch, List<EmployeePayroll>> empByBranch = groupByBranchForEmpPayroll(empPayAll);
			
			for(Branch b : empByBranch.keySet()) {
				List<EmployeePayroll> employees = empByBranch.get(b);
				
				Collections.sort(employees, new EmployeePayrollComparer());
				List<TaxAnnualForm> employees1 = new ArrayList<TaxAnnualForm>();
				
				for (EmployeePayroll emp : employees) {
					ta = new TaxAnnualForm();
					ta.setEpay(emp);
					// int type=3;
					DeductionsType type = new DeductionsType();
	
					type.setId(3);
					DeductionsDone decDone = EmpPayTaxFactroy.findDeductionDone(
							type, emp);
					if (decDone == null) {
						ta.setLifeinsurance("0");
	
					}
	
					else {
						ta.setLifeinsurance(decDone.getAmount().toString());
					}
	
					TypesData typeData = new TypesData();
					typeData.setId(21);
					Long empDep = EmpPayTaxFactroy.findEmpDepdentsWithType(
							typeData, emp.getEmployeeId());
					Long ob = 0l;
					if (empDep == ob) {
						ta.setChilderen("NA");
	
					}
	
					else {
						ta.setChilderen(empDep.toString());
					}
	
					TypesData typeData1 = new TypesData();
					typeData1.setId(20);
					EmpDependents empDep1 = EmpPayTaxFactroy
							.findEmpDepdentsWithType2(typeData1,
									emp.getEmployeeId());
	
					if (empDep1 == null) {
						ta.setSpouse("NA");
	
					}
	
					else {
						ta.setSpouse(empDep1.getOccupationType().getName());
					}
	
					employees1.add(ta);
				}
	
				int rows = employees1.size(), temp = 0, actRow = 0, start = 0, end = 0;
				int cols = 6;
				long n = (long) Math.ceil((double) rows / (double) 28);
	
				for (int i = 1; i <= n; i++) {
	
					if (rows > 28) {
	
						if (i == 1) {
							temp = rows - 28;
	
						} else {
							temp = temp - 28;
						}
	
						if (temp > 28) {
	
							actRow = 28;
	
							if (i == 1) {
								start = 0;
								end = 28;
	
							} else {
								start = end;
	
								end = end + 28;
	
							}
						} else {
	
							int check = temp + 28;
							if (check > 28)
							{
								actRow = 28;
								start = end;
								end = end + 28;
	
							} else {
								actRow = check;
	
								if (i == 1) {
									start = 0;
									end = 28;
								} else {
									start = end;
	
									end = rows;
								}
	
							}
						}
					}
	
					else {
						actRow = rows;
	
						start = 0;
	
						end = rows;
					}
	
					String[][] content = { { "Employee Id", "Employee Name", "Base Salary (MMK)",
							"Total Income (MMK)", "Taxable Income (MMK)", "Tax Amount (MMK)" } };
					PdfFactory.taxAnnualPdf(doc, actRow, cols, i, employees1, end,
							start, content);
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"TaxAnnual.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward taxMonthPdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PDDocument doc = new PDDocument();
		try {
			List<EmpPayrollMap> employees = EmpPayTaxFactroy.findTaxMonthly(0);
			EmployeeForm acfm = null;

			int rows = employees.size(), temp = 0, actRow = 0, start = 0, end = 0;
			int cols = 5;
			long n = (long) Math.ceil((double) rows / (double) 30);

			for (int i = 1; i <= n; i++) {

				if (rows > 30) {

					if (i == 1) {
						temp = rows - 30;

					} else {
						temp = temp - 30;
					}

					if (temp > 30) {

						actRow = 30;

						if (i == 1) {
							start = 0;
							end = 30;

						} else {
							start = end;

							end = end + 30;

						}
					} else {

						int check = temp + 30;
						if (check > 30)

						{
							start = end;

							end = end + 30;

						} else {
							actRow = check;

							if (i == 1) {
								start = 0;
								end = 30;
							} else {
								start = end;

								end = rows;
							}

						}
					}
				}

				else {
					actRow = rows;

					start = 0;

					end = rows;
				}

				String[][] content = { { "Id", "Name", "Designation", "Salary",
						"IncomeTaxDeducted" } };
				PdfFactory.taxMonthPdf(doc, actRow, cols, i, employees, end,
						start, content);
			}

			response.setContentType("application/zip");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"DATA.ZIP\"");

			ServletOutputStream os = response.getOutputStream();

			ZipOutputStream zos = new ZipOutputStream(os);

			// byte bytes[] = new byte[2048];

			// PDPage p=new PDPage();

			// doc.addPage(page)
			for (int i = 1; i <= 3; i++) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				doc.save(baos);

				zos.putNextEntry(new ZipEntry("taxmonth" + i + ".pdf"));

				zos.write(baos.toByteArray());
				zos.closeEntry();
				baos.flush();
				baos.close();
			}
			// doc.
			// zos.write(250);
			// doc.save(zos);

			// doc.
			// zos.write(bytes, 0, 1);
			// zos.setComment("Sraavan");

			// zos.closed=true;

			// zos.

			// baos.writeTo(os);

			// doc.close();
			// zos.closeEntry();
			// doc.save(baos);
			// baos.writeTo(os);
			zos.flush();

			zos.close();

			os.flush();
			os.close();
			// zos.closeEntry();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward empEarnsPDF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PDDocument doc = new PDDocument();
		try {
			List<EmployeePayroll> empPayAll = EmpPayTaxFactroy
					.findAllEmpPayroll();

			Map<Branch, List<EmployeePayroll>> empByBranch = groupByBranchForEmpPayroll(empPayAll);
			
			for(Branch b : empByBranch.keySet()) {
				List<EmployeePayroll> empPayroll = empByBranch.get(b);
				
				Collections.sort(empPayroll, new EmployeePayrollComparer());
				
				int rows = empPayroll.size(), temp = 0, actRow = 0, start = 0, end = 0;
				int cols = 4;
				long n = (long) Math.ceil((double) rows / (double) 28);
	
				for (int i = 1; i <= n; i++) {
	
					if (rows > 28) {
	
						if (i == 1) {
							temp = rows - 28;
	
						} else {
							temp = temp - 28;
						}
	
						if (temp > 28) {
	
							actRow = 28;
	
							if (i == 1) {
								start = 0;
								end = 28;
	
							} else {
								start = end;
	
								end = end + 28;
	
							}
						} else {
	
							int check = temp + 28;
							if (check > 28)
							{
								actRow = 28;
								start = end;
	
								end = end + 28;
	
							} else {
								actRow = check;
	
								if (i == 1) {
									start = 0;
									end = 28;
								} else {
									start = end;
	
									end = rows;
								}
	
							}
						}
					}
	
					else {
						actRow = rows;
	
						start = 0;
	
						end = rows;
					}
	
					String[][] content = { { "Employee Id", "Employee Name", "Department",
							"Netpay (MMK)" } };
					PdfFactory.empEarns(doc, actRow, cols, i, empPayroll, end,
							start, content);
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"EmployeeEarnings.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward empAcumPDF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PDDocument doc = new PDDocument();
		try {

			List<Object[]> ls = LeaveRequestFactory.findByStatusNodays(1);
			List<EmployeeForm> acl = new ArrayList<EmployeeForm>();
			EmployeeForm acfm = null;
			for (Object[] obj : ls) {
				acfm = new EmployeeForm();
				Employee e = (Employee) obj[0];
				acfm.setEmployeeId(e.getEmployeeId());
				acfm.setFirstname(e.getFirstname() + "." + e.getMiddlename());
				acfm.setBirthdate(e.getBirthdate());
				acfm.setHiredate(e.getBirthdate());
				acfm.setPayrol(EmpPayTaxFactroy.findEmpPayrollbyEmpID(e));
				acfm.setDeptId(e.getDeptId());
				acfm.setCount((Long) obj[1]);
				acl.add(acfm);
			}

			int rows = acl.size(), temp = 0, actRow = 0, start = 0, end = 0;
			int cols = 6;
			long n = (long) Math.ceil((double) rows / (double) 30);

			for (int i = 1; i <= n; i++) {

				if (rows > 30) {

					if (i == 1) {
						temp = rows - 30;

					} else {
						temp = temp - 30;
					}

					if (temp > 30) {

						actRow = 30;

						if (i == 1) {
							start = 0;
							end = 30;

						} else {
							start = end;

							end = end + 30;

						}
					} else {

						int check = temp + 30;
						if (check > 30)

						{
							start = end;

							end = end + 30;

						} else {
							actRow = check;

							if (i == 1) {
								start = 0;
								end = 30;
							} else {
								start = end;

								end = rows;
							}

						}
					}
				}

				else {
					actRow = rows;

					start = 0;

					end = rows;
				}

				String[][] content = { { "Id", "Name", "LeaveDays", "TillDate",
						"Pending", "Department" } };
				PdfFactory.employeeacumPdf(doc, actRow, cols, i, acl, end,
						start, content);
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"employeeacum.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward payRollSummaryPDF(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PDDocument doc = new PDDocument();
		try {
			List<EmployeePayroll> empPayAll = EmpPayTaxFactroy.findAllEmpPayroll();

			Map<Branch, List<EmployeePayroll>> empByBranch = groupByBranchForEmpPayroll(empPayAll);
			
			for(Branch b : empByBranch.keySet()) {
				List<EmployeePayroll> eptx = empByBranch.get(b);
				
				Collections.sort(eptx, new EmployeePayrollComparer());
				
				int rows = eptx.size(), temp = 0, actRow = 0, start = 0, end = 0;
				int cols = 7;
				long n = (long) Math.ceil((double) rows / (double) 28);
	
				for (int i = 1; i <= n; i++) {
	
					if (rows > 28) {
	
						if (i == 1) {
							temp = rows - 28;
	
						} else {
							temp = temp - 28;
						}
	
						if (temp > 28) {
	
							actRow = 28;
	
							if (i == 1) {
								start = 0;
								end = 28;
	
							} else {
								start = end;
	
								end = end + 28;
	
							}
						} else {
	
							int check = temp + 28;
							if (check > 28)
							{
								actRow = 28;
								start = end;
								end = end + 28;
	
							} else {
								actRow = check;
	
								if (i == 1) {
									start = 0;
									end = 28;
								} else {
									start = end;
	
									end = rows;
								}
	
							}
						}
					}
	
					else {
						actRow = rows;
	
						start = 0;
	
						end = rows;
					}
	
					String[][] content = { { "Employee Id", "Employee Name", "Base Salary (MMK)", "Gross (MMK)", 
							"Deductions (MMK)", "Tax (MMK)", "Net Pay (MMK)"} };
					PdfFactory.employeePayrollSummaryPDFadd(doc, actRow, cols, i,
							eptx, end, start, content);
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"PayrollSummary.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward empDepartmentPdf(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PDDocument doc = new PDDocument();
		try {
			List<Employee> employees = EmployeeFactory.findAll();
			Map<Branch, List<Employee>> empByBranch = groupByBranch(employees);
			
			for(Branch b : empByBranch.keySet()) {
				List<Employee> empOfBranch = empByBranch.get(b);
				
				Collections.sort(empOfBranch, new EmployeeComparer());
	
				Map<Department, Integer> depts = new HashMap<Department, Integer>();
				for(Employee emp : empOfBranch) {
					Department dept = emp.getDeptId();
					if( depts.containsKey(dept)) {
						Integer cntOfEmps = depts.get(dept);
						cntOfEmps++;
						depts.put(dept, cntOfEmps);
					} else {
						depts.put(dept, new Integer(1));
					}
				}
				
				int rows = empOfBranch.size(), temp = 0, actRow = 0, start = 0, end = 0;
	
				int cols = 4;
				int rowsCnt = 28 - (depts.size() + 1);
				long n = (long) Math.ceil((double) rows / (double) rowsCnt);
	
				for (int i = 1; i <= n; i++) {
	
					if (rows > rowsCnt) {
	
						if (i == 1) {
							temp = rows - rowsCnt;
	
						} else {
							temp = temp - rowsCnt;
						}
	
						if (temp > rowsCnt) {
	
							actRow = rowsCnt;
	
							if (i == 1) {
								start = 0;
								end = rowsCnt;
	
							} else {
								start = end;
	
								end = end + rowsCnt;
	
							}
						} else {
	
							int check = temp + rowsCnt;
							if (check > rowsCnt)
	
							{
								actRow = rowsCnt;
								start = end;
	
								end = end + rowsCnt;
	
							} else {
								actRow = check;
	
								if (i == 1) {
									start = 0;
									end = rowsCnt;
								} else {
									start = end;
	
									end = rows;
								}
	
							}
						}
					}
	
					else {
						actRow = rows;
	
						start = 0;
	
						end = rows;
					}
	
					String[][] content = { { "Employee Id", "Employee Name", "Branch",
							"Department" } };
					PdfFactory.empDepartmentPdf(doc, actRow, cols, i, empOfBranch,
							end, start, content, depts);
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"EmployeesByDept.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward empActivePdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PDDocument doc = new PDDocument();
		try {
			List<Employee> employees = EmployeeFactory.findAll();
			Map<Branch, List<Employee>> empByBranch = groupByBranch(employees);
			
			for(Branch b : empByBranch.keySet()) {
				List<Employee> empOfBranch = empByBranch.get(b);
				
				Collections.sort(empOfBranch, new EmployeeComparer());
	
				int rows = empOfBranch.size(), temp = 0, actRow = 0, start = 0, end = 0;
	
				int cols = 4;
				long n = (long) Math.ceil((double) rows / (double) 26);
	
				boolean firstPage = true;
				
				for (int i = 1; i <= n; i++) {
	
					if (rows > 26) {
	
						if (i == 1) {
							temp = rows - 26;
	
						} else {
							temp = temp - 26;
						}
	
						if (temp > 26) {
	
							actRow = 26;
	
							if (i == 1) {
								start = 0;
								end = 26;
	
							} else {
								start = end;
	
								end = end + 26;
	
							}
						} else {
	
							int check = temp + 26;
							if (check > 26)
	
							{
								actRow = 26;
								start = end;
	
								end = end + 26;
	
							} else {
								actRow = check;
	
								if (i == 1) {
									start = 0;
									end = 26;
								} else {
									start = end;
	
									end = rows;
								}
	
							}
						}
					}
	
					else {
						actRow = rows;
	
						start = 0;
	
						end = rows;
					}
	
					String[][] content = { { "Employee Id", "Employee Name", "Status",
							"Date*" } };
					PdfFactory.empActivePdf(doc, actRow, cols, i, empOfBranch, end,
							start, content, firstPage);
					firstPage = false;
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"EmployeeStatus.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward empAdressPdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PDDocument doc = new PDDocument();
		try {
			List<Employee> employees = EmployeeFactory.findAll();
			Map<Branch, List<Employee>> empByBranch = groupByBranch(employees);
			
			for(Branch b : empByBranch.keySet()) {
				List<Employee> empOfBranch = empByBranch.get(b);
				
				Collections.sort(empOfBranch, new EmployeeComparer());
	
				int rows = empOfBranch.size(), temp = 0, actRow = 0, start = 0, end = 0;
				int cols = 3;
				long n = (long) Math.ceil((double) rows / (double) 28);
	
				for (int i = 1; i <= n; i++) {
	
					if (rows > 28) {
	
						if (i == 1) {
							temp = rows - 28;
	
						} else {
							temp = temp - 28;
						}
	
						if (temp > 28) {	
	
							actRow = 28;
	
							if (i == 1) {
								start = 0;
								end = 28;
	
							} else {
								start = end;
	
								end = end + 28;
	
							}
						} else {
	
							int check = temp + 28;
							if (check > 28)
	
							{
								actRow = 28;
								start = end;
	
								end = end + 28;
	
							} else {
								actRow = check;
	
								if (i == 1) {
									start = 0;
									end = 28;
								} else {
									start = end;
	
									end = rows;
								}
	
							}
						}
					}
	
					else {
						actRow = rows;
	
						start = 0;
	
						end = rows;
					}
	
					String[][] content = { { "Employee Id", "Employee Name", "Address" } };
					PdfFactory.empAdressPdf(doc, actRow, cols, i, empOfBranch, end,
							start, content);
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.save(baos);

			doc.close();

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"EmployeeAddress.pdf\"");

			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private Map<Branch, List<Employee>> groupByBranch(List<Employee> employees) {
		Map<Branch, List<Employee>> grpByBranch = new HashMap<Branch, List<Employee>>();
		
		for(Employee emp : employees) {
			Branch b = emp.getDeptId().getBranchId();
			
			if(grpByBranch.containsKey(b)) {
				grpByBranch.get(b).add(emp);
			} else {
				List<Employee> emps = new ArrayList<Employee>();
				emps.add(emp);
				grpByBranch.put(b, emps);
			}
		}
		
		return grpByBranch;
	}
	
	private Map<Branch, List<EmployeePayroll>> groupByBranchForEmpPayroll(List<EmployeePayroll> employees) {
		Map<Branch, List<EmployeePayroll>> grpByBranch = new HashMap<Branch, List<EmployeePayroll>>();
		
		for(EmployeePayroll emp : employees) {
			Branch b = emp.getEmployeeId().getDeptId().getBranchId();
			
			if(grpByBranch.containsKey(b)) {
				grpByBranch.get(b).add(emp);
			} else {
				List<EmployeePayroll> emps = new ArrayList<EmployeePayroll>();
				emps.add(emp);
				grpByBranch.put(b, emps);
			}
		}
		
		return grpByBranch;
	}
}
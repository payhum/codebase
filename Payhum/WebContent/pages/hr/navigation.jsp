<%@include file="../common/jspHeader.jsp"%>


<ul id="mbmcpebul_table" class="mbmcpebul_menulist css_menu"
	style="width: 100%; height: 27px;">
	
		<li class="topitem">
		<div class="buttonbg gradient_button gradient27" style="width: 73px;">
		   <span>	<a href="<%=request.getContextPath() + "/do/HRHome"%>"> Home</a></span>
 		</div>
 	</li>
	<li class="topitem spaced_li"><div
			class="buttonbg gradient_button gradient27" style="width: auto;">
			<div class="arrow">
				<a class="button_2">Modules</a>
			</div>
		</div>
		<ul class="gradient_menu gradient95">
			<li class="gradient_menuitem gradient31 first_item"><a
				href="<%=request.getContextPath() + "/do/Employee"%>">Employees</a>
			</li>
			
			<li class="gradient_menuitem gradient31 first_item"><a
				href="<%=request.getContextPath() + "/do/holidays"%>">Holidays</a>
			</li>
			
			<li class="gradient_menuitem gradient31 last_item"><a
				href="<%=request.getContextPath() + "/do/Position"%>"> Job Titles</a>
			</li>
					
			<li class="gradient_menuitem gradient31 first_item"><a
				href="<%=request.getContextPath() + "/do/ApproveOverTime"%>">Overtime</a>
			</li>
			
			<li class="topitem spaced_li">
				<div class="buttonbg gradient_button gradient27"
					style="width: auto;">
					<div class="arrow">
						<a class="button_2" style="color: #000">Leave</a>
					</div>
				</div>

				<ul class="gradient_menu gradient95">
					<li class="gradient_menuitem gradient31"><a
						href="<%=request.getContextPath() + "/do/LeaveType"%>"> Define
							Leave Type</a></li>
					<li class="gradient_menuitem gradient31 last_item"><a
						href="<%=request.getContextPath() + "/do/ApproveLeave"%>">
							Approve Leave</a></li>
				</ul>
			</li>


			<li class="topitem spaced_li">
				<div class="buttonbg gradient_button gradient27"
					style="width: auto;">
					<div class="arrow">
						<a class="button_2" style="color: #000">Benefit Packages</a>
					</div>
				</div>

				<ul class="gradient_menu gradient95">
					<li class="gradient_menuitem gradient31 last_item"><a
						href="<%=request.getContextPath() + "/do/BenefitType"%>">
							Define Benefit Type</a></li>
					<li class="gradient_menuitem gradient31 last_item"><a
						href="<%=request.getContextPath() + "/do/Benefit"%>"> Assign
							Benefit</a></li>
				</ul>
			</li>


			<!--<li class="topitem spaced_li">
					<div class="buttonbg gradient_button gradient27" style="width: auto;">
						<div class="arrow">
							<a class="button_2" style="color:#000">Security</a>
						</div>
					</div>
					
					<ul class="gradient_menu gradient95">
						<li class="gradient_menuitem gradient31 last_item"><a href="<%=request.getContextPath() + "/do/Role"%>">
					Manage Roles</a></li>
						<li class="gradient_menuitem gradient31 last_item"><a href="<%=request.getContextPath() + "/do/User"%>">
					Manage Users</a></li>
					</ul>
				</li>  -->




		</ul></li>

	<!-- <li class="topitem"><div
			class="buttonbg gradient_button gradient27" style="width: 73px;">
			<a href="<%=request.getContextPath() + "/do/Payroll"%>">
					Payroll</a>
		</div></li> -->


	<li class="topitem"><div
			class="buttonbg gradient_button gradient27" style="width: auto;">
			<div class="arrow">
				<a class="button_2"> Reports</a>
			</div>


		</div>
		<ul class="gradient_menu gradient95">


			<li class="topitem spaced_li">
				<div class="buttonbg gradient_button gradient27"
					style="width: auto;">
					<div class="arrow">
						<a class="button_2" style="color: #000">GL Reports</a>
					</div>
				</div>

				<ul class="gradient_menu gradient95">
					<li class="gradient_menuitem gradient31"><a
						href="<%=request.getContextPath() + "/do/GLReportsEmployee"%>">
							Employee View</a></li>
					<li class="gradient_menuitem gradient31 last_item"><a
						href="<%=request.getContextPath() + "/do/GLReportsCompany"%>">
							Company View</a></li>

				</ul>
			</li>


			<li class="topitem spaced_li">
				<div class="buttonbg gradient_button gradient27"
					style="width: auto;">
					<div class="arrow">
						<a class="button_2" style="color: #000">Payroll</a>
					</div>
				</div>

				<ul class="gradient_menu gradient95">
					
					<li class="gradient_menuitem gradient31 last_item"><a
						href="<%=request.getContextPath() + "/do/PayrollSummary"%>">
							Summary</a></li>
					<li class="gradient_menuitem gradient31 last_item"><a
						href="<%=request.getContextPath() + "/do/PayrollDetails"%>">
							Details</a></li>
				</ul>
			</li>
			<li class="topitem spaced_li">
				<div class="buttonbg gradient_button gradient27"
					style="width: auto;">
					<div class="arrow">
						<a class="button_2" style="color: #000">Employee</a>
					</div>
				</div>

				<ul class="gradient_menu gradient95">
					<%-- <li class="gradient_menuitem gradient31"><a
						href="<%=request.getContextPath() + "/do/EarningsPerCompany"%>">
							Per Company</a></li> --%>
			<li class="gradient_menuitem gradient31 last_item"><a
						href="<%=request.getContextPath() + "/do/EarningsRecord"%>">
							Accumulators</a></li> 
					<li class="gradient_menuitem gradient31 last_item"><a
						href="<%=request.getContextPath() + "/do/EarningsPerDepartment"%>">
							Earnings</a></li>
				</ul>
			</li>
			<li class="gradient_menuitem gradient31 first_item"><a
				href="#"> Tax</a>
						<ul class="gradient_menu gradient95">
						
							<li class="gradient_menuitem gradient31 first_item"><a
				href="<%=request.getContextPath() + "/do/TaxAnnual"%>">
				Annual</a></li>


			<li class="gradient_menuitem gradient31 first_item"><a
				href="<%=request.getContextPath() + "/do/TaxMonthly"%>">
					Monthly</a></li>
				</ul>
				</li>


			


			<li class="gradient_menuitem gradient31 first_item"><a
				href="#">
					Miscellaneous</a>
					<ul class="gradient_menu gradient95">
					
					<li class="gradient_menuitem gradient31 first_item"><a
				href="<%=request.getContextPath() + "/do/ActiveTerminate"%>">
					Active vs Terminated</a></li>



			<li class="gradient_menuitem gradient31 first_item"><a
				href="<%=request.getContextPath() + "/do/EmployeeDept"%>">
					Employees By Dept</a></li>


			<li class="gradient_menuitem gradient31 first_item"><a
				href="<%=request.getContextPath() + "/do/EmployeesAddress"%>">
					Employees Address</a></li>

					
					</ul>
					
					</li>


		</ul></li>
	<li class="topitem"><div
			class="buttonbg gradient_button gradient27" style="width:auto;">
			<a href="<%=request.getContextPath() + "/do/SettingsHR"%>">
				Settings</a>
		</div></li>
		
		
		<li class="topitem"><div
			class="buttonbg gradient_button gradient27" style="width: auto;">
			<div>
				<a class="compLicenses">Company License</a>
			</div>
		</div></li>
		
		<li class="topitem">
		<div class="buttonbg gradient_button gradient27" style="width: auto;">
		   <span>	<a href="<%=request.getContextPath() + "/do/BranchDept"%>">Branches and Departments</a></span>
 		</div>
 	</li>
</ul>

<script>
    var compLicWindow;
	//$("#compLicenses").click(function(e){
	$("#mbmcpebul_table").delegate(".compLicenses", "click", function(e) {
		e.preventDefault();	        
      	 createNewCmpLicForm();
      	compLicWindow.open();
      	compLicWindow.center();
	});
  		 
  		 createNewCmpLicForm = function (){        	         	 
	        	if(compLicWindow)
	        		compLicWindow.content("");
	        		compLicWindow = $("#compLicensesWnd").kendoWindow({
	                 title: "",
 	                 modal : true,
	                 resizable: false,
	                 width : 650
	             }).data("kendoWindow");
	        	 
	        	 compLicWindow.open();
	        	 compLicWindow.center();
	        	 
	         }
</script>

<div class="k-content" id="compLicensesWnd" style="display:none">
	<div>
		<div >
			<div id="form">
				<div>
					<span style="display : none;"><a class="k-button k-icontext" id="cancelCmp" style="display:none !important;"><span class="k-cancel k-icon"></span>Cancel</a></span>
					<div  style="width: 630px;">
			<fieldset>
				<legend>Upload Company License File</legend>
		
		<div>
			<form method="post" action="<%=request.getContextPath() + "/do/UploadCompLicenseFile"%>" enctype="multipart/form-data">
			Upload the License file given by the Master to add/activate the Company and the License information for using this payroll application.
			<br><br>Select the file to be uploaded and click on <b>Upload</b> button.<br><br>
			    Company License file to upload: <input type="file" name="uploadFile" />
	            <br/><br/> 
	            <input class="k-button k-icontext" type="submit" value="Upload" />
	        </form>
		</div>
		</fieldset>
	</div>
				</div>

			</div>
		</div>
		
	</div>
</div>
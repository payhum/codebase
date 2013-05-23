<%@include file="../../common/jspHeader.jsp"%>
<style>
.k-textbox {
	width: 11.8em;
}

.k-grid tbody .k-button {
	min-width: 1px;
}

.required {
	font-weight: bold;
}

.accept,.status {
	padding-left: 90px;
}

.valid {
	color: green;
}

.invalid {
	color: red;
}

span.k-tooltip {
	margin-left: 6px;
}

#tick {
	display: none
}

#cross {
	display: none
}
</style>
<div class="payroll_form">
	<div id="container">
		<div style="width: 800px;">
			<fieldset>
				<legend>Run Payroll</legend>
				<div style="float: left">
					Generate the payroll for the current pay period.<br> <br>
					<b><i>NOTE: Ensure the employee status is updated before
							generating the payroll for the current pay period.</i></b> <br> <br>
					<div class="label" style="width : 95px !important;">Branch</div>
					<div class="field">
						<select id="branchName"></select>
					</div><br>
					<div>
					<div class="label" style="width : 95px !important;"><b>Currency Rates</b></div><br><br>
					<div class="label">1 USD </div>
					<div class="field">
						<input type="text" required=required class="k-input k-textbox" id="usdVal" value="" /> MMK
					</div>
					<div class="label">1 EURO</div>
					<div class="field">
						<input type="text" required=required class="k-input k-textbox" id="euroVal" value="" /> MMK
					</div>
					<div class="label">1 POUND</div>
					<div class="field">
						<input type="text" required=required class="k-input k-textbox" id="poundVal" value="" /> MMK
					</div>
					<a class="k-button k-icontext" id="processBranch">Process</a> <br> <br>
					<div class="clear"></div>
				</div>
				
				<div style="float: left; display:none" id="runPayrollDiv">	
				    <form method="post" action="<%=request.getContextPath() + "/do/GeneratePayroll"%>" enctype="multipart/form-data">
				    	<input class="k-button k-icontext" type="submit" value="Download Payroll File"/> to be sent to Master for processing.<br>
			        </form> <br>
				</div>
			</fieldset>
		</div>
		<div style="width: 800px;">
			<fieldset>
				<legend>Search Employees</legend>
				<div style="float: left">
					<div style="display: none" id="employeeHeader">

						<table cellspacing=15 style="border: 1px solid #999">
							<tr>
								<td>Photo</td>
								<td>Id #</td>
								<td>Full Name</td>
							</tr>
							<tr>
								<td><img id="photo" style="border: 2px solid #000"
									width=100 height=75 src="" /></td>
								<td><label id="empId" style="font-size: 16px;">No
										employee selected</label></td>
								<td><label id="fullNames" style="font-size: 16px;">No
										employee selected</label></td>
							</tr>
						</table>
					</div>
					<label>First Name</label> <input type="text" style="width: 350px"
						id="searchByName" />
				</div>
				<div style="float: right; padding-right: 100px">
					<input type="button" id="view_pay_summary" value="View Summary"
						style="display: none" />
				</div>
				<div style="clear: both"></div>
			</fieldset>
		</div>

		<div id="calculations" style="display: none">

			<div id="example" class="k-content">

				<fieldset>
					<legend>General Summary</legend>

					<table id="employeeSummaryGrid">
						<thead>
							<tr>
								<th>Employee National ID</th>
								<th>Gross Salary</th>
								<th>Worked for</th>

								<th>Monthly Salary Before TAX</th>
								<th>Overtime</th>
							</tr>
						</thead>




						<tbody data-template="row-template" data-bind="source: products">
						</tbody>


						<script id="row-template" type="text/x-kendo-template">
        


<tr>
								<td data-bind="text: employeeId.empNationalID">
			 <td >#=kendo.toString(grossSalary, "n2")#</td>
								 <td >#=kendo.toString(grossSalary, "n2")#</td>

							 <td >#=kendo.toString(grossSalary, "n2")#</td>
					 <td >#=kendo.toString(overtimeamt, "n2")#</td>
							</tr>
    </script>


					</table>
				</fieldset>
			</div>

			<div>
				<div style="float: left; width: 440px" id="allowances">
				<div id="exampleAllowancesList" class="k-content">
					<fieldset>
						<legend>Income Details</legend>
<table id="IncomeGrid">
						<thead>
							<tr>
								<th>Name</th>
								<th>Amount</th>
								
							</tr>
						</thead>




<tbody data-template="row-AllowancesLis"  data-bind="source: AllowancesList"></tbody>
			

						

						


							<script id="row-AllowancesLis" type="text/x-kendo-template">

  
<tr>
								<td >Allowances</td>
			 <td >#=kendo.toString(allowances, "n2")#</td>
							</tr>
<tr>
								<td >accomodation</td>
			 <td >#=kendo.toString(accomodationAmount, "n2")#</td>
							</tr>
       
<tr>
								<td >employerSS</td>

								 <td >#=kendo.toString(employerSS, "n2")#</td></tr>


<tr>
								<td >taxableOverseasIncome</td>
		
								 <td >#=kendo.toString(taxableOverseasIncome, "n2")#</td></tr>



    </script>

						
						
					</table>
					</fieldset>
					</div>
				</div>



				<div style="margin-left: 440px" id="deductions">
				<div id="exampleDeductionList" class="k-content">
					<fieldset>
						<legend>Deductions</legend>
						<table id="DeducListGrid">
						<thead>
							<tr>
								<th>Name</th>
								<th>Amount</th>
								
							</tr>
						</thead>

							

<tbody data-template="row-DeductionList" data-bind="source: DeductionList"></tbody>

							<script id="row-DeductionList" type="text/x-kendo-template">

 	
						
       <tr>
								<td >#=type.name#</td>
			 <td >#=kendo.toString(amount, "n2")#</td>
							</tr>
       
    </script>
						</table>
					</fieldset>
					</div>
				</div>
				<p style="clear: both"></p>
			</div>


			<div style="float: left; width: 440px">
					<div id="exampleNetpay" class="k-content">
				<fieldset>
					<legend>Net payable</legend>
			<table id="NetListGrid">
						<thead>
							<tr>
								<th>Name</th>
								<th>Amount</th>
								
							</tr>
						</thead>

						

<tbody data-template="row-NetpayList"  data-bind="source: NetpayLists"></tbody>
						<script id="row-NetpayList" type="text/x-kendo-template">
					
       
       <tr>
								<td >TaxableIncome</td>
			 <td >#=kendo.toString(taxableIncome, "n2")#</td>
							</tr>

<tr>
								<td >TaxAmount</td>
			 <td >#=kendo.toString(taxAmount, "n2")#</td>
							</tr>

<tr>
								<td >Netpay</td>
			 <td >#=kendo.toString(netPay, "n2")#</td>
							</tr>
    </script>
					</table>
				</fieldset>
				</div>
			</div>
			
				<div style="margin-left: 440px" id="deductions">
				<div id="exampleExempList" class="k-content">
					<fieldset>
						<legend>Exemptions</legend>
						<table id="ExempListGrid">
						<thead>
							<tr>
								<th>Name</th>
								<th>Amount</th>
								
							</tr>
						</thead>

							

<tbody data-template="row-ExempList" data-bind="source: ExempLists"></tbody>

							<script id="row-ExempList" type="text/x-kendo-template">

 	
						
       <tr>
								<td >#=type.name#</td>
			 <td >#=kendo.toString(amount, "n2")#</td>
							</tr>
       
    </script>
						</table>
					</fieldset>
					
					</div>
				</div>
		</div>
	</div>
</div>


<div id="overtimeSheet" style="display: none">

	<table id="overtimeGrid">
		<thead>
			<tr>
				<th>Day</th>
				<th>Hours</th>
				<th>Rate based on</th>
				<th>Amount</th>
			</tr>

		</thead>

		<tbody>
			<tr>
				<td><a href="#" class="otDatepicker">March 12, 2012</a></td>
				<td>3.25</td>
				<td>
					<div>
						<input type="radio" value="workday" name="otRate"
							checked="checked" /><label>Work day(1.5%)</label>
					</div>
					<div>
						<input type="radio" value="Weekend" name="otRate" /><label>Weekend(2%)</label>
					</div>
					<div>
						<input type="radio" value="Holiday" name="otRate" /><label>Holiday(2.5%)</label>
					</div>
				</td>
				<td>128.45</td>
			</tr>
			<tr>
				<td><a href="#" class="otDatepicker">Bug 08, 2012</a></td>
				<td>3.25</td>
				<td>
					<div>
						<input type="radio" value="workday" name="otRate1" /><label>Work
							day(1.5%)</label>
					</div>
					<div>
						<input type="radio" value="Weekend" name="otRate1" /><label>Weekend(2%)</label>
					</div>
					<div>
						<input type="radio" value="Holiday" name="otRate1"
							checked="checked" /><label>Holiday(2.5%)</label>
					</div>
				</td>
				<td>450</td>


			</tr>
			<tr>
				<td></td>
				<td></td>
				<td><label>Total</label></td>
				<td><input type="text" disabled="disabled"
					style="width: 60px; font-size: 18px" value="528.45" /><label>ETB</label></td>
			</tr>
		</tbody>
	</table>

</div>


<div id="payrollSettingsWindow" style="display: none">
	<div id="payrollSettingTabs">
		<ul class="list">
			<li class="k-state-active">Overtime Setting</li>
			<li>Tax Rates for Local</li>
			<li>Tax Rates for NRF</li>
 			<li>Deduction Setting</li>
			<li>Payroll period Setting</li>
		</ul>

		<div>
			<form>
				<fieldset>
					<legend>Overtime pay rate Definition</legend>
					<label>Choose day group</label><br> <select id="daygroup"></select>

					<label>% of Gross salary</label> <input type="text"
						id="percentgrup" /><br> <br> <input type="button"
						class="ratesave" value="Save" /> <input type="reset"
						class="overClear" value="Clear" />
				</fieldset>
			</form>
		</div>


		<div id="taxratesdiv">
			<div id="example" class="k-content">
				<div id="grid"></div>
				<div id="empForm"></div>
			</div>

			<form>
				<fieldset>
					<legend>Tax Rates Settings</legend>
					<label>From (MMK)</label> <input type="text" id="incomeFrom"
						disabled /><br> <label>To(MMK)</label> <input type="text"
						id="incomeTo" /><br> <input type="hidden" id="incomeFromId" /><br>
					<label>Rate </label> <select id="taxpercent">

					</select> <label> %</label><br> <br> <input type="button"
						class="taxRateSave" value="Save" /> <input type="reset"
						class="taxClear" value="Clear" />
				</fieldset>
			</form>
		</div>
		
		<div id="foriegn">
			<div id="foriegnDiv" class="k-content" style="display : none !important;" >
				<div id="foriegnTable" style="display : none !important;"></div>
				<div id="empForm"></div>
			</div>

			<form>
				<fieldset>
					<legend>Tax Rates Settings For Non Resident Foreigner</legend>
					<label style="display : none !important;">From (MMK)</label> <input type="text" id="foriegnincomeFrom"
						disabled  style="display : none !important;"/><label style="display :none !important;">To(MMK)</label> <input type="text"
						id="foriegnincomeTo" value="1"  disabled style="display:none !important;"/><input type="hidden" id="foriegnincomeFromId" />
					
					<label>All income is taxed at <span id = "forTax" ></span></label><br/>
					
					<label>To change the percentage, select the rate and update</label><br/>
					<label>Rate </label> <select id="foriegntaxpercent">

					
					</select> <label> %</label><br> <br> <input type="button"
						class="foriegntaxRateSave" value="Update" /> <input type="reset"
						class="foriegntaxClear" value="Clear" />
				</fieldset>
			</form>
		</div>


		<div class="decdiv">
			<form>
				<fieldset>
					<legend>Deduction Type Definition</legend>

					<div style="float: right;">
						<label>List of recorded Deductions</label><br> <select
							id="multiselect" class="multy"></select> <a href="#"
							class="edtdec">Edit</a> <a href="#" class="rmvdec">Remove</a>
					</div>
					<input type="hidden" id="did" />
					<div id="tickets">
						<label class="required">Name</label><br> <input type="text"
							id="decname" name="decname" class="k-textbox"
							placeholder="Deduction Name" required
							validationMessage="Please Enter Deduction" /><br> <img
							id="tick"
							src="<%=request.getContextPath() + "/css/images/tick.png"%>"
							style="margin-left: 5cm" /><br> <img id="cross"
							src="<%=request.getContextPath() + "/css/images/cross.png"%>"
							width="16" height="16" style="margin-left: 5cm" /><br> <label
							class="required">Description</label><br>
						<textarea rows="5" cols="50" id="dedctext" name="dedctext"
							class="k-textbox" placeholder="Deduction Description" required
							validationMessage="Please Enter Deduction Description "></textarea>
						<br> <br> <input type="button" class="dedc" value="Save" />
						<input type="reset" class="decClear" value="Clear" /> <span
							class="status"></span>

					</div>
				</fieldset>
			</form>
			<!-- start Deduction scipt -->

			<!-- End Deduction scipt -->
		</div>

		<div id="payPrdDiv">

			<form>
				<fieldset>
					<legend>Payroll period Definition</legend>
					<label>Choose Payroll period</label><br> <select
						id="payperiodval">

					</select> <input type="hidden" id="idPayPeriod" /> <input type="button"
						class="payPrdSave" value="Change" /> <input type="reset"
						class="payPrdClear" value="Clear" />
				</fieldset>
			</form>


		</div>

	</div>

</div>

<div id="pay_summary_cont" style="width: 650px; display: none">
	
	
	
	

	<div style="clear: both">
		<input type="button" class="print" value="Print" />
	
		<img  height="42" width="80"  src="<%=request.getContextPath() + "/css/images/companyLogo/logo.jpg"%>" /> 
	</div>
					<div id="exampleSalryListWin" class="k-content">

						<div data-template="row-SalryListWin"
							data-bind="source: SalryListWins"></div>


						<script id="row-SalryListWin" type="text/x-kendo-template">
					
       
       
<h3 style="float: right">#=employeeId.deptId.branchId.companyId.name#</h3>
<hr style="clear: both">
		<h3 style="float: right">#=employeeId.firstname#</h3>
		<hr style="clear: both">
	</div>
	<div>
		<h3 style="float: left">Basic</h3>
		<hr style="clear: both" />
	</div>
	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">Salary</p>
		</div>
		<div style="float: right">
<h3 style="float: right">#=kendo.toString(employeeId.positionId.salary, "n2")#</h3>


		</div>
		<div style="clear: both"></div>
	</div>
    </script>
		</div>		
	


	<div>
		<h3 style="float: left">Income Details</h3>
		<hr style="clear: both" />
	</div>
	
	
	
					<div id="exampleIncomeWin" class="k-content">

						<div data-template="row-IncomeWin"
							data-bind="source: IncomeWins"></div>


						<script id="row-IncomeWin" type="text/x-kendo-template">
					
              
	
	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">Allowances</p>
		</div>
		<div style="float: right">
			MMK #=kendo.toString(allowances, "n2")#
		</div>
		<div style="clear: both"></div>
	</div>

	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">Accomodationtion</p>
		</div>
		<div style="float: right">
	MMK #=kendo.toString(accomodationAmount, "n2")#
		</div>
		<div style="clear: both"></div>
	</div>
	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">taxableOverseasIncome</p>
		</div>
		<div style="float: right">
	  	MMK #=kendo.toString(taxableOverseasIncome, "n2")#
		</div>
		<div style="clear: both"></div>
	</div>

	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">employerSS</p>
		</div>
		<div style="float: right">
			 	MMK #=kendo.toString(employerSS, "n2")#
		</div>
		<div style="clear: both"></div>
	</div>
				
			
 </script>
		</div>	
	
	
	
	

	






	<div>
		<h3 style="float: left">Deductions</h3>
		<hr style="clear: both" />
	</div>
	
		<div id="exampleDeducWin" class="k-content">

						<div data-template="row-DeducWin"
							data-bind="source: DeducWins"></div>


						<script id="row-DeducWin" type="text/x-kendo-template"> 
<div>
		<div style="float:left"><p style="width:400px;font-size:13px;text-align:right">#=type.name#</p></div>
		<div style="float:right">
MMK #=kendo.toString(amount, "n2")#
</div>
		<div style="clear:both"></div>
	</div>


</script>
		</div>	


	<div>
		<h3 style="float: left">Exemptions</h3>
		<hr style="clear: both" />
	</div>
	
		<div id="exampleExempWin" class="k-content">

						<div data-template="row-ExempWin"
							data-bind="source: ExempWins"></div>


						<script id="row-ExempWin" type="text/x-kendo-template"> 
<div>
		<div style="float:left"><p style="width:400px;font-size:13px;text-align:right">#=type.name#</p></div>
		<div style="float:right">
MMK #=kendo.toString(amount, "n2")#
</div>
		<div style="clear:both"></div>
	</div>


</script>
		</div>	

	<hr>
	<div>
		<div style="float: left">
			<h3>Total</h3>
		</div>
		
		<div id="exampleTotalWin" class="k-content">

						<div data-template="row-TotalWin"
							data-bind="source: TotalWins"></div>


						<script id="row-TotalWin" type="text/x-kendo-template"> 


		<div style="float: right">
		
MMK #=kendo.toString(totalDeductions, "n2")#
		</div>
		<div style="clear: both"></div>
	</div>
	
	
	
	<hr>

	<div>
		<div style="float: left">
			<h3>Net Pay</h3>
		</div>
		<div style="float: right">
			
MMK #=kendo.toString(netPay, "n2")#
			<hr>
			<hr>
		</div>
		<div style="clear: both"></div>
	</div>

</script>
		</div>	
		
		

</div>




</div>
<style scoped>
h3 {
	font-size: 18px;
}
</style>


<script>
	function getForiegnTax(){
		$.ajax({
			 type : "POST",
			 url:'<%=request.getContextPath() + "/do/CommanTaxReatesActions?parameter=getTaxRateForForiegnTax"%>',
			 success : function(data1){
 			 	 $("#forTax").text(data1[0]+"%");
		 	 }
			
	});
	}

	var no_Of_steps = 3, first_step = 1;
	$(document).ready(function() {
		
		$("#branchName").kendoDropDownList({
			dataTextField : "name",
			dataValueField : "id",
			optionLabel: "Select Branch",
			dataSource : {
				type : "json",
				transport : {
					read : "<%=request.getContextPath() + "/do/ReadBranches"%>"
				}
			}
	    });
		
		$("#processBranch").bind("click", function() {    				
			branchId = $("#branchName").val();
			usd = $("#usdVal").val();
			euro = $("#euroVal").val();
			pound = $("#poundVal").val();
    		
			var branch = JSON.stringify({
 	   			"branchId"	  : branchId,
 	   			"usd"		  : usd,
 	   			"euro"		  : euro,
 	   			"pound"		  : pound
  			});   
 			
			if(branchId == "") {
  				alert("Select a Branch to process.");
  			} else if (usd == "" || euro == "" || pound == "") {
  				alert("Enter values for the currency conversion. It has to be set to 1 to proceed further.");
  			} else {
	 			$.ajax({
					url 		: "<%=request.getContextPath() + "/do/SaveBranchToProcess"%>",
					type 		: 'POST',
					dataType 	: 'json',
					contentType : 'application/json; charset=utf-8',
					data 		:  branch,
					success 	:  function(data){
						if(data == 1) {
							alert("No Valid License available.");
						} else if (data == 2) {
							alert("License has expired. Contact Support.");
						} else if (data == 3) {
							alert("License is tampered. Contact Support.");
						} else if (data == 4) {
							alert("Adhoc Payroll is processed, click on the Download button to download the file.");

							var ele = document.getElementById("runPayrollDiv");
							if(ele.style.display == "none") {
						    	ele.style.display = "block";
							};
						} else if (data == 5) {
							alert("Unexpected Error Occurred. Contact Support.");
						} else if (data == 6) {
							alert("Nothing to process in Adhoc Payroll.");
						} else {
							alert("Successfully processed the payroll, click on the Download button to download the file.");

							var ele = document.getElementById("runPayrollDiv");
							if(ele.style.display == "none") {
						    	ele.style.display = "block";
							};
						}
						
					},
					failure : function(e){
						alert(e.responseText);
					}
				});
  			}
		});
  			
		var  df=$("#payrollSettingsWindow").html();
	
	    var payPerdData = [
                 {text: "Weekly", value:"1"},
                 {text: "Bi-weekly", value:"2"},
                 {text: "Monthly", value:"3"}
      	];
		var percentSelect=null;
	 
	 	var rateIncome=[  {text: "1", value:"1"},
	                   {text: "2", value:"2"},
	                   
	                   {text: "3", value:"3"},
	                   {text: "4", value:"4"},
	                   
	                   {text: "5", value:"5"},
	                   
	                   {text: "6", value:"6"},
	                   {text: "7", value:"7"},
	                   
	                   {text: "8", value:"8"},
	                   {text: "9", value:"9"},
	                   
	                   {text: "10", value:"10"},
	                   
	                   {text: "11", value:"11"},
	                   
	                   {text: "12", value:"12"},
	                   {text: "13", value:"13"},
	                   {text: "14", value:"14"},
	                   {text: "15", value:"15"},
	                   
	                   {text: "16", value:"16"},
	                   
	                   {text: "17", value:"17"},
	                   
	                   {text: "18", value:"18"},
	                   
	                   {text: "19", value:"19"},
	                   {text: "20", value:"20"},
	                   
	                   {text: "21", value:"21"},
	                   
	                   {text: "22", value:"22"},
	                   
	                   {text: "23", value:"23"},
	                   
	                   {text: "24", value:"24"},
	                   
	                   {text: "25", value:"25"},
	                   {text: "26", value:"26"},
	                   {text: "27", value:"27"},
	                   {text: "28", value:"28"},
	                   {text: "29", value:"29"},
	                   {text: "30", value:"30"},
					   {text: "31", value:"31"},
	                   
	                   {text: "32", value:"32"},
	                   
	                   {text: "33", value:"33"},
	                   
	                   {text: "34", value:"34"},
	                   
	                   {text: "35", value:"35"},
	                   {text: "36", value:"36"},
	                   {text: "37", value:"37"},
	                   {text: "38", value:"38"},
	                   {text: "39", value:"39"},
	                   {text: "40", value:"40"}
	         
	                   
	                   ];  
	 

		$("#employeeSummaryGrid").kendoGrid();
	
		$("#IncomeGrid").kendoGrid();
		
		$("#DeducListGrid").kendoGrid();
		
		$("#NetListGrid").kendoGrid();
		
		
		$("#ExempListGrid").kendoGrid();
		$("#calculations_payroll_menu").kendoMenu();

		$("#showOvertimeSheet").click(function() {
 			$("#overtimeSheet").css("display", "block");
			$("#overtimeGrid").kendoGrid();
			var wnd = $("#overtimeSheet").kendoWindow({
				modal : true,
				resizable : false,
				width : 800,
				height : 250,
				title : "Overtime work hours"
			}).data("kendoWindow");
			wnd.center();
			wnd.open();
		});

		$("#editAndSave").click(function() {
			if ($(this).val("Edit")) {
				$(this).val("Save");
				$("#currentlyAssigned").removeAttr("disabled");
				$("#currentlyAssigned").css("border", "1px solid #333021");
			}
		});
		
		$("#cancelEmp").bind("click", function() { 
			 empWindow.content('');
			 empWindow.close();	                	 
         }); 

		$("#launchSettingsWindow").bind("click", function() {
			var wnd=null;
			function reuseScript(className) {
						var blr=false;
		 				$("#payrollSettingsWindow").html(df);
 						switch(className) {
						
				            case 1:
		 		            break;
				            case 2:
				            	$("ul.list li:nth-child(1)").removeClass("k-state-active");
				            	$("ul.list li:nth-child(2)").addClass("k-state-active");
				           	 	break;
				            case 3:
				            	$("ul.list li:nth-child(1)").removeClass("k-state-active");
				            	$("ul.list li:nth-child(3)").addClass("k-state-active");
				           	 	break;
				            case 4:
				            	$("ul.list li:nth-child(1)").removeClass("k-state-active");
				            	$("ul.list li:nth-child(4)").addClass("k-state-active");
				            	break;     
				            case 5:
				            	$("ul.list li:nth-child(1)").removeClass("k-state-active");
				            	$("ul.list li:nth-child(5)").addClass("k-state-active");
				            	break;
		 		        }
		 	
						$("#payrollSettingsWindow").css("display", "block");
						$("#payrollSettingTabs").kendoTabStrip({
							animation : {
								open : {
									effects : "fadeIn"
								}
							}
					
						});
					
						wnd = $("#payrollSettingsWindow").kendoWindow({
						
							width : 700,
							hieght:800,
							title : "Payroll Settings"
						}).data("kendoWindow");
		
						wnd.center();
						wnd.open();
					 
						$("#daygroup").kendoDropDownList({
							dataTextField : "dayGroupName",
							dataValueField : "id",
							optionLabel: "Select DayGroup",
							dataSource : {
								type : "json",
								transport : {
									read : "<%=request.getContextPath() + "/do/ReadOvertimePayRate"%>"
								}
							}
					    }).change(function(){
					    	var day=$("#daygroup").val();
			 				if(day!='') {		
								var rate = JSON.stringify([{
				 					"id" :day
							 	}]);
						
								$.ajax({
									 type : "POST",
									 url:'<%=request.getContextPath() + "/do/OvertimeAction"%>',
									 dataType : 'json',
									 contentType : 'application/json; charset=utf-8',
									 data : rate,
									 success : function(data1){
										 alert(data1);
										
										
										var d=data1;
										var pers=d[0].grossPercent;
										$("#percentgrup").val(pers);
										 if(data1)
										 { alert("true");}
										 else
											 {alert("false");}
										 
									 }
									
								}); 
						}
						else {
							 reuseScript();
			 			}
				 	});
					
					$("#taxratesdiv").ready(function() {
		 				$.ajax({
								 type : "POST",
								 url:'<%=request.getContextPath() + "/do/CommanTaxReatesActions?parameter=getTaxRate"%>',
		 						 success : function(data1){
		 						 	var d=data1;
			 						 $("#incomeFrom").val(d[0].incomeTo);
									 $("#incomeFromId").val(d[0].id);
							 	 }
								
						});
						$("#taxpercent").kendoDropDownList({
				                 dataTextField: "text",
				                 dataValueField: "value",
				                 optionLabel: "Select %",
				                 dataSource: rateIncome
				     	});
			             
			      		percentSelect=$("#taxpercent").data("kendoDropDownList");
					});
					
					$("#foriegn").ready(function() {
						
						getForiegnTax();
		 				$.ajax({
								 type : "POST",
								 url:'<%=request.getContextPath() + "/do/CommanTaxReatesActions?parameter=getTaxRateForForiegn"%>',
		 						 success : function(data1){
		 						 	var d=data1;
			 						 $("#foriegnincomeFrom").val(d[0].incomeTo);
									 $("#foriegnincomeFromId").val(d[0].id);
							 	 }
								
						});
						$("#foriegntaxpercent").kendoDropDownList({
				                 dataTextField: "text",
				                 dataValueField: "value",
				                 optionLabel: "Select %",
				                 dataSource: rateIncome
				     	});
			             
			      		percentSelect=$("#foriegntaxpercent").data("kendoDropDownList");
					});
					
					$("#payPrdDiv").ready(function() {
		 			 	$("#payperiodval").kendoDropDownList({
			                 dataTextField: "text",
			                 dataValueField: "value",
			                 dataSource: payPerdData
		             	});
					
						var paydrop = $("#payperiodval").data("kendoDropDownList");
		 				$.ajax({
							 type : "POST",
							 url:'<%=request.getContextPath() + "/do/CommanPayPeriodAction?parameter=getPayPeriod"%>',
		 					 success : function(data1){
		 						var d=data1;
								$("#idPayPeriod").val(d[0].id);
		 						paydrop.value(d[0].periodValue);
		 					 }
		 				});
					});
					
					var  empDataSource = new kendo.data.DataSource({
			        	transport : {
			           		read : {
			           			url:'<%=request.getContextPath() + "/do/CommanTaxReatesActions?parameter=getAllTaxRates"%>',
			           			dataType : "json",
			           			cache : false
			           		}
				         },
			           	pageSize: 3,
			           	autoSync : true,
			           	batch : true 
			        });
			       
					var foriegnempDataSource = new kendo.data.DataSource({
			        	transport : {
			           		read : {
			           			url:'<%=request.getContextPath() + "/do/CommanTaxReatesActions?parameter=getAllTaxRatesForiegn"%>',
			           			dataType : "json",
			           			cache : false
			           		}
				         },
			           	pageSize: 3,
			           	autoSync : true,
			           	batch : true 
			        });
			       
			        
					var grid=$("#grid").kendoGrid({
			            dataSource : empDataSource,
			            pageable: true,
			            columns: [
		                          { hidden:true,field:"id", title: "id" ,width: "1px"},
		                          { field: "incomeFrom", title:"From", width: "30px" },
		                          { field: "incomeTo", title:"To",  template : "#= incomeTo = incomeFrom ? incomeTo : 'Above' #", width: "30px" },
		                          { field: "incomePercentage", title:"%",width: "30px" },
		                          { command: [{"name" : "edit", text:"", className : "editEmp "}, {"name" :"destroy", text:"", className:"delTax"}], title: "", width: "50px" }],
			                      
			  
			        }).data("kendoGrid");
					
					
					var grid1 = $("#foriegnTable").kendoGrid({
			            dataSource : foriegnempDataSource,
			            pageable: true,
			            columns: [
		                          { hidden:true,field:"id", title: "id" ,width: "1px"},
		                          { field: "incomeFrom", title:"From", width: "30px" },
		                          { field: "incomeTo", title:"To",  template : "#= incomeTo = incomeFrom ? incomeTo : 'Above' #", width: "30px" },
		                          { field: "incomePercentage", title:"%",width: "30px" },
		                          { command: [{"name" : "edit", text:"", className : "editForiegnTaxRate"}, {"name" :"destroy", text:"", className:"delForiegnTax"}], title: "", width: "50px" }],
			                      
			  
			        }).data("kendoGrid");
					 
					$("#grid").delegate(".delTax", "click", function(e) {
		 				e.preventDefault();
					 	var dataItem = grid.dataItem($(this).closest("tr"));
					  
					   	percentSelect.value(dataItem.incomePersent);
						$("#incomeTo").val(dataItem.incomeTo).attr('disabled', 'disabled');;
						$("#incomeFromId").val(dataItem.id).attr('disabled', 'disabled');;
						
						$("#incomeFrom").val(dataItem.incomeFrom);
						 
						var taxpercnt=$("#taxpercent").val();
						var incomeTo=$("#incomeTo").val();
						var idTax= $("#incomeFromId").val();
						
						var incomeFrom=$("#incomeFrom").val();
						
						var rate = JSON.stringify([{
							"id":idTax,
							"incomeFrom" :incomeFrom,
							"incomeTo":incomeTo,
							"incomePersent":taxpercnt
							}]);
						  $.ajax({
			      				 type : "POST",
			      				url:'<%=request.getContextPath() + "/do/CommanTaxReatesActions?parameter=deleteTaxRate"%>',
			     				 dataType : 'json',
			     				 contentType : 'application/json; charset=utf-8',
			     				 data : rate,
			     				 success : function(datas){ 
			     					 //alert(datas);
			     				 
			     					reuseScript(2);
			     				 }	        				
			     			});
					});
				
					
					$("#foriegnTable").delegate(".delForiegnTax", "click", function(e) {
		 				e.preventDefault();
 					 	var dataItem = grid1.dataItem($(this).closest("tr"));
					  
					   	percentSelect.value(dataItem.incomePersent);
						$("#foriegnincomeTo").val(dataItem.incomeTo).attr('disabled', 'disabled');;
						$("#foriegnincomeFromId").val(dataItem.id).attr('disabled', 'disabled');;
						
						$("#foriegnincomeFrom").val(dataItem.incomeFrom);
						 
						var taxpercnt=$("#foriegntaxpercent").val();
						var incomeTo=$("#foriegnincomeTo").val();
						var idTax= $("#foriegnincomeFromId").val();
						
						var incomeFrom=$("#foriegnincomeFrom").val();
						
						var rate = JSON.stringify([{
							"id":idTax,
							"incomeFrom" :incomeFrom,
							"incomeTo":incomeTo,
							"incomePersent":taxpercnt
							}]);
						  $.ajax({
			      				 type : "POST",
			      				url:'<%=request.getContextPath() + "/do/CommanTaxReatesActions?parameter=deleteTaxRate"%>',
			     				 dataType : 'json',
			     				 contentType : 'application/json; charset=utf-8',
			     				 data : rate,
			     				 success : function(datas){ 
			     					// alert(datas);
			     				 
			     					reuseScript(3);
			     				 }	        				
			     			});
					});
				
					
					
					
					
					$("#grid").delegate(".editEmp", "click", function(e) {
		 				e.preventDefault();
 			        	var dataItem = grid.dataItem($(this).closest("tr"));
		                percentSelect.value(dataItem.incomePersent);
		      
						$("#incomeTo").val(dataItem.incomeTo).attr('disabled', 'disabled');;
						$("#incomeFromId").val(dataItem.id).attr('disabled', 'disabled');;
		 				$("#incomeFrom").val(dataItem.incomeFrom);
		 				$(".taxRateSave").val('Update');
		                $(".editTaxPercent").bind("click", function() { 
			             	  var  id = $(".eperid").val(); 
			            	  var  incEdit = $(".incomePersentEdit").val(); 
			             	  var editData = JSON.stringify([{
			  					"id" : id,
			  					"incomePersent" : incEdit,
			  				  }]);
			             	  $.ajax({
			      				 type : "POST",
			      				url:'<%=request.getContextPath()
								+ "/do/CommanTaxReatesActions?parameter=upDateTaxRate"%>',
			     				 dataType : 'json',
			     				 contentType : 'application/json; charset=utf-8',
			     				 data : editData,
			     				 success : function(datas){ 
			      					reuseScript(2);
			     				 }	        				
			     			});	 
		                });
		            	 
					}); 
					
					
					
					$(".taxClear").bind("click", function() { 
		 				 reuseScript(2);
		 			});
					
					$(".taxRateSave").bind("click", function() { 
   			 			var taxpercnt=$("#taxpercent").val();
						var incomeTo=$("#incomeTo").val();
						var idTax= $("#incomeFromId").val();
						var taxbutton=$(".taxRateSave").val();
						var incomeFrom=$("#incomeFrom").val();
						
		 				if(incomeFrom==''||incomeTo==''||taxpercnt=='-1'|| incomeFrom > incomeTo) {
			 				if(taxbutton=="Update") {
			 					perFlag=true;
							}
		 					else {		
			 					alert("fill data correctly");
								return false;
							}
		 				}
						else {
		 					if(taxbutton=="Update") {
		 						perFlag=true;
							 }
						}
						var rate = JSON.stringify([{
							"id":idTax,
							"incomeFrom" :incomeFrom,
							"incomeTo":incomeTo,
							"incomePersent":taxpercnt
						}]);
						
						if(taxbutton=="Save") {
				 			$.ajax({
									 type : "POST",
									 url:'<%=request.getContextPath() + "/do/TaxRatesAction"%>',
									 dataType : 'json',
									 contentType : 'application/json; charset=utf-8',
									 data : rate,
									 success : function(data1){
		 							 	reuseScript(2);
		 							 }
		 						});
		 				}
					
						if(perFlag) {
		 					if(taxbutton=="Update") {
		 				  	  $.ajax({
				      				 type : "POST",
				      				url:'<%=request.getContextPath() + "/do/CommanTaxReatesActions?parameter=upDateTaxRate"%>',
				     				 dataType : 'json',
				     				 contentType : 'application/json; charset=utf-8',
				     				 data : rate,
				     				 success : function(datas){ 
		 		     					reuseScript(2);
				     				 }	        				
				     			});	 
		 					}
					  }
							
				 }); 
					
					
					$("#foriegnTable").delegate(".editForiegnTaxRate", "click", function(e) {
 		 				e.preventDefault();
 			        	var dataItem = grid1.dataItem($(this).closest("tr"));
		                percentSelect.value(dataItem.incomePersent);
		      
						$("#foriegnincomeTo").val(dataItem.incomeTo).attr('disabled', 'disabled');;
						$("#foriegnincomeFromId").val(dataItem.id).attr('disabled', 'disabled');
		 				$("#foriegnincomeFrom").val(dataItem.incomeFrom);
		 				$(".foriegntaxRateSave").val('Update');
		                $(".editTaxPercent").bind("click", function() { 
			             	  var  id = $(".eperid").val(); 
			            	  var  incEdit = $(".incomePersentEdit").val(); 
			            	   
			             	  var editData = JSON.stringify([{
			  					"id" : id,
			  					"incomePersent" : incEdit,
			  				  }]);
			             	  $.ajax({
			      				 type : "POST",
			      				url:'<%=request.getContextPath()
								+ "/do/CommanTaxReatesActions?parameter=upDateTaxRate"%>',
			     				 dataType : 'json',
			     				 contentType : 'application/json; charset=utf-8',
			     				 data : editData,
			     				 success : function(datas){ 
			      					reuseScript(3);
			     				 }	        				
			     			});	 
		                });
		            	 
					}); 
					
					$(".foriegntaxClear").bind("click", function() { 
		 				 reuseScript(3);
		 			});
					
					$(".foriegntaxRateSave").bind("click", function() { 
  			 			var taxpercnt=$("#foriegntaxpercent").val();
						var incomeTo=$("#foriegnincomeTo").val();
						var idTax= $("#foriegnincomeFromId").val();
						var taxbutton=$(".foriegntaxRateSave").val();
						var incomeFrom=$("#foriegnincomeFrom").val();
 						
		 				
						var rate = JSON.stringify([{
							"id":idTax,
							"incomeFrom" :incomeFrom,
							"incomeTo":incomeTo,
							"incomePersent":taxpercnt
						}]);
 						if(taxbutton=="Update") {
  				 			$.ajax({
									 type : "POST",
									 url:'<%=request.getContextPath() + "/do/ForiegnTaxRatesAction"%>',
									 dataType : 'json',
									 contentType : 'application/json; charset=utf-8',
									 data : rate,
									 success : function(data1){
		 							 	reuseScript(3);
		 							 }
		 						});
		 				}
					
				 }); 
					
				 var dnameup;
				 $(".ratesave").bind("click", function() { 
						var day=$("#daygroup").val();
						var grossPercent=$("#percentgrup").val();
						var rate = JSON.stringify([{
		 					"id" :day,
							"grossPercent":grossPercent
		 				 }]);
						 if(day!='') {
							 $.ajax({
								 type : "POST",
								 url:'<%=request.getContextPath() + "/do/UpdateOvertimeAction"%>',
								 dataType : 'json',
								 contentType : 'application/json; charset=utf-8',
								 data : rate,
								 success : function(data1){
			 					 	reuseScript();
			 					 }
			 				});
		 				}
						else {
							alert("please select other one");
						 	reuseScript();
		 				}
		 	  }); 	
			  $(".payPrdSave").bind("click", function() {  
		 			var idPayPeriod=$("#idPayPeriod").val();
					var payperiodval =$("#payperiodval :selected").val();
		 			var payperiodText =$("#payperiodval :selected").text();
		 			var rate = JSON.stringify([{
						"id":idPayPeriod,
						"periodValue" :payperiodval,
						"periodName":payperiodText
		 			 }]);
		 			$.ajax({
						 type : "POST",
							url:'<%=request.getContextPath()
							+ "/do/CommanPayPeriodAction?parameter=upDatePayPeriod"%>',
						 dataType : 'json',
						 contentType : 'application/json; charset=utf-8',
						 data :rate,
						 success : function(data1){
		 					 if(data1)
								 {alert("tue");}
							 else
								 {}
							 reuseScript(4);
						 }
		 			});
		 	});
			
			  $("#multiselect").kendoDropDownList({
						dataTextField : "name",
						dataValueField : "id",
						optionLabel: "Select Deduction",
						dataSource : {
							type : "json",
							transport : {
								read : "<%=request.getContextPath() + "/do/ReadDeductionAction"%>"
							}
						}
		       }); 
			   
			   $(".edtdec").bind("click", function() { 	
					var ae=$("#multiselect").val(); 
					var updateData1 = JSON.stringify([{
		 				"id" :ae
		 			 }]);
				     $.ajax({
						 type : "POST",
						 url:'<%=request.getContextPath() + "/do/DeductionGet"%>',
						 dataType : 'json',
						 contentType : 'application/json; charset=utf-8',
						 data : updateData1,
						 success : function(data1){
		 					 var d=data1;
		 					 var ids=d[0].id;
		 					 var nam=d[0].name;
							  var tax=d[0].description;
		 					  $("#decname").val(nam);
							  $("#dedctext").val(tax);
							  dnameup=nam;
							  $("#did").val(ids);
							  $(".dedc").val("Update");
							  $("#decname").css('border', '1px #000 solid');
								$('#cross').hide();
							  var validator = $("#tickets").kendoValidator().data("kendoValidator"),
			                  status = $(".status");
							  if (validator.validate()) {
			                      status.text("")
			                          .removeClass("invalid")
			                          .addClass("valid");
			                      
			                      
			                  } else {
			                      status.text("")
			                          .removeClass("valid")
			                          .addClass("invalid");
			                      return false;
			                      
			                  }
						 }
		 			}); 
			 }); 
		 
			$(".rmvdec").bind("click", function() { 
				var aa=$("#multiselect").val();
		 	 	if(aa=='') {
					alert("fill data ");
					return false;
				}
				var updateData1 = JSON.stringify([{
					 "id" :aa
			 	}]);
				$.ajax({
							 type : "POST",
							 url:'<%=request.getContextPath() + "/do/DeleteDeductionAction"%>',
							 dataType : 'json',
							 contentType : 'application/json; charset=utf-8',
							 data : updateData1,
							 success : function(data1){
		 						 if(data1)
									 {alert("Sucessfully deleted");}
								 else
									 {alert("Cannot delete as the deduction is already used.");}
								 reuseScript(3);
							 }
							
				}); 
			});
					
			$(".overClear").bind("click", function() { 
				reuseScript();
			});
			
			$(".decClear").bind("click", function() { 
				reuseScript(3);
			});
					
			$(".dedc").bind("click", function() { 
						  var validator = $("#tickets").kendoValidator().data("kendoValidator"),
		                  status = $(".status");
						  if (validator.validate()) {
		                      status.text("")
		                          .removeClass("invalid")
		                          .addClass("valid");
		                      
		                      
		                  } else {
		                      status.text("")
		                          .removeClass("valid")
		                          .addClass("invalid");
		                      return false;
		                      
		                  }
				 
				var dedcname=$("#decname").val();
		 		var dedctxt=$("#dedctext").val();
				var id=$("#did").val();
				var idf=$(".dedc").val();
		 		var updateData = JSON.stringify([{
		 			"name" : dedcname,
					"description" : dedctxt,
					"id":id
		 		 }]);       
		 		if(idf=="Update") {
		 			$.ajax({
						 type : "POST",
						 url:'<%=request.getContextPath() + "/do/UpdateDeductionAction"%>',
						 dataType : 'json',
						 contentType : 'application/json; charset=utf-8',
						 data : updateData,
						 success : function(data){
		 					 if(data) {
		 						 alert("Data UpDate ");
								 reuseScript(3);
		 					}
							else {
								 alert("Data not UpDate");
							}
		 				 }
		 			}); 
		 		}
			 
				if(idf=="Save"&&!blr) {
					$.ajax({
						 type : "POST",
						 url:'<%=request.getContextPath() + "/do/DeductionAction"%>',
						 dataType : 'json',
						 contentType : 'application/json; charset=utf-8',
						 data : updateData,
						 success : function(data){
			 				 if(data){
			 					 alert("Data insert");
								 reuseScript(3);
							 }
							 else {
								 alert("Data not insert");
							}
			 			 }
		 			}); 
				}
				
				else if(idf=="Save") {
					 alert("Please choose another one ");
		 		}
		 	 });
			 
			$("#decname").bind("keyup",function() {
		  			var dedcname=$(this).val();
		 			var updateData = JSON.stringify([{
		 				"name" : dedcname,
		 			 }]); 
		 			if(dedcname!=''&&dedcname.length > 0&&dedcname!=dnameup) {
			 			$.ajax({
							 type : "POST",
							 url:'<%=request.getContextPath() + "/do/CheckDeductionName"%>',
							 dataType : 'json',
							 contentType : 'application/json; charset=utf-8',
							 data : updateData,
							 success : function(datas){
			 					 if(datas)  {
									  	$("#decname").css('border', '3px #C33 solid').focus();	
										$('#tick').hide();
										$('#cross').fadeIn();
										blr=true;
								
								 	alert("please chose another one");
						          }
								  else {
									  $("#decname").css('border', '3px #090 solid');
										$('#cross').hide();
										$('#tick').fadeIn();
										blr=false;
								  }
		 					 }
		 				});
					}
					else{
		 				$(this).css('border', '3px #CCC solid');
						$('#tick').hide();
		 			}
						
			 });
 		} 
		reuseScript();
 	});
		
    $("#clearSearch").click(function(){
 			$("#searchByName").data("kendoAutoComplete").placeholder("Start typing");
			$("#employeeHeader").css("display", "none");
			$("#calculations").css("display", "none");
        	$("#view_pay_summary").css("display", "none");
    });
	var jsonObject;
	var jsonExemp;
	var jsonDeduc;
	$("#searchByName").kendoAutoComplete({
 	        dataSource: new kendo.data.DataSource({
	            transport: {
	                read: "<%=request.getContextPath() + "/do/AccountPayrollAtuo"%>"
				}
			}),
			select : function(e) {
 				var dataItem = this.dataItem(e.item.index());
				jsonObject=dataItem;
 				$("#employeeHeader").css("display", "block");
				$("#calculations").css("display", "block");
				$("#view_pay_summary").css("display", "block");
 			    $("#empId") .text(dataItem.employeeId.employeeId);
			    $("#fullNames") .text(dataItem.employeeId.firstname + ' ' + dataItem.employeeId.middlename + ' ' + dataItem.employeeId.lastname);
			    $("#photo").attr("src", "/OpenHR" + dataItem.photo);
			    var viewModel = kendo.observable({
 					products : dataItem
				});
				kendo.bind($("#example"), viewModel);
				var AllowancesList = kendo.observable({
											AllowancesList : dataItem
									});
				kendo.bind($("#exampleAllowancesList"),AllowancesList);
					 var array = dataItem.deductionsDone;
					 var decTxt = "[";
					for ( var i = 0; i < array.length; i++) {
 						if (i == array.length - 1) {
							decTxt = decTxt+ kendo.stringify(dataItem.deductionsDone[i]);
						} else {
							decTxt = decTxt+ kendo.stringify(dataItem.deductionsDone[i])+ ",";
						}

					}
					decTxt = decTxt + "]";
					var sss = decTxt;
 					var result = JSON.parse(sss);
					 jsonDeduc=JSON.parse(sss);
 					var DeducsList = kendo.observable({
						DeductionList : result
					});
					kendo.bind($("#exampleDeductionList"),DeducsList);
					
						var NetpayList = kendo.observable({
							NetpayLists : dataItem
						});
						kendo.bind($("#exampleNetpay"), NetpayList);
							var arrayExemp = dataItem.exemptionsDone;
							var excemTxt = "[";
							for ( var i = 0; i < arrayExemp.length; i++) {
 
								if (i == arrayExemp.length - 1) {
									excemTxt = excemTxt + kendo.stringify(dataItem.exemptionsDone[i]);
								} 
								else {
									excemTxt = excemTxt+ kendo.stringify(dataItem.exemptionsDone[i])+ ",";
								}
 							}
							excemTxt = excemTxt + "]";
												
							var exs = excemTxt;
 							var resultExemp = JSON.parse(exs);
												
							jsonExemp=JSON.parse(exs);
 							var ExempList = kendo.observable({
								ExempLists : resultExemp
							});
							kendo.bind($("#exampleExempList"),ExempList);
				},
				dataTextField : "fullName",
				placeholder : 'Start typing',
				template : kendo.template($("#searchByNameAutoComplete").html())
	 });

	$("#printPaySlip").click(function() {

			$(".information_msg").css("display", "none");
			$("input[type=button]").css("display","none");
			$("a").css("display", "none");
			$("#calculations").jqprint();
			setTimeout(function() {
					$(".information_msg").css("display", "block");
					$("input[type=button]").css("display", "block");
					$("a").css("display", "block");
			}, 5000);
	});

	$("#view_pay_summary").click(function() {
		$("#pay_summary_cont").css("display", "block");
		$("#pay_summary_cont").kendoWindow({
				modal : true,
				title : "Pay Summary"
		});
		$("#pay_summary_cont").data("kendoWindow").center();
		$("#pay_summary_cont").data("kendoWindow").open();
 		var SalryListWin= kendo.observable({
			SalryListWins : jsonObject
		});
		kendo.bind($("#exampleSalryListWin"),SalryListWin);
 			var IncomeWin= kendo.observable({
				IncomeWins : jsonObject
			});
			kendo.bind($("#exampleIncomeWin"),IncomeWin);
 			var DeducWin= kendo.observable({
						DeducWins : jsonDeduc
			});
			kendo.bind($("#exampleDeducWin"),DeducWin);
 			var ExempWin= kendo.observable({
				ExempWins : jsonExemp
			});
			kendo.bind($("#exampleExempWin"), ExempWin);
 			var TotalWin= kendo.observable({
				TotalWins : jsonObject
			});
			kendo.bind($("#exampleTotalWin"), TotalWin);
 		});
		 
		$("#editAllowances").click( function() {
			var allowanceEditor = $("#allowances").clone();
			$(allowanceEditor) .kendoWindow({
				modal : true,
				resizable : false,
				title : "Allowances editor window"
			});

			$("div.k-widget input[type='text']").removeAttr("disabled");
			$("div.k-widget input#editAllowances").val("Save");
			$("div.k-widget input#editAllowances").click(function() {
				if (confirm('Are you sure you want to save changes \nYes if you want to proceed'+ '\nNo if you want to cancel')) {
						alert('Your changes are saved successfully!');
 						alert(JSON.stringify(allowanceEditor));
					$(allowanceEditor).data("kendoWindow").close();
				} 
				else {
					$(allowanceEditor).data("kendoWindow").close();
				}
			});
			$(allowanceEditor).data("kendoWindow").center();
			$(allowanceEditor).data("kendoWindow").open();
		});
		
		function PrintDiv() {    
			$(".print").hide();
			var divToPrint = document.getElementById('pay_summary_cont');
			var popupWin = window.open('', '_blank', 'width=300,height=300');
			popupWin.document.open();
			popupWin.document.write('<html><body onload="window.print()">' + divToPrint.innerHTML + '</html>');
		    popupWin.document.close();
		}
		$(".print").bind("click",function(){
			PrintDiv();
			$(".print").show();
		});
	});
</script>


<script id="searchByNameAutoComplete" type="text/x-kendo-tmpl"> 	

<table>
	<tr>
	    
     
<td><p style="font-family:'Century Gothic'">#=employeeId.firstname+' '+employeeId.middlename+' '+employeeId.lastname#</p></td>    
    </tr>
</table>	 
</script>

<style>
img.image_preview {
	border: 2px solid #fff;
}

#prevousStep,#nextStep {
	padding: 4px;
}

#prevousStep {
	float: left;
}

#nextStep {
	float: right;
}
</style>

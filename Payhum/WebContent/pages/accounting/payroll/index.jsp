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
					<a href="<%=request.getContextPath() + "/do/GeneratePayroll"%>">
						<input type="button" value="Run Payroll" />
					</a>
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
								<td><label id="fullName" style="font-size: 16px;">No
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

			<div class="k-content">

				<fieldset>
					<legend>General Summary</legend>

					<table id="employeeSummaryGrid">
						<thead>
							<tr>
								<th>Employee ID</th>
								<th>Gross Salary</th>
								<th>Worked for</th>

								<th>Monthly Salary Before TAX</th>
								<th>Overtime</th>
							</tr>
						</thead>

						<tbody>
							<tr>
								<td>MODETH-0001/2012</td>
								<td><input disabled="disabled" style="width: 60px"
									type="text" value="5500" />ETB</td>
								<td><input disabled="disabled" style="width: 60px"
									type="text" value="20" />days<br> <a href="#">View
										Time sheet</a></td>

								<td><input disabled="disabled" type="text"
									style="width: 60px" value="4500" />ETB</td>
								<td><input disabled="disabled" style="width: 60px"
									type="text" value="528.45" />ETB<br> <a href="#"
									id="showOvertimeSheet">View Overtime sheet</a></td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</div>

			<div>
				<div style="float: left; width: 440px" id="allowances">
					<fieldset>
						<legend>Allowances</legend>
						<p class="information_msg"
							style="font-size: 14px; padding: 5px; background-color: #006699; color: #fff; border: 1px solid yellow">
							UnCheck on <i>Allowance</i> to exculde from payroll
						</p>
						<div style="padding: 10px;">
							<div style="float: left; width: 200px">
								<input type="checkbox" checked="checked" value="0" />Transport
								Allowance<br> <input type="text" value="1000"
									disabled="disabled" />
							</div>

							<div style="margin-left: 200px">
								<input type="checkbox" checked="checked" value="0" />House
								Allowance<br> <input type="text" value="2000"
									disabled="disabled" />
							</div>
							<p style="clear: both"></p>
						</div>

						<div style="padding: 10px;">
							<div style="float: left; width: 200px">
								<input type="checkbox" checked="checked" value="0" />Bonus<br>
								<input type="text" value="550" disabled="disabled" />
							</div>

							<div style="margin-left: 200px"></div>
							<p style="clear: both"></p>
						</div>

						<div style="padding: 10px;">
							<div style="float: left; width: 200px">
								<h2 style="font-size: 20px">Sub total</h2>
								<input type="text" style="width: 80px; font-size: 18px"
									value="3550" disabled="disabled" />ETB
							</div>

							<div style="margin-left: 200px">


								<input type="button" value="Edit" id="editAllowances" />

							</div>
							<p style="clear: both"></p>
						</div>
					</fieldset>
				</div>



				<div style="margin-left: 440px" id="deductions">
					<fieldset>
						<legend>Deductions</legend>
						<p class="information_msg"
							style="font-size: 14px; padding: 5px; background-color: #006699; color: #fff; border: 1px solid yellow;">
							un check on <i>Deduction</i> to exclude from payroll
						</p>
						<div style="padding: 10px;">
							<div style="float: left; width: 200px">
								<input type="checkbox" checked="checked" value="0" />Pension(6%)<br>
								<input type="text" value="300" disabled="disabled" />
							</div>

							<div style="margin-left: 200px">
								<input type="checkbox" checked="checked" value="0" />Income
								TAX(15%)<br> <input type="text" value="950"
									disabled="disabled" />
							</div>
							<p style="clear: both"></p>
						</div>

						<div style="padding: 10px;">
							<div style="float: left; width: 200px">
								<input type="checkbox" checked="checked" value="0" />Staff
								Loan(10%)<br> <input type="text" value="550"
									disabled="disabled" />
							</div>

							<div style="margin-left: 200px"></div>
							<p style="clear: both"></p>
						</div>

						<div style="padding: 10px;">
							<div style="float: left; width: 200px">
								<h2 style="font-size: 20px">Sub total</h2>
								<input type="text" style="width: 80px; font-size: 18px"
									value="1800" disabled="disabled" />ETB
							</div>

							<div style="margin-left: 200px">


								<input type="button" value="Edit" id="editDeductions" />

							</div>
							<p style="clear: both"></p>
						</div>
					</fieldset>
				</div>
				<p style="clear: both"></p>
			</div>


			<div style="float: left; width: 440px">
				<fieldset>
					<legend>Net payable</legend>
					<div style="padding: 10px;">
						<div style="float: left; width: 200px">

							<input type="text" style="width: 80px; font-size: 18px"
								value="6250" disabled="disabled" /><label>ETB</label>
						</div>

						<p style="clear: both"></p>
					</div>

				</fieldset>
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
				<td><a href="#" class="otDatepicker">Aug 08, 2012</a></td>
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
			<li>Tax Rates Setting</li>
			<li>Deduction Type Setting</li>
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
		<input type="button" value="Print" />
	</div>
	<div>
		<h3 style="float: right">Anthony Williams</h3>
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
			<strong style="color: #0f0; font-style: underline; font-size: 16px;">2569.56</strong>
		</div>
		<div style="clear: both"></div>
	</div>




	<div>
		<h3 style="float: left">Allowances</h3>
		<hr style="clear: both" />
	</div>
	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">House</p>
		</div>
		<div style="float: right">
			<strong style="color: #0f0; font-style: underline; font-size: 16px;">251.26</strong>
		</div>
		<div style="clear: both"></div>
	</div>

	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">Fuel</p>
		</div>
		<div style="float: right">
			<strong style="color: #0f0; font-style: underline; font-size: 16px;">452</strong>
		</div>
		<div style="clear: both"></div>
	</div>

	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">Representation</p>
		</div>
		<div style="float: right">
			<strong style="color: #0f0; font-style: underline; font-size: 16px;">236.9</strong>
		</div>
		<div style="clear: both"></div>
	</div>
	<hr>






	<div>
		<h3 style="float: left">Deductions</h3>
		<hr style="clear: both" />
	</div>
	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">Income
				Tax(35%)</p>
		</div>
		<div style="float: right">
			<strong style="color: #0f0; font-style: underline; font-size: 16px;">652.26</strong>
		</div>
		<div style="clear: both"></div>
	</div>

	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">Pension(6%)</p>
		</div>
		<div style="float: right">
			<strong style="color: #0f0; font-style: underline; font-size: 16px;">142.45</strong>
		</div>
		<div style="clear: both"></div>
	</div>
	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">Staff
				Loan</p>
		</div>
		<div style="float: right">
			<strong style="color: #0f0; font-style: underline; font-size: 16px;">520.25</strong>
		</div>
		<div style="clear: both"></div>
	</div>
	<hr>
	<div>
		<div style="float: left">
			<h3>Total</h3>
		</div>
		<div style="float: right">
			<strong style="color: #0f0; font-style: underline; font-size: 18px;">2397.56</strong>
		</div>
		<div style="clear: both"></div>
	</div>
	<hr>

	<div>
		<div style="float: left">
			<h3>Net Pay</h3>
		</div>
		<div style="float: right">
			<strong style="color: #0f0; font-style: underline; font-size: 18px;">1500.56</strong>
			<hr>
			<hr>
		</div>
		<div style="clear: both"></div>
	</div>


</div>


<style scoped>
h3 {
	font-size: 18px;
}
</style>


<script>
	var no_Of_steps = 3, first_step = 1;
	$(document).ready(function() {
		//var   empTemplate= kendo.template($("#employeeTemplate").html());
		
		//alert(empTemplate);
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
	                   {text: "30", value:"30"}
	         
	                   
	                   ];  
	//var  decdiv=$(".decdiv").html();
	//alert(df);

	
	
		$("#employeeSummaryGrid").kendoGrid();
		$("#calculations_payroll_menu").kendoMenu();

		$("#showOvertimeSheet").click(function() {
			
			//alert("fdf");
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
		function reuseScript(className)
		
		{
			var blr=false;
			//alert("hello");
		$("#payrollSettingsWindow").html(df);
		
		//$("ul.list li:nth-child(1)").removeClass("k-state-active");
		
		
		//var f=$("ul.list li:nth-child(3)").addClass("k-state-active");
		
		//var className = 1;
		
		switch(className)
        {
            case 1:
            	//alert('1!');
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
            default:
                
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
			   //alert("The text has been changed.-------"+day);
		if(day!='')
			{		var rate = JSON.stringify([{
				
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
				
			}); }
		else
			{
			 reuseScript();
			
			}
		
			
			  });
			
			$("#taxratesdiv").ready(function()
					{
				//alert("taxratesdiv");

				$.ajax({
						 type : "POST",
						 url:'<%=request.getContextPath()
					+ "/do/CommanTaxReatesActions?parameter=getTaxRate"%>',
						
						
						 success : function(data1){
						// alert("hello-----"+data1);
						 var d=data1;
						 
						 $("#incomeFrom").val(d[0].incomeFrom);
						 $("#incomeFromId").val(d[0].id);
						 
						// reuseScript(2);
							
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
		$("#payPrdDiv").ready(function()
				
		
					{
				
			
		 $("#payperiodval").kendoDropDownList({
                 dataTextField: "text",
                 dataValueField: "value",
                 dataSource: payPerdData
             });
			
			var paydrop = $("#payperiodval").data("kendoDropDownList");
	
			
				$.ajax({
					 type : "POST",
						url:'<%=request.getContextPath()
					+ "/do/CommanPayPeriodAction?parameter=getPayPeriod"%>',
					
					
					 
					 success : function(data1){
						// alert(data1);
						
						var d=data1;
						$("#idPayPeriod").val(d[0].id);
						
						
						
						
				//$("#payperiodval :selected").text(d[0].periodName);
						//$("#payperiodval").val(d[0].periodValue);
						
						
						paydrop.value(d[0].periodValue);
						
						
						
					 }
					
				});
				
				
					});
			var   empDataSource = new kendo.data.DataSource({
	        	transport : {
	           		read : {
	           			url:'<%=request.getContextPath()
					+ "/do/CommanTaxReatesActions?parameter=getAllTaxRates"%>',
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
                          { field:"id",hidden:true, title: "id" ,width: "1px"},
                          { field: "incomeFrom", title:"From", width: "30px" },
                          { field: "incomeTo", title:"To", width: "30px" },
                          { field: "incomePersent", title:"%",width: "30px" },
                          { command: [{"name" : "edit", text:"", className : "editEmp"}, {"name" :"destroy", text:"", className:"delTax"}], title: "", width: "50px" }],
	                      
	  
	        }).data("kendoGrid");
			//$("#GRID_ID").find("table th").eq(COLUMN_NO).hide();
			$("#grid").delegate(".delTax", "click", function(e) {
				
				
				//("delTax");
				
			e.preventDefault();
			 var dataItem = grid.dataItem($(this).closest("tr"));
			   //alert(dataItem.id+"-----"+dataItem.incomePersent);     
			  // alert(dataItem.incomePersent);
			   
			  // 	$("#taxpercent").val(dataItem.incomePersent);
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
	      				url:'<%=request.getContextPath()
					+ "/do/CommanTaxReatesActions?parameter=deleteTaxRate"%>',
	     				 dataType : 'json',
	     				 contentType : 'application/json; charset=utf-8',
	     				 data : rate,
	     				 success : function(datas){ 
	     					 alert(datas);
	     					 
	     					
	                    	 
	     					//wnd2.close();
	     					
	     					//$("#employeeTemplate").html('');
	     					reuseScript(2);
	     				 }	        				
	     			});
			});
			

			$("#grid").delegate(".editEmp", "click", function(e) {
	//alert("hello");
				e.preventDefault();
	//alert(e+"eeeeeeeeeee"+grid);
	        var dataItem = grid.dataItem($(this).closest("tr"));
	        	       // alert(dataItem.id+"-----"+dataItem.incomePersent);      
      //    alert(empTemplate+"empTemplate");
         //   var wnd2 = $("#empForm").kendoWindow({
                 ///   title: "Employee Details",
                 //   modal: true, 
                 //  resizable: false,
	                // width : 700
              //  }).data("kendoWindow");          
            
				  
          //   wnd2.content(empTemplate(dataItem));
              // $(".cancelTax").click(function() { 
            	 // wnd2.content('');
               //	 wnd2.close();	                	 
             //  }); 
              // wnd2.open(); 
              // wnd2.center();
               
               	percentSelect.value(dataItem.incomePersent);
      
			$("#incomeTo").val(dataItem.incomeTo).attr('disabled', 'disabled');;
			$("#incomeFromId").val(dataItem.id).attr('disabled', 'disabled');;
			
			$("#incomeFrom").val(dataItem.incomeFrom);
               
		
			$(".taxRateSave").val('Update');
               
               //alert( $("#editTaxPercent").bind("click"));
               
               $(".editTaxPercent").bind("click", function() { 
            	 //  alert("hellort");
            	  var  id = $(".eperid").val(); 
            	  var  incEdit = $(".incomePersentEdit").val(); 
            	  
            	  var editData = JSON.stringify([{
  					"id" : id,
  					"incomePersent" : incEdit,
  				
  				 }]);
            	  
            		//var f=$("#employeeTemplate").html();
            		//alert(f);
            	  $.ajax({
      				 type : "POST",
      				url:'<%=request.getContextPath()
					+ "/do/CommanTaxReatesActions?parameter=upDateTaxRate"%>',
     				 dataType : 'json',
     				 contentType : 'application/json; charset=utf-8',
     				 data : editData,
     				 success : function(datas){ 
     					 alert(datas);
     					 
     					
                    	 
     					//wnd2.close();
     					
     					//$("#employeeTemplate").html('');
     					reuseScript(2);
     				 }	        				
     			});	 
            	  
          
                   
               });
            	   
            	   
            	   
}); 
			
			$(".taxClear").bind("click", function() { //alert("hello");

				 reuseScript(2);
				 //alert("hello");
			});
			
			$(".taxRateSave").bind("click", function() { 
		//alert("taxpercentincomeToincomeFrom");
			var taxpercnt=$("#taxpercent").val();
			var incomeTo=$("#incomeTo").val();
			var idTax= $("#incomeFromId").val();
			var taxbutton=$(".taxRateSave").val();
			var incomeFrom=$("#incomeFrom").val();
	//alert(incomeFrom > incomeTo);
	
	
	
			if(incomeFrom==''||incomeTo==''||taxpercnt=='-1'|| incomeFrom > incomeTo)
				{
				//alert("in if"+taxbutton);
				
				if(taxbutton=="Update")
					
				{
					//alert("hxkjgzxj");
					perFlag=true;
					
					//return true;
				}
				
				else
					{		
					
					alert("fill data correctly");
					return false;
					}

				
				
				}
			else
				{
				
if(taxbutton=="Update")
					
				{
					//alert("hxkjgzxj");
					perFlag=true;
					
					//return true;
				}
				}
			var rate = JSON.stringify([{
				"id":idTax,
				"incomeFrom" :incomeFrom,
				"incomeTo":incomeTo,
			"incomePersent":taxpercnt
				
				
			 }]);
	if(taxbutton=="Save")
				
			{
			//alert("savbe");
			$.ajax({
					 type : "POST",
					 url:'<%=request.getContextPath() + "/do/TaxRatesAction"%>',
					 dataType : 'json',
					 contentType : 'application/json; charset=utf-8',
					 data : rate,
					 success : function(data1){
					 alert(data1);
						
					 reuseScript(2);
						
					 }
					
				});
				
				}
			
			
			
			if(perFlag)
				{
				
				
	//alert(taxbutton);
			if(taxbutton=="Update")
			
			{
				//alert("Update");
			  	  $.ajax({
	      				 type : "POST",
	      				url:'<%=request.getContextPath()
					+ "/do/CommanTaxReatesActions?parameter=upDateTaxRate"%>',
	     				 dataType : 'json',
	     				 contentType : 'application/json; charset=utf-8',
	     				 data : rate,
	     				 success : function(datas){ 
	     					 alert(datas);
	     					 
	     					
	                    	 
	     					//wnd2.close();
	     					
	     					//$("#employeeTemplate").html('');
	     					reuseScript(2);
	     				 }	        				
	     			});	 
	            	  
			}
			
				
				
		
				}
					
			}); 
			
			
			
			
			var dnameup;
			
			
			
			
			$(".ratesave").bind("click", function() { //alert("edit");
				var day=$("#daygroup").val();
				var grossPercent=$("#percentgrup").val();
				var rate = JSON.stringify([{
					
					"id" :day,
					"grossPercent":grossPercent
					
					
				 }]);
				
				
				if(day!='')
				{
				$.ajax({
					 type : "POST",
					 url:'<%=request.getContextPath() + "/do/UpdateOvertimeAction"%>',
					 dataType : 'json',
					 contentType : 'application/json; charset=utf-8',
					 data : rate,
					 success : function(data1){
					 alert(data1);
						
					 reuseScript();
						
					 }
					
				});
				
				}
				
				else
				{
					alert("please select other one");
				 reuseScript();
				
				}
				
				}); 	
				
	
			
			
			$(".payPrdSave").bind("click", function() { //alert("hello");
			
			var idPayPeriod=$("#idPayPeriod").val();
			var payperiodval =$("#payperiodval :selected").val();
			
			var payperiodText =$("#payperiodval :selected").text();
			//alert(payperiodval+"---Ratet---"+payperiodText);
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
					// alert(data1);
					 if(data1)
						 {alert("tue");}
					 else
						 {alert("false");}
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
			
			
			
			$(".edtdec").bind("click", function() {// alert("edit"); 	
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
				//	 alert(data1);
					 var d=data1;
					// alert(d[0].name);
					 
					 var ids=d[0].id;
					 
					 var nam=d[0].name;
					  var tax=d[0].description;
					 // alert(ids+"---"+nam+"---"+tax);
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
				//alert("remove----"+aa); 
				
				if(aa=='')
					{
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
						// alert(data1);
						 if(data1)
							 {alert("Sucessfuully deleted");}
						 else
							 {alert("alredy in Use");}
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
				  
				  
				  
				  
		//alert("helllo");
		var dedcname=$("#decname").val();
		
		var dedctxt=$("#dedctext").val();
		var id=$("#did").val();
		var idf=$(".dedc").val();
		
		var updateData = JSON.stringify([{
			
			"name" : dedcname,
			"description" : dedctxt,
			"id":id
			
		 }]);       
		
		//alert(dnameup+"dnameup");
		
		if(idf=="Update")
			
			{
			//alert("Update");
		
			$.ajax({
				 type : "POST",
				 url:'<%=request.getContextPath() + "/do/UpdateDeductionAction"%>',
				 dataType : 'json',
				 contentType : 'application/json; charset=utf-8',
				 data : updateData,
				 success : function(data){
					 alert(data);
					 
					 if(data)
						 {
						 
						 alert("Data UpDate ");
						 reuseScript(3);
				 
				///	wnd.restore();
					alert(""+f);	
						
						 }
					 else
						 {
						 alert("Data not UpDate");
						 }
					 
				 }
				
			}); }
		
		else
			{
			
			
			}
	
		//alert(blr+"----blr");
		if(idf=="Save"&&!blr)
		{	$.ajax({
			 type : "POST",
			 url:'<%=request.getContextPath() + "/do/DeductionAction"%>',
			 dataType : 'json',
			 contentType : 'application/json; charset=utf-8',
			 data : updateData,
			 success : function(data){
				 alert(data);
				 
				 if(data)
					 {
					 
					 alert("Data insert");
					 reuseScript(3);
					 }
				 else
					 {
					 alert("Data not insert");
					 }
				 
			 }
			
		}); }
		
		else if(idf=="Save")
			{
			 alert("please chose another one ");
			
			}
		
	
			});
			
			//var dedcnamecheck=$("#decname").val();	

			$("#decname").bind("keyup",function()
			
				{
				
				//alert("keyup");
			var dedcname=$(this).val();
			
			
			
			var updateData = JSON.stringify([{
				
				"name" : dedcname,
				
				
			 }]); 
			//alert("---"+dedcname);
			if(dedcname!=''&&dedcname.length > 0&&dedcname!=dnameup)
			{
			
			
			$.ajax({
				 type : "POST",
				 url:'<%=request.getContextPath() + "/do/CheckDeductionName"%>',
				 dataType : 'json',
				 contentType : 'application/json; charset=utf-8',
				 data : updateData,
				 success : function(datas){
				// alert(data);
				 if(datas) //if username not avaiable
				  {
					  	$("#decname").css('border', '3px #C33 solid').focus();	
						$('#tick').hide();
						$('#cross').fadeIn();
						blr=true;
				
				 alert("please chose another one");
		          }
				  else
				  {
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
			
		
			//alert("he");
			
			
		 
			
			reuseScript();
			
				
				
				//$(".multiselect").html('');
				
		});
		
		$("#clearSearch").click(function(){
			
$("#searchByName").data("kendoAutoComplete").placeholder("Start typing");
			$("#employeeHeader").css("display", "none");
			$("#calculations").css("display", "none");
        	$("#view_pay_summary").css("display", "none");
		});
		
		$("#searchByName").kendoAutoComplete({
		
	        dataSource: new kendo.data.DataSource({
	            transport: {
	                read: "<%=request.getContextPath() + "/do/ReadEmployeeAction"%>"
														}
													}),
											select : function(e) {
												alert("hello");
												////////////////////////////////////////////////////
												var dataItem = this
														.dataItem(e.item
																.index());
												////////////////////////////////////////////////////
												$("#employeeHeader").css(
														"display", "block");
												$("#calculations").css(
														"display", "block");
												$("#view_pay_summary").css(
														"display", "block");
												////////////////////////////////////////////////////
												$("#empId").text(
														dataItem.employeeId);
												$("#fullName")
														.text(
																dataItem.firstname
																		+ ' '
																		+ dataItem.middlename
																		+ ' '
																		+ dataItem.lastname);
												$("#photo")
														.attr(
																"src",
																"/OpenHR"
																		+ dataItem.photo);
												//output selected dataItem
												alert(kendo.stringify(dataItem));       
											},
											dataTextField : "firstname",
											placeholder : 'Start typing',
											template : kendo
													.template($(
															"#searchByNameAutoComplete")
															.html())
										});

						$("#printPaySlip").click(
								function() {

									$(".information_msg")
											.css("display", "none");
									$("input[type=button]").css("display",
											"none");
									$("a").css("display", "none");
									$("#calculations").jqprint();
									setTimeout(function() {
										$(".information_msg").css("display",
												"block");
										$("input[type=button]").css("display",
												"block");
										$("a").css("display", "block");
									}, 5000);
								});

						$("#view_pay_summary").click(
								function() {
									$("#pay_summary_cont").css("display",
											"block");
									$("#pay_summary_cont").kendoWindow({
										modal : true,
										title : "Pay Summary"
									});
									$("#pay_summary_cont").data("kendoWindow")
											.center();
									$("#pay_summary_cont").data("kendoWindow")
											.open();
								});

						$("#editAllowances")
								.click(
										function() {
											var allowanceEditor = $(
													"#allowances").clone();
											$(allowanceEditor)
													.kendoWindow(
															{
																modal : true,
																resizable : false,
																title : "Allowances editor window"
															});

											$("div.k-widget input[type='text']")
													.removeAttr("disabled");

											$(
													"div.k-widget input#editAllowances")
													.val("Save");
											$(
													"div.k-widget input#editAllowances")
													.click(
															function() {
																if (confirm('Are you sure you want to save changes \nYes if you want to proceed'
																		+ '\nNo if you want to cancel')) {
																	alert('Your changes are saved successfully!');
																	//iterate the new value set from the modal window
																	alert(JSON
																			.stringify(allowanceEditor));
																	$(
																			allowanceEditor)
																			.data(
																					"kendoWindow")
																			.close();
																} else {
																	$(
																			allowanceEditor)
																			.data(
																					"kendoWindow")
																			.close();
																}
															});
											$(allowanceEditor).data(
													"kendoWindow").center();
											$(allowanceEditor).data(
													"kendoWindow").open();

										});
					});
</script>


<script id="searchByNameAutoComplete" type="text/x-kendo-tmpl"> 	
<table>
	<tr>
	    <td><img width=75 height=60 class='image_preview'  src="#='/OpenHR'+photo#"/></td>
        <td><p style="font-family:'Century Gothic'">#=firstname+' '+middlename+' '+lastname#</p></td>    
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
<script type="text/x-kendo-template" id="employeeTemplate">
				
	<div class="field">
<div class="label">%Of Income</div>
				<input type="hidden" class="eperid" value="#=id#"/>
                
				<input type="text" class="k-input k-textbox incomePersentEdit"   value="#=incomePersent#" />
			</div>
			<div class="clear"></div>

		<div>
			<div class="field">
				<a class="k-button k-icontext editTaxPercent" ><span class="k-icon k-update"></span>Update</a> <a
					class="k-button k-icontext cancelTax"><span class="k-icon k-cancel"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
		</div>
</script>


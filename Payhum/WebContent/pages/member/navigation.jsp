<%@include file="../common/jspHeader.jsp"%>
<div style="padding-top: 5px;">	
	<div style="float:right">
		<input id="accountSettings" type="button" value="Account"/>
		<input id="view_pay_summary" type="button" value="PaySlip"/>
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

<script>
$(function(){
	
	var jsonObject;

	var jsonDeduc;

	var jsonExemp;
	
	
	$.ajax({
		type: 'POST',
		 url:'<%=request.getContextPath()
+ "/do/EmployeeCommanAction?parameter=getEmpPayMentDetails"%>',
		 dataType : 'json',
		 contentType : 'application/json; charset=utf-8',
		 cache: false,
		 success : function(data){
			//alert(data.id);
			jsonObject=data;
			
			var array = data.deductionsDone;
			 var decTxt = "[";
			for ( var i = 0; i < array.length; i++) {
				if (i == array.length - 1) {
					decTxt = decTxt+ kendo.stringify(jsonObject.deductionsDone[i]);
				} else {
					decTxt = decTxt+ kendo.stringify(jsonObject.deductionsDone[i])+ ",";
				}

			}
			decTxt = decTxt + "]";
			var sss = decTxt;
			
			 jsonDeduc=JSON.parse(sss);
			
			
			 var arrayExemp = jsonObject.exemptionsDone;
				var excemTxt = "[";
				for ( var i = 0; i < arrayExemp.length; i++) {

					if (i == arrayExemp.length - 1) {
						excemTxt = excemTxt + kendo.stringify(jsonObject.exemptionsDone[i]);
					} 
					else {
						excemTxt = excemTxt+ kendo.stringify(jsonObject.exemptionsDone[i])+ ",";
					}
				}
				excemTxt = excemTxt + "]";
									
				var exs = excemTxt;
				var resultExemp = JSON.parse(exs);
									
				jsonExemp=JSON.parse(exs);
			
			
		 }
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





<script>
	$("#accountSettings").click(function(e){
		$("#accountSettingsWnd").css("display","block");
		var accountSettingWnd = $("#accountSettingsWnd").kendoWindow({
			modal : true,
			title : "Account Settings" 
		}).data("kendoWindow");
		accountSettingWnd.center();
		accountSettingWnd.open();
	});	
</script>
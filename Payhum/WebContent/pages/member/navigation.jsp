<%@include file="../common/jspHeader.jsp"%>
<div style="padding-top: 5px;">	
	<div style="float:right">
		<input id="accountSettings" type="button" value="Account"/>
		<input id="view_pay_summary" type="button" value="PaySlip"/>
	</div>	
</div> 

<div id="pay_summary_cont" style="width: 650px; display: none">
	
	<div id="payDateDiv">
	
	SelectDates<input class="payRollDatesDropDownList" id="payRollDates"/>
	<a class="k-button k-icontext" id="saveEmp"><span class="k-add k-icon"></span>Search</a> <a
					class="k-button k-icontext" id="cancelEmp"><span class="k-cancel k-icon"></span>Cancel</a>
					
					
		</div>			
<div id="paySlipDiv">
	<div style="clear: both">
		<input type="button" class="print" value="Print" />
	
<img  height="42" width="80"  src="<%=request.getContextPath() + "/css/images/logo.jpg"%>" /> 
	</div>
					<div id="exampleSalryListWin" class="k-content">

						<div data-template="row-SalryListWin"
							data-bind="source: SalryListWins"></div>


						<script id="row-SalryListWin" type="text/x-kendo-template">
					
       
       <h3 style="float: left">Company </h3>
<h3 style="float: right">#=emppayId.employeeId.deptId.branchId.companyId.name#</h3>
<hr style="clear: both">
		
	</div>
	<div>
		<h3 style="float: left">Name </h3>
<h3 style="float: right">#=emppayId.employeeId.firstname+"."+emppayId.employeeId.firstname#</h3>
		<hr style="clear: both" />
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
			<p style="width: 400px; font-size: 13px; text-align: right">NetPay</p>
		</div>
		<div style="float: right">
			MMK #=kendo.toString(netPay, "n2")#
		</div>
		<div style="clear: both"></div>
	</div>

	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">TaxAmont</p>
		</div>
		<div style="float: right">
	MMK #=kendo.toString(taxAmount, "n2")#
		</div>
		<div style="clear: both"></div>
	</div>
	<div>
		<div style="float: left">
			<p style="width: 400px; font-size: 13px; text-align: right">Social Security</p>
		</div>
		<div style="float: right">
	  	MMK #=kendo.toString(socialSec, "n2")#
		</div>
		<div style="clear: both"></div>
	</div>

	<div>
		
		<div style="clear: both"></div>
	</div>
				
			
 </script>
		</div>	
	
	
	
	

	






	

	

</div>	

</div>

<script>
$(function(){
	
	var croppingData;
	
	var jsonObject;

	var jsonDeduc;

	var jsonExemp;
	
	$("#paySlipDiv").hide();
	$("#saveEmp").bind("click",function(){
		var payRollDates=$("#payRollDates").val();
		 croppingData = JSON.stringify({
		 		"id":payRollDates,
			
		});
		
		//alert("hello"+payRollDates);
		
		
		
		
		
		

		$.ajax({
			type: 'POST',
		
			 url:'<%=request.getContextPath()
	+ "/do/EmployeeCommanAction?parameter=getEmpPayMentDetails"%>',

			 dataType : 'json',
			 contentType : 'application/json; charset=utf-8',
			 cache: false,
			 data 		: croppingData,
			 success : function(data){
				//alert(data.id);
				jsonObject=data;
				
				$("#payDateDiv").hide();
				$("#paySlipDiv").show();
				var SalryListWin= kendo.observable({
					SalryListWins : jsonObject
				});
				kendo.bind($("#exampleSalryListWin"),SalryListWin);
		 			var IncomeWin= kendo.observable({
						IncomeWins : jsonObject
					});
					kendo.bind($("#exampleIncomeWin"),IncomeWin);
		 			var DeducWin= kendo.observable({
								DeducWins : jsonObject
					});
					kendo.bind($("#exampleDeducWin"),DeducWin);
		 			var ExempWin= kendo.observable({
						ExempWins : jsonObject
					});
					kendo.bind($("#exampleExempWin"), ExempWin);
		 			var TotalWin= kendo.observable({
						TotalWins : jsonObject
					});
					kendo.bind($("#exampleTotalWin"), TotalWin);
				
			 }
		});
		
		
		
		
	});
	var payRollDatesDataSource1= new kendo.data.DataSource({
		  
		  transport : {

		read : {
		type: 'POST',
		 url:'<%=request.getContextPath()+ "/do/CommantypesAction?parameter=getPayRollDates"%>',
		 dataType : 'json',
		 contentType : 'application/json; charset=utf-8',
		 cache: false,
		 complete : function(jqXHR, textStatus){
			 //alert(jqXHR.responseText);
	     }
	}

		  },
	autoSync : true,
		batch : true 
		
	});
	$(".payRollDatesDropDownList").kendoDropDownList({
		  
		  dataTextField : "runDate",
			dataValueField : "id",
			 optionLabel:"Select Date",
			dataSource :payRollDatesDataSource1,
			
			
	 }).data("kendoDropDownList");
	
	

	
	
	
	
	$("#view_pay_summary").click(function() {
		$("#pay_summary_cont").css("display", "block");
		$("#pay_summary_cont").kendoWindow({
				modal : true,
				title : "Pay Summary"
		});
		$("#pay_summary_cont").data("kendoWindow").center();
		$("#pay_summary_cont").data("kendoWindow").open();
		
		$("#payDateDiv").show();
		$("#payRollDates").data("kendoDropDownList").value('');	    
		
	
 		
		$("#paySlipDiv").hide();
		
		
 		});
	
	
	
	function PrintDiv() {    
		$(".print").hide();
		var divToPrint = document.getElementById('paySlipDiv');
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
    var empWindow;
	$("#accountSettings").click(function(e){
		e.preventDefault();	        
      	 createNewCmpForm();
  		 empWindow.open();
  		 empWindow.center();
	});
  		 
  		 createNewCmpForm = function (){        	         	 
	        	if(empWindow)
	        		empWindow.content("");
	        	 	empWindow = $("#accountSettingsWnd").kendoWindow({
	                 title: "",
 	                 modal : true,
	                 resizable: false,
	                 width : 650
	             }).data("kendoWindow");
	        	 
	        	 empWindow.open();
	        	 empWindow.center();
	        	 
	         };
	         
  		 
  		 
  		 
		/* $("#accountSettingsWnd").css("display","block");
		var accountSettingWnd = $("#accountSettingsWnd").kendoWindow({
			modal : true,
			title : "Account Settings" 
		}).data("kendoWindow");
		accountSettingWnd.center();
		accountSettingWnd.open(); */
 </script>
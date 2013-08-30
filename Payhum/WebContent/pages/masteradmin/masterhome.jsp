<%@include file="../common/jspHeader.jsp"%>
<script type="text/javascript"  src="/javascript/jquery_min.js"></script>

<div class="master_form">

	<div  style="width: 800px;">
			<fieldset>
				<legend>Upload Client Company File</legend>
		
		<div>
			<form method="post" action="<%=request.getContextPath() + "/do/UploadFile"%>" enctype="multipart/form-data">
			Upload the Payroll file for the current month from a client company here. <br>Select the file to be uploaded and click on <b>Upload</b> button.<br><br>
			    Select Client Company file to upload: <input type="file" name="uploadFile" />
	            <br/><br/> 
	            <input class="k-button k-icontext" type="submit" value="Upload" />
	        </form>
		</div>
		</fieldset>
	</div>

	<div  style="width: 800px;">
			<fieldset>
				<legend>Generate Files for Government and Bank</legend>
			Select the Client Company from the list here and click <b>Process</b> to process the selected company payroll and then download the required file.
		<br><br>
		<div>
					<div class="label">Company</div>
					<div class="field">
						<input id="compDropDownList" />
					</div>
					<div class="clear"></div>
				</div> <br>

				<div id="dp1">
					<div class="label">Branch</div>
					<div class="field">
						<input class="branchDropDownList" id="branchVal" />
				<a class="k-button k-icontext" id="processComp">Process</a> <br> <br>
							</div>
					<div class="clear"></div>
				</div>
		
		
			
		<div style="display:none" id="genBankDiv">	
		    <form method="post" action="<%=request.getContextPath() + "/do/GenerateBankFile"%>" enctype="multipart/form-data">
		    	<input class="k-button k-icontext" type="submit" value="Download Bank File"/> to be sent to Bank for crediting the Salary to Employees.<br>
	        </form> <br>
		</div>
		
		<div style="display:none" id="genCashDiv">	
		    <form method="post" action="<%=request.getContextPath() + "/do/GenerateCashFile"%>" enctype="multipart/form-data">
		    	<input class="k-button k-icontext" type="submit" value="Download Cash File"/> to be used to pay salary by cash for employees who do not have bank accounts.<br>
	        </form> <br>
		</div>
		
		<div style="display:none" id="genGovtDiv">
			<form method="post" action="<%=request.getContextPath() + "/do/GenerateGovtFile"%>" enctype="multipart/form-data">
		    	<input class="k-button k-icontext" type="submit" value="Download Tax File"/> to be sent to IRD office to inform the Tax being credited on behalf of the Employees of the Company.
	        </form> <br>
		</div>

		<div style="display:none" id="genSSDiv">
			<form method="post" action="<%=request.getContextPath() + "/do/GenerateSSGovtFile"%>" enctype="multipart/form-data">
		    	<input class="k-button k-icontext" type="submit" value="Download Social Security File"/> to be sent to Social Security office to inform the Social Security amount being credited on behalf of the Employees of the Company.
	        </form> <br>
		</div>
		
</fieldset>
</div>			
</div>

<script>
			$(document).ready(function(){
				 $("#compDropDownList").kendoDropDownList({
					  
					  dataTextField : "companyId.name",
						dataValueField : "companyId.id",
						optionLabel: "Select Company",
						dataSource :{
							type : "json",
							transport : {
								read : "<%=request.getContextPath() + "/do/ReadCompanyAction"%>"
							}
						}
						
				  }).data("kendoDropDownList");
				 
				 $("#dp1").hide();
			});
			
			$("#compDropDownList").change( function() {
				$(".branchDropDownList").html('');
				$("#dp1").show();
				//alert("value"+$(this).val());
				var dId = $(this).val();
				
				var CompData = JSON.stringify({
					"compId":dId 
				 });  
				
				var branchDataSource1 = new kendo.data.DataSource({
	                	 transport : {
	                    		read : {
	                    			type: 'POST',
	                    			 url:'<%=request.getContextPath()
				+ "/do/CommantypesAction?parameter=getAllBranchesOfComp"%>',
			        				 dataType : 'json',
			        				 contentType : 'application/json; charset=utf-8',
			        				 cache: false,
			        				 
			        		},
	                     
	                    		parameterMap: function (data, type) {
	                    			if(type = "read"){
	                    			//	alert(BrachData+"hello updateData");
	                    				return CompData;
	                    			}
	                    			
	                    		}
	                    	},
	                   
	                      
	                       	autoSync : true,
	                       	batch : true 
	                    });
				
				 
				  
				$(".branchDropDownList").kendoDropDownList({
					dataTextField : "name",
					dataValueField : "id",
					optionLabel: "Select Branch",
					dataSource :branchDataSource1
				
			       }).data("kendoDropDownList");
			});
			
			$("#processComp").bind("click", function() {    				
      			branchId = $("#branchVal").val();
        		
      			if (branchId == "") {
      				alert("Select a Branch to process.");
			    } else {
	     			var comp = JSON.stringify({
	     	   			"branchId"    : branchId
	      			});   
	     			
	     			$.ajax({
						url 		: "<%=request.getContextPath() + "/do/SaveCompanyToProcess"%>",
						type 		: 'POST',
						dataType 	: 'json',
						contentType : 'application/json; charset=utf-8',
						data 		:  comp,
						success 	:  function(){
							alert("Successfully processed the data, click on the Download buttons to download the files.");
							
							var ele = document.getElementById("genBankDiv");
							if(ele.style.display == "none") {
						    	ele.style.display = "block";
							};
							
							var ele = document.getElementById("genCashDiv");
							if(ele.style.display == "none") {
						    	ele.style.display = "block";
							};
							
							var ele = document.getElementById("genGovtDiv");
							if(ele.style.display == "none") {
						    	ele.style.display = "block";
							};
							
							var ele = document.getElementById("genSSDiv");
							if(ele.style.display == "none") {
						    	ele.style.display = "block";
							};
						},
						failure : function(e){
							alert(e.responseText);
						}
					});
      			}
  });	

</script>
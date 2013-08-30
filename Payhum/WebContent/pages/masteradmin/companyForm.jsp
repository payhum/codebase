<%@page import="com.openhr.employee.EmployeeIdUtility"%>
<%@page import="com.openhr.Config"%>
<%Config.readConfig();%>
<div id="employeeForm">
	<div class="clear"></div>
	<div style = "font-size : 30px !important;">Create Company  </div><br/>
	<div class="clear"></div>
	<div id="left-col">
		<div>
			<div class="label" style="width : 135px !important;">CompanyId</div>
			<div class="field">
				<input type="text" readonly="readonly" class="k-input k-textbox" id="companyId"  
				value=  "<%= "C-"+ EmployeeIdUtility.companyNextId() %>"  />
			</div>
			<div class="clear"></div>
		</div>


		<div>
			<div class="label" style="width : 135px !important;">Name</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="companyName"  value="" />
			</div>
			<div class="clear"></div>
		</div>
 
		<div>
			<div class="label" style="width : 135px !important;">Address</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="address"  value="" />
			</div>
			<div class="clear"></div>
		</div>
		
		<div>
			<div class="label" style="width : 135px !important;">Financial Year Start Date</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="fystart"  value="" />
			</div>
			<div class="clear"></div>
		</div>
		
		<br/>
		
		<div>
			<div class="label" style="width : 135px !important;">License Valid From</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="fromDate"  value="" />
			</div>
			<div class="clear"></div>
		</div><br/>
		
		<div>
			<div class="label" style="width : 135px !important;">License Valid To</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="toDate"  value="" />
			</div>
			<div class="clear"></div>
		</div><br/>
  
		<div>
			<div class="field">
				<a class="k-button k-icontext" id="saveCmp"><span class="k-add k-icon"></span>Save</a> 
				<a class="k-button k-icontext" id="cancelCmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div></div> 


<script> 
			function hi(){
				
				empDataSource = new kendo.data.DataSource({
                	transport : {
                   		read : {
                   			url : "<%=request.getContextPath() + "/do/ReadCompanyAction"%>",
                   			dataType : "json",
                   			cache : false,
                    		}
                   	},
                    autoSync : true,
                   	batch : true 
                });
            	
            	var grid = $("#grid").kendoGrid({            	             	 
                    dataSource		:  empDataSource,
                    height			: 430,
                    sortable		: true,
                    pageable		: true,
                    detailTemplate	: kendo.template($("#template").html()),
                    detailInit		: detailInit,       
    	            toolbar : [{"name" : "create", className : "newCmp", text : "Add New Company" }, {"name" : "delete", className : "deleteCmp displayClass", text : "Delete Company" }, {"name" : "edit", className : "editCmp displayClass", text : "Edit Company" }, {"name" : "addBranch", className : "addBranch displayClass", text : "Add Branch" }, {"name" : "addDept", className : "addDept displayClass", text : "Add Department" }, {"name" : "license", className : "editLicense displayClass", text : "License Renewal" }],

                     columns : [
									{ template:'#=companyId.companyId ? companyId.id: ""#', width : 1},  								
								{ field : "companyId.companyId", title : " Company Id",    width : 100  },
								{ field : "companyId.name", title : "company Name",    width : 100  }									 
								  ]     
            	
                 });
            
			}


		$(document).ready(function(){
			$("#fromDate").kendoDatePicker();
			$("#toDate").kendoDatePicker();
			$("#fystart").kendoDatePicker();
		});
 
		$("#cancelCmp").bind("click", function() { 
			empWindow.content('');
			empWindow.close();	                	 
   		}); 
			 
		$("#saveCmp").bind("click", function() {    				
            		companyId = $("#companyId").val(); 
         			name      = $("#companyName").val(); 
         			address   = $("#address").val();
         			fromDate  = $("#fromDate").val();
         			toDate    = $("#toDate").val();
         			fystart   = $("#fystart").val();
          			 
         			var comp = JSON.stringify({
         	   			"companyId"	  : companyId,
         	   			"name" 	      : name, 
         	   			"address"     : address,
         	   			"fromDate"    : fromDate,
         	   			"toDate"      : toDate,
         	   			"fystart"     : fystart
         			 });   
     				 
         			
    				$.ajax({
    					url 		: "<%=request.getContextPath() + "/do/CreateCompany"%>",
    					type 		: 'POST',
    					dataType 	: 'json',
    					contentType : 'application/json; charset=utf-8',
    					data 		:  comp,
    					success 	:  function(){
    						 $("#cancelCmp").click();
    						 location.reload();
    					},
    					failure : function(e){
    						alert(e.responseText);
    					}
    				});
      });		 
</script>

<div id="imageCropper" style="display:none">	
	<a id="cropImage" class="k-button">OK</a>
	<input type=hidden id="x"/>
	<input type=hidden id="y"/>
	<input type=hidden id="w"/>
	<input type=hidden id="h"/>
	<img  alt="" id="target" src=""/>	
</div>
 
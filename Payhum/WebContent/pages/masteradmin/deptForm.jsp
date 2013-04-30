<%@page import="com.openhr.employee.EmployeeIdUtility"%>
<%@page import="com.openhr.Config"%>
<%Config.readConfig();%>
<div id="employeeForm">
	<div class="clear"></div>
	<div style = "font-size : 30px !important;">Create Department  </div><br/>
	<div class="clear"></div>
	<div id="left-col">
		<div>
			<div class="label" style="width : 135px !important;">Department Id</div>
			<div class="field">
				<input type="text" readonly="readonly" class="k-input k-textbox" id="deptId"  
				value=  "<%= "D-"+ EmployeeIdUtility.DepartmentNextId() %>"  />
			</div>
			<div class="clear"></div>
		</div>


		<div>
			<div class="label" style="width : 135px !important;">Department Name</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="deptName"  value="" />
			</div>
			<div class="clear"></div>
		</div>
 
		<div>
			<div class="label" style="width : 135px !important;">Branch</div>
			<div class="field">
				<select id="branch"></select>
				<!-- <input type="text" required=required class="k-input k-textbox" id="branch"  value="" /> -->
			</div>
			<div class="clear"></div>
		</div><br/>
    
		<div>
			<div class="field">
				<a class="k-button k-icontext" id="saveBranch"><span class="k-add k-icon"></span>Save</a> 
				<a class="k-button k-icontext" id="cancelCmp"><span class="k-cancel k-icon"></span>Cancel</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div></div> 


<script> 

		$(document).ready(function(){
			$("#branch").kendoDropDownList({
				dataTextField : "name",
				dataValueField : "id",
				optionLabel: "Select Branch",
				dataSource : {
					type : "json",
					transport : {
						read : "<%=request.getContextPath() + "/do/ReadBranches"%>"
					}
				}
		    })
		});
		 
		$("#cancelCmp").bind("click", function() { 
			empWindow.content('');
			empWindow.close();	                	 
   		}); 
			 
		$("#saveBranch").bind("click", function() {    				
          			name      = $("#deptName").val(); 
         			branchId  = $("#branch").val();
            			 
         			var dept = JSON.stringify({
         	   			"branchId"	  : branchId,
         	   			"name" 	      : name 
          			 });   
     				
    				$.ajax({
    					url 		: "<%=request.getContextPath() + "/do/CreateDepartment"%>",
    					type 		: 'POST',
    					dataType 	: 'json',
    					contentType : 'application/json; charset=utf-8',
    					data 		:  dept,
    					success 	:  function(){
    						 $("#cancelCmp").click();
    						 
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
 
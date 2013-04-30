<%@page import="com.openhr.employee.EmployeeIdUtility"%>
<%@page import="com.openhr.Config"%>
<%Config.readConfig();%>
<div id="employeeForm">
	<div class="clear"></div>
	<div style = "font-size : 30px !important;">Create Branch  </div><br/>
	<div class="clear"></div>
	<div id="left-col">
 		<div>
			<div class="label" style="width : 135px !important;">BranchId</div>
			<div class="field">
				<input type="text" readonly="readonly" class="k-input k-textbox" id="branchId"  
				value=  "<%= "B-"+ EmployeeIdUtility.BranchNextId() %>"  />
			</div>
			<div class="clear"></div>
		</div>


		<div>
			<div class="label" style="width : 135px !important;">Branch Name</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="branchName"  value="" />
			</div>
			<div class="clear"></div>
		</div>
 
		<div>
			<div class="label" style="width : 135px !important;">Branch Address</div>
			<div class="field">
				<input type="text" required=required class="k-input k-textbox" id="branchAddress"  value="" />
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

		 
		$("#cancelCmp").bind("click", function() { 
 			empWindow.content('');
			empWindow.close();	                	 
   		}); 
			 
		$("#saveBranch").bind("click", function() {    				
             		branchName      = $("#branchName").val(); 
         			branchAddress   = $("#branchAddress").val();
         			var companyId =   $(".k-window-title").text();   
         		 
        		 
         			var branch = JSON.stringify({
         	   			"branchName"	  : branchName,
         	   			"branchAddress"   : branchAddress,
         	   			"companyId"		  : companyId
          			 });   
     				
    				$.ajax({
    					url 		: "<%=request.getContextPath() + "/do/CreateBranch"%>",
    					type 		: 'POST',
    					dataType 	: 'json',
    					contentType : 'application/json; charset=utf-8',
    					data 		:  branch,
    					success 	:  function(){
    						 $("#cancelCmp").click();
    						 /* empDataSource.read();	 */
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
 
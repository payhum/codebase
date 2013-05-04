<%@page import="com.openhr.data.LeaveRequest"%>
<style>
.displayClass {
	display: none;
}
</style>

<%@page import="com.openhr.factories.LeaveTypeFactory"%>
<%@page import="com.openhr.data.LeaveType"%>
<%@page import="com.openhr.data.Users"%>
<%@page import="java.util.List"%>
<%@page import="java.util.*"%>
<div id="leaveTabs">
	<div>
		<div class="k-content">
			<div class="legend">
				<div style="float: right">
					<input type="submit" id="applyLeave" value="New" /> <input
						type="submit" value="Delete" id="deleteLeave" class="displayClass" /> <input
						type="submit" value="Edit" style="display: none !important;" />
				</div>
				<div style="float: left">
					<p>Your Leave History</p>
				</div>
				<div style="clear: both"></div>
			</div>
			<div id="addLeaveDiv" class="displayClass">
				<span><label>Leave From : &nbsp;&nbsp;</label></span> <span><input
					type="text" name="leaveDate" id="leaveFrom" /></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span><label>Leave To : &nbsp;&nbsp;</label></span> <span><input
					type="text" name="returnDate" id="leaveTo" /></span><br /> <br /> <span><label>Leave
						Type :&nbsp;&nbsp; </label></span> <span> <input type="hidden"
					name="noOfDays" value="2" /> <input type="hidden"
					name="employeeId" id="employeeId" /> <input type="hidden"
					id="leaveKinds" value="" /> <input type="hidden" name="userType"
					value="Employee" /> <select name="leaveTypeId" id="TypeOfLeave">
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
				</span> <span><label>Description : &nbsp;&nbsp;</label></span> <span><input
					type="textarea" style="width: 130px !important;" name="description"
					id="description" /></span><br /> <br />
				<div>
					<span>Available : </span>&nbsp;&nbsp;&nbsp; <span id="remaining"></span>
				</div>
				<div style="float: right;">
					<span><input type="submit" id="addLeave" value="Apply" /></span>&nbsp;&nbsp;
					<span><input type="button" id="canceLeave" value="Cancel" /></span>
				</div>
			</div>
			<br /> <br />
			<div id="grid"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
 	function getEmployeeLeaves(){
   		function employeeDropDownEditor(container, options) {
	   		$('<input data-text-field="employeeId" data-value-field="id" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList({
	         	autoBind: false,
	            dataSource: {
	           		type: "json",
	             	transport: {
	                 	read: "<%=request.getContextPath() + "/do/ReadEmployeeAction"%>"
	                }
	            }
	        }); 
	     }
	        
	     function leaveTypeDropDownEditor(container, options) {
	     	$('<input data-text-field="name" data-value-field="id" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList({
	        	autoBind: false,
	           	dataSource: {
	            	type: "json",
	          		transport: {
	                	read: "<%=request.getContextPath() + "/do/ReadLeaveTypeAction"%>"
	                }
	            }
	        }); 
	     }	
	     
  			$("#grid").kendoGrid({
  				dataSource : {
  					transport : {
  						read : {
  	                        url : "<%=request.getContextPath() + "/do/ReadLeaveInfo"%>",
  	                        dataType : 'json',
  	                        cache : false
  	                    },
  	                    parameterMap: function(options, operation) {
  	                        if (operation !== "read" && options.models) {
  	                        	$.each(options.models, function(){
  	                        		/*this.leaveDate = new Date(this.leaveDate);
  	                        		this.returnDate = new Date(this.returnDate);*/
  	                        	});
  	                            return JSON.stringify(options.models);
  	                        }
  	                    }
  					},
  					
  					batch : true,
  	                pageSize : 10
  				},
  				columns: [
  					{ field : "employeeId", title : "Employee Id",  editor : employeeDropDownEditor, template: '#=id ? employeeId.id: ""#', width : 100 },
  					{ title : "Full Name",template: '#=employeeId ? employeeId.firstname +" "+employeeId.middlename +" "+employeeId.lastname: ""#', width : 180 },
  					{ field : "leaveTypeId", title : "Leave Type",  editor : leaveTypeDropDownEditor, template: '#=leaveTypeId ? leaveTypeId.name: ""#', width : 120 },
  					{ field : "leaveDate", title : "Leave Date", template : "#= kendo.toString(new Date(leaveDate), 'MMM, dd yyyy') #", width : 100  },
  		            { field : "returnDate", title : "Return Date", template : "#= kendo.toString(new Date(returnDate) , 'MMM, dd yyyy') #", width : 100 },
  		            { field : "noOfDays", title : "No of Days", width : 100 },
  		            { field : "description", title : "Description", width : 100 },
  		            { field : "status", title : "Status", template : "#= status != 2 ? (status == 0 ? 'New' : 'Approved')  : 'Rejected' #", width : 100 },
   	            ], 
  	            sortable: true,
  	            scrollable: true,
  	            filterable : true,
  	            selectable : "row",
   	            pageable : true
  			});	
  		}
  			
  		$(document).ready(function() {	
    		getEmployeeLeaves();
      		$("#leaveFrom").kendoDatePicker();
    		$("#leaveTo").kendoDatePicker();
  			$("#TypeOfLeave").kendoDropDownList({
				dataTextField : "name",
				dataValueField : "id",
				optionLabel: "Select Leave Type",
				dataSource : {
					type : "json",
					transport : {
						read : "<%=request.getContextPath() + "/do/ReadLeaveTypes"%>"
					}
				}
		    });
      		<%List<LeaveType>	list =  (List<LeaveType>) request.getAttribute("leaveTypes");   
    		LeaveType myleave = null;
         	for (Iterator iterator = list.iterator(); iterator.hasNext();) {  
            	 myleave = (LeaveType)iterator.next();%>
	            leaveTypes = "<%=myleave.getName()%>";
	            leaveValue = "<%=myleave.getId()%>"; 
            <%}%>     
      		employeeId = <%=request.getSession().getAttribute("employeeId")%>;
     		$('#employeeId').val(employeeId);
     		 
     		
 		});
 	
		$('#applyLeave, #canceLeave').click(function(){
			isDisplayed = $("#addLeaveDiv").hasClass("displayClass");
			if(isDisplayed){
				 $("#leaveFrom").val('');
				 $("#leaveTo").val('');
				 $("#description").val('');
				 $("#addLeaveDiv").removeClass("displayClass");
			}
			else{
				 $("#leaveFrom").val('');
				 $("#leaveTo").val('');
				 $("#description").val('');
				$("#addLeaveDiv").addClass("displayClass");
			}
 		});    
	
		$("#addLeave").click(function(){
	 		leaveremain = $('#remaining').text().split('/')[0];
 			 
	 		leaveFrom   = $("#leaveFrom").val();
			leaveTo     = $("#leaveTo").val();
			leaveType   = $("#TypeOfLeave").val();
			description = $("#description").val();
			employeeId  = $("#employeeId").val();
			 
	
			var date1 = new Date(leaveFrom);
			var date2 = new Date(leaveTo);
			var timeDiff = Math.abs(date2.getTime() - date1.getTime());
			var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
			var resultValue = true;
			var flag = 0;
			
			var current = new Date();
			var currentDate = (current.getMonth()+1) + '/' + current.getDate() + '/' + current.getFullYear();
			var fromDate = (date1.getMonth()+1) + '/' + date1.getDate() + '/' + date1.getFullYear();
 			
			if(currentDate == fromDate){
				flag = 1;
				alert('Leave From Should not be Todays Date');
			}
			
			if(date1.getDate() < current.getDate()){
 				if(date1.getMonth() <= current.getMonth()){
	 				flag = 1;
					alert('Leave From Should not be Lessthan CurrentDate');
				}
			}
			
			if(leaveType == ""){
				flag = 1;
				alert('LeaveType Should not be Empty');
			}
			
			
			if(date2.getTime() < date1.getTime()){
				flag = 1;
				alert('LeaveTo Should be Greaterthan LeaveFrom');
			}
			
			
			if((diffDays > leaveremain) && (flag != 1)){
				resultValue = confirm("Requested number of days is not available. If you proceed there will be loss of pay.");
			}
			
	   		leaveApply = JSON.stringify({
	   			"leaveFrom"	  : leaveFrom,
	   			"leaveTo" 	  : leaveTo, 
	   			"leaveType"   : leaveType, 
	   			"description" : description,
	   			"employeeId"  : employeeId
	   		}); 
	   		
	   		if((resultValue && flag == 0)){
    	
		   		$.ajax({
		       		url 		: "<%=request.getContextPath() + "/do/LeaveApplicationAction"%>",
		       		type 		: 'POST',
					dataType 	: 'json',
					contentType : 'application/json; charset=utf-8',
					data 		: leaveApply,
					success     : function(data){
					    $("#leaveFrom").val('');
						$("#leaveTo").val('');
		 				$("#description").val('');
	 					$("#addLeaveDiv").addClass("displayClass");
						$("#grid").empty();
						getEmployeeLeaves();
	 				}
		        });  
	   		}
	   		else if(flag != 1){
	   			 
	   			isDisplayed = $("#addLeaveDiv").hasClass("displayClass");
				if(isDisplayed){
					 $("#leaveFrom").val('');
					 $("#leaveTo").val('');
					 $("#description").val('');
					 $("#addLeaveDiv").removeClass("displayClass");
				}
				else{
					 $("#leaveFrom").val('');
					 $("#leaveTo").val('');
					 $("#description").val('');
					$("#addLeaveDiv").addClass("displayClass");
				}
	   		}
		});
		
		  $("#grid").delegate(".k-grid-content", "click", function(e){
			var containClass = $("#deleteLeave").hasClass("displayClass");
			if(containClass){
				$("#deleteLeave").removeClass("displayClass");
			}
			else{
				$("#deleteLeave").addClass("displayClass");
			}
			
		 });
	
		$("#deleteLeave").click(function(){
			 employeeId = $(".k-state-selected").find('td').eq(0).text();
			 leaveDate  = $(".k-state-selected").find('td').eq(3).text();
			 status1 = $(".k-state-selected").find('td').eq(7).text();
 	 	  	 deleteLeaveAction = JSON.stringify({
		   		"employeeId"	  : employeeId,
		   		"leaveDate" 	  : leaveDate 
	 	 	 }); 
 	 	  	 
		     if(status1 == "New"){	
			  	 $.ajax({
			   		url 		: "<%=request.getContextPath() + "/do/DeleteLeaveApplication"%>",
			    	type 		: 'POST',
					dataType 	: 'json',
					contentType : 'application/json; charset=utf-8',
					data 		: deleteLeaveAction,
					success     : function(){
		 				$("#grid").empty();
						getEmployeeLeaves();
					}
			 	 }); 
		     }
		     else{
		    	alert("Approved/Rejected Leaves cannot be deleted.");
	 	     }
 	  	});
	
		$("#TypeOfLeave").change(function(){
  				var leaveId = $("#TypeOfLeave").val(); 
 				leavetype = JSON.stringify({
		   			"leaveTypeId"	   : $("#TypeOfLeave").val(),
		    		 "employeeId"  : $('#employeeId').val()
		   		}); 
				
 				$.ajax({
		       		url 		: "<%=request.getContextPath() + "/do/CheckLeaveAvail"%>",
			type : 'POST',
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			data : leavetype,
			success : function(data) {
				available = data[0] + '/' + data[1]
				$('#remaining').text('' + available);
			},
			error : function() {
				alert('error');
			}
		});
	});
</script>



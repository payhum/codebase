<style>
.displayClass{
	display : none;
}
</style>

<%@page import="com.openhr.factories.LeaveTypeFactory"%>
<%@page import="com.openhr.data.LeaveType" %>
<%@page import="com.openhr.data.Users" %>
<%@page import="java.util.List" %>
<%@page import="java.util.*" %>
<div id="leaveTabs">
	<ul> <li class="k-state-active">Make Request</li> </ul>
 	<div>
 		<div class="k-content">
 			<div class="legend">
 				<div style="float:right">
					<input type="submit" id="applyLeave" value="New"/>
					<input type="submit" value="Delete" id="deleteLeave"/>
					<input type="submit" value="Edit" style="display:none !important;"/>
				</div>
 				<div style="float:left">
					<p>Your Leave History</p>
				</div>
				<div style="clear:both"></div><br/>
			</div><br/><br/>
 			<div id="addLeaveDiv" class="displayClass">
 					<span><label>Leave From : &nbsp;&nbsp;</label></span>
					<span><input type="text" name="leaveDate" id="leaveFrom" /></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 					<span><label>Leave To : &nbsp;&nbsp;</label></span>
					<span><input type="text" name="returnDate" id="leaveTo" /></span><br/><br/>
					<span><label>Leave Type :&nbsp;&nbsp; </label></span>
 					<span>
 						<input type="hidden" name="noOfDays" value="2"/>
 						<input type="hidden" name="employeeId" id="employeeId" />
						<input type="hidden" id="leaveKinds" value="" />
						<input type="hidden" name="userType" value="Employee"/>
						<select name="leaveTypeId" id="TypeOfLeave"> </select>&nbsp;&nbsp;&nbsp;&nbsp;
  					</span>
 					<span><label>Description : &nbsp;&nbsp;</label></span>
					<span><input type="textarea" style="width:130px !important;" name="description" id="description" /></span><br/><br/>
  					<div style="float:right;">
	  					<span><input type="submit" id="addLeave" value="Apply"/></span>&nbsp;&nbsp;
						<span><input type="button" id="canceLeave" value="Cancel"/></span>
 					</div>
  			</div><br/><br/>
  			<div id="grid"> </div>
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
	       		
   			
  			var leaveApprovalModel = kendo.data.Model.define({
  	        	id: "id",            
  	            fields: {
  	            	leaveDate : {
  	            		type : "date"
  	            	},
  	            	returnDate : {
  	            		type : "date"
  	            	},
  	            	noOfDays : {
  	            		type : "number"
  	            	},
  	        		status : {
  	        			type : "string"
  	        		},
  	                employeeId:{
  	        			defaultValue : {
  	        				id : 0,
  	        				employeeId : "",
  	        				firstname : "",
  	        				middlename : "",
  	        				lastname : "",        				
  	        				sex : "",
  	        				hiredate : "",
  	        				birthdate : "",
  	        				positionId : {
  	        					defaultValue : {
  	        						id : 0,
  	        						name : "",
  	        						salary : 0,
  	        						raisePerYear : 0
  	        					}
  	        				},
  	        				photo : "",
  	        			}
  	        		},
  	        		leaveTypeId: {
  	                	defaultValue : {
  	                		id:  0,
  	                		name: "",
  	                        dayCap: 0 
  	                	}
  	                },
  	                description : {
  	                	type : "string"
  	                }
  	            }
  	        });		
  			
  			$("#grid").kendoGrid({
  				dataSource : {
  					transport : {
  						read : {
  	                        url : "<%=request.getContextPath() + "/do/ReadRequestedAction"%>",
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
  					schema : {
  						model :leaveApprovalModel
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
  		            { field : "status", title : "Status", template : "#= status == 0 ? 'New' : 'Accepted' #", width : 100 },
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
     	<% List<LeaveType>	list =  (List<LeaveType>) request.getAttribute("leaveTypes");   
    	 LeaveType myleave = null;
         for (Iterator iterator = list.iterator(); iterator.hasNext();) {  
             myleave = (LeaveType)iterator.next();  
         %>
            leaveTypes = "<%=myleave.getName()%>";
            leaveValue = "<%=myleave.getId()%>"; 
            $('#TypeOfLeave').append('<option name='+leaveTypes+' value='+leaveValue+'>'+leaveTypes+'</option>');
          <% } %>     
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
 
		leaveFrom   = $("#leaveFrom").val();
		leaveTo     = $("#leaveTo").val();
		leaveType   = $("#TypeOfLeave").val();
		description = $("#description").val();
		employeeId  = $("#employeeId").val();
 		 
   		leaveApply = JSON.stringify({
   			"leaveFrom"	  : leaveFrom,
   			"leaveTo" 	  : leaveTo, 
   			"leaveType"   : leaveType, 
   			"description" : description,
   			"employeeId"  : employeeId
   		}); 
    	
   		$.ajax({
       		url 		: "<%=request.getContextPath() + "/do/LeaveApplicationAction"%>",
       		type 		: 'POST',
			dataType 	: 'json',
			contentType : 'application/json; charset=utf-8',
			data 		: leaveApply,
			success     : function(){
			    $("#leaveFrom").val('');
				$("#leaveTo").val('');
 				$("#description").val('');
 			 		 
				$("#addLeaveDiv").addClass("displayClass");
				$("#grid").empty();
				getEmployeeLeaves();
			}
        });  
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
	
	
    
</script>

<%@include file="../../common/jspHeader.jsp"%>
<h2 class="legend">Approve Leaves</h2>

<div id="grid"></div>

<script>

	function getLeaveRequests(){
		 
		
		$("#grid").kendoGrid({
			dataSource : {
				transport : {
					read : {
                        url : "<%=request.getContextPath() + "/do/ReadRequestedLeaveAction"%>",
                        dataType : 'json',
                        cache : false
                    },
                    parameterMap: function(options, operation) {
                        if (operation !== "read" && options.models) {
                        	$.each(options.models, function(){
                        	 
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
				{ title : "Full name",template: '#=employeeId ? employeeId.firstname +" "+employeeId.middlename +" "+employeeId.lastname: ""#', width : 180 },
				{ field : "leaveTypeId", title : "Leave Type",  editor : leaveTypeDropDownEditor, template: '#=leaveTypeId ? leaveTypeId.name: ""#', width : 120 },
				{ field : "leaveDate", title : "Leave date", template : "#= kendo.toString(new Date(leaveDate), 'MMM, dd yyyy') #", width : 100  },
	            { field : "returnDate", title : "Return date", template : "#= kendo.toString(new Date(returnDate) , 'MMM, dd yyyy') #", width : 100 },
	            { field : "noOfDays", title : "No of days", width : 100 },
	            { field : "description", title : "Description", width : 100 },
	            { field : "status", title : "Status", template : "#= status == 0 ? 'New' : 'In Process' #", width : 100 },
	            {
                    command: [{name : "approve", text: "Approve", className: "approve"}, {name : "reject", text : "Reject", className: "reject" }], width:200, filterable:false
                }
            ], 
            sortable: true,
            scrollable: true,
            filterable : true,
            selectable : "row",
            /*resizable : true,*/
            pageable : true
		});	
		
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
	}
	
	
	$(document).ready(function(){
 		getLeaveRequests();
	});
		
 		
		
		 $("#grid").delegate(".approve", "click", function(e) {
 			employeeId = $('.k-state-selected').find('td').eq(0).text();
 			leaveDate = $('.k-state-selected').find('td').eq(3).text();
        	leaveId = employeeId;
       		approverId = "<%=(request.getSession().getAttribute("employeeId")!=null ? request.getSession().getAttribute("employeeId") : "")%>";
        	approvedByDate = new Date().getTime(); 
       		approvalData = JSON.stringify({"approvedbydate" : approvedByDate, "approverId" : approverId, "requestId" : leaveId, "leaveDate" : leaveDate, "status" : "1"}); 
        	 
       		if(approverId!=''){
 	       		 $.ajax({
	       		  	url 		: "<%=request.getContextPath() + "/do/ApproveLeaveAction"%>",
	       			type 		: 'POST',
					dataType 	: 'json',
					contentType : 'application/json; charset=utf-8',
					data 		: approvalData,
					success 	: function(){
						$("#grid").empty();
						getLeaveRequests();
					}
	       		 });  
       		}
       		else {
     			document.body = "<div style='border:1px solid #f00; height : 60px; width : 70px;font-size:24px;'>Browser Session has expired</div>";
       			alert("Your browser's session have expired!\nPlease login again to continue");
     			window.location.href = "<%=request.getContextPath()+"/do/LogoutAction"%>";
   			} 
       			
        });  
		 
		 $("#grid").delegate(".reject", "click", function(e) {
	 			employeeId = $('.k-state-selected').find('td').eq(0).text();
	 			leaveDate = $('.k-state-selected').find('td').eq(3).text();
	        	leaveId = employeeId;
	       		var approverId = "<%=(request.getSession().getAttribute("employeeId")!=null ? request.getSession().getAttribute("employeeId") : "")%>";
	       		var approvedByDate = new Date().getTime(); 
	       		var rejectData = JSON.stringify({"approvedbydate" : approvedByDate, "approverId" : approverId, "requestId" : leaveId, "leaveDate" : leaveDate, "status" : "2"}); 
	        	 
	       		if(approverId!=''){
	 	       		 $.ajax({
		       		  	url 		: "<%=request.getContextPath() + "/do/ApproveLeaveAction"%>",
		       			type 		: 'POST',
						dataType 	: 'json',
						contentType : 'application/json; charset=utf-8',
						data 		: rejectData,
						success 	: function(){
							$("#grid").empty();
							getLeaveRequests();
						}
		       		 });  
	       		}
	       		else {
	     			document.body = "<div style='border:1px solid #f00; height : 60px; width : 70px;font-size:24px;'>Browser Session has expired</div>";
	       			alert("Your browser's session have expired!\nPlease login again to continue");
	     			window.location.href = "<%=request.getContextPath()+"/do/LogoutAction"%>";
	   			} 
	       			
	        });  
		
	 
		
</script>
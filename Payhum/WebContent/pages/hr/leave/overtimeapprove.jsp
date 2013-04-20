<%@include file="../../common/jspHeader.jsp" %>
<h2 class="legend">Approve Leave form</h2>

<div id="grid"> </div>

<script>

	function getLeaveRequests(){
		var leaveApprovalModel = kendo.data.Model.define({
        	id: "id",            
            fields: {
            	overTimeDate : {
              		type : "date"
              	},
              	 
              	noOfHours : {
              		type : "String"
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
        		}
        		 
            }
        });		
		
		$("#grid").kendoGrid({
			dataSource : {
				transport : {
					read : {
                        url : "<%=request.getContextPath() + "/do/ReadOverTimeInfo"%>",
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
				{ title : "Full name",template: '#=employeeId ? employeeId.firstname +" "+employeeId.middlename +" "+employeeId.lastname: ""#', width : 180 },
 				{ field : "overTimeDate", title : "Requested Date", template : "#= kendo.toString(new Date(overTimeDate), 'MMM, dd yyyy') #", width : 100  },
 	            { field : "noOfHours", title : "Hours", width : 100 },
 	            { field : "status", title : "Status", template : "#= status == 0 ? 'Pending' : 'Approved' #", width : 100 },
              	{
                    command: [{name : "approve", text: "Approve", className: "approve"}, {name : "reject", text : "Reject", className: "approve" }], width:200, filterable:false
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
 			requestedDate = $('.k-state-selected').find('td').eq(2).text();
        	approverId = "<%=(request.getSession().getAttribute("employeeId")!=null ? request.getSession().getAttribute("employeeId") : "")%>";
        	approveOverTime = JSON.stringify({"approverId" : approverId, "requestedDate" : requestedDate, "employeeId" : employeeId }); 
        	 
       		if(approverId!=''){
 	       		 $.ajax({
	       		  	url 		: "<%=request.getContextPath() + "/do/ApproveOverTimeAction"%>",
	       			type 		: 'POST',
					dataType 	: 'json',
					contentType : 'application/json; charset=utf-8',
					data 		: approveOverTime,
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
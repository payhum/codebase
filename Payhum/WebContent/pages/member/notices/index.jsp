<div>
	<div id="side_bar" style="float: right; padding: 20px; width: 300px;">
		<h2 style="font-size: 16px;">Message Notice Board General Summary</h2><br/><br/>
		<p style="height: 300px; width:286px; overflow: auto;">Welcome to the Employee
			review page. User can do actions related like View Notices, Holidays,
			Apply for Leaves, Register the Overtime details, Income and Deduction
			declarations and also change the user settings like password.</p>
	</div>

	<div id="messages" style="padding: 20px; width: 460px; float: left">
		<div class="legend" style="padding-right: 10px; text-align: right">

			<div style="float: right">
				<input type="button" value="Hide" id="hideMsg"
					style="display: none;" /> <label>Order by :</label> <select
					id="type" onchange="test();">

					<option selected>Overtime</option>
					<option>Leave</option>

				</select>
			</div>

			<div style="float: left">
				<p>Messages on Notice Board</p>
			</div>
			<div style="clear: both"></div>

		</div>
		<br />
		<br />
		<div id="overtimegrid"></div>
		<div id="leavegrid"></div>

	</div>
	<div style="clear: both"></div>
</div>


<script>
	<%
		request.setAttribute("test","test");
	%>
  
	function getEmployeeLeaves1(){
   		
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
       		
		 
			
			$("#overtimegrid").kendoGrid({
				dataSource : {
					transport : {
						read : {
	                        url : "<%=request.getContextPath() + "/do/ReadNoticeAction"%>",
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
						{ field : "status", title : "Status", template : "#= status != 2 ? (status == 0 ? 'Pending' : 'Approved')  : 'Rejected' #", width : 100 },
				 		{ field : "overTimeDate", title : "Overtime Date", template : "#= kendo.toString(new Date(overTimeDate), 'MMM, dd yyyy') #", width : 100  },
					  	{ field : "ApprovedDate", title : "Approved Date", template : "#= status != 2 ? (status == 0 ? '-' :  kendo.toString(new Date(approvedDate), 'MMM, dd yyyy') )  :  kendo.toString(new Date(approvedDate), 'MMM, dd yyyy')  #", width : 100 },
  	            ], 
	            sortable: true,
	            scrollable: true,
	            filterable : true,
	            selectable : "row",
	            pageable : true
			});	
		}
	
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
	 
	function getLeaves(){
 			
			$("#leavegrid").kendoGrid({
				dataSource : {
					transport : {
						read : {
	                        url : "<%=request.getContextPath() + "/do/ReadApprovedLeaveAction"%>",
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
					{ field : "status", title : "Status", template : "#= status != 2 ? (status == 0 ? 'Pending' : 'Approved')  : 'Rejected' #", width : 100 },
					{ field : "leaveDate", title : "Leave Date", template : "#= kendo.toString(new Date(leaveDate), 'MMM, dd yyyy') #", width : 100  },
  		            { field : "returnDate", title : "Return Date", template : "#= kendo.toString(new Date(returnDate) , 'MMM, dd yyyy') #", width : 100 },
  		           
					], 
	            sortable: true,
	            scrollable: true,
	            filterable : true,
	            selectable : "row",
	            pageable : true
			});	
		}

 
	
	
	
	
		function test(){
			<%
			request.getSession().setAttribute("context", "notice");
			%>
			   type = $("#type").val();
				if(type == "Leave"){
					$("#overtimegrid").empty();
					$("#overtimegrid").attr('style','display:none;');
					$("#leavegrid").attr('style','display:block;');
					getLeaves();
				}
				else{
					$("#leavegrid").empty();
					$("#leavegrid").attr('style','display:none;');
					$("#overtimegrid").attr('style','display:block;');
					getEmployeeLeaves1();
				}
 		}
	
	 
		$(document).ready(function() {		
			getEmployeeLeaves1();
			
		});
 
  
</script>
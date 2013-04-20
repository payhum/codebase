<div>
	<div id="side_bar" style="float:right;padding:20px;width:300px;">
		<h2 style="font-size:16px;">Message Notice Board General Summary</h2>
    	<p style="height:300px; overflow:auto;">Welcome to the Employee review page. 
    	User can do actions related like View Notices, Holidays, Apply for Leaves, Register the Overtime details,
    	Income and Deduction declarations and also change the user settings like password.
    	</p>
	</div>

	<div id="messages" style="padding:20px;width:460px;float:left">
		<div class="legend" style="padding-right:10px; text-align:right">
		
			<div style="float:right">
				<input type="button" value="Hide" id="hideMsg" style="display:none;"/>
				<label>Order by :</label>
				<select id="type" onchange="test();">
				 
					<option>
						Leave
					</option>
					<option selected>
						Overtime
					</option>
				</select>			
			</div>
			
			<div style="float:left">
				<p>Messages on Notice Board</p>		
			</div>
			<div style="clear:both"></div>
		
		</div><br/><br/>
 		<div id="grid2"> </div><br/>
		<div id="grid3"></div>
 		 
	</div>
	<div style="clear:both"></div>
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
       		
			
			var leaveApprovalModel = kendo.data.Model.define({
	        	id: "id",   
	        	fields: {
		        	status : {
	        			type : "int"
	        		},
	        		
	            	overTimeDate : {
	            		type : "date"
	            	},
	            	ApprovedDate : {
	            		type : "date"
	            	},
				}
	        });		
			
			$("#grid2").kendoGrid({
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
					schema : {
						model :leaveApprovalModel
					},
					batch : true,
	                pageSize : 10
				},
				columns: [
						{ field : "status", title : "Status", template : "Overtime", width : 100 },
				 		{ field : "overTimeDate", title : "Overtime Date", template : "#= kendo.toString(new Date(overTimeDate), 'MMM, dd yyyy') #", width : 100  },
					  	{ field : "ApprovedDate", title : "Approved Date", template : "#=  kendo.toString(new Date(approvedDate), 'MMM, dd yyyy')  #", width : 100 },
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
   		
		var leaveApprovalModel1 = kendo.data.Model.define({
	        	id: "id",            
	            fields: {
	            	status : {
	        			type : "int"
	        		},
        			approvedbydate :{
        				type : "date"
        			},
	            	requestId:{
  	        			defaultValue : {
  	        				id : 0,
  	        				leaveDate : "",
  	        				returnDate : "",
  	        				status : "",
  	        				noOfDays : "",        				
  	        				description : "",
  	        				 
  	        				employeeId : {
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
  	    	        				}
  	        				},
  	        				leaveTypeId: {
  	    	                	defaultValue : {
  	    	                		id:  0,
  	    	                		name: "",
  	    	                        dayCap: 0 
  	    	                	}
  	    	                },
  	        			}
  	        			},
  	        		},
	           },
	           
	        });		
			
			$("#grid3").kendoGrid({
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
					schema : {
						model :leaveApprovalModel1
					},
					batch : true,
	                pageSize : 10
				},
				columns: [
				          
					{ field : "status", title : "Status", template : "Leave", width : 100 },
					{ field : "requestId", title : "Approved Date",   template: '#=id ?  kendo.toString(new Date(requestId.returnDate), "MMM, dd yyyy"): ""#', width : 100 },	  
					{ field : "requestId", title : "Leave Date",   template: '#=id ?  kendo.toString(new Date(requestId.leaveDate), "MMM, dd yyyy"): ""#', width : 100 },	           
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
					$("#grid2").empty();
					$("#grid2").attr('style','display:none;');
					$("#grid3").attr('style','display:blcok;');
					getLeaves();
				}
				else{
					$("#grid3").empty();
					$("#grid3").attr('style','display:none;');
					$("#grid2").attr('style','display:block;');
					getEmployeeLeaves1();
				}
 		}
	
	 
		$(document).ready(function() {		
			getEmployeeLeaves1();
			
		});
 
  
</script>
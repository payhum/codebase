<style>
.displayClass {
	display: none;
}
</style>

<div id="benefitTabs">
	<div>
		<div class="k-content">
			<div class="legend">
				<div style="float: right">
					<input type="submit" value="New" id="addOverTime" /> <input
						type="submit" id="deleteOverTime" value="Delete" /> <input
						type="submit" value="Edit" style="display: none !important;" />
				</div>

				<div style="float: left">
					<p>Your Overtime History</p>
				</div>
				<div style="clear: both"></div>
			</div>
			<div id="addOverTimeDiv" class="displayClass">
				<span><label>Request Date : &nbsp;&nbsp;</label></span> <span><input
					type="text" name="leaveDate" id="requestOnDate" /></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span><label>Hours : &nbsp;&nbsp;</label></span> <span><input
					type="text" name="noofhours" id="noOfHours" /></span><br /> <br />
				<div style="float: right;">
					<span><input type="submit" id="overTimeReq" value="Apply" /></span>&nbsp;&nbsp;
					<span><input type="button" id="canceOverTime" value="Cancel" /></span>
				</div>
			</div>
			<br /> <br />
			<div id="grid1"></div>
		</div>
	</div>
</div>
<script>
	
function getEmployeeOverTimes(){
  	
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
   		
		 
		$("#grid1").kendoGrid({
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
                      		 
                      	});
                          return JSON.stringify(options.models);
                      }
                  }
				},
				
			  batch : true,
              pageSize : 10
			},
			columns: [
 				{ field : "overTimeDate", title : "Requested Date", template : "#= kendo.toString(new Date(overTimeDate), 'MMM, dd yyyy') #", width : 100  },
 	            { field : "noOfHours", title : "Hours", width : 100 },
		        { field : "status", title : "Status", template : "#= status != 2 ? (status == 0 ? 'New' : 'Approved')  : 'Rejected' #", width : 100 },
 	           	{ field : "approvedDate", title : "Processed Date", template : "#= approvedBy == 'PayHum' ? '-' : kendo.toString(new Date(approvedDate), 'MMM, dd yyyy') #", width : 100 },
             ], 
          sortable: true,
          scrollable: true,
          filterable : true,
          selectable : "row",
           pageable : true
		});	
	}



	$(document).ready(function() {
 		getEmployeeOverTimes();
 		$("#requestOnDate").kendoDatePicker();
		 
	});
 	
	$("#addOverTime, #canceOverTime").click(function(){
		isDisplayed = $("#addOverTimeDiv").hasClass("displayClass");
		if(isDisplayed){
			 $("#addOverTimeDiv").removeClass("displayClass");
			 $("#noOfHours").val('');
			 $("#requestOnDate").val('');
		}
		else{
			$("#addOverTimeDiv").addClass("displayClass");
			$("#noOfHours").val('');
			 $("#requestOnDate").val('');
		}
	});
	
	
	$("#overTimeReq").click(function(){
 		requestOnDate   = $("#requestOnDate").val();
		noOfHours     	= $("#noOfHours").val();
		employeeId 		= <%=request.getSession().getAttribute("employeeId")%>;
		 
   		applyOverTime = JSON.stringify({
   			"requestOnDate"	  : requestOnDate,
   			"noOfHours" 	  : noOfHours,
   			"employeeId"	  : employeeId
    	}); 
    	
   		$.ajax({
       		url 		: "<%=request.getContextPath() + "/do/ApplyOverTime"%>",
       		type 		: 'POST',
			dataType 	: 'json',
			contentType : 'application/json; charset=utf-8',
			data 		: applyOverTime,
			success     : function(){
				$("#addOverTimeDiv").addClass("displayClass");
			    $("#grid1").empty();
				getEmployeeOverTimes();  
			}
        });  
	});
	
	$("#deleteOverTime").click(function(){
		 employeeId 	= <%=request.getSession().getAttribute("employeeId")%>;
		 requestOnDate  = $(".k-state-selected").find('td').eq(0).text();
		 status1 = $(".k-state-selected").find('td').eq(2).text();
		 deleteOverTime = JSON.stringify({
	   		"employeeId"	  : employeeId,
	   		"requestOnDate"   : requestOnDate 
 	 	 }); 
	    
  	  	if(status1 == "New"){
		  	 $.ajax({
		   		url 		: "<%=request.getContextPath() + "/do/DeleteOverTime"%>",
				type : 'POST',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				data : deleteOverTime,
				success : function() {
					$("#grid1").empty();
					getEmployeeOverTimes();
				}
			});
		} else {
			alert("Approved/Rejected Overtime records cannot be deleted.");
		}
	});
</script>

<script id="searchByNameAutoComplete" type="text/x-kendo-tmpl">
 	<div class="autoCompleteDIV">
		<img width=50 height=50 class='image_preview'  src="#='/OpenHR'+photo#"/> 
		<b>#=employeeId+' - '+firstname+' '+middlename+' '+lastname#</b>
	</div>
</script>
<style>
div.autoCompleteDIV {
	vertical-align: middle;
	display: block;
}
</style>
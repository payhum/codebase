<div id="leaveTabs">
	<ul>
		<li class="k-state-active">Make Request</li>		
	</ul>

	

	<div>

		<div class="k-content">

			<div class="legend">
			
				<div style="float:right">
					<input type="submit" value="New"/>
					<input type="submit" value="Delete"/>
					<input type="submit" value="Edit"/>
				</div>
			
				<div style="float:left">
					<p>Your Leave History</p>
				</div>
				<div style="clear:both"></div>
			</div>
			<div id="pendingGrid">
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
    var leaveDateStart, leaveDateEnd, noOfDays, employeeId, leaveTypeId;
    $(document).ready(function() {						
        $("#leaveTabs").kendoTabStrip({
            animation : {
                open : {
                    effects : "fadeIn"
                }
            }
        });
						
        employeeId = $("#empId").kendoAutoComplete({							
            autoBind: false,
            dataTextField: "employeeId", 
            dataSource: {
                type: "json",
                transport: {
                    read: "<%=request.getContextPath() + "/do/ReadEmployeeAction"%>"
				}
			}
		}).data('kendoAutoComplete');
        
        
        leaveTypeId  = $("#leaveType").kendoDropDownList({
        	autoBind: false,
            dataTextField: "name", 
            dataValueField: "id",
            dataSource: { 
            	type: "json",
                transport: {
                    read: "<%=request.getContextPath() + "/do/ReadLeaveTypeAction"%>"
				}
			}
        }).data("kendoDropDownList");
        
		leaveDateStart = $("#leaveDate").kendoDatePicker({
			min : new Date(),
			value : new Date()
		}).data("kendoDatePicker");

		leaveDateEnd = $("#returnDate").kendoDatePicker({
			min : new Date()
		}).data("kendoDatePicker");

		noOfDays = $("#noOfDays").kendoNumericTextBox({
			min : 1,
			value : 1,
			max : 20,
			step : 1,
			format : "# days",
			change : function(e) {
				var leaveDate = new Date(leaveDateStart.value());
				var returnDate = leaveDate;
				if (leaveDate.getDay() == 0 || leaveDate.getDay() == 6) {
					alert("please select a working day as leave day");
					return;
				}
				//returnDate.setDate(leaveDate.getDate() + noOfDays.value());									
		
				var returnDateBy = returnDate;
				var temp = returnDateBy;
				console.log("My Leave date " + temp);
				for ( var dayCount = 1; dayCount <= noOfDays.value(); dayCount++) {
					temp.setDate(temp.getDate() + 1);
					returnDateBy = temp; 
					if (returnDateBy.getDay() == 6) {
						temp.setDate(temp.getDate() + 2);
					}	
				}
				leaveDateEnd.value(kendo.toString(returnDateBy, 'MM/dd/yyyy'));
			}
		}).data('kendoNumericTextBox');

		$("#leaveTabs").kendoTabStrip({
			animation : {
				open : {
					effects : "fadeIn"
				}
			}
		});

		$("#saveLeaveReq").bind("click",function(e) {
			var empId = employeeId.value();
			var leaveType = leaveTypeId.value(); 
			var leaveDate = new Date($("#leaveDate").val()).getTime();
			var noOfDays = $("#noOfDays").val();
			var returnDate = new Date($("#returnDate").val()).getTime();
			var description = $("#description").val();

			var JSONData = JSON.stringify([{
				"employeeId" : empId,
				"leaveTypeId" : leaveType,
				"leaveDate" : leaveDate,
				"noOfDays" : noOfDays,
				"returnDate" : returnDate,
				"description" : description
			}]);
			
			$.ajax({
				url : "http://localhost:8080/OpenHR/do/LeaveApplicationAction",
				type : "POST",
				dataType : "json",
				contentType : 'application/json; charset=utf-8',
				data : JSONData,
				error : function(response) {
					alert(response.responseText);
				}
			});
		});	
	
	});
</script>

<style>
.displayClass{
	display : none;
}
</style>

<%@page import="com.openhr.factories.LeaveTypeFactory"%>
<%@page import="com.openhr.data.Leave" %>
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
					<input type="submit" value="Delete"/>
					<input type="submit" value="Edit"/>
				</div>
 				<div style="float:left">
					<p>Your Leave History</p>
				</div>
				<div style="clear:both"></div><br/>
			</div><br/><br/>
 			<div id="addLeaveDiv" class="displayClass">
				<form action="/Payhum/do/LeaveApplicationAction">
					<span><label>LeaveFrom : &nbsp;&nbsp;</label></span>
					<span><input type="text" name="leaveDate" id="leaveFrom" value="2012/07/10" /></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 					<span><label>LeaveFrom : &nbsp;&nbsp;</label></span>
					<span><input type="text" name="returnDate" id="leaveTo" value="2012/08/11"/></span><br/><br/>
					<span><label>LeaveType :&nbsp;&nbsp; </label></span>
 					<span>
 						<input type="hidden" name="noOfDays" value="2"/>
 						<input type="hidden" name="employeeId" id="employeeId" />
						<input type="hidden" id="leaveKinds" value="" />
						<select name="leaveTypeId" id="TypeOfLeave"> </select>&nbsp;&nbsp;&nbsp;&nbsp;
  					</span>
 					<span><label>LeaveDescription : &nbsp;&nbsp;</label></span>
					<span><input type="textarea" style="width:130px !important;" name="description" id="description" /></span><br/><br/>
  					<div style="float:right;">
	  					<span><input type="submit" id="addLeave" value="Apply"/></span>&nbsp;&nbsp;
						<span><input type="button" id="canceLeave" value="Cancel"/></span>
 					</div>
 				</form>
 			</div><br/><br/>
			<div id="pendingGrid">
				<table id="loansGrid">
 				<thead>
					<tr>
						<th data-field="requestDate">Requested On Date</th>
						<th data-field="requestedLeavingDate">Request Benefit Type</th>
						<th data-field="requestedReturningDate">Amount</th>
						<th data-field="status">Status</th>
						<th data-field="approvedBy">Approved Date</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>11/07/2012</td>
						<td>Overtime Payment</td>
						<td>$ 250</td>
						<td>Pending</td>
						<td>-</td>
					</tr>
					<tr>
						<td>11/07/2012</td>
						<td>Transport Allowance</td>
						<td>$ 150</td>
						<td>Approved</td>
						<td>13/01/2012</td>
					</tr>
 				</tbody>
			</table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

  	$(document).ready(function() {	
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
 	
	$('#applyLeave').click(function(){
		isDisplayed = $("#addLeaveDiv").hasClass("displayClass");
		if(isDisplayed){
			 $("#addLeaveDiv").removeClass("displayClass");
		}
		else{
			$("#addLeaveDiv").addClass("displayClass");
		}
 	});    
    
</script>

<div>
	<div id="side_bar" style="float:right;padding:20px;width:300px;">
		<h2 style="font-size:16px;">Message Notice Board General Summary</h2>
    	<p style="height:300px; overflow:auto;">Welcome to the Employee review page. 
    	User can do actions related like View Notices, Apply for Leaves, Register the Overtime details,
    	Request for Loans and also change the user settings like password.
    	
    	More features will be added soon.</p>
	</div>

	<div id="messages" style="padding:20px;width:460px;float:left">
		<div class="legend" style="padding-right:10px; text-align:right">
		
			<div style="float:right">
				<input type="button" value="Hide" id="hideMsg"/>
				<label>Order by :</label>
				<select>
				
					<option>
						New First
					</option>
					<option>
						Important First
					</option>
				</select>			
			</div>
			
			<div style="float:left">
				<p>Messages on Notice Board</p>		
			</div>
			<div style="clear:both"></div>
		
		</div>
		
		<table id="messagesGrid">
			<thead>
				<tr>
					<th data-field="messageType">Message On</th>
					<th data-field="content">Message Content</th>
					<th data-field="content">Date</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>
				<tr>
					<td>Leave</td>
					<td>Your request for <strong>Leave</strong> has been approved
						you can contact your...
					</td>
					<td>Jan 01, 2012</td>
				</tr>				
			</tbody>
		</table>
	</div>
	<div style="clear:both"></div>
</div>
	

<script>
$(document).ready(function(){
	$("#messagesGrid").kendoGrid({
		scrollable :  true,
		selectable : "multiple, row", 
		sortable : true,
		height : 300
	});
	
	
});
</script>